package com.asheriit.asheriitsfarmerslife.init;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.objects.items.AlcoholicItem;
import com.asheriit.asheriitsfarmerslife.properties.AlcoholicStrength;
import com.asheriit.asheriitsfarmerslife.utils.ColorHelper;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModAlcoholicItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FarmersLife.MOD_ID);

    // --------------------------------------------- WINES  ---------------------------------------------
    public static final RegistryObject<Item> BARBERA_WINE_BOTTLE = ModItems.ITEMS.register("barbera_wine_bottle", () ->
            new AlcoholicItem(ColorHelper.WineColors.BARBERA_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-1)
                            .effect(() -> new EffectInstance(ModEffects.AQUATIC_STAGE_1.get(), 1800), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.WEAK).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> CABERNET_SAUVIGNON_WINE_BOTTLE = ModItems.ITEMS.register("cabernet_sauvignon_wine_bottle", () ->
            new AlcoholicItem(ColorHelper.WineColors.CABERNET_SAUVIGNON_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-1)
                            .effect(() -> new EffectInstance(ModEffects.ANTITOXIN_STAGE_1.get()), 0.4F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.WEAK).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> MERLOT_WINE_BOTTLE = ModItems.ITEMS.register("merlot_wine_bottle", () ->
            new AlcoholicItem(ColorHelper.WineColors.MERLOT_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-1)
                            .effect(() -> new EffectInstance(ModEffects.LOOT_MULTIPLIER_STAGE_1.get(), 400), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.WEAK).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> RED_GLOBE_WINE_BOTTLE = ModItems.ITEMS.register("red_globe_wine_bottle", () ->
            new AlcoholicItem(ColorHelper.WineColors.RED_GLOBE_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-2)
                            .effect(() -> new EffectInstance(ModEffects.KILLING_SPREE_STAGE_1.get(), 400), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.WEAK).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> KOSHU_WINE_BOTTLE = ModItems.ITEMS.register("koshu_wine_bottle", () ->
            new AlcoholicItem(ColorHelper.WineColors.KOSHU_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-1)
                            .effect(() -> new EffectInstance(ModEffects.SHOW_ORES_STAGE_1.get(), 1200), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.WEAK).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> RIESLING_WINE_BOTTLE = ModItems.ITEMS.register("riesling_wine_bottle", () ->
            new AlcoholicItem(ColorHelper.WineColors.RIESLING_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-1)
                            .effect(() -> new EffectInstance(ModEffects.GRAVITY_STAGE_1.get(), 1800), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.WEAK).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> ENRICHED_BARBERA_WINE_BOTTLE = ModItems.ITEMS.register("enriched_barbera_wine", () ->
            new AlcoholicItem(ColorHelper.WineColors.ENRICHED_BARBERA_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-2)
                            .effect(() -> new EffectInstance(ModEffects.AQUATIC_STAGE_2.get(), 1600), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.MEDIUM).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_2.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> ENRICHED_CABERNET_SAUVIGNON_WINE_BOTTLE = ModItems.ITEMS.register("enriched_cabernet_sauvignon_wine", () ->
            new AlcoholicItem(ColorHelper.WineColors.ENRICHED_CABERNET_SAUVIGNON_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-2)
                            .effect(() -> new EffectInstance(ModEffects.ANTITOXIN_STAGE_2.get()), 0.4F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.MEDIUM).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_2.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> ENRICHED_MERLOT_WINE_BOTTLE = ModItems.ITEMS.register("enriched_merlot_wine", () ->
            new AlcoholicItem(ColorHelper.WineColors.ENRICHED_MERLOT_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-2)
                            .effect(() -> new EffectInstance(ModEffects.LOOT_MULTIPLIER_STAGE_2.get(), 300), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.MEDIUM).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_2.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> ENRICHED_RED_GLOBE_WINE_BOTTLE = ModItems.ITEMS.register("enriched_red_globe_wine", () ->
            new AlcoholicItem(ColorHelper.WineColors.ENRICHED_RED_GLOBE_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-3)
                            .effect(() -> new EffectInstance(ModEffects.KILLING_SPREE_STAGE_2.get(), 300), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.MEDIUM).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_2.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> ENRICHED_KOSHU_WINE_BOTTLE = ModItems.ITEMS.register("enriched_koshu_wine", () ->
            new AlcoholicItem(ColorHelper.WineColors.ENRICHED_KOSHU_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-2)
                            .effect(() -> new EffectInstance(ModEffects.SHOW_ORES_STAGE_2.get(), 1000), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.MEDIUM).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_2.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> ENRICHED_RIESLING_WINE_BOTTLE = ModItems.ITEMS.register("enriched_riesling_wine", () ->
            new AlcoholicItem(ColorHelper.WineColors.ENRICHED_RIESLING_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-2)
                            .effect(() -> new EffectInstance(ModEffects.GRAVITY_STAGE_2.get(), 1600), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.MEDIUM).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_2.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> POTENT_BARBERA_WINE_BOTTLE = ModItems.ITEMS.register("potent_barbera_wine", () ->
            new AlcoholicItem(ColorHelper.WineColors.POTENT_BARBERA_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-3)
                            .effect(() -> new EffectInstance(ModEffects.AQUATIC_STAGE_3.get(), 1400), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.STRONG).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_3.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> POTENT_CABERNET_SAUVIGNON_WINE_BOTTLE = ModItems.ITEMS.register("potent_cabernet_sauvignon_wine", () ->
            new AlcoholicItem(ColorHelper.WineColors.POTENT_CABERNET_SAUVIGNON_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-3)
                            .effect(() -> new EffectInstance(ModEffects.ANTITOXIN_STAGE_3.get()), 0.4F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.STRONG).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_3.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> POTENT_MERLOT_WINE_BOTTLE = ModItems.ITEMS.register("potent_merlot_wine", () ->
            new AlcoholicItem(ColorHelper.WineColors.POTENT_MERLOT_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-3)
                            .effect(() -> new EffectInstance(ModEffects.LOOT_MULTIPLIER_STAGE_3.get(), 200), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.STRONG).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_3.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> POTENT_RED_GLOBE_WINE_BOTTLE = ModItems.ITEMS.register("potent_red_globe_wine", () ->
            new AlcoholicItem(ColorHelper.WineColors.POTENT_RED_GLOBE_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-4)
                            .effect(() -> new EffectInstance(ModEffects.KILLING_SPREE_STAGE_3.get(), 200), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.STRONG).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_3.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> POTENT_KOSHU_WINE_BOTTLE = ModItems.ITEMS.register("potent_koshu_wine", () ->
            new AlcoholicItem(ColorHelper.WineColors.POTENT_KOSHU_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-3)
                            .effect(() -> new EffectInstance(ModEffects.SHOW_ORES_STAGE_3.get(), 800), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.STRONG).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_3.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> POTENT_RIESLING_WINE_BOTTLE = ModItems.ITEMS.register("potent_riesling_wine", () ->
            new AlcoholicItem(ColorHelper.WineColors.POTENT_RIESLING_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-3)
                            .effect(() -> new EffectInstance(ModEffects.GRAVITY_STAGE_3.get(), 1400), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.STRONG).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_3.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> AGED_BARBERA_WINE_BOTTLE = ModItems.ITEMS.register("aged_barbera_wine_bottle", () ->
            new AlcoholicItem(ColorHelper.AgedWineColors.AGED_BARBERA_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-2)
                            .effect(() -> new EffectInstance(ModEffects.AQUATIC_STAGE_1.get(), 3600), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.MEDIUM).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> AGED_CABERNET_SAUVIGNON_WINE_BOTTLE = ModItems.ITEMS.register("aged_cabernet_sauvignon_wine_bottle", () ->
            new AlcoholicItem(ColorHelper.AgedWineColors.AGED_CABERNET_SAUVIGNON_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-2)
                            .effect(() -> new EffectInstance(ModEffects.ANTITOXIN_STAGE_1.get()), 0.7F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.MEDIUM).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> AGED_MERLOT_WINE_BOTTLE = ModItems.ITEMS.register("aged_merlot_wine_bottle", () ->
            new AlcoholicItem(ColorHelper.AgedWineColors.AGED_MERLOT_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-2)
                            .effect(() -> new EffectInstance(ModEffects.LOOT_MULTIPLIER_STAGE_1.get(), 800), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.MEDIUM).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> AGED_RED_GLOBE_WINE_BOTTLE = ModItems.ITEMS.register("aged_red_globe_wine_bottle", () ->
            new AlcoholicItem(ColorHelper.AgedWineColors.AGED_RED_GLOBE_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-3)
                            .effect(() -> new EffectInstance(ModEffects.KILLING_SPREE_STAGE_1.get(), 800), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.MEDIUM).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> AGED_KOSHU_WINE_BOTTLE = ModItems.ITEMS.register("aged_koshu_wine_bottle", () ->
            new AlcoholicItem(ColorHelper.AgedWineColors.AGED_KOSHU_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-2)
                            .effect(() -> new EffectInstance(ModEffects.SHOW_ORES_STAGE_1.get(), 2400), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.MEDIUM).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> AGED_RIESLING_WINE_BOTTLE = ModItems.ITEMS.register("aged_riesling_wine_bottle", () ->
            new AlcoholicItem(ColorHelper.AgedWineColors.AGED_RIESLING_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-2)
                            .effect(() -> new EffectInstance(ModEffects.GRAVITY_STAGE_1.get(), 3600), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.MEDIUM).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> AGED_ENRICHED_BARBERA_WINE_BOTTLE = ModItems.ITEMS.register("aged_enriched_barbera_wine", () ->
            new AlcoholicItem(ColorHelper.AgedWineColors.AGED_ENRICHED_BARBERA_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-3)
                            .effect(() -> new EffectInstance(ModEffects.AQUATIC_STAGE_2.get(), 3200), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.STRONG).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_2.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> AGED_ENRICHED_CABERNET_SAUVIGNON_WINE_BOTTLE = ModItems.ITEMS.register("aged_enriched_cabernet_sauvignon_wine", () ->
            new AlcoholicItem(ColorHelper.AgedWineColors.AGED_ENRICHED_CABERNET_SAUVIGNON_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-3)
                            .effect(() -> new EffectInstance(ModEffects.ANTITOXIN_STAGE_2.get()), 0.7F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.STRONG).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_2.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> AGED_ENRICHED_MERLOT_WINE_BOTTLE = ModItems.ITEMS.register("aged_enriched_merlot_wine", () ->
            new AlcoholicItem(ColorHelper.AgedWineColors.AGED_ENRICHED_MERLOT_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-3)
                            .effect(() -> new EffectInstance(ModEffects.LOOT_MULTIPLIER_STAGE_2.get(), 600), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.STRONG).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_2.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> AGED_ENRICHED_RED_GLOBE_WINE_BOTTLE = ModItems.ITEMS.register("aged_enriched_red_globe_wine", () ->
            new AlcoholicItem(ColorHelper.AgedWineColors.AGED_ENRICHED_RED_GLOBE_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-4)
                            .effect(() -> new EffectInstance(ModEffects.KILLING_SPREE_STAGE_2.get(), 600), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.STRONG).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_2.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> AGED_ENRICHED_KOSHU_WINE_BOTTLE = ModItems.ITEMS.register("aged_enriched_koshu_wine", () ->
            new AlcoholicItem(ColorHelper.AgedWineColors.AGED_ENRICHED_KOSHU_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-3)
                            .effect(() -> new EffectInstance(ModEffects.SHOW_ORES_STAGE_2.get(), 2000), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.STRONG).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_2.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> AGED_ENRICHED_RIESLING_WINE_BOTTLE = ModItems.ITEMS.register("aged_enriched_riesling_wine", () ->
            new AlcoholicItem(ColorHelper.AgedWineColors.AGED_ENRICHED_RIESLING_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-3)
                            .effect(() -> new EffectInstance(ModEffects.GRAVITY_STAGE_2.get(), 3200), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.STRONG).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_2.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> AGED_POTENT_BARBERA_WINE_BOTTLE = ModItems.ITEMS.register("aged_potent_barbera_wine", () ->
            new AlcoholicItem(ColorHelper.AgedWineColors.AGED_POTENT_BARBERA_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-4)
                            .effect(() -> new EffectInstance(ModEffects.AQUATIC_STAGE_3.get(), 2800), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.GODLY).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_3.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> AGED_POTENT_CABERNET_SAUVIGNON_WINE_BOTTLE = ModItems.ITEMS.register("aged_potent_cabernet_sauvignon_wine", () ->
            new AlcoholicItem(ColorHelper.AgedWineColors.AGED_POTENT_CABERNET_SAUVIGNON_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-4)
                            .effect(() -> new EffectInstance(ModEffects.ANTITOXIN_STAGE_3.get()), 0.7F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.GODLY).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_3.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> AGED_POTENT_MERLOT_WINE_BOTTLE = ModItems.ITEMS.register("aged_potent_merlot_wine", () ->
            new AlcoholicItem(ColorHelper.AgedWineColors.AGED_POTENT_MERLOT_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-4)
                            .effect(() -> new EffectInstance(ModEffects.LOOT_MULTIPLIER_STAGE_3.get(), 400), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.GODLY).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_3.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> AGED_POTENT_RED_GLOBE_WINE_BOTTLE = ModItems.ITEMS.register("aged_potent_red_globe_wine", () ->
            new AlcoholicItem(ColorHelper.AgedWineColors.AGED_POTENT_RED_GLOBE_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-5)
                            .effect(() -> new EffectInstance(ModEffects.KILLING_SPREE_STAGE_3.get(), 400), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.GODLY).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_3.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> AGED_POTENT_KOSHU_WINE_BOTTLE = ModItems.ITEMS.register("aged_potent_koshu_wine", () ->
            new AlcoholicItem(ColorHelper.AgedWineColors.AGED_POTENT_KOSHU_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-4)
                            .effect(() -> new EffectInstance(ModEffects.SHOW_ORES_STAGE_3.get(), 1600), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.GODLY).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_3.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> AGED_POTENT_RIESLING_WINE_BOTTLE = ModItems.ITEMS.register("aged_potent_riesling_wine", () ->
            new AlcoholicItem(ColorHelper.AgedWineColors.AGED_POTENT_RIESLING_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-4)
                            .effect(() -> new EffectInstance(ModEffects.GRAVITY_STAGE_3.get(), 2800), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.GODLY).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_3.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> OLD_BARBERA_WINE_BOTTLE = ModItems.ITEMS.register("old_barbera_wine_bottle", () ->
            new AlcoholicItem(ColorHelper.OldWineColors.OLD_BARBERA_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-3)
                            .effect(() -> new EffectInstance(ModEffects.AQUATIC_STAGE_1.get(), 7200), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.STRONG).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> OLD_CABERNET_SAUVIGNON_WINE_BOTTLE = ModItems.ITEMS.register("old_cabernet_sauvignon_wine_bottle", () ->
            new AlcoholicItem(ColorHelper.OldWineColors.OLD_CABERNET_SAUVIGNON_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-3)
                            .effect(() -> new EffectInstance(ModEffects.ANTITOXIN_STAGE_1.get()), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.STRONG).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> OLD_MERLOT_WINE_BOTTLE = ModItems.ITEMS.register("old_merlot_wine_bottle", () ->
            new AlcoholicItem(ColorHelper.OldWineColors.OLD_MERLOT_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-3)
                            .effect(() -> new EffectInstance(ModEffects.LOOT_MULTIPLIER_STAGE_1.get(), 1600), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.STRONG).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> OLD_RED_GLOBE_WINE_BOTTLE = ModItems.ITEMS.register("old_red_globe_wine_bottle", () ->
            new AlcoholicItem(ColorHelper.OldWineColors.OLD_RED_GLOBE_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-4)
                            .effect(() -> new EffectInstance(ModEffects.KILLING_SPREE_STAGE_1.get(), 1600), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.STRONG).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> OLD_KOSHU_WINE_BOTTLE = ModItems.ITEMS.register("old_koshu_wine_bottle", () ->
            new AlcoholicItem(ColorHelper.OldWineColors.OLD_KOSHU_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-3)
                            .effect(() -> new EffectInstance(ModEffects.SHOW_ORES_STAGE_1.get(), 4800), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.STRONG).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> OLD_RIESLING_WINE_BOTTLE = ModItems.ITEMS.register("old_riesling_wine_bottle", () ->
            new AlcoholicItem(ColorHelper.OldWineColors.OLD_RIESLING_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-3)
                            .effect(() -> new EffectInstance(ModEffects.GRAVITY_STAGE_1.get(), 7200), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.STRONG).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> OLD_ENRICHED_BARBERA_WINE_BOTTLE = ModItems.ITEMS.register("old_enriched_barbera_wine", () ->
            new AlcoholicItem(ColorHelper.OldWineColors.OLD_ENRICHED_BARBERA_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-4)
                            .effect(() -> new EffectInstance(ModEffects.AQUATIC_STAGE_2.get(), 6400), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.GODLY).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_2.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> OLD_ENRICHED_CABERNET_SAUVIGNON_WINE_BOTTLE = ModItems.ITEMS.register("old_enriched_cabernet_sauvignon_wine", () ->
            new AlcoholicItem(ColorHelper.OldWineColors.OLD_ENRICHED_CABERNET_SAUVIGNON_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-4)
                            .effect(() -> new EffectInstance(ModEffects.ANTITOXIN_STAGE_2.get()), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.GODLY).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_2.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> OLD_ENRICHED_MERLOT_WINE_BOTTLE = ModItems.ITEMS.register("old_enriched_merlot_wine", () ->
            new AlcoholicItem(ColorHelper.OldWineColors.OLD_ENRICHED_MERLOT_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-4)
                            .effect(() -> new EffectInstance(ModEffects.LOOT_MULTIPLIER_STAGE_2.get(), 1200), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.GODLY).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_2.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> OLD_ENRICHED_RED_GLOBE_WINE_BOTTLE = ModItems.ITEMS.register("old_enriched_red_globe_wine", () ->
            new AlcoholicItem(ColorHelper.OldWineColors.OLD_ENRICHED_RED_GLOBE_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-5)
                            .effect(() -> new EffectInstance(ModEffects.KILLING_SPREE_STAGE_2.get(), 1200), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.GODLY).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_2.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> OLD_ENRICHED_KOSHU_WINE_BOTTLE = ModItems.ITEMS.register("old_enriched_koshu_wine", () ->
            new AlcoholicItem(ColorHelper.OldWineColors.OLD_ENRICHED_KOSHU_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-4)
                            .effect(() -> new EffectInstance(ModEffects.SHOW_ORES_STAGE_2.get(), 4000), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.GODLY).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_2.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> OLD_ENRICHED_RIESLING_WINE_BOTTLE = ModItems.ITEMS.register("old_enriched_riesling_wine", () ->
            new AlcoholicItem(ColorHelper.OldWineColors.OLD_ENRICHED_RIESLING_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-4)
                            .effect(() -> new EffectInstance(ModEffects.GRAVITY_STAGE_2.get(), 6400), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.GODLY).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_2.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> OLD_POTENT_BARBERA_WINE_BOTTLE = ModItems.ITEMS.register("old_potent_barbera_wine", () ->
            new AlcoholicItem(ColorHelper.OldWineColors.OLD_POTENT_BARBERA_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-5)
                            .effect(() -> new EffectInstance(ModEffects.AQUATIC_STAGE_3.get(), 5600), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.DEADLY).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_3.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> OLD_POTENT_CABERNET_SAUVIGNON_WINE_BOTTLE = ModItems.ITEMS.register("old_potent_cabernet_sauvignon_wine", () ->
            new AlcoholicItem(ColorHelper.OldWineColors.OLD_POTENT_CABERNET_SAUVIGNON_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-5)
                            .effect(() -> new EffectInstance(ModEffects.ANTITOXIN_STAGE_3.get()), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.DEADLY).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_3.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> OLD_POTENT_MERLOT_WINE_BOTTLE = ModItems.ITEMS.register("old_potent_merlot_wine", () ->
            new AlcoholicItem(ColorHelper.OldWineColors.OLD_POTENT_MERLOT_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-5)
                            .effect(() -> new EffectInstance(ModEffects.LOOT_MULTIPLIER_STAGE_3.get(), 800), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.DEADLY).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_3.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> OLD_POTENT_RED_GLOBE_WINE_BOTTLE = ModItems.ITEMS.register("old_potent_red_globe_wine", () ->
            new AlcoholicItem(ColorHelper.OldWineColors.OLD_POTENT_RED_GLOBE_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-6)
                            .effect(() -> new EffectInstance(ModEffects.KILLING_SPREE_STAGE_3.get(), 800), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.DEADLY).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_3.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> OLD_POTENT_KOSHU_WINE_BOTTLE = ModItems.ITEMS.register("old_potent_koshu_wine", () ->
            new AlcoholicItem(ColorHelper.OldWineColors.OLD_POTENT_KOSHU_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-5)
                            .effect(() -> new EffectInstance(ModEffects.SHOW_ORES_STAGE_3.get(), 3200), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.DEADLY).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_3.get(), 1200), 1.0F)));

    public static final RegistryObject<Item> OLD_POTENT_RIESLING_WINE_BOTTLE = ModItems.ITEMS.register("old_potent_riesling_wine", () ->
            new AlcoholicItem(ColorHelper.OldWineColors.OLD_POTENT_RIESLING_WINE_COLOR_HEX, new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-5)
                            .effect(() -> new EffectInstance(ModEffects.GRAVITY_STAGE_3.get(), 5600), 1.0F).build())
                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
                    .alcoholicStrength(AlcoholicStrength.DEADLY).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_3.get(), 1200), 1.0F)));


//    public static final RegistryObject<Item> WEAK_BARBERA_WINE_BOTTLE = ITEMS.register("weak_barbera_wine", () ->
//            new AlcoholicItem(new AlcoholicItem.AlcoholProperties().properties(new Item.Properties()
//                    .food(new Food.Builder().saturation(0.0F).setAlwaysEdible().hunger(-1)
//                            .effect(() -> new EffectInstance(), 1.0F).build())
//                    .group(FarmersLife.FARMERS_LIFE_CATEGORY))
//                    .alcoholicStrength(AlcoholicStrength.WEAK).drunkEffect(() -> new EffectInstance(ModEffects.DRUNK_STAGE_1.get(), 1200), 1.0F)));
}
