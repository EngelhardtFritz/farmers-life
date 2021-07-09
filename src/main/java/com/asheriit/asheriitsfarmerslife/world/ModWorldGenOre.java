package com.asheriit.asheriitsfarmerslife.world;

import com.google.common.collect.ImmutableSet;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

public class ModWorldGenOre {
    public static final Set<BiomeDictionary.Type> SALT_ORE_BLACKLIST = ImmutableSet.of(
            BiomeDictionary.Type.SANDY,
            BiomeDictionary.Type.END,
            BiomeDictionary.Type.NETHER
    );

    public static void generateOre() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            // ------------ OVERWORLD ONLY ------------
            if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.OVERWORLD)) {
                if (BiomeDictionary.getTypes(biome).stream().noneMatch(SALT_ORE_BLACKLIST::contains)) {
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModDefaultBiomeFeatureConfigs.SALT_ORE, 5)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(6, 0, 0, 96))));
                }
            }
        }
    }
}
