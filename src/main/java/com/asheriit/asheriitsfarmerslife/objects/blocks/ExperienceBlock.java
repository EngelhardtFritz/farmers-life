package com.asheriit.asheriitsfarmerslife.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

public class ExperienceBlock extends Block {
    private final int minExperience, maxExperience;

    public ExperienceBlock(int minExperience, int maxExperience, Properties properties) {
        super(properties);
        if (minExperience > maxExperience) {
            this.minExperience = maxExperience;
            this.maxExperience = minExperience;
        } else {
            this.minExperience = minExperience;
            this.maxExperience = maxExperience;
        }
    }

    @Override
    public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? MathHelper.nextInt(RANDOM, minExperience, maxExperience) : 0;
    }
}
