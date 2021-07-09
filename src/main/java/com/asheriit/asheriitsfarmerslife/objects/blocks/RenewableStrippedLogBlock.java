package com.asheriit.asheriitsfarmerslife.objects.blocks;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

public class RenewableStrippedLogBlock extends LogBlock {
    private final Block logBlock;
    private static final IntegerProperty AGE = BlockStateProperties.AGE_0_2;

    public RenewableStrippedLogBlock(Block logBlock, MaterialColor verticalColorIn, Properties properties) {
        super(verticalColorIn, properties.tickRandomly());
        this.logBlock = logBlock;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (this.canRenewBark(worldIn, pos)) {
            if (random.nextInt(20) == 0 && (logBlock instanceof GenericLogBlock || logBlock instanceof CorkOakLogBlock)) {
                int currentAge = state.get(AGE);
                if (currentAge == this.getMaxAge()) {
                    worldIn.setBlockState(pos, logBlock.getDefaultState().with(AXIS, state.get(AXIS)), Constants.BlockFlags.BLOCK_UPDATE);
                } else {
                    worldIn.setBlockState(pos, state.with(AGE, currentAge + 1), Constants.BlockFlags.BLOCK_UPDATE);
                }
            }
        }
        super.randomTick(state, worldIn, pos, random);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder.add(AGE));
    }

    public boolean canRenewBark(ServerWorld worldIn, BlockPos pos) {
        for (int x = -1; x < 1; x++) {
            for (int y = 0; y < 8; y++) {
                for (int z = -1; z < 1; z++) {
                    BlockPos blockPos = new BlockPos(pos.add(x, y, z));
                    BlockState state = worldIn.getBlockState(blockPos);
                    if (state.isIn(BlockTags.LEAVES)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getMaxAge() {
        return 2;
    }
}
