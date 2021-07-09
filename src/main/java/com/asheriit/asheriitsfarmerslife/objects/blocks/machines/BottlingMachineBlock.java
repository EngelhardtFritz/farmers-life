package com.asheriit.asheriitsfarmerslife.objects.blocks.machines;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.recipes.BottlingRecipe;
import com.asheriit.asheriitsfarmerslife.recipes.FiltrationRecipe;
import com.asheriit.asheriitsfarmerslife.tileentity.BottlingMachineTileEntity;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
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
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class BottlingMachineBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty HAS_BOTTLE = BlockStateProperties.HAS_BOTTLE_0;

    private static final VoxelShape[] SHAPES = makeShapes();
    private final Object2IntMap<BlockState> blockStateMap = new Object2IntOpenHashMap<>();

    public BottlingMachineBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH).with(HAS_BOTTLE, false));
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
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof BottlingMachineTileEntity) {
                BottlingMachineTileEntity bottlingMachineTileEntity = (BottlingMachineTileEntity) tileEntity;

                ItemStack playerItemStack = player.getHeldItem(Hand.MAIN_HAND);
                Item item = playerItemStack.getItem();
                // The item is not a BucketItem
                if (!(item instanceof BucketItem)) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, bottlingMachineTileEntity, pos);
                    return ActionResultType.SUCCESS;
                } else {
                    BucketItem bucketItem = (BucketItem) item;
                    Fluid fluidInBucket = bucketItem.getFluid();

                    if (fluidInBucket == Fluids.EMPTY) {
                        // The bucket is empty
                        ItemStack result = bottlingMachineTileEntity.emptyIOTankIfPossible(playerItemStack, player, true, BottlingMachineTileEntity.INPUT_TANK);

                        if (((BucketItem) result.getItem()).getFluid() != Fluids.EMPTY) {
                            playerItemStack.shrink(1);
                            player.addItemStackToInventory(result);
                            worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            return ActionResultType.SUCCESS;
                        }
                    } else {
                        // The bucket is full
                        if (bottlingMachineTileEntity.isFluidValid(BottlingMachineTileEntity.INPUT_TANK, new FluidStack(fluidInBucket, 1)) &&
                                BottlingRecipe.BottlerRecipeSerializer.fluidInputList.contains(fluidInBucket)) {
                            ItemStack result = bottlingMachineTileEntity.fillIOTankIfPossible(playerItemStack, player, true, BottlingMachineTileEntity.INPUT_TANK);
                            player.setItemStackToSlot(EquipmentSlotType.MAINHAND, result);
                            worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            return ActionResultType.SUCCESS;
                        } else {
                            NetworkHooks.openGui((ServerPlayerEntity) player, bottlingMachineTileEntity, pos);
                            return ActionResultType.SUCCESS;
                        }
                    }
                }
            }
        }

        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof BottlingMachineTileEntity) {
                BottlingMachineTileEntity bottlingMachineTileEntity = (BottlingMachineTileEntity) tileEntity;
                bottlingMachineTileEntity.setPos(pos);

                bottlingMachineTileEntity.getInventoryCapability().ifPresent((handler) -> IntStream.range(0, handler.getSlots()).forEach((index) -> {
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
            if (tileEntity instanceof BottlingMachineTileEntity) {
                BottlingMachineTileEntity bottlingMachineTileEntity = (BottlingMachineTileEntity) tileEntity;
                FluidStack fluidInTank = bottlingMachineTileEntity.getFluidInTank(BottlingMachineTileEntity.INPUT_TANK);
                if (fluidInTank.getAmount() >= 1000) {
                    IFluidState fluidState = fluidInTank.getFluid().getDefaultState();
                    return super.removedByPlayer(state, world, pos, player, willHarvest, fluidState);
                }
            }
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, HAS_BOTTLE);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.BOTTLING_MACHINE_TILE_ENTITY.get().create();
    }

    private static VoxelShape[] makeShapes() {
        final VoxelShape FRONT = Block.makeCuboidShape(0.5F, 0, 0, 15.5F, 15.0F, 5);
        final VoxelShape LEFT = Block.makeCuboidShape(11, 0, 0.5F, 16, 15.0F, 15.5F);
        final VoxelShape BACK = Block.makeCuboidShape(0.5F, 0, 11, 15.5F, 15.0F, 16);
        final VoxelShape RIGHT = Block.makeCuboidShape(0, 0, 0.5F, 5, 15.0F, 15.5F);
        final VoxelShape OUTLET_NORTH = Block.makeCuboidShape(7.5F, 10, 3.5, 8.5F, 16, 5);
        final VoxelShape OUTLET_EAST = Block.makeCuboidShape(11, 10, 7.5F, 12.5F, 16, 8.5F);
        final VoxelShape OUTLET_SOUTH = Block.makeCuboidShape(7.5F, 10, 11, 8.5F, 16, 12.5F);
        final VoxelShape OUTLET_WEST = Block.makeCuboidShape(3.5F, 10, 7.5F, 5, 16, 8.5F);
        final VoxelShape NORTH_SOUTH = Block.makeCuboidShape(7, 12, 5, 9, 14, 11);
        final VoxelShape WEST_EAST = Block.makeCuboidShape(5, 12, 7, 11, 14, 9);
        final VoxelShape BOT_NORTH = Block.makeCuboidShape(4, 0, 0, 12, 2, 8);
        final VoxelShape BOT_EAST = Block.makeCuboidShape(8, 0, 4, 16, 2, 12);
        final VoxelShape BOT_SOUTH = Block.makeCuboidShape(4, 0, 8, 12, 2, 16);
        final VoxelShape BOT_WEST = Block.makeCuboidShape(0, 0, 4, 8, 2, 12);

        return new VoxelShape[]{
                VoxelShapes.or(BACK, OUTLET_NORTH, NORTH_SOUTH, BOT_NORTH),
                VoxelShapes.or(RIGHT, OUTLET_EAST, WEST_EAST, BOT_EAST),
                VoxelShapes.or(FRONT, OUTLET_SOUTH, NORTH_SOUTH, BOT_SOUTH),
                VoxelShapes.or(LEFT, OUTLET_WEST, WEST_EAST, BOT_WEST)
        };
    }

    private int getIndex(BlockState state) {
        return this.blockStateMap.computeIntIfAbsent(state, (blockState) -> {
            int i;
            if (blockState.get(FACING) == Direction.NORTH) {
                i = 0;
            } else if (blockState.get(FACING) == Direction.EAST) {
                i = 1;
            } else if (blockState.get(FACING) == Direction.SOUTH) {
                i = 2;
            } else {
                i = 3;
            }

            return i;
        });
    }
}
