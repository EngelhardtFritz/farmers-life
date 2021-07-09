package com.asheriit.asheriitsfarmerslife.objects.blocks.machines;

import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import com.asheriit.asheriitsfarmerslife.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class DryingBedBlock extends Block {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
    public static final BooleanProperty FILLED = ModBlockStateProperties.FILLED;
    private static final VoxelShape SHAPE = Block.makeCuboidShape(0,0,0,16,6,16);
    private static final int MAX_AGE = 3;

    public DryingBedBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(AGE, 0).with(FILLED, false));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        final boolean filled = state.get(FILLED);
        final int age = state.get(AGE);
        int lightLevel = worldIn.getLightSubtracted(pos.up(), 0);
        if (filled && age < this.getMaxAge() && worldIn.isDaytime() && lightLevel > 11 && rand.nextInt(6) == 0) {
            worldIn.setBlockState(pos, state.with(AGE, age + 1));
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack playerItemStack = player.getHeldItem(handIn);
        Item playerItem = playerItemStack.getItem();
        boolean filled = state.get(FILLED);
        if (!filled && playerItem == Items.WATER_BUCKET) {
            if (!worldIn.isRemote) {
                if (!player.abilities.isCreativeMode) {
                    ItemStack emptyBucket = new ItemStack(Items.BUCKET, 1);
                    EquipmentSlotType equipmentSlotType = handIn == Hand.MAIN_HAND ? EquipmentSlotType.MAINHAND : EquipmentSlotType.OFFHAND;
                    player.setItemStackToSlot(equipmentSlotType, emptyBucket);
                }
                worldIn.setBlockState(pos, state.with(FILLED, true));
            }
            return ActionResultType.SUCCESS;
        }

        int age = state.get(AGE);
        if (age == this.getMaxAge() && filled) {
            if (!worldIn.isRemote) {
                int randomCount = worldIn.getRandom().nextInt(2) + 1;
                ItemStack salt = new ItemStack(ModItems.SEA_SALT.get(), randomCount);

                worldIn.setBlockState(pos, state.with(AGE, 0).with(FILLED, false));
                spawnAsEntity(worldIn, pos, salt);
            }
            return ActionResultType.SUCCESS;
        }

        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        int age = state.get(AGE);
        if (age == this.getMaxAge() && !worldIn.isRemote) {
            int randomCount = worldIn.getRandom().nextInt(2) + 1;
            ItemStack salt = new ItemStack(ModItems.SEA_SALT.get(), randomCount);
            spawnAsEntity(worldIn, pos, salt);
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE, FILLED);
    }

    private int getMaxAge() {
        return MAX_AGE;
    }
}
