package com.asheriit.asheriitsfarmerslife.world;

import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;

public class ModDefaultBiomeFeatureConfigs {
    public static final BlockState SALT_ORE = ModBlocks.SALT_ORE.get().getDefaultState();

    private static final BlockState SHORT_GRASS = ModBlocks.SHORT_GRASS.get().getDefaultState();
    private static final BlockState PEAT_MOSS = ModBlocks.PEAT_MOSS.get().getDefaultState();


    public static final BlockClusterFeatureConfig SHORT_GRASS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(SHORT_GRASS), new SimpleBlockPlacer())).tries(32).build();
    public static final BlockClusterFeatureConfig PEAT_MOSS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(PEAT_MOSS), new SimpleBlockPlacer())).tries(8).build();

}
