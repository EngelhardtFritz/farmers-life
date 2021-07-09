package com.asheriit.asheriitsfarmerslife.objects.blocks.machines;

import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.tileentity.WinePressHandleTileEntity;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WinePressHandleBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape MIDDLE_NORTH_SOUTH = Block.makeCuboidShape(6.5F, 0, 0, 9.5F, 4, 16);
    private static final VoxelShape MIDDLE_WEST_EAST = Block.makeCuboidShape(0, 0, 6.5F, 16, 4, 9.5F);
    private static final VoxelShape SIDE_NORTH = Block.makeCuboidShape(2, 0, 0, 14, 4, 1);
    private static final VoxelShape SIDE_EAST = Block.makeCuboidShape(15, 0, 2, 16, 4, 14);
    private static final VoxelShape SIDE_SOUTH = Block.makeCuboidShape(2, 0, 15, 14, 4, 16);
    private static final VoxelShape SIDE_WEST = Block.makeCuboidShape(0, 0, 2, 1, 4, 14);
    private static final VoxelShape CENTER_PILLAR = Block.makeCuboidShape(7, 0, 7, 9, 14, 9);
    private static final VoxelShape SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 16, 16);
    private static final VoxelShape[] COLLISION_SHAPES = new VoxelShape[]{
            VoxelShapes.or(MIDDLE_WEST_EAST, SIDE_NORTH, SIDE_SOUTH, CENTER_PILLAR),
            VoxelShapes.or(MIDDLE_NORTH_SOUTH, SIDE_WEST, SIDE_EAST, CENTER_PILLAR),
    };
    private final Object2IntMap<BlockState> blockStateMap = new Object2IntOpenHashMap<>();

    public WinePressHandleBlock(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return COLLISION_SHAPES[this.getIndex(state)];
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, World worldIn, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn, @Nonnull BlockRayTraceResult hit) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof WinePressHandleTileEntity) {
            WinePressHandleTileEntity winePressHandleTileEntity = (WinePressHandleTileEntity) tileEntity;
            winePressHandleTileEntity.triggerHandle();
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof WinePressHandleTileEntity) {
                WinePressHandleTileEntity winePressHandleTileEntity = (WinePressHandleTileEntity) tileEntity;
                world.destroyBlock(winePressHandleTileEntity.getMasterPos(), true);
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
        return ModTileEntityTypes.WINE_PRESS_HANDLE_TILE_ENTITY.get().create();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    protected int getIndex(BlockState state) {
        return this.blockStateMap.computeIntIfAbsent(state, (blockState) -> {
            Direction stateDirection = blockState.get(FACING);
            if (stateDirection == Direction.NORTH || stateDirection == Direction.SOUTH) {
                return 0;
            }
            return 1;
        });
    }
}
