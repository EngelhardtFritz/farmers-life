package com.asheriit.asheriitsfarmerslife.world;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModFeatures;
import com.asheriit.asheriitsfarmerslife.world.feature.CorkOakTree;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

public class ModWorldGenTrees {
    public static final Set<BiomeDictionary.Type> CORK_TREE_BIOME_WHITELIST = ImmutableSet.of(
            BiomeDictionary.Type.FOREST
    );

    public static void generateTrees() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            // ------------ OVERWORLD ONLY ------------
            if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.OVERWORLD)) {
                if (BiomeDictionary.getTypes(biome).stream().anyMatch(CORK_TREE_BIOME_WHITELIST::contains) && biome == Biomes.FOREST) {
                    biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.FANCY_TREE.withConfiguration(CorkOakTree.FANCY_CORK_OAK_TREE_CONFIG).withChance(0.2F)), Feature.NORMAL_TREE.withConfiguration(CorkOakTree.CORK_OAK_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(0, 0.03F, 1))));
                }
            }
        }
    }
}
