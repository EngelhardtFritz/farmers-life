package com.asheriit.asheriitsfarmerslife.utils;

import com.asheriit.asheriitsfarmerslife.config.FarmersLifeConfig;
import com.asheriit.asheriitsfarmerslife.init.ModAlcoholicItems;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModItems;
import com.google.common.collect.Ordering;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ItemGroupSortingHelper {
    private static final List<Item> BEFORE_FERTILIZER_BLOCKS = Arrays.asList(
            ModBlocks.TEST_BLOCK_A_ITEM.get(),
            ModBlocks.TEST_BLOCK_B_ITEM.get(),

            ModBlocks.SHORT_GRASS_ITEM.get(),
            ModBlocks.GARDEN_DIRT_ITEM.get(),
            // ------------------------- FARMLAND -------------------------
            ModBlocks.GARDEN_FARMLAND_ITEM.get(),
            ModBlocks.SUBMERGED_GRASS_FARMLAND_ITEM.get(),
            ModBlocks.SUBMERGED_DIRT_FARMLAND_ITEM.get(),
            // ------------------------- CROPS -------------------------
            ModBlocks.PEAT_MOSS_ITEM.get(),
            // ------------------------- TRELLIS SEEDS AND CROPS -------------------------
            ModItems.SEED_POUCH.get(),
            ModBlocks.BARBERA_TRELLIS_SEED_ITEM.get(),
            ModBlocks.CABERNET_TRELLIS_SEED_ITEM.get(),
            ModBlocks.MERLOT_TRELLIS_SEED_ITEM.get(),
            ModBlocks.RED_GLOBE_TRELLIS_SEED_ITEM.get(),
            ModBlocks.KOSHU_TRELLIS_SEED_ITEM.get(),
            ModBlocks.RIESLING_TRELLIS_SEED_ITEM.get(),
            ModBlocks.SULTANA_TRELLIS_SEED_ITEM.get(),
            ModItems.BARBERA_CROP.get(),
            ModItems.CABERNET_CROP.get(),
            ModItems.MERLOT_CROP.get(),
            ModItems.RED_GLOBE_CROP.get(),
            ModItems.KOSHU_CROP.get(),
            ModItems.RIESLING_CROP.get(),
            ModItems.SULTANA_CROP.get(),
            // ------------------------- SUBMERGED CROPS -------------------------
            ModBlocks.RICE_JAPONICA_SEED_ITEM.get(),
            ModBlocks.RICE_BLACK_JAPONICA_SEED_ITEM.get(),
            ModBlocks.RICE_INDICA_SEED_ITEM.get(),
            ModBlocks.RICE_YAMADANISHIKI_SEED_ITEM.get(),
            // ------------------------- LARGE CROPS -------------------------
            ModItems.CORN_CROP.get(),
            ModBlocks.CORN_SEED_ITEM.get(),
            // ------------------------- RAW FOOD RESOURCES -------------------------
            ModItems.RICE_JAPONICA_CROP.get(),
            ModItems.RICE_BLACK_JAPONICA_CROP.get(),
            ModItems.RICE_INDICA_CROP.get(),
            ModItems.RICE_YAMADANISHIKI_CROP.get(),
            // ------------------------- THRESHED GRAINS -------------------------
            ModItems.RICE_GRAINS_JAPONICA.get(),
            ModItems.RICE_GRAINS_BLACK_JAPONICA.get(),
            ModItems.RICE_GRAINS_INDICA.get(),
            ModItems.RICE_GRAINS_YAMADANISHIKI.get(),
            // ------------------------- SOAKED GRAINS -------------------------
            ModItems.SOAKED_RICE_GRAINS_JAPONICA.get(),
            ModItems.SOAKED_RICE_GRAINS_BLACK_JAPONICA.get(),
            ModItems.SOAKED_RICE_GRAINS_INDICA.get(),
            ModItems.SOAKED_RICE_GRAINS_YAMADANISHIKI.get(),
            // ------------------------- STEAMED GRAINS -------------------------
            ModItems.STEAMED_RICE_GRAINS_JAPONICA.get(),
            ModItems.STEAMED_RICE_GRAINS_BLACK_JAPONICA.get(),
            ModItems.STEAMED_RICE_GRAINS_INDICA.get(),
            ModItems.STEAMED_RICE_GRAINS_YAMADANISHIKI.get(),
            // ------------------------- FOOD INGREDIENTS -------------------------
            ModItems.WHEAT_FLOUR.get(),
            // ------------------------- TRELLIS -------------------------
            ModBlocks.WOOD_TRELLIS_ITEM.get()
    );

    private static final List<Item> FERTILIZER_ITEMS = Arrays.asList(
            // ------------------------- FERTILIZER -------------------------
            ModItems.EMPTY_SMALL_FERTILIZER_BAG.get(),
            ModItems.EMPTY_MEDIUM_FERTILIZER_BAG.get(),
            ModItems.EMPTY_LARGE_FERTILIZER_BAG.get(),
            ModItems.RAW_ANIMAL_WASTE_FERTILIZER.get(),
            ModBlocks.COMPRESSED_RAW_ANIMAL_WASTE_FERTILIZER_ITEM.get(),
            ModItems.SMALL_ANIMAL_WASTE_FERTILIZER.get(),
            ModItems.MEDIUM_ANIMAL_WASTE_FERTILIZER.get(),
            ModItems.LARGE_ANIMAL_WASTE_FERTILIZER.get(),
            ModItems.RAW_PEAT_FERTILIZER.get(),
            ModBlocks.COMPRESSED_RAW_PEAT_FERTILIZER_ITEM.get(),
            ModItems.SMALL_PEAT_FERTILIZER.get(),
            ModItems.MEDIUM_PEAT_FERTILIZER.get(),
            ModItems.LARGE_PEAT_FERTILIZER.get(),
            ModItems.RAW_PLANT_TYPE_FERTILIZER.get(),
            ModBlocks.COMPRESSED_RAW_PLANT_TYPE_FERTILIZER_ITEM.get(),
            ModItems.SMALL_PLANT_TYPE_FERTILIZER.get(),
            ModItems.MEDIUM_PLANT_TYPE_FERTILIZER.get(),
            ModItems.LARGE_PLANT_TYPE_FERTILIZER.get()
    );

    private static final List<Item> WATERING_CAN_ITEMS = Arrays.asList(
            // -------------------------  WATERING CANS -------------------------
            ModItems.SMALL_WATERING_CAN.get(),
            ModItems.MEDIUM_WATERING_CAN.get(),
            ModItems.LARGE_WATERING_CAN.get()
    );

    private static final List<Item> TOOL_ITEMS = Arrays.asList(
            // ------------------------- TOOLS - SPADES -------------------------
            ModItems.WOOD_SPADE.get(),
            ModItems.STONE_SPADE.get(),
            ModItems.IRON_SPADE.get(),
            ModItems.GOLD_SPADE.get(),
            ModItems.DIAMOND_SPADE.get(),
            // ------------------------- TOOLS - FLAILS -------------------------
            ModItems.WOOD_FLAIL.get(),
            ModItems.STONE_FLAIL.get(),
            ModItems.IRON_FLAIL.get(),
            ModItems.GOLD_FLAIL.get(),
            ModItems.DIAMOND_FLAIL.get()
    );

    private static final List<Item> MACHINE_ITEMS = Arrays.asList(
            // -------------------------  WORKBENCH -------------------------
            ModBlocks.FARMER_WORKBENCH_ITEM.get(),
            // -------------------------  FUNCTIONAL BLOCKS -------------------------
            ModBlocks.THRESHING_RICE_JAPONICA_ITEM.get(),
            ModBlocks.THRESHING_RICE_BLACK_JAPONICA_ITEM.get(),
            ModBlocks.THRESHING_RICE_INDICA_ITEM.get(),
            ModBlocks.THRESHING_RICE_YAMADANISHIKI_ITEM.get(),
            // -------------------------  MACHINES -------------------------
            ModBlocks.DIRT_DRYING_BED_ITEM.get(),
            ModBlocks.DRYING_MACHINE_ITEM.get(),
            ModBlocks.WOOD_WINE_PRESS_ITEM.get(),
            ModBlocks.STOMPING_BARREL_ITEM.get(),
            ModBlocks.FERTILIZER_COMPOSTER_ITEM.get(),
            ModBlocks.FERMENTING_BARREL_ITEM.get(),
            ModBlocks.WOOD_FLUID_STORAGE_BARREL_ITEM.get(),
            ModBlocks.FILTRATION_MACHINE_ITEM.get(),
            ModBlocks.FINING_MACHINE_ITEM.get(),
            ModBlocks.BOTTLING_MACHINE_ITEM.get(),
            ModBlocks.AGING_WINE_RACK_ITEM.get(),
            ModBlocks.BOILING_CAULDRON_ITEM.get(),
            ModBlocks.TEMPERATURE_CHAMBER_ITEM.get()/*,
            ModBlocks.MORTAR_AND_PESTLE_ITEM.get()*/
    );

    private static final List<Item> STORAGE_BLOCKS = Arrays.asList(
            // -------------------------  STORAGE BLOCKS - WINE -------------------------
            ModBlocks.ACACIA_WINE_RACK_ITEM.get(),
            ModBlocks.BIRCH_WINE_RACK_ITEM.get(),
            ModBlocks.DARK_OAK_WINE_RACK_ITEM.get(),
            ModBlocks.JUNGLE_WINE_RACK_ITEM.get(),
            ModBlocks.OAK_WINE_RACK_ITEM.get(),
            ModBlocks.SPRUCE_WINE_RACK_ITEM.get()
    );

    private static final List<Item> FOOD_POTIONS = Arrays.asList(
            // ------------------------- WINE BOTTLES -------------------------
            ModItems.CORKED_GLASS_BOTTLE.get(),
            ModItems.WINE_CORK.get(),
            // ------------------------- BARBERA WINE -------------------------
            ModAlcoholicItems.BARBERA_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_BARBERA_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_BARBERA_WINE_BOTTLE.get(),
            ModAlcoholicItems.ENRICHED_BARBERA_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_ENRICHED_BARBERA_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_ENRICHED_BARBERA_WINE_BOTTLE.get(),
            ModAlcoholicItems.POTENT_BARBERA_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_POTENT_BARBERA_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_POTENT_BARBERA_WINE_BOTTLE.get(),
            // ------------------------- CABERNET SAUVIGNON WINE -------------------------
            ModAlcoholicItems.CABERNET_SAUVIGNON_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_CABERNET_SAUVIGNON_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_CABERNET_SAUVIGNON_WINE_BOTTLE.get(),
            ModAlcoholicItems.ENRICHED_CABERNET_SAUVIGNON_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_ENRICHED_CABERNET_SAUVIGNON_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_ENRICHED_CABERNET_SAUVIGNON_WINE_BOTTLE.get(),
            ModAlcoholicItems.POTENT_CABERNET_SAUVIGNON_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_POTENT_CABERNET_SAUVIGNON_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_POTENT_CABERNET_SAUVIGNON_WINE_BOTTLE.get(),
            // ------------------------- MERLOT WINE -------------------------
            ModAlcoholicItems.MERLOT_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_MERLOT_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_MERLOT_WINE_BOTTLE.get(),
            ModAlcoholicItems.ENRICHED_MERLOT_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_ENRICHED_MERLOT_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_ENRICHED_MERLOT_WINE_BOTTLE.get(),
            ModAlcoholicItems.POTENT_MERLOT_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_POTENT_MERLOT_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_POTENT_MERLOT_WINE_BOTTLE.get(),
            // ------------------------- RED GLOBE WINE -------------------------
            ModAlcoholicItems.RED_GLOBE_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_RED_GLOBE_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_RED_GLOBE_WINE_BOTTLE.get(),
            ModAlcoholicItems.ENRICHED_RED_GLOBE_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_ENRICHED_RED_GLOBE_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_ENRICHED_RED_GLOBE_WINE_BOTTLE.get(),
            ModAlcoholicItems.POTENT_RED_GLOBE_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_POTENT_RED_GLOBE_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_POTENT_RED_GLOBE_WINE_BOTTLE.get(),
            // ------------------------- KOSHU WINE -------------------------
            ModAlcoholicItems.KOSHU_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_KOSHU_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_KOSHU_WINE_BOTTLE.get(),
            ModAlcoholicItems.ENRICHED_KOSHU_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_ENRICHED_KOSHU_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_ENRICHED_KOSHU_WINE_BOTTLE.get(),
            ModAlcoholicItems.POTENT_KOSHU_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_POTENT_KOSHU_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_POTENT_KOSHU_WINE_BOTTLE.get(),
            // ------------------------- RIESLING WINE -------------------------
            ModAlcoholicItems.RIESLING_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_RIESLING_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_RIESLING_WINE_BOTTLE.get(),
            ModAlcoholicItems.ENRICHED_RIESLING_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_ENRICHED_RIESLING_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_ENRICHED_RIESLING_WINE_BOTTLE.get(),
            ModAlcoholicItems.POTENT_RIESLING_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_POTENT_RIESLING_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_POTENT_RIESLING_WINE_BOTTLE.get(),
            // ------------------------- FOOD - MISC -------------------------
            ModItems.EGG_PROTEIN.get(),
            ModItems.EGG_YOLK.get()
    );

    private static final List<Item> ORE_ITEMS = Arrays.asList(
            // -------------------------  ORE -------------------------
            ModItems.ROCK_SALT.get(),
            ModBlocks.COMPRESSED_ROCK_SALT_ITEM.get(),
            ModItems.SEA_SALT.get(),
            ModBlocks.COMPRESSED_SEA_SALT_ITEM.get(),
            ModBlocks.SALT_ORE_ITEM.get(),
            // -------------------------  FURNACE ITEMS -------------------------
            ModItems.MOLTEN_GLASS.get()
    );

    private static final List<Item> TREE_BLOCKS = Arrays.asList(
            ModBlocks.CORK_OAK_LOG_ITEM.get(),
            ModBlocks.STRIPPED_CORK_OAK_LOG_ITEM.get(),
            ModBlocks.CORK_OAK_LEAVES_ITEM.get(),
            ModBlocks.CORK_OAK_SAPLING_ITEM.get(),
            ModItems.CORK_BARK.get()
    );

    private static final List<Item> DAM_ITEMS = Arrays.asList(
            // -------------------------  DAM -------------------------
            ModBlocks.DIRT_SMALL_DAM_ITEM.get(),
            ModBlocks.DIRT_LARGE_DAM_ITEM.get(),
            ModBlocks.GRASS_SMALL_DAM_ITEM.get(),
            ModBlocks.GRASS_LARGE_DAM_ITEM.get()
    );

    private static final List<Item> GLASS_ITEMS = Arrays.asList(
            // -------------------------  GLASS -------------------------
            ModBlocks.MIDDLE_GLASS_DOOR_ITEM.get(),
            ModBlocks.GLASS_FLOOR_ITEM.get(),
            ModBlocks.WHITE_STAINED_GLASS_FLOOR_ITEM.get(),
            ModBlocks.ORANGE_STAINED_GLASS_FLOOR_ITEM.get(),
            ModBlocks.MAGENTA_STAINED_GLASS_FLOOR_ITEM.get(),
            ModBlocks.LIGHT_BLUE_STAINED_GLASS_FLOOR_ITEM.get(),
            ModBlocks.YELLOW_STAINED_GLASS_FLOOR_ITEM.get(),
            ModBlocks.LIME_STAINED_GLASS_FLOOR_ITEM.get(),
            ModBlocks.PINK_STAINED_GLASS_FLOOR_ITEM.get(),
            ModBlocks.GRAY_STAINED_GLASS_FLOOR_ITEM.get(),
            ModBlocks.LIGHT_GRAY_STAINED_GLASS_FLOOR_ITEM.get(),
            ModBlocks.CYAN_STAINED_GLASS_FLOOR_ITEM.get(),
            ModBlocks.PURPLE_STAINED_GLASS_FLOOR_ITEM.get(),
            ModBlocks.BLUE_STAINED_GLASS_FLOOR_ITEM.get(),
            ModBlocks.BROWN_STAINED_GLASS_FLOOR_ITEM.get(),
            ModBlocks.GREEN_STAINED_GLASS_FLOOR_ITEM.get(),
            ModBlocks.RED_STAINED_GLASS_FLOOR_ITEM.get(),
            ModBlocks.BLACK_STAINED_GLASS_FLOOR_ITEM.get()
    );

    private static final List<Item> MISC_ITEMS = Arrays.asList(
            // -------------------------  MISC - CROP "WASTE" -------------------------
            ModItems.RICE_IN_HUSK.get(),
            ModItems.KOJI_SPORES.get(),
            ModItems.SPOILED_KOJI_SPORES.get(),
            ModItems.RICE_HUSK.get(),
            ModItems.CORN_HUSK.get(),
            ModItems.CHAFF.get(),
            ModItems.CHAFF_ROD.get(),
            // -------------------------  MISC - FLUID FILTER -------------------------
            ModItems.PAPER_FLUID_FILTER.get(),
            // -------------------------  MISC -------------------------
            ModItems.BOILING_CAULDRON_LID.get(),
            ModItems.SMALL_WATER_TUBE.get(),
            ModItems.WATER_TUBE.get(),
            ModItems.SMALL_WATERING_CAN_BODY.get(),
            ModItems.MEDIUM_WATERING_CAN_BODY.get(),
            ModItems.LARGE_WATERING_CAN_BODY.get(),
            ModItems.WATERING_CAN_HANDLE.get(),
            ModBlocks.PEAT_STAGE_ONE_ITEM.get(),
            ModBlocks.PEAT_STAGE_TWO_ITEM.get(),
            ModBlocks.PEAT_STAGE_THREE_ITEM.get(),
            ModItems.WINE_PRESS_PISTON.get(),
            ModItems.WINE_PRESS_MOUNT.get(),
            ModItems.WOOD_BARREL_LID.get(),
            ModItems.GRAPE_WASTE.get()
    );

    private static final List<Item> BUCKET_ITEMS = Arrays.asList(
            // -------------------------  BUCKETS - BARBERA -------------------------
            ModItems.BARBERA_MUST_BUCKET.get(),
            ModItems.FERMENTED_BARBERA_MUST_BUCKET.get(),
            ModItems.FILTERED_FERMENTED_BARBERA_MUST_BUCKET.get(),
            ModItems.BARBERA_WINE_BUCKET.get(),
            // -------------------------  BUCKETS - CABERNET SAUVIGNON -------------------------
            ModItems.CABERNET_SAUVIGNON_MUST_BUCKET.get(),
            ModItems.FERMENTED_CABERNET_SAUVIGNON_MUST_BUCKET.get(),
            ModItems.FILTERED_FERMENTED_CABERNET_SAUVIGNON_MUST_BUCKET.get(),
            ModItems.CABERNET_SAUVIGNON_WINE_BUCKET.get(),
            // -------------------------  BUCKETS - MERLOT -------------------------
            ModItems.MERLOT_MUST_BUCKET.get(),
            ModItems.FERMENTED_MERLOT_MUST_BUCKET.get(),
            ModItems.FILTERED_FERMENTED_MERLOT_MUST_BUCKET.get(),
            ModItems.MERLOT_WINE_BUCKET.get(),
            // -------------------------  BUCKETS - RED GLOBE -------------------------
            ModItems.RED_GLOBE_MUST_BUCKET.get(),
            ModItems.FERMENTED_RED_GLOBE_MUST_BUCKET.get(),
            ModItems.FILTERED_FERMENTED_RED_GLOBE_MUST_BUCKET.get(),
            ModItems.RED_GLOBE_WINE_BUCKET.get(),
            // -------------------------  BUCKETS - KOSHU -------------------------
            ModItems.KOSHU_MUST_BUCKET.get(),
            ModItems.FERMENTED_KOSHU_MUST_BUCKET.get(),
            ModItems.FILTERED_FERMENTED_KOSHU_MUST_BUCKET.get(),
            ModItems.KOSHU_WINE_BUCKET.get(),
            // -------------------------  BUCKETS - RIESLING -------------------------
            ModItems.RIESLING_MUST_BUCKET.get(),
            ModItems.FERMENTED_RIESLING_MUST_BUCKET.get(),
            ModItems.FILTERED_FERMENTED_RIESLING_MUST_BUCKET.get(),
            ModItems.RIESLING_WINE_BUCKET.get(),
            // -------------------------  BUCKETS - SAKE -------------------------
            ModItems.KOJI_RICE_SHUBO_WATER_MIX_SPOILED_BUCKET.get(),
            ModItems.FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_BUCKET.get(),
            ModItems.FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_BUCKET.get(),
            ModItems.SAKE_BUCKET.get()
    );


    public static Comparator<ItemStack> orderCreativeTabs() {
        List<Item> orderedItemList = new ArrayList<>(BEFORE_FERTILIZER_BLOCKS);

        orderedItemList.addAll(FERTILIZER_ITEMS);
        orderedItemList.addAll(WATERING_CAN_ITEMS);
        orderedItemList.addAll(TOOL_ITEMS);
        orderedItemList.addAll(MACHINE_ITEMS);
        orderedItemList.addAll(STORAGE_BLOCKS);
        orderedItemList.addAll(FOOD_POTIONS);
        orderedItemList.addAll(ORE_ITEMS);
        orderedItemList.addAll(TREE_BLOCKS);

        if (FarmersLifeConfig.COMMON.enableDamBlockRecipes.get()) {
            orderedItemList.addAll(DAM_ITEMS);
        }

        orderedItemList.addAll(GLASS_ITEMS);
        orderedItemList.addAll(MISC_ITEMS);
        orderedItemList.addAll(BUCKET_ITEMS);

        return Ordering.explicit(orderedItemList).onResultOf(ItemStack::getItem);
    }

    public static boolean isItemDisabled(Item item) {
        if (!FarmersLifeConfig.COMMON.enableDamBlockRecipes.get() && (item == ModBlocks.DIRT_SMALL_DAM_ITEM.get() ||
                item == ModBlocks.DIRT_LARGE_DAM_ITEM.get() || item == ModBlocks.GRASS_SMALL_DAM_ITEM.get() ||
                item == ModBlocks.GRASS_LARGE_DAM_ITEM.get())) {
            return true;
        }

        return false;
    }
}
