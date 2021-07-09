package com.asheriit.asheriitsfarmerslife.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class NoAmbientOcclusionBlock extends Block {
    public NoAmbientOcclusionBlock(Properties properties) {
        super(properties);
    }

    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }
}
