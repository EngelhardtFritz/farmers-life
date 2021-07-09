package com.asheriit.asheriitsfarmerslife.objects.blocks.machines;

import com.asheriit.asheriitsfarmerslife.api.utils.FacingHelper;
import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import com.asheriit.asheriitsfarmerslife.init.ModItems;
import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.recipes.BoilingRecipe;
import com.asheriit.asheriitsfarmerslife.recipes.SoakingRecipe;
import com.asheriit.asheriitsfarmerslife.tileentity.BoilingCauldronTileEntity;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class BoilingCauldronBlock extends Block {
    public static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.makeCuboidShape(0, 0, 0, 16, 16, 16),
            VoxelShapes.or(Block.makeCuboidShape(0, 0, 0, 16, 6, 16), Block.makeCuboidShape(0, 6, 0, 16, 16, 2), Block.makeCuboidShape(14, 6, 0, 16, 16, 16), Block.makeCuboidShape(0, 6, 14, 16, 16, 16), Block.makeCuboidShape(0, 6, 0, 2, 16, 16))
    };
    private final Object2IntMap<BlockState> blockStateMap = new Object2IntOpenHashMap<>();

    public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final BooleanProperty HAS_LID = ModBlockStateProperties.HAS_LID;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public BoilingCauldronBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(HORIZONTAL_AXIS, Direction.Axis.X)
                .with(HAS_LID, false)
                .with(LIT, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[this.getIndex(state)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[this.getIndex(state)];
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction facing = context.getPlacementHorizontalFacing();
        Direction.Axis horizontalAxis = facing == Direction.NORTH || facing == Direction.SOUTH ? Direction.Axis.Z : Direction.Axis.X;
        return this.getDefaultState().with(HORIZONTAL_AXIS, horizontalAxis);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof BoilingCauldronTileEntity) {
                BoilingCauldronTileEntity boilingCauldronTileEntity = (BoilingCauldronTileEntity) tileEntity;

                ItemStack playerItemStack = player.getHeldItem(Hand.MAIN_HAND);
                Item item = playerItemStack.getItem();
                // The item is not a BucketItem
                if (item instanceof BucketItem) {
                    BucketItem bucketItem = (BucketItem) item;
                    Fluid fluidInBucket = bucketItem.getFluid();

                    if (fluidInBucket == Fluids.EMPTY) {
                        // The bucket is empty
                        ItemStack result = boilingCauldronTileEntity.emptyIOTankIfPossible(playerItemStack, player, false, BoilingCauldronTileEntity.OUTPUT_FLUID_TANK);

                        if (((BucketItem) result.getItem()).getFluid() != Fluids.EMPTY) {
                            playerItemStack.shrink(1);
                            player.addItemStackToInventory(result);
                            worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            return ActionResultType.SUCCESS;
                        }
                    } else {
                        // The bucket is full
                        if (boilingCauldronTileEntity.isFluidValid(BoilingCauldronTileEntity.INPUT_FLUID_TANK, new FluidStack(fluidInBucket, 1)) &&
                                (BoilingRecipe.BoilingRecipeSerializer.fluidInputList.contains(fluidInBucket) ||
                                        SoakingRecipe.SoakingRecipeSerializer.fluidInputList.contains(fluidInBucket))) {
                            ItemStack result = boilingCauldronTileEntity.fillIOTankIfPossible(playerItemStack, player, true, BoilingCauldronTileEntity.INPUT_FLUID_TANK);
                            player.setItemStackToSlot(EquipmentSlotType.MAINHAND, result);
                            worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            return ActionResultType.SUCCESS;
                        }
                    }
                } else if (item == ModItems.BOILING_CAULDRON_LID.get() && !state.get(HAS_LID)) {
                    if (!player.abilities.isCreativeMode) {
                        playerItemStack.shrink(1);
                    }
                    worldIn.setBlockState(pos, state.with(HAS_LID, true));
                    return ActionResultType.SUCCESS;
                } else if ((item == Items.AIR || (item == ModItems.BOILING_CAULDRON_LID.get() && (playerItemStack.getCount() + 1) <= 64)) &&
                        state.get(HAS_LID) && player.isSneaking()) {
                    player.inventory.addItemStackToInventory(new ItemStack(ModItems.BOILING_CAULDRON_LID.get(), 1));
                    worldIn.setBlockState(pos, state.with(HAS_LID, false));
                    return ActionResultType.SUCCESS;
                } else {
                    NetworkHooks.openGui((ServerPlayerEntity) player, boilingCauldronTileEntity, pos);
                    return ActionResultType.SUCCESS;
                }
            }
            return ActionResultType.PASS;
        }
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof BoilingCauldronTileEntity) {
                BoilingCauldronTileEntity boilingCauldronTileEntity = (BoilingCauldronTileEntity) tileEntity;
                boilingCauldronTileEntity.setPos(pos);

                boilingCauldronTileEntity.getInventoryCapability().ifPresent((handler) -> IntStream.range(0, handler.getSlots()).forEach((index) -> {
                    InventoryHelper.spawnItemStack(worldIn, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, handler.getStackInSlot(index));
                }));
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof BoilingCauldronTileEntity) {
                BoilingCauldronTileEntity boilingCauldronTileEntity = (BoilingCauldronTileEntity) tileEntity;
                FluidStack outputFluid = boilingCauldronTileEntity.getFluidInTank(BoilingCauldronTileEntity.OUTPUT_FLUID_TANK);
                if (!outputFluid.isEmpty() && outputFluid.getAmount() >= 1000) {
                    IFluidState fluidState = outputFluid.getFluid().getDefaultState();
                    return super.removedByPlayer(state, world, pos, player, willHarvest, fluidState);
                }
                FluidStack inputFluid = boilingCauldronTileEntity.getFluidInTank(BoilingCauldronTileEntity.INPUT_FLUID_TANK);
                if (!inputFluid.isEmpty() && inputFluid.getAmount() >= 1000) {
                    IFluidState fluidState = inputFluid.getFluid().getDefaultState();
                    return super.removedByPlayer(state, world, pos, player, willHarvest, fluidState);
                }
            }
        }

        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        if (state.get(LIT)) {
            return 13;
        }
        return 0;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.BOILING_CAULDRON_TILE_ENTITY.get().create();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_AXIS, HAS_LID, LIT);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        // TODO: explain functionality
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(LIT)) {
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = (double) pos.getY();
            double d2 = (double) pos.getZ() + 0.5D;
            if (rand.nextDouble() < 0.1D) {
                worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            for (Direction direction : FacingHelper.HORIZONTAL_DIRECTIONS) {
                Direction.Axis direction$axis = direction.getAxis();
                double d3 = 0.4D;
                double d4 = rand.nextDouble() * 0.6D - 0.3D;
                double d5 = direction$axis == Direction.Axis.X ? (double) direction.getXOffset() * d3 : d4;
                double d6 = rand.nextDouble() * 4.0D / 16.0D;
                double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getZOffset() * d3 : d4;
                worldIn.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
                worldIn.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            }
        }
        super.animateTick(stateIn, worldIn, pos, rand);
    }

    protected int getIndex(BlockState state) {
        return this.blockStateMap.computeIntIfAbsent(state, (blockState) -> {
            if (blockState.get(HAS_LID)) {
                return 0;
            } else {
                return 1;
            }
        });
    }
}
