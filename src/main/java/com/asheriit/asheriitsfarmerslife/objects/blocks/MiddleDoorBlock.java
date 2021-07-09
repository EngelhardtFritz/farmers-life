package com.asheriit.asheriitsfarmerslife.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class MiddleDoorBlock extends DoorBlock {
    protected static final VoxelShape MIDDLE_NORTH_SOUTH = Block.makeCuboidShape(7.0D, 0.0D, 0.0D, 9.0D, 16.0D, 16.0D);
    protected static final VoxelShape MIDDLE_WEST_EAST = Block.makeCuboidShape(0.0D, 0.0D, 7.0D, 16.0D, 16.0D, 9.0D);

    protected static final VoxelShape MIDDLE_NORTH_WEST = Block.makeCuboidShape(0.0D, 0.0D, 1.0D, 2.0D, 16.0D, 9.0D);
    protected static final VoxelShape MIDDLE_NORTH_EAST = Block.makeCuboidShape(14.0D, 0.0D, 1.0D, 16.0D, 16.0D, 9.0D);
    protected static final VoxelShape MIDDLE_SOUTH_WEST = Block.makeCuboidShape(0.0D, 0.0D, 7.0D, 2.0D, 16.0D, 15.0D);
    protected static final VoxelShape MIDDLE_SOUTH_EAST = Block.makeCuboidShape(14.0D, 0.0D, 7.0D, 16.0D, 16.0D, 15.0D);
    protected static final VoxelShape MIDDLE_NORTH_WEST_ALT = Block.makeCuboidShape(1.0D, 0.0D, 0.0D, 9.0D, 16.0D, 2.0D);
    protected static final VoxelShape MIDDLE_NORTH_EAST_ALT = Block.makeCuboidShape(7.0D, 0.0D, 0.0D, 15.0D, 16.0D, 2.0D);
    protected static final VoxelShape MIDDLE_SOUTH_WEST_ALT = Block.makeCuboidShape(1.0D, 0.0D, 14.0D, 9.0D, 16.0D, 16.0D);
    protected static final VoxelShape MIDDLE_SOUTH_EAST_ALT = Block.makeCuboidShape(7.0D, 0.0D, 14.0D, 15.0D, 16.0D, 16.0D);
    protected static final VoxelShape MIDDLE_WEST_NORTH_EAST = VoxelShapes.or(MIDDLE_NORTH_WEST, MIDDLE_NORTH_EAST);
    protected static final VoxelShape MIDDLE_NORTH_EAST_SOUTH = VoxelShapes.or(MIDDLE_NORTH_EAST_ALT, MIDDLE_SOUTH_EAST_ALT);
    protected static final VoxelShape MIDDLE_EAST_SOUTH_WEST = VoxelShapes.or(MIDDLE_SOUTH_WEST, MIDDLE_SOUTH_EAST);
    protected static final VoxelShape MIDDLE_SOUTH_WEST_NORTH = VoxelShapes.or(MIDDLE_NORTH_WEST_ALT, MIDDLE_SOUTH_WEST_ALT);


    public MiddleDoorBlock(Properties builder) {
        super(builder);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(FACING);
        boolean isNotOpen = !state.get(OPEN);
        switch (direction) {
            case WEST:
                return isNotOpen ? MIDDLE_NORTH_SOUTH : MIDDLE_NORTH_EAST_SOUTH;
            case EAST:
            default:
                return isNotOpen ? MIDDLE_NORTH_SOUTH : MIDDLE_SOUTH_WEST_NORTH;
            case NORTH:
                return isNotOpen ? MIDDLE_WEST_EAST : MIDDLE_EAST_SOUTH_WEST;
            case SOUTH:
                return isNotOpen ? MIDDLE_WEST_EAST : MIDDLE_WEST_NORTH_EAST;
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.getShape(state, worldIn, pos, context);
    }
}
