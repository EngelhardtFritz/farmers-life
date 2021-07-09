package com.asheriit.asheriitsfarmerslife.init;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class ModTags {

    public static class Items {
        // ----------------------- TAGS FOR MOD -----------------------
        public static final Tag<Item> FERTILIZER = createTag(FarmersLife.MOD_ID, "fertilizer");
        public static final Tag<Item> RAW_FERTILIZER = createTag(FarmersLife.MOD_ID, "raw_fertilizer");
        public static final Tag<Item> WATERING_CANS = createTag(FarmersLife.MOD_ID, "watering_cans");
        public static final Tag<Item> SPADES = createTag(FarmersLife.MOD_ID, "spades");
        public static final Tag<Item> PRECIPITATION_MATERIALS = createTag(FarmersLife.MOD_ID, "precipitation_materials");
        public static final Tag<Item> ORGANIC_WASTE = createTag(FarmersLife.MOD_ID, "organic_waste");
        public static final Tag<Item> WINE_BOTTLES = createTag(FarmersLife.MOD_ID, "wine_bottles");

        // ----------------------- NEW LOG TYPES -----------------------
        public static final Tag<Item> CORK_OAK_LOGS = createTag(FarmersLife.MOD_ID, "cork_oak_logs");

        // ----------------------- TAGS FOR FORGE -----------------------
        public static final Tag<Item> SALT = createTag("forge", "salt/salt");
        // ----------------------- TAGS FOR FORGE - SEEDS -----------------------
        public static final Tag<Item> SEEDS_RICE = createTag("forge", "seeds/rice");
        public static final Tag<Item> SEEDS_WINE_GRAPES = createTag("forge", "seeds/wine_grape");
        public static final Tag<Item> SEEDS_CORN = createTag("forge", "seeds/corn");
        // ----------------------- TAGS FOR FORGE - CROPS -----------------------
        public static final Tag<Item> CROPS_RICE = createTag("forge", "crops/rice");
        public static final Tag<Item> CROPS_WINE_GRAPES = createTag("forge", "crops/wine_grape");
        public static final Tag<Item> CROPS_CORN = createTag("forge", "crops/corn");
        // ----------------------- TAGS FOR FORGE - HUSKS -----------------------
        public static final Tag<Item> HUSKS = createTag("forge", "husks");
        // ----------------------- TAGS FOR FORGE - GRAINS -----------------------
        public static final Tag<Item> GRAINS = createTag("forge", "grains/grains");
        public static final Tag<Item> GRAINS_RICE = createTag("forge", "grains/rice");
        public static final Tag<Item> SOAKED_GRAINS_RICE = createTag("forge", "grains/soaked_rice");
        public static final Tag<Item> STEAMED_GRAINS_RICE = createTag("forge", "grains/steamed_rice");

        private static Tag<Item> createTag(String modid, String namespace) {
            return new ItemTags.Wrapper(new ResourceLocation(modid, namespace));
        }
    }

    public static class Blocks {
        // ----------------------- TAGS FOR MOD -----------------------
        public static final Tag<Block> FARMLANDS = createTag(FarmersLife.MOD_ID, "farmlands");
        public static final Tag<Block> TRELLISES = createTag(FarmersLife.MOD_ID, "trellises");
        public static final Tag<Block> GRASS_BLOCKS = createTag(FarmersLife.MOD_ID, "grass_blocks");
        public static final Tag<Block> SUBMERGED_FARMLAND = createTag(FarmersLife.MOD_ID, "submerged_farmland");
        public static final Tag<Block> GRASS_SUBMERGED_FARMLAND = createTag(FarmersLife.MOD_ID, "grass_submerged_farmland");

        // ----------------------- NEW LOG TYPES -----------------------
        public static final Tag<Block> CORK_OAK_LOGS = createTag(FarmersLife.MOD_ID, "cork_oak_logs");

        public static final Tag<Block> SOILS = createTag(FarmersLife.MOD_ID, "soils");
        public static final Tag<Block> WINE_GRAPE_SOILS = createTag(FarmersLife.MOD_ID, "soils/wine_grape");
        public static final Tag<Block> COMPRESSED = createTag(FarmersLife.MOD_ID, "compressed");
        public static final Tag<Block> COMPRESSED_FERTILIZER = createTag(FarmersLife.MOD_ID, "compressed/fertilizer");

        // ----------------------- TAGS FOR FORGE - CROPS -----------------------
        public static final Tag<Block> CROPS_RICE = createTag("forge", "crops/rice");
        public static final Tag<Block> CROPS_WINE_GRAPES = createTag("forge", "crops/wine_grape");
        public static final Tag<Block> CROPS_CORN = createTag("forge", "crops/corn");

        private static Tag<Block> createTag(String modid, String namespace) {
            return new BlockTags.Wrapper(new ResourceLocation(modid, namespace));
        }
    }

    public static class Fluids {
        // ----------------------- TAGS FOR FORGE -----------------------
        public static final Tag<Fluid> MUST = createTag("forge", "must");
        public static final Tag<Fluid> BARBERA = createTag("forge", "must/barbera");
        public static final Tag<Fluid> CABERNET_SAUVIGNON = createTag("forge", "must/cabernet_sauvignon");
        public static final Tag<Fluid> MERLOT = createTag("forge", "must/merlot");
        public static final Tag<Fluid> RED_GLOBE = createTag("forge", "must/red_globe");
        public static final Tag<Fluid> KOSHU = createTag("forge", "must/koshu");
        public static final Tag<Fluid> RIESLING = createTag("forge", "must/riesling");

        public static final Tag<Fluid> FERMENTED_MUST = createTag("forge", "fermented_must");
        public static final Tag<Fluid> FERMENTED_BARBERA = createTag("forge", "fermented_must/barbera");
        public static final Tag<Fluid> FERMENTED_CABERNET_SAUVIGNON = createTag("forge", "fermented_must/cabernet_sauvignon");
        public static final Tag<Fluid> FERMENTED_MERLOT = createTag("forge", "fermented_must/merlot");
        public static final Tag<Fluid> FERMENTED_RED_GLOBE = createTag("forge", "fermented_must/red_globe");
        public static final Tag<Fluid> FERMENTED_KOSHU = createTag("forge", "fermented_must/koshu");
        public static final Tag<Fluid> FERMENTED_RIESLING = createTag("forge", "fermented_must/riesling");

        public static final Tag<Fluid> FILTERED_FERMENTED_MUST = createTag("forge", "filtered_must");
        public static final Tag<Fluid> FILTERED_BARBERA = createTag("forge", "filtered_must/barbera");
        public static final Tag<Fluid> FILTERED_CABERNET_SAUVIGNON = createTag("forge", "filtered_must/cabernet_sauvignon");
        public static final Tag<Fluid> FILTERED_MERLOT = createTag("forge", "filtered_must/merlot");
        public static final Tag<Fluid> FILTERED_RED_GLOBE = createTag("forge", "filtered_must/red_globe");
        public static final Tag<Fluid> FILTERED_KOSHU = createTag("forge", "filtered_must/koshu");
        public static final Tag<Fluid> FILTERED_RIESLING = createTag("forge", "filtered_must/riesling");

        public static final Tag<Fluid> WINE = createTag("forge", "wine");
        public static final Tag<Fluid> BARBERA_WINE = createTag("forge", "wine/barbera");
        public static final Tag<Fluid> CABERNET_SAUVIGNON_WINE = createTag("forge", "wine/cabernet_sauvignon");
        public static final Tag<Fluid> MERLOT_WINE = createTag("forge", "wine/merlot");
        public static final Tag<Fluid> RED_GLOBE_WINE = createTag("forge", "wine/red_globe");
        public static final Tag<Fluid> KOSHU_WINE = createTag("forge", "wine/koshu");
        public static final Tag<Fluid> RIESLING_WINE = createTag("forge", "wine/riesling");

        public static final Tag<Fluid> SAKE = createTag("forge", "sake");
        public static final Tag<Fluid> SAKE_STEPS = createTag("forge", "sake/steps");

        private static Tag<Fluid> createTag(String modid, String namespace) {
            return new FluidTags.Wrapper(new ResourceLocation(modid, namespace));
        }
    }

}
