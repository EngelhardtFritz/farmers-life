package com.asheriit.asheriitsfarmerslife.world.feature;

import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.Random;

public class CorkOakTree extends Tree {
    public static final TreeFeatureConfig CORK_OAK_TREE_CONFIG = (new TreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.CORK_OAK_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.CORK_OAK_LEAVES.get().getDefaultState()),
            new BlobFoliagePlacer(2, 0))
            .baseHeight(4).heightRandA(2).foliageHeight(3).ignoreVines().setSapling((IPlantable) ModBlocks.CORK_OAK_SAPLING.get())).build();

    public static final TreeFeatureConfig FANCY_CORK_OAK_TREE_CONFIG = (new TreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlocks.CORK_OAK_LOG.get().getDefaultState()),
            new SimpleBlockStateProvider(ModBlocks.CORK_OAK_LEAVES.get().getDefaultState()),
            new BlobFoliagePlacer(0, 0)).setSapling((IPlantable) ModBlocks.CORK_OAK_SAPLING.get())).build();

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
        return randomIn.nextInt(10) == 0 ? Feature.FANCY_TREE.withConfiguration(FANCY_CORK_OAK_TREE_CONFIG) : Feature.NORMAL_TREE.withConfiguration(CORK_OAK_TREE_CONFIG);
    }
}
