package com.asheriit.asheriitsfarmerslife.objects.blocks.machines;

import com.asheriit.asheriitsfarmerslife.api.utils.AnimationTimingHelper;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.tileentity.WinePressHandleTileEntity;
import com.asheriit.asheriitsfarmerslife.tileentity.WinePressTileEntity;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RedstoneSide;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.stream.IntStream;

public class WinePressBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 16, 16);

    public WinePressBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(FACING, Direction.NORTH));
    }

    @Override
    public boolean isValidPosition(@Nonnull BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.up()).getBlock() instanceof AirBlock;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity placer, @Nonnull ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (placer != null) {
            // Set the slave block in the world and update master and save
            BlockState handleState = ModBlocks.WOOD_WINE_PRESS_HANDLE.get().getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, placer.getHorizontalFacing().getOpposite());
            if (!worldIn.isRemote) {
                worldIn.setBlockState(pos.up(), handleState, 2);
                WinePressHandleTileEntity winePressHandleTileEntity = (WinePressHandleTileEntity) worldIn.getTileEntity(pos.up());
                if (winePressHandleTileEntity != null) {
                    winePressHandleTileEntity.setMasterPos(pos);
                }

                WinePressTileEntity tileEntity = (WinePressTileEntity) worldIn.getTileEntity(pos);
                if (tileEntity != null) {
                    tileEntity.addSlave(pos.up());
                }
            }
        }
    }

    /**
     * Each time a neighbour changed check if a redstone signal is active and attached to this block
     * if it is the recipe process will be updated
     */
    @Override
    public void neighborChanged(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof WinePressTileEntity) {
            WinePressTileEntity winePressTileEntity = (WinePressTileEntity) tileEntity;
            boolean isPowered = worldIn.isBlockPowered(pos) || this.isBlockPowered(worldIn, pos);

            if (!winePressTileEntity.wasPowered() && isPowered) {
                winePressTileEntity.setWasPowered(true);

                if (AnimationTimingHelper.getElapsedTime() >= winePressTileEntity.getNextAllowedInteraction()) {
                    winePressTileEntity.setNextAllowedInteraction(AnimationTimingHelper.getElapsedTime() + WinePressTileEntity.ANIMATION_LENGTH);
                    winePressTileEntity.updateRecipeProgress();
                }
            }

            if ((!isPowered && winePressTileEntity.wasPowered())) {
                winePressTileEntity.setWasPowered(false);
            }
        }
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, World worldIn, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn, @Nonnull BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof WinePressTileEntity) {
                WinePressTileEntity winePressTileEntity = (WinePressTileEntity) tileEntity;

                ItemStack playerItemStack = player.getHeldItem(Hand.MAIN_HAND);
                Item item = playerItemStack.getItem();
                // The item is not an BucketItem
                if (!(item instanceof BucketItem)) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, winePressTileEntity, pos);
                    return ActionResultType.SUCCESS;
                } else {
                    BucketItem bucketItem = (BucketItem) item;
                    Fluid fluidInBucket = bucketItem.getFluid();

                    if (fluidInBucket == Fluids.EMPTY) {
                        // The bucket is empty
                        ItemStack result = winePressTileEntity.emptyIOTankIfPossible(playerItemStack, player, true, WinePressTileEntity.OUTPUT_TANK_SLOT);

                        if (((BucketItem) result.getItem()).getFluid() != Fluids.EMPTY) {
                            playerItemStack.shrink(1);
                            player.addItemStackToInventory(result);
                            worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            return ActionResultType.SUCCESS;
                        }
                    } else {
                        NetworkHooks.openGui((ServerPlayerEntity) player, winePressTileEntity, pos);
                        return ActionResultType.SUCCESS;
                    }
                }
                return ActionResultType.FAIL;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void onReplaced(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof WinePressTileEntity) {
                WinePressTileEntity winePressTileEntity = (WinePressTileEntity) tileEntity;
                winePressTileEntity.setPos(pos);

                winePressTileEntity.getInventoryCapability().ifPresent((handler) -> IntStream.range(0, handler.getSlots()).forEach((index) -> {
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
            if (tileEntity instanceof WinePressTileEntity) {
                WinePressTileEntity winePressTileEntity = (WinePressTileEntity) tileEntity;
                if (winePressTileEntity.getSlave() != null) {
                    world.destroyBlock(winePressTileEntity.getSlave(), false);
                    winePressTileEntity.removeSlave();
                }
                FluidStack outputFluid = winePressTileEntity.getFluidInTank(WinePressTileEntity.OUTPUT_TANK_SLOT);
                if (outputFluid.getAmount() >= 1000) {
                    IFluidState fluidState = outputFluid.getFluid().getDefaultState();
                    return super.removedByPlayer(state, world, pos, player, willHarvest, fluidState);
                }
            }
        }

        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side) {
        if (side != null) {
            return state.getProperties().contains(FACING) && side == state.get(FACING);
        }
        return false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.WINE_PRESS_TILE_ENTITY.get().create();
    }

    private boolean isBlockPowered(World worldIn, BlockPos pos) {
        BlockPos north = pos.north();
        BlockPos east = pos.east();
        BlockPos south = pos.south();
        BlockPos west = pos.west();
        BlockState stateNorth = worldIn.getBlockState(north);
        BlockState stateEast = worldIn.getBlockState(east);
        BlockState stateSouth = worldIn.getBlockState(south);
        BlockState stateWest = worldIn.getBlockState(west);

        Collection<IProperty<?>> propertiesNorth = stateNorth.getProperties();
        Collection<IProperty<?>> propertiesEast = stateEast.getProperties();
        Collection<IProperty<?>> propertiesSouth = stateSouth.getProperties();
        Collection<IProperty<?>> propertiesWest = stateWest.getProperties();

        boolean isPowered = false;

        if (propertiesNorth.contains(BlockStateProperties.POWER_0_15)) {
            int power = stateNorth.get(BlockStateProperties.POWER_0_15);

            isPowered = power > 0 && propertiesNorth.contains(BlockStateProperties.REDSTONE_SOUTH) && stateNorth.get(BlockStateProperties.REDSTONE_SOUTH) == RedstoneSide.SIDE;
        }

        if (propertiesEast.contains(BlockStateProperties.POWER_0_15)) {
            int power = stateEast.get(BlockStateProperties.POWER_0_15);

            isPowered = isPowered || (power > 0 && propertiesEast.contains(BlockStateProperties.REDSTONE_WEST) && stateEast.get(BlockStateProperties.REDSTONE_WEST) == RedstoneSide.SIDE);
        }

        if (propertiesSouth.contains(BlockStateProperties.POWER_0_15)) {
            int power = stateSouth.get(BlockStateProperties.POWER_0_15);

            isPowered = isPowered || (power > 0 && propertiesSouth.contains(BlockStateProperties.REDSTONE_NORTH) && stateSouth.get(BlockStateProperties.REDSTONE_NORTH) == RedstoneSide.SIDE);
        }

        if (propertiesWest.contains(BlockStateProperties.POWER_0_15)) {
            int power = stateWest.get(BlockStateProperties.POWER_0_15);

            isPowered = isPowered || (power > 0 && propertiesWest.contains(BlockStateProperties.REDSTONE_EAST) && stateWest.get(BlockStateProperties.REDSTONE_EAST) == RedstoneSide.SIDE);
        }
        return isPowered;
    }
}
