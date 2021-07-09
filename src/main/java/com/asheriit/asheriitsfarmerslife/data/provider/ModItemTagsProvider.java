package com.asheriit.asheriitsfarmerslife.data.provider;

import com.asheriit.asheriitsfarmerslife.init.ModAlcoholicItems;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModItems;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerTags() {
        // ---------------------------------------- REGISTER TO FORGE TAGS ----------------------------------------
        this.registerForgeItemTags();

        // ---------------------------------------- REGISTER TO MINECRAFT TAGS ----------------------------------------
        this.registerMinecraftItemTags();

        // ---------------------------------------- CROPS ----------------------------------------
        getBuilder(ModTags.Items.CROPS_RICE)
                .add(ModItems.RICE_JAPONICA_CROP.get())
                .add(ModItems.RICE_BLACK_JAPONICA_CROP.get())
                .add(ModItems.RICE_INDICA_CROP.get())
                .add(ModItems.RICE_YAMADANISHIKI_CROP.get());

        getBuilder(ModTags.Items.CROPS_WINE_GRAPES)
                .add(ModItems.BARBERA_CROP.get())
                .add(ModItems.CABERNET_CROP.get())
                .add(ModItems.MERLOT_CROP.get())
                .add(ModItems.RED_GLOBE_CROP.get())
                .add(ModItems.KOSHU_CROP.get())
                .add(ModItems.RIESLING_CROP.get())
                .add(ModItems.SULTANA_CROP.get());

        getBuilder(ModTags.Items.CROPS_CORN)
                .add(ModItems.CORN_CROP.get());

        // ---------------------------------------- GRAINS ----------------------------------------
        getBuilder(ModTags.Items.GRAINS)
                .add(ModTags.Items.GRAINS_RICE)
                .add(ModTags.Items.SOAKED_GRAINS_RICE);

        getBuilder(ModTags.Items.GRAINS_RICE)
                .add(ModItems.RICE_GRAINS_JAPONICA.get())
                .add(ModItems.RICE_GRAINS_BLACK_JAPONICA.get())
                .add(ModItems.RICE_GRAINS_INDICA.get())
                .add(ModItems.RICE_GRAINS_YAMADANISHIKI.get());

        getBuilder(ModTags.Items.SOAKED_GRAINS_RICE)
                .add(ModItems.SOAKED_RICE_GRAINS_JAPONICA.get())
                .add(ModItems.SOAKED_RICE_GRAINS_BLACK_JAPONICA.get())
                .add(ModItems.SOAKED_RICE_GRAINS_INDICA.get())
                .add(ModItems.SOAKED_RICE_GRAINS_YAMADANISHIKI.get());

        getBuilder(ModTags.Items.STEAMED_GRAINS_RICE)
                .add(ModItems.STEAMED_RICE_GRAINS_JAPONICA.get())
                .add(ModItems.STEAMED_RICE_GRAINS_BLACK_JAPONICA.get())
                .add(ModItems.STEAMED_RICE_GRAINS_INDICA.get())
                .add(ModItems.STEAMED_RICE_GRAINS_YAMADANISHIKI.get());

        // ---------------------------------------- SEEDS ----------------------------------------
        getBuilder(ModTags.Items.SEEDS_RICE)
                .add(ModBlocks.RICE_JAPONICA_SEED_ITEM.get())
                .add(ModBlocks.RICE_BLACK_JAPONICA_SEED_ITEM.get())
                .add(ModBlocks.RICE_INDICA_SEED_ITEM.get())
                .add(ModBlocks.RICE_YAMADANISHIKI_SEED_ITEM.get());

        getBuilder(ModTags.Items.SEEDS_WINE_GRAPES)
                .add(ModBlocks.BARBERA_TRELLIS_SEED_ITEM.get())
                .add(ModBlocks.CABERNET_TRELLIS_SEED_ITEM.get())
                .add(ModBlocks.MERLOT_TRELLIS_SEED_ITEM.get())
                .add(ModBlocks.RED_GLOBE_TRELLIS_SEED_ITEM.get())
                .add(ModBlocks.KOSHU_TRELLIS_SEED_ITEM.get())
                .add(ModBlocks.RIESLING_TRELLIS_SEED_ITEM.get())
                .add(ModBlocks.SULTANA_TRELLIS_SEED_ITEM.get());

        getBuilder(ModTags.Items.SEEDS_CORN)
                .add(ModBlocks.CORN_SEED_ITEM.get());

        // ---------------------------------------- HUSKS ----------------------------------------
        getBuilder(ModTags.Items.HUSKS)
                .add(ModItems.RICE_HUSK.get())
                .add(ModItems.CORN_HUSK.get());

        // ---------------------------------------- SPADES ----------------------------------------
        getBuilder(ModTags.Items.SPADES)
                .add(ModItems.WOOD_SPADE.get())
                .add(ModItems.STONE_SPADE.get())
                .add(ModItems.IRON_SPADE.get())
                .add(ModItems.GOLD_SPADE.get())
                .add(ModItems.DIAMOND_SPADE.get());

        // ---------------------------------------- RAW FERTILIZER ----------------------------------------
        getBuilder(ModTags.Items.RAW_FERTILIZER)
                .add(ModItems.RAW_ANIMAL_WASTE_FERTILIZER.get())
                .add(ModItems.RAW_PEAT_FERTILIZER.get())
                .add(ModItems.RAW_PLANT_TYPE_FERTILIZER.get());

        // ---------------------------------------- FERTILIZER ----------------------------------------
        getBuilder(ModTags.Items.FERTILIZER)
                .add(ModItems.SMALL_ANIMAL_WASTE_FERTILIZER.get())
                .add(ModItems.SMALL_PEAT_FERTILIZER.get())
                .add(ModItems.SMALL_PLANT_TYPE_FERTILIZER.get())
                .add(ModItems.MEDIUM_ANIMAL_WASTE_FERTILIZER.get())
                .add(ModItems.MEDIUM_PEAT_FERTILIZER.get())
                .add(ModItems.MEDIUM_PLANT_TYPE_FERTILIZER.get())
                .add(ModItems.LARGE_ANIMAL_WASTE_FERTILIZER.get())
                .add(ModItems.LARGE_PEAT_FERTILIZER.get())
                .add(ModItems.LARGE_PLANT_TYPE_FERTILIZER.get());

        // ---------------------------------------- WATERING_CANS ----------------------------------------
        getBuilder(ModTags.Items.WATERING_CANS)
                .add(ModItems.SMALL_WATERING_CAN.get())
                .add(ModItems.MEDIUM_WATERING_CAN.get())
                .add(ModItems.LARGE_WATERING_CAN.get());

        // ---------------------------------------- FINING INGREDIENTS ----------------------------------------
        getBuilder(ModTags.Items.PRECIPITATION_MATERIALS)
                .add(ModItems.EGG_PROTEIN.get())
                .add(Items.CLAY_BALL);

        // ---------------------------------------- ORGANIC WASTE ----------------------------------------
        getBuilder(ModTags.Items.ORGANIC_WASTE)
                .add(ModItems.GRAPE_WASTE.get());

        // ---------------------------------------- WINE BOTTLES ----------------------------------------
        getBuilder(ModTags.Items.WINE_BOTTLES)
                .add(ModItems.CORKED_GLASS_BOTTLE.get())
                .add(ModAlcoholicItems.BARBERA_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.CABERNET_SAUVIGNON_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.MERLOT_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.RED_GLOBE_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.KOSHU_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.RIESLING_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.ENRICHED_BARBERA_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.ENRICHED_CABERNET_SAUVIGNON_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.ENRICHED_MERLOT_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.ENRICHED_RED_GLOBE_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.ENRICHED_KOSHU_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.ENRICHED_RIESLING_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.POTENT_BARBERA_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.POTENT_CABERNET_SAUVIGNON_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.POTENT_MERLOT_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.POTENT_RED_GLOBE_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.POTENT_KOSHU_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.POTENT_RIESLING_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.AGED_BARBERA_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.AGED_CABERNET_SAUVIGNON_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.AGED_MERLOT_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.AGED_RED_GLOBE_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.AGED_KOSHU_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.AGED_RIESLING_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.AGED_ENRICHED_BARBERA_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.AGED_ENRICHED_CABERNET_SAUVIGNON_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.AGED_ENRICHED_MERLOT_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.AGED_ENRICHED_RED_GLOBE_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.AGED_ENRICHED_KOSHU_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.AGED_ENRICHED_RIESLING_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.AGED_POTENT_BARBERA_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.AGED_POTENT_CABERNET_SAUVIGNON_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.AGED_POTENT_MERLOT_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.AGED_POTENT_RED_GLOBE_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.AGED_POTENT_KOSHU_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.AGED_POTENT_RIESLING_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.OLD_BARBERA_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.OLD_CABERNET_SAUVIGNON_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.OLD_MERLOT_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.OLD_RED_GLOBE_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.OLD_KOSHU_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.OLD_RIESLING_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.OLD_ENRICHED_BARBERA_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.OLD_ENRICHED_CABERNET_SAUVIGNON_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.OLD_ENRICHED_MERLOT_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.OLD_ENRICHED_RED_GLOBE_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.OLD_ENRICHED_KOSHU_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.OLD_ENRICHED_RIESLING_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.OLD_POTENT_BARBERA_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.OLD_POTENT_CABERNET_SAUVIGNON_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.OLD_POTENT_MERLOT_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.OLD_POTENT_RED_GLOBE_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.OLD_POTENT_KOSHU_WINE_BOTTLE.get())
                .add(ModAlcoholicItems.OLD_POTENT_RIESLING_WINE_BOTTLE.get());

        // ---------------------------------------- CORK OAK TAG ----------------------------------------
        getBuilder(ModTags.Items.CORK_OAK_LOGS)
                .add(ModBlocks.CORK_OAK_LOG_ITEM.get())
                .add(ModBlocks.STRIPPED_CORK_OAK_LOG_ITEM.get());
    }

    private void registerForgeItemTags() {
        // ---------------------------------------- SEEDS ----------------------------------------
        getBuilder(Tags.Items.SEEDS)
                .add(ModTags.Items.SEEDS_RICE)
                .add(ModTags.Items.SEEDS_WINE_GRAPES)
                .add(ModTags.Items.SEEDS_CORN);

        // ---------------------------------------- CROPS ----------------------------------------
        getBuilder(Tags.Items.CROPS)
                .add(ModTags.Items.CROPS_RICE)
                .add(ModTags.Items.CROPS_WINE_GRAPES)
                .add(ModTags.Items.CROPS_CORN);

        // ---------------------------------------- SALT ----------------------------------------
        getBuilder(ModTags.Items.SALT)
                .add(ModItems.ROCK_SALT.get())
                .add(ModItems.SEA_SALT.get());

        // ---------------------------------------- GLASS ----------------------------------------
        getBuilder(Tags.Items.GLASS_BLACK).add(ModBlocks.BLACK_STAINED_GLASS_FLOOR_ITEM.get());
        getBuilder(Tags.Items.GLASS_BLUE).add(ModBlocks.BLUE_STAINED_GLASS_FLOOR_ITEM.get());
        getBuilder(Tags.Items.GLASS_BROWN).add(ModBlocks.BROWN_STAINED_GLASS_FLOOR_ITEM.get());
        getBuilder(Tags.Items.GLASS_COLORLESS).add(ModBlocks.GLASS_FLOOR_ITEM.get());
        getBuilder(Tags.Items.GLASS_CYAN).add(ModBlocks.CYAN_STAINED_GLASS_FLOOR_ITEM.get());
        getBuilder(Tags.Items.GLASS_GRAY).add(ModBlocks.GRAY_STAINED_GLASS_FLOOR_ITEM.get());
        getBuilder(Tags.Items.GLASS_GREEN).add(ModBlocks.GREEN_STAINED_GLASS_FLOOR_ITEM.get());
        getBuilder(Tags.Items.GLASS_LIGHT_BLUE).add(ModBlocks.LIGHT_BLUE_STAINED_GLASS_FLOOR_ITEM.get());
        getBuilder(Tags.Items.GLASS_LIGHT_GRAY).add(ModBlocks.LIGHT_GRAY_STAINED_GLASS_FLOOR_ITEM.get());
        getBuilder(Tags.Items.GLASS_LIME).add(ModBlocks.LIME_STAINED_GLASS_FLOOR_ITEM.get());
        getBuilder(Tags.Items.GLASS_MAGENTA).add(ModBlocks.MAGENTA_STAINED_GLASS_FLOOR_ITEM.get());
        getBuilder(Tags.Items.GLASS_ORANGE).add(ModBlocks.ORANGE_STAINED_GLASS_FLOOR_ITEM.get());
        getBuilder(Tags.Items.GLASS_PINK).add(ModBlocks.PINK_STAINED_GLASS_FLOOR_ITEM.get());
        getBuilder(Tags.Items.GLASS_PURPLE).add(ModBlocks.PURPLE_STAINED_GLASS_FLOOR_ITEM.get());
        getBuilder(Tags.Items.GLASS_RED).add(ModBlocks.RED_STAINED_GLASS_FLOOR_ITEM.get());
        getBuilder(Tags.Items.GLASS_WHITE).add(ModBlocks.WHITE_STAINED_GLASS_FLOOR_ITEM.get());
        getBuilder(Tags.Items.GLASS_YELLOW).add(ModBlocks.YELLOW_STAINED_GLASS_FLOOR_ITEM.get());

        // ---------------------------------------- STAINED GLASS ----------------------------------------
        getBuilder(Tags.Items.STAINED_GLASS)
                .add(ModBlocks.BLACK_STAINED_GLASS_FLOOR_ITEM.get())
                .add(ModBlocks.BLUE_STAINED_GLASS_FLOOR_ITEM.get())
                .add(ModBlocks.BROWN_STAINED_GLASS_FLOOR_ITEM.get())
                .add(ModBlocks.CYAN_STAINED_GLASS_FLOOR_ITEM.get())
                .add(ModBlocks.GRAY_STAINED_GLASS_FLOOR_ITEM.get())
                .add(ModBlocks.GREEN_STAINED_GLASS_FLOOR_ITEM.get())
                .add(ModBlocks.LIGHT_BLUE_STAINED_GLASS_FLOOR_ITEM.get())
                .add(ModBlocks.LIGHT_GRAY_STAINED_GLASS_FLOOR_ITEM.get())
                .add(ModBlocks.LIME_STAINED_GLASS_FLOOR_ITEM.get())
                .add(ModBlocks.MAGENTA_STAINED_GLASS_FLOOR_ITEM.get())
                .add(ModBlocks.ORANGE_STAINED_GLASS_FLOOR_ITEM.get())
                .add(ModBlocks.PINK_STAINED_GLASS_FLOOR_ITEM.get())
                .add(ModBlocks.PURPLE_STAINED_GLASS_FLOOR_ITEM.get())
                .add(ModBlocks.RED_STAINED_GLASS_FLOOR_ITEM.get())
                .add(ModBlocks.WHITE_STAINED_GLASS_FLOOR_ITEM.get())
                .add(ModBlocks.YELLOW_STAINED_GLASS_FLOOR_ITEM.get());
    }

    private void registerMinecraftItemTags() {
        // ---------------------------------------- DOORS ----------------------------------------
        getBuilder(ItemTags.DOORS)
                .add(ModBlocks.MIDDLE_GLASS_DOOR_ITEM.get());

        // ---------------------------------------- SAPLING ----------------------------------------
        getBuilder(ItemTags.SAPLINGS)
                .add(ModBlocks.CORK_OAK_SAPLING_ITEM.get());

        // ---------------------------------------- LOGS ----------------------------------------
        getBuilder(ItemTags.LOGS)
                .add(ModTags.Items.CORK_OAK_LOGS);
    }
}
