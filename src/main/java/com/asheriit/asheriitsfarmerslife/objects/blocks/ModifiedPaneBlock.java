package com.asheriit.asheriitsfarmerslife.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class ModifiedPaneBlock extends PaneBlock {
    public ModifiedPaneBlock(Properties builder) {
        super(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockReader iblockreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.south();
        BlockPos blockpos3 = blockpos.west();
        BlockPos blockpos4 = blockpos.east();
        BlockState blockstate = iblockreader.getBlockState(blockpos1);
        BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
        BlockState blockstate2 = iblockreader.getBlockState(blockpos3);
        BlockState blockstate3 = iblockreader.getBlockState(blockpos4);
        return this.getDefaultState()
                .with(NORTH, this.canConnectTo(blockstate, blockstate.isSolidSide(iblockreader, blockpos1, Direction.SOUTH)))
                .with(SOUTH, this.canConnectTo(blockstate1, blockstate1.isSolidSide(iblockreader, blockpos2, Direction.NORTH)))
                .with(WEST, this.canConnectTo(blockstate2, blockstate2.isSolidSide(iblockreader, blockpos3, Direction.EAST)))
                .with(EAST, this.canConnectTo(blockstate3, blockstate3.isSolidSide(iblockreader, blockpos4, Direction.WEST)))
                .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return facing.getAxis().isHorizontal() ?
                stateIn.with(FACING_TO_PROPERTY_MAP.get(facing), this.canConnectTo(facingState, facingState.isSolidSide(worldIn, facingPos, facing.getOpposite()))) :
                super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public boolean canConnectTo(BlockState state, boolean p_220112_2_) {
        Block block = state.getBlock();
        return !cannotAttach(block) && p_220112_2_ || block instanceof PaneBlock || block instanceof MiddleDoorBlock;
    }
}
