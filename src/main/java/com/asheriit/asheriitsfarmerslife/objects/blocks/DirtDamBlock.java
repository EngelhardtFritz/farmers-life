package com.asheriit.asheriitsfarmerslife.objects.blocks;

import com.asheriit.asheriitsfarmerslife.init.ModTags;
import com.asheriit.asheriitsfarmerslife.utils.BlockType;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class DirtDamBlock extends GrassDamBlock {
    private final Block grassBlock;

    public DirtDamBlock(Block grassBlock, BlockType blockType, boolean hasCrop, Properties properties) {
        super(blockType, hasCrop, properties);
        this.grassBlock = grassBlock;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        BlockState up = worldIn.getBlockState(pos.up());
        BlockState north = worldIn.getBlockState(pos.north());
        BlockState east = worldIn.getBlockState(pos.east());
        BlockState south = worldIn.getBlockState(pos.south());
        BlockState west = worldIn.getBlockState(pos.west());

        if (up.getBlock() instanceof AirBlock && (north.isIn(ModTags.Blocks.GRASS_BLOCKS) || east.isIn(ModTags.Blocks.GRASS_BLOCKS) ||
                south.isIn(ModTags.Blocks.GRASS_BLOCKS) || west.isIn(ModTags.Blocks.GRASS_BLOCKS))) {
            if (rand.nextInt(3) == 0) {
                BlockState stateToSet;
                if (state.getProperties().contains(BlockStateProperties.AGE_0_5)) {
                    stateToSet = grassBlock.getDefaultState()
                            .with(NORTH, state.get(NORTH))
                            .with(EAST, state.get(EAST))
                            .with(SOUTH, state.get(SOUTH))
                            .with(WEST, state.get(WEST))
                            .with(AGE, state.get(AGE));
                } else {
                    stateToSet = grassBlock.getDefaultState()
                            .with(NORTH, state.get(NORTH))
                            .with(EAST, state.get(EAST))
                            .with(SOUTH, state.get(SOUTH))
                            .with(WEST, state.get(WEST));
                }
                worldIn.setBlockState(pos, stateToSet, 2);
            }
        }
        super.tick(state, worldIn, pos, rand);
    }
}
