package com.asheriit.asheriitsfarmerslife.objects.blocks.machines;

import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.recipes.TemperatureChamberRecipe;
import com.asheriit.asheriitsfarmerslife.tileentity.TemperatureChamberTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class TemperatureChamberBlock extends Block {
    public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;

    public TemperatureChamberBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(HORIZONTAL_AXIS, Direction.Axis.X));
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
            if (tileEntity instanceof TemperatureChamberTileEntity) {
                TemperatureChamberTileEntity temperatureChamberTileEntity = (TemperatureChamberTileEntity) tileEntity;

                ItemStack playerItemStack = player.getHeldItem(Hand.MAIN_HAND);
                Item item = playerItemStack.getItem();
                // The item is not a BucketItem
                if (item instanceof BucketItem) {
                    BucketItem bucketItem = (BucketItem) item;
                    Fluid fluidInBucket = bucketItem.getFluid();

                    if (fluidInBucket == Fluids.EMPTY) {
                        // The bucket is empty
                        ItemStack result = temperatureChamberTileEntity.emptyIOTankIfPossible(playerItemStack, player, false, TemperatureChamberTileEntity.INPUT_FLUID_TANK);

                        if (((BucketItem) result.getItem()).getFluid() != Fluids.EMPTY) {
                            playerItemStack.shrink(1);
                            player.addItemStackToInventory(result);
                            worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            return ActionResultType.SUCCESS;
                        }
                    } else {
                        // The bucket is full
                        if (temperatureChamberTileEntity.isFluidValid(TemperatureChamberTileEntity.INPUT_FLUID_TANK, new FluidStack(fluidInBucket, 1)) &&
                                TemperatureChamberRecipe.TemperatureChamberRecipeSerializer.fluidInputList.contains(fluidInBucket)) {
                            ItemStack result = temperatureChamberTileEntity.fillIOTankIfPossible(playerItemStack, player, true, TemperatureChamberTileEntity.INPUT_FLUID_TANK);
                            player.setItemStackToSlot(EquipmentSlotType.MAINHAND, result);
                            worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            return ActionResultType.SUCCESS;
                        }
                    }
                }

                NetworkHooks.openGui((ServerPlayerEntity) player, temperatureChamberTileEntity, pos);
                return ActionResultType.SUCCESS;
            }
            return ActionResultType.PASS;
        }
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity != null && tileEntity instanceof TemperatureChamberTileEntity) {
                TemperatureChamberTileEntity temperatureChamberTileEntity = (TemperatureChamberTileEntity) tileEntity;
                temperatureChamberTileEntity.setPos(pos);

                temperatureChamberTileEntity.getInventoryCapability().ifPresent((handler) -> IntStream.range(0, handler.getSlots()).forEach((index) -> {
                    InventoryHelper.spawnItemStack(worldIn, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, handler.getStackInSlot(index));
                }));
                worldIn.updateComparatorOutputLevel(pos, this);
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof TemperatureChamberTileEntity) {
                TemperatureChamberTileEntity temperatureChamberTileEntity = (TemperatureChamberTileEntity) tileEntity;
                FluidStack inputFluid = temperatureChamberTileEntity.getFluidInTank(TemperatureChamberTileEntity.INPUT_FLUID_TANK);
                if (!inputFluid.isEmpty() && inputFluid.getAmount() >= 1000) {
                    IFluidState fluidState = inputFluid.getFluid().getDefaultState();
                    return super.removedByPlayer(state, world, pos, player, willHarvest, fluidState);
                }
            }
        }

        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.TEMPERATURE_CHAMBER_TILE_ENTITY.get().create();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_AXIS);
    }
}
