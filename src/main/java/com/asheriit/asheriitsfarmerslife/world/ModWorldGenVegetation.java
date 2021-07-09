package com.asheriit.asheriitsfarmerslife.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;

public class ModWorldGenVegetation {
    public static void generateVegetation() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            // ------------ OVERWORLD ONLY ------------
            if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.OVERWORLD)) {
                // ------------ FEATURES FOR JUNGLE BIOMES ------------
                if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.JUNGLE)) {
                    biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(ModDefaultBiomeFeatureConfigs.PEAT_MOSS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(1))));
                }

                // ------------ FEATURES FOR ALL BIOMES ------------
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(ModDefaultBiomeFeatureConfigs.SHORT_GRASS_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(2))));
            }
        }
    }
}
