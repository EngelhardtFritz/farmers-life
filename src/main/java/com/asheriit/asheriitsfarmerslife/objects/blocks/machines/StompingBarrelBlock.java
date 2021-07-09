package com.asheriit.asheriitsfarmerslife.objects.blocks.machines;

import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.tileentity.StompingBarrelTileEntity;
import com.asheriit.asheriitsfarmerslife.tileentity.WinePressTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class StompingBarrelBlock extends Block {
    private static final VoxelShape NORTH = Block.makeCuboidShape(0, 0, 0, 16, 16, 1);
    private static final VoxelShape EAST = Block.makeCuboidShape(15, 0, 0, 16, 16, 16);
    private static final VoxelShape SOUTH = Block.makeCuboidShape(0, 0, 15, 16, 16, 16);
    private static final VoxelShape WEST = Block.makeCuboidShape(0, 0, 0, 1, 16, 16);
    private static final VoxelShape DOWN = Block.makeCuboidShape(0, 0, 0, 16, 3, 16);
    private static final VoxelShape SHAPE = VoxelShapes.or(NORTH, EAST, SOUTH, WEST, DOWN);

    public StompingBarrelBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return SHAPE;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity != null && tileEntity instanceof StompingBarrelTileEntity) {
                StompingBarrelTileEntity winePressTileEntity = (StompingBarrelTileEntity) tileEntity;

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
                        ItemStack result = winePressTileEntity.emptyIOTankIfPossible(playerItemStack, player, true, StompingBarrelTileEntity.OUTPUT_TANK_SLOT);

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
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        // Validate that the entity was falling and is now standing on the ground
        if (!worldIn.isRemote && !entityIn.isAirBorne && entityIn.fallDistance > 0 && entityIn.onGround && !entityIn.velocityChanged) {
            TileEntity tileEntity = worldIn.getTileEntity(entityIn.getPosition());
            if (tileEntity instanceof StompingBarrelTileEntity) {
                StompingBarrelTileEntity winePressTileEntity = (StompingBarrelTileEntity) tileEntity;
                winePressTileEntity.updateRecipeProgress();
            }
        }
        super.onEntityCollision(state, worldIn, pos, entityIn);
    }

    @Override
    public void onReplaced(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity != null && tileEntity instanceof StompingBarrelTileEntity) {
                StompingBarrelTileEntity winePressTileEntity = (StompingBarrelTileEntity) tileEntity;
                winePressTileEntity.setPos(pos);

                winePressTileEntity.getInventoryCapability().ifPresent((handler) -> IntStream.range(0, handler.getSlots()).forEach((index) -> {
                    InventoryHelper.spawnItemStack(worldIn, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, handler.getStackInSlot(index));
                }));
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof StompingBarrelTileEntity) {
                StompingBarrelTileEntity stompingBarrelTileEntity = (StompingBarrelTileEntity) tileEntity;
                FluidStack outputFluid = stompingBarrelTileEntity.getFluidInTank(StompingBarrelTileEntity.OUTPUT_TANK_SLOT);
                if (outputFluid.getAmount() >= 1000) {
                    IFluidState fluidState = outputFluid.getFluid().getDefaultState();
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
        return ModTileEntityTypes.STOMPING_BARREL_TILE_ENTITY.get().create();
    }
}
