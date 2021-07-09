package com.asheriit.asheriitsfarmerslife.init;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.objects.items.*;
import com.asheriit.asheriitsfarmerslife.properties.ItemTierType;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FarmersLife.MOD_ID);

    // --------------------------------------------- SEEDS - POUCH ---------------------------------------------
    public static final RegistryObject<Item> SEED_POUCH = ITEMS.register("seed_pouch", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));

    // --------------------------------------------- CROPS - WATERLOGGED ---------------------------------------------
    public static final RegistryObject<Item> RICE_JAPONICA_CROP = ITEMS.register("rice_japonica_crop", () ->
            new WaterloggedCropItem(ModBlocks.RICE_JAPONICA.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> RICE_BLACK_JAPONICA_CROP = ITEMS.register("rice_black_japonica_crop", () ->
            new WaterloggedCropItem(ModBlocks.RICE_BLACK_JAPONICA.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> RICE_INDICA_CROP = ITEMS.register("rice_indica_crop", () ->
            new WaterloggedCropItem(ModBlocks.RICE_INDICA.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> RICE_YAMADANISHIKI_CROP = ITEMS.register("rice_yamadanishiki_crop", () ->
            new WaterloggedCropItem(ModBlocks.RICE_YAMADANISHIKI.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // --------------------------------------------- CROPS - TRELLIS ---------------------------------------------
    public static final RegistryObject<Item> BARBERA_CROP = ITEMS.register("barbera_crop", () ->
            new TrellisCropItem(ModBlocks.BARBERA_TRELLIS_CROP.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> CABERNET_CROP = ITEMS.register("cabernet_sauvignon_crop", () ->
            new TrellisCropItem(ModBlocks.CABERNET_TRELLIS_CROP.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> MERLOT_CROP = ITEMS.register("merlot_crop", () ->
            new TrellisCropItem(ModBlocks.MERLOT_TRELLIS_CROP.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> RED_GLOBE_CROP = ITEMS.register("red_globe_crop", () ->
            new TrellisCropItem(ModBlocks.RED_GLOBE_TRELLIS_CROP.get(), new Item.Properties().food(new Food.Builder().hunger(1).saturation(0.0F).build()).group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> KOSHU_CROP = ITEMS.register("koshu_crop", () ->
            new TrellisCropItem(ModBlocks.KOSHU_TRELLIS_CROP.get(), new Item.Properties().food(new Food.Builder().hunger(1).saturation(0.2F).build()).group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> RIESLING_CROP = ITEMS.register("riesling_crop", () ->
            new TrellisCropItem(ModBlocks.RIESLING_TRELLIS_CROP.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> SULTANA_CROP = ITEMS.register("sultana_crop", () ->
            new TrellisCropItem(ModBlocks.SULTANA_TRELLIS_CROP.get(), new Item.Properties().food(new Food.Builder().hunger(2).saturation(0.2F).build()).group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // --------------------------------------------- CROPS - GRAINS (HUSKS AND CHAFF REMOVED) ---------------------------------------------
    public static final RegistryObject<Item> RICE_GRAINS_JAPONICA = ITEMS.register("rice_grains_japonica", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> RICE_GRAINS_BLACK_JAPONICA = ITEMS.register("rice_grains_black_japonica", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> RICE_GRAINS_INDICA = ITEMS.register("rice_grains_indica", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> RICE_GRAINS_YAMADANISHIKI = ITEMS.register("rice_grains_yamadanishiki", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // --------------------------------------------- CROPS - SOAKED ---------------------------------------------
    public static final RegistryObject<Item> SOAKED_RICE_GRAINS_JAPONICA = ITEMS.register("soaked_rice_grains_japonica", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> SOAKED_RICE_GRAINS_BLACK_JAPONICA = ITEMS.register("soaked_rice_grains_black_japonica", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> SOAKED_RICE_GRAINS_INDICA = ITEMS.register("soaked_rice_grains_indica", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> SOAKED_RICE_GRAINS_YAMADANISHIKI = ITEMS.register("soaked_rice_grains_yamadanishiki", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // --------------------------------------------- CROPS - STEAMED ---------------------------------------------
    public static final RegistryObject<Item> STEAMED_RICE_GRAINS_JAPONICA = ITEMS.register("steamed_rice_grains_japonica", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> STEAMED_RICE_GRAINS_BLACK_JAPONICA = ITEMS.register("steamed_rice_grains_black_japonica", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> STEAMED_RICE_GRAINS_INDICA = ITEMS.register("steamed_rice_grains_indica", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> STEAMED_RICE_GRAINS_YAMADANISHIKI = ITEMS.register("steamed_rice_grains_yamadanishiki", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // --------------------------------------------- LARGE CROPS ---------------------------------------------
    public static final RegistryObject<Item> CORN_CROP = ITEMS.register("corn_crop", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));

    // --------------------------------------------- PLOW SHOVEL ---------------------------------------------
    public static final RegistryObject<Item> WOOD_SPADE = ITEMS.register("wooden_spade", () ->
            new SpadeItem(ItemTier.WOOD, 1.5F, -3.0F, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> STONE_SPADE = ITEMS.register("stone_spade", () ->
            new SpadeItem(ItemTier.STONE, 1.5F, -3.0F, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> IRON_SPADE = ITEMS.register("iron_spade", () ->
            new SpadeItem(ItemTier.IRON, 1.5F, -3.0F, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> GOLD_SPADE = ITEMS.register("golden_spade", () ->
            new SpadeItem(ItemTier.GOLD, 1.5F, -3.0F, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> DIAMOND_SPADE = ITEMS.register("diamond_spade", () ->
            new SpadeItem(ItemTier.DIAMOND, 1.5F, -3.0F, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));

    // --------------------------------------------- FLAIL ---------------------------------------------
    public static final RegistryObject<Item> WOOD_FLAIL = ITEMS.register("wooden_flail", () ->
            new FlailItem(ItemTier.WOOD, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> STONE_FLAIL = ITEMS.register("stone_flail", () ->
            new FlailItem(ItemTier.STONE, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> IRON_FLAIL = ITEMS.register("iron_flail", () ->
            new FlailItem(ItemTier.IRON, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> GOLD_FLAIL = ITEMS.register("golden_flail", () ->
            new FlailItem(ItemTier.GOLD, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> DIAMOND_FLAIL = ITEMS.register("diamond_flail", () ->
            new FlailItem(ItemTier.DIAMOND, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));

    // --------------------------------------------- SALT ---------------------------------------------
    public static final RegistryObject<Item> ROCK_SALT = ITEMS.register("rock_salt", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> SEA_SALT = ITEMS.register("sea_salt", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));

    // --------------------------------------------- FERTILIZER BAG ---------------------------------------------
    public static final RegistryObject<Item> EMPTY_SMALL_FERTILIZER_BAG = ITEMS.register("empty_small_fertilizer_bag", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> EMPTY_MEDIUM_FERTILIZER_BAG = ITEMS.register("empty_medium_fertilizer_bag", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> EMPTY_LARGE_FERTILIZER_BAG = ITEMS.register("empty_large_fertilizer_bag", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));

    // --------------------------------------------- RAW FERTILIZER COMPONENTS ---------------------------------------------
    public static final RegistryObject<Item> RAW_ANIMAL_WASTE_FERTILIZER = ITEMS.register("raw_animal_waste_fertilizer", () ->
            new RawFertilizerItem(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> RAW_PEAT_FERTILIZER = ITEMS.register("raw_peat_fertilizer", () ->
            new RawFertilizerItem(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> RAW_PLANT_TYPE_FERTILIZER = ITEMS.register("raw_plant_type_fertilizer", () ->
            new RawFertilizerItem(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));

    // --------------------------------------------- FERTILIZERS ---------------------------------------------
    public static final RegistryObject<Item> SMALL_ANIMAL_WASTE_FERTILIZER = ITEMS.register("small_animal_waste_fertilizer", () ->
            new FertilizerItem(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).defaultMaxDamage(5), ItemTierType.COMMON));
    public static final RegistryObject<Item> SMALL_PEAT_FERTILIZER = ITEMS.register("small_peat_fertilizer", () ->
            new FertilizerItem(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).defaultMaxDamage(5), ItemTierType.UNCOMMON));
    public static final RegistryObject<Item> SMALL_PLANT_TYPE_FERTILIZER = ITEMS.register("small_plant_type_fertilizer", () ->
            new FertilizerItem(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).defaultMaxDamage(5), ItemTierType.RARE));
    public static final RegistryObject<Item> MEDIUM_ANIMAL_WASTE_FERTILIZER = ITEMS.register("medium_animal_waste_fertilizer", () ->
            new FertilizerItem(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).defaultMaxDamage(10), ItemTierType.COMMON));
    public static final RegistryObject<Item> MEDIUM_PEAT_FERTILIZER = ITEMS.register("medium_peat_fertilizer", () ->
            new FertilizerItem(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).defaultMaxDamage(10), ItemTierType.UNCOMMON));
    public static final RegistryObject<Item> MEDIUM_PLANT_TYPE_FERTILIZER = ITEMS.register("medium_plant_type_fertilizer", () ->
            new FertilizerItem(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).defaultMaxDamage(10), ItemTierType.RARE));
    public static final RegistryObject<Item> LARGE_ANIMAL_WASTE_FERTILIZER = ITEMS.register("large_animal_waste_fertilizer", () ->
            new FertilizerItem(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).defaultMaxDamage(20), ItemTierType.COMMON));
    public static final RegistryObject<Item> LARGE_PEAT_FERTILIZER = ITEMS.register("large_peat_fertilizer", () ->
            new FertilizerItem(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).defaultMaxDamage(20), ItemTierType.UNCOMMON));
    public static final RegistryObject<Item> LARGE_PLANT_TYPE_FERTILIZER = ITEMS.register("large_plant_type_fertilizer", () ->
            new FertilizerItem(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).defaultMaxDamage(20), ItemTierType.RARE));

    // --------------------------------------------- WATERING CAN PARTS ---------------------------------------------
    public static final RegistryObject<Item> SMALL_WATER_TUBE = ITEMS.register("small_water_tube", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> WATER_TUBE = ITEMS.register("water_tube", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> SMALL_WATERING_CAN_BODY = ITEMS.register("small_watering_can_body", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> MEDIUM_WATERING_CAN_BODY = ITEMS.register("medium_watering_can_body", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> LARGE_WATERING_CAN_BODY = ITEMS.register("large_watering_can_body", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> WATERING_CAN_HANDLE = ITEMS.register("watering_can_handle", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));

    // --------------------------------------------- WATERING CAN ---------------------------------------------
    public static final RegistryObject<Item> SMALL_WATERING_CAN = ITEMS.register("small_watering_can", () ->
            new WateringCanItem(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1), ItemTierType.COMMON));
    public static final RegistryObject<Item> MEDIUM_WATERING_CAN = ITEMS.register("medium_watering_can", () ->
            new WateringCanItem(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1), ItemTierType.UNCOMMON));
    public static final RegistryObject<Item> LARGE_WATERING_CAN = ITEMS.register("large_watering_can", () ->
            new WateringCanItem(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1), ItemTierType.RARE));

    // --------------------------------------------- FILTER ---------------------------------------------
    public static final RegistryObject<Item> PAPER_FLUID_FILTER = ITEMS.register("paper_fluid_filter", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));

    // --------------------------------------------- FURNACE ITEMS ---------------------------------------------
    public static final RegistryObject<Item> MOLTEN_GLASS = ITEMS.register("molten_glass", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));

    // --------------------------------------------- MISC ---------------------------------------------
    public static final RegistryObject<Item> WINE_PRESS_PISTON = ITEMS.register("wine_press_piston", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> WINE_PRESS_MOUNT = ITEMS.register("wine_press_mount", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> GRAPE_WASTE = ITEMS.register("grape_waste", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> WOOD_BARREL_LID = ITEMS.register("wood_barrel_lid", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> CORK_BARK = ITEMS.register("cork_bark", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> WINE_CORK = ITEMS.register("wine_cork", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> BOILING_CAULDRON_LID = ITEMS.register("boiling_cauldron_lid", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> RICE_HUSK = ITEMS.register("rice_husk", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> CORN_HUSK = ITEMS.register("corn_husk", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> CHAFF = ITEMS.register("chaff", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> CHAFF_ROD = ITEMS.register("chaff_rod", () ->
            new ChaffRodItem(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> RICE_IN_HUSK = ITEMS.register("rice_in_husk", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> KOJI_SPORES = ITEMS.register("koji_spores", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> SPOILED_KOJI_SPORES = ITEMS.register("spoiled_koji_spores", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));

    // --------------------------------------------- MISC - FOOD INGREDIENTS ---------------------------------------------
    public static final RegistryObject<Item> WHEAT_FLOUR = ITEMS.register("wheat_flour", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));

    // --------------------------------------------- MISC - FOOD ---------------------------------------------
    public static final RegistryObject<Item> EGG_PROTEIN = ITEMS.register("egg_protein", () ->
            new Item(new Item.Properties().food(new Food.Builder().fastToEat().hunger(0).saturation(0.1F).build()).group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> EGG_YOLK = ITEMS.register("egg_yolk", () ->
            new Item(new Item.Properties().food(new Food.Builder().fastToEat().hunger(0).saturation(0.1F).build()).group(FarmersLife.FARMERS_LIFE_CATEGORY)));

    // --------------------------------------------- BUCKETS - MUST ---------------------------------------------
    public static final RegistryObject<BucketItem> BARBERA_MUST_BUCKET = ITEMS.register("barbera_must_bucket",
            () -> new BucketItem(ModFluids.BARBERA_MUST_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> CABERNET_SAUVIGNON_MUST_BUCKET = ITEMS.register("cabernet_sauvignon_must_bucket",
            () -> new BucketItem(ModFluids.CABERNET_SAUVIGNON_MUST_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> MERLOT_MUST_BUCKET = ITEMS.register("merlot_must_bucket",
            () -> new BucketItem(ModFluids.MERLOT_MUST_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> RED_GLOBE_MUST_BUCKET = ITEMS.register("red_globe_must_bucket",
            () -> new BucketItem(ModFluids.RED_GLOBE_MUST_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> KOSHU_MUST_BUCKET = ITEMS.register("koshu_must_bucket",
            () -> new BucketItem(ModFluids.KOSHU_MUST_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> RIESLING_MUST_BUCKET = ITEMS.register("riesling_must_bucket",
            () -> new BucketItem(ModFluids.RIESLING_MUST_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));

    // --------------------------------------------- BUCKETS - FERMENTED MUST ---------------------------------------------
    public static final RegistryObject<BucketItem> FERMENTED_BARBERA_MUST_BUCKET = ITEMS.register("fermented_barbera_must_bucket",
            () -> new BucketItem(ModFluids.FERMENTED_BARBERA_MUST_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> FERMENTED_CABERNET_SAUVIGNON_MUST_BUCKET = ITEMS.register("fermented_cabernet_sauvignon_must_bucket",
            () -> new BucketItem(ModFluids.FERMENTED_CABERNET_SAUVIGNON_MUST_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> FERMENTED_MERLOT_MUST_BUCKET = ITEMS.register("fermented_merlot_must_bucket",
            () -> new BucketItem(ModFluids.FERMENTED_MERLOT_MUST_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> FERMENTED_RED_GLOBE_MUST_BUCKET = ITEMS.register("fermented_red_globe_must_bucket",
            () -> new BucketItem(ModFluids.FERMENTED_RED_GLOBE_MUST_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> FERMENTED_KOSHU_MUST_BUCKET = ITEMS.register("fermented_koshu_must_bucket",
            () -> new BucketItem(ModFluids.FERMENTED_KOSHU_MUST_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> FERMENTED_RIESLING_MUST_BUCKET = ITEMS.register("fermented_riesling_must_bucket",
            () -> new BucketItem(ModFluids.FERMENTED_RIESLING_MUST_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));

    // --------------------------------------------- BUCKETS - FILTERED FERMENTED MUST ---------------------------------------------
    public static final RegistryObject<BucketItem> FILTERED_FERMENTED_BARBERA_MUST_BUCKET = ITEMS.register("filtered_fermented_barbera_must_bucket",
            () -> new BucketItem(ModFluids.FILTERED_FERMENTED_BARBERA_MUST_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> FILTERED_FERMENTED_CABERNET_SAUVIGNON_MUST_BUCKET = ITEMS.register("filtered_fermented_cabernet_sauvignon_must_bucket",
            () -> new BucketItem(ModFluids.FILTERED_FERMENTED_CABERNET_SAUVIGNON_MUST_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> FILTERED_FERMENTED_MERLOT_MUST_BUCKET = ITEMS.register("filtered_fermented_merlot_must_bucket",
            () -> new BucketItem(ModFluids.FILTERED_FERMENTED_MERLOT_MUST_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> FILTERED_FERMENTED_RED_GLOBE_MUST_BUCKET = ITEMS.register("filtered_fermented_red_globe_must_bucket",
            () -> new BucketItem(ModFluids.FILTERED_FERMENTED_RED_GLOBE_MUST_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> FILTERED_FERMENTED_KOSHU_MUST_BUCKET = ITEMS.register("filtered_fermented_koshu_must_bucket",
            () -> new BucketItem(ModFluids.FILTERED_FERMENTED_KOSHU_MUST_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> FILTERED_FERMENTED_RIESLING_MUST_BUCKET = ITEMS.register("filtered_fermented_riesling_must_bucket",
            () -> new BucketItem(ModFluids.FILTERED_FERMENTED_RIESLING_MUST_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));

    // --------------------------------------------- BUCKETS - CLEAR  ---------------------------------------------
    public static final RegistryObject<BucketItem> BARBERA_WINE_BUCKET = ITEMS.register("barbera_wine_bucket",
            () -> new BucketItem(ModFluids.BARBERA_WINE_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> CABERNET_SAUVIGNON_WINE_BUCKET = ITEMS.register("cabernet_sauvignon_wine_bucket",
            () -> new BucketItem(ModFluids.CABERNET_SAUVIGNON_WINE_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> MERLOT_WINE_BUCKET = ITEMS.register("merlot_wine_bucket",
            () -> new BucketItem(ModFluids.MERLOT_WINE_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> RED_GLOBE_WINE_BUCKET = ITEMS.register("red_globe_wine_bucket",
            () -> new BucketItem(ModFluids.RED_GLOBE_WINE_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> KOSHU_WINE_BUCKET = ITEMS.register("koshu_wine_bucket",
            () -> new BucketItem(ModFluids.KOSHU_WINE_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> RIESLING_WINE_BUCKET = ITEMS.register("riesling_wine_bucket",
            () -> new BucketItem(ModFluids.RIESLING_WINE_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));

    // --------------------------------------------- BUCKETS - SAKE  ---------------------------------------------
    public static final RegistryObject<BucketItem> KOJI_RICE_SHUBO_WATER_MIX_SPOILED_BUCKET = ITEMS.register("koji_rice_shubo_water_mix_spoiled_bucket",
            () -> new BucketItem(ModFluids.KOJI_RICE_SHUBO_WATER_MIX_SPOILED_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_BUCKET = ITEMS.register("fermented_koji_rice_shubo_water_mix_bucket",
            () -> new BucketItem(ModFluids.FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_BUCKET = ITEMS.register("filtered_fermented_koji_rice_shubo_water_mix_bucket",
            () -> new BucketItem(ModFluids.FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));
    public static final RegistryObject<BucketItem> SAKE_BUCKET = ITEMS.register("sake_bucket",
            () -> new BucketItem(ModFluids.SAKE_FLUID, new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY).maxStackSize(1)));

    // --------------------------------------------- WINE - BOTTLE  ---------------------------------------------
    public static final RegistryObject<Item> CORKED_GLASS_BOTTLE = ITEMS.register("corked_glass_bottle", () ->
            new Item(new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
}
