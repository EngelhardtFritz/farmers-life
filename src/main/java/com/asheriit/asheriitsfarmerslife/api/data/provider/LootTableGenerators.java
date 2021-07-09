package com.asheriit.asheriitsfarmerslife.api.data.provider;

import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.*;
import net.minecraft.world.storage.loot.functions.ApplyBonus;
import net.minecraft.world.storage.loot.functions.ExplosionDecay;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LootTableGenerators {
    public static LootTable addThreshingSpecialLootTable(@Nonnull IItemProvider cropItem, @Nonnull Block propertyBlock, @Nonnull IntegerProperty property) {
        int propertySize = property.getAllowedValues().size();
        int maxPropertyValue = (Integer) property.getAllowedValues().toArray()[propertySize - 1] + 1;
        if (maxPropertyValue < 0) return LootTable.EMPTY_LOOT_TABLE;

        LootTable.Builder lootTableBuilder = LootTable.builder();
        LootPool.Builder lootPool = LootPool.builder().rolls(ConstantRange.of(1));

        for (int i = 0; i < maxPropertyValue; i++) {
            lootPool.addEntry(ItemLootEntry.builder(cropItem)
                    .acceptCondition(BlockStateProperty.builder(propertyBlock)
                            .fromProperties(StatePropertiesPredicate.Builder.newBuilder()
                                    .withIntProp(property, i)))
                    .acceptFunction(SetCount.builder(ConstantRange.of((i + 1) * 2))));
        }

        return lootTableBuilder.addLootPool(lootPool).acceptFunction(ExplosionDecay.builder()).build();
    }

    public static LootTable addThreshingBlockLootTable(@Nonnull IItemProvider grainItem, @Nonnull IItemProvider chaffItem, @Nullable IItemProvider huskItem) {
        LootTable.Builder lootTable = LootTable.builder();

        LootPool.Builder grainDrops = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(grainItem)
                        .acceptFunction(SetCount.builder(BinomialRange.of(1, 0.15F))));
        lootTable.addLootPool(grainDrops);

        lootTable.addLootPool(LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(grainItem).acceptFunction(SetCount.builder(ConstantRange.of(1)))));

        if (huskItem != null) {
            LootPool.Builder huskDrops = LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(huskItem)
                            .acceptFunction(SetCount.builder(BinomialRange.of(1, 0.2F))));
            lootTable.addLootPool(huskDrops);

            lootTable.addLootPool(LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(huskItem).acceptFunction(SetCount.builder(ConstantRange.of(1)))));

            LootPool.Builder chaffDrops = LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(chaffItem)
                            .acceptFunction(SetCount.builder(BinomialRange.of(1, 0.25F))));
            lootTable.addLootPool(chaffDrops);
        } else {
            LootPool.Builder chaffDrops = LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(chaffItem).acceptFunction(SetCount.builder(BinomialRange.of(2, 0.5F))));
            lootTable.addLootPool(chaffDrops);
        }

        lootTable.addLootPool(LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(chaffItem).acceptFunction(SetCount.builder(ConstantRange.of(1)))));

        return lootTable.acceptFunction(ExplosionDecay.builder()).build();
    }

    public static LootTable addTrellisCropLootTable(IItemProvider seedItem, IItemProvider cropItem, Block propertyBlock, IntegerProperty property, int propertyValue, int extraCropRolls, float extraCropProbability) {
        LootPool.Builder defaultLoot = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(cropItem)
                        .acceptCondition(BlockStateProperty.builder(propertyBlock)
                                .fromProperties(StatePropertiesPredicate.Builder.newBuilder()
                                        .withIntProp(property, propertyValue)))
                        .alternatively(ItemLootEntry.builder(seedItem)
                                .acceptCondition(BlockStateProperty.builder(propertyBlock)
                                        .fromProperties(StatePropertiesPredicate.Builder.newBuilder()
                                                .withBoolProp(ModBlockStateProperties.IS_ROOT_BLOCK, true)))));

        LootPool.Builder extraLoot = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(cropItem)
                        .acceptFunction(SetCount.builder(BinomialRange.of(extraCropRolls, extraCropProbability))))
                .acceptCondition(BlockStateProperty.builder(propertyBlock)
                        .fromProperties(StatePropertiesPredicate.Builder.newBuilder()
                                .withIntProp(property, propertyValue)))
                .acceptCondition(BlockStateProperty.builder(propertyBlock)
                        .fromProperties(StatePropertiesPredicate.Builder.newBuilder()
                                .withBoolProp(ModBlockStateProperties.IS_ROOT_BLOCK, false)));

        return LootTable.builder()
                .setParameterSet(LootParameterSets.BLOCK)
                .addLootPool(defaultLoot)
                .addLootPool(extraLoot)
                .acceptFunction(ExplosionDecay.builder())
                .build();
    }

    /**
     * Delegate to default Crop Loot Table with husk item
     */
    public static LootTable addDefaultMinecraftCropLootTable(IItemProvider seedItem, IItemProvider cropItem, Block propertyBlock, IntegerProperty property, int propertyValue, int extraSeedCount, float extraSeedProbability, int extraCropRolls, float extraCropProbability) {
        return LootTableGenerators.addDefaultMinecraftCropLootTable(seedItem, cropItem, null, propertyBlock, property, propertyValue, extraSeedCount, extraSeedProbability, extraCropRolls, extraCropProbability, 0, 0);
    }

    public static LootTable addDefaultMinecraftCropLootTable(IItemProvider seedItem, IItemProvider cropItem, IItemProvider huskItem, Block propertyBlock, IntegerProperty property, int propertyValue, int extraSeedCount, float extraSeedProbability, int extraCropRolls, float extraCropProbability, int huskRolls, float huskChance) {
        LootPool.Builder defaultLoot = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(cropItem)
                        .acceptCondition(BlockStateProperty.builder(propertyBlock)
                                .fromProperties(StatePropertiesPredicate.Builder.newBuilder()
                                        .withIntProp(property, propertyValue)))
                        .alternatively(ItemLootEntry.builder(seedItem)));

        LootPool.Builder extraSeeds = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(seedItem)
                        .acceptFunction(SetCount.builder(BinomialRange.of(extraSeedCount, extraSeedProbability)))
//                        .acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, extraSeedProbability, extraSeedCount)))
                        .acceptCondition(BlockStateProperty.builder(propertyBlock)
                                .fromProperties(StatePropertiesPredicate.Builder.newBuilder()
                                        .withIntProp(property, propertyValue))));

        LootTable.Builder lootTableBuilder = LootTable.builder()
                .setParameterSet(LootParameterSets.BLOCK)
                .addLootPool(defaultLoot)
                .addLootPool(extraSeeds);

        if (extraCropProbability != 0 || extraCropRolls != 0) {
            LootPool.Builder extraCrops = LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(cropItem)
                            .acceptFunction(SetCount.builder(BinomialRange.of(extraCropRolls, extraCropProbability))))
                    .acceptCondition(BlockStateProperty.builder(propertyBlock)
                            .fromProperties(StatePropertiesPredicate.Builder.newBuilder()
                                    .withIntProp(property, propertyValue)));

            lootTableBuilder.addLootPool(extraCrops);
        }

        if (huskItem != null && huskRolls > 0 && huskChance > 0) {
            LootPool.Builder husks = LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(huskItem)
                            .acceptFunction(SetCount.builder(BinomialRange.of(huskRolls, huskChance))))
                    .acceptCondition(BlockStateProperty.builder(propertyBlock)
                            .fromProperties(StatePropertiesPredicate.Builder.newBuilder()
                                    .withIntProp(property, propertyValue)));

            lootTableBuilder.addLootPool(husks);
        }

        lootTableBuilder.acceptFunction(ExplosionDecay.builder());
        return lootTableBuilder.build();
    }

    private static LootTable addDefaultWithRangeLootTable(IItemProvider itemProvider, float rangeMin, float rangeMax) {
        return LootTable.builder()
                .setParameterSet(LootParameterSets.BLOCK)
                .addLootPool(LootPool.builder()
                        .rolls(ConstantRange.of(1))
                        .acceptFunction(SetCount.builder(RandomValueRange.of(rangeMin, rangeMax)))
                        .addEntry(ItemLootEntry.builder(itemProvider)
                                .acceptCondition(SurvivesExplosion.builder())))
                .build();
    }

    public static LootTable addDefaultWithBinomialRangeLootTable(IItemProvider seedItem, IItemProvider itemProvider, float randomChance) {
        return LootTable.builder()
                .addLootPool(LootPool.builder()
                        .rolls(ConstantRange.of(1))
                        .addEntry(ItemLootEntry.builder(itemProvider)
                                .acceptFunction(SetCount.builder(ConstantRange.of(2)))
                                .acceptCondition(RandomChance.builder(randomChance))
                                .alternatively(ItemLootEntry.builder(itemProvider)
                                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                        .acceptFunction(ExplosionDecay.builder()))))
                .addLootPool(LootPool.builder()
                        .rolls(ConstantRange.of(1))
                        .addEntry(ItemLootEntry.builder(seedItem)
                                .acceptCondition(RandomChance.builder(0.25F))
                                .acceptCondition(SurvivesExplosion.builder())))
                .build();
    }

    public static LootTable addMultipleLootWithChanceSilkAndShearsLootTable(int rollCount, float rollChance, float randChance, IItemProvider silkTouchItem, IItemProvider... itemProviders) {
        final IItemProvider shears = Items.SHEARS;
        LootTable.Builder tableBuilder = LootTable.builder().setParameterSet(LootParameterSets.BLOCK);

        LootPool.Builder defaultPoolBuilder = LootPool.builder().rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(silkTouchItem)
                        .acceptCondition(MatchTool.builder(ItemPredicate.Builder.create()
                                .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))
                                .alternative(MatchTool.builder(ItemPredicate.Builder.create().item(shears)))));

        LootPool.Builder poolBuilder = LootPool.builder()
                .rolls(BinomialRange.of(rollCount, rollChance))
                .acceptCondition(MatchTool.builder(ItemPredicate.Builder.create()
                        .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))).inverted())
                .acceptCondition(MatchTool.builder(ItemPredicate.Builder.create().item(shears)).inverted());

        for (IItemProvider itemProvider : itemProviders) {
            poolBuilder.addEntry(ItemLootEntry.builder(itemProvider)
                    .acceptCondition(RandomChance.builder(randChance))
                    .acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE, 2))
                    .acceptFunction(ExplosionDecay.builder()));
        }
        return tableBuilder.addLootPool(defaultPoolBuilder).addLootPool(poolBuilder).build();
    }

    public static LootTable addDefaultSilkTouchFortuneLootTable(IItemProvider silkTouchItem, IItemProvider defaultItem, int maxDrops) {
        return LootTable.builder()
                .addLootPool(LootPool.builder()
                        .rolls(ConstantRange.of(1))
                        .addEntry(ItemLootEntry.builder(silkTouchItem)
                                .acceptCondition(MatchTool.builder(ItemPredicate.Builder.create()
                                        .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))))
                                .alternatively(ItemLootEntry.builder(defaultItem)
                                        .acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE))
                                        .acceptCondition(MatchTool.builder(ItemPredicate.Builder.create()
                                                .enchantment(new EnchantmentPredicate(Enchantments.FORTUNE, MinMaxBounds.IntBound.atLeast(1))))))
                                .alternatively(ItemLootEntry.builder(defaultItem)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1, maxDrops)))
                                        .acceptCondition(SurvivesExplosion.builder()))))
                .build();
    }

    public static LootTable addDefaultWithRangeAndSilkTouchLootTable(IItemProvider defaultItem, IItemProvider silkTouchItem, float rangeMin, float rangeMax) {
        return LootTable.builder()
                .setParameterSet(LootParameterSets.BLOCK)
                .addLootPool(LootPool.builder()
                        .rolls(ConstantRange.of(1))
                        .addEntry(ItemLootEntry.builder(silkTouchItem)
                                .acceptCondition(MatchTool.builder(ItemPredicate.Builder.create()
                                        .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))))
                                .alternatively(ItemLootEntry.builder(defaultItem)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(rangeMin, rangeMax))))))
                .build();
    }

    public static LootTable addDefaultLootTable(IItemProvider itemProvider) {
        return LootTable.builder()
                .setParameterSet(LootParameterSets.BLOCK)
                .addLootPool(LootPool.builder()
                        .rolls(ConstantRange.of(1))
                        .addEntry(ItemLootEntry.builder(itemProvider)
                                .acceptCondition(SurvivesExplosion.builder())))
                .build();
    }

    public static LootTable addSilkTouchBlockLootTable(IItemProvider defaultItem, IItemProvider silkTouchItem) {
        return LootTable.builder()
                .setParameterSet(LootParameterSets.BLOCK)
                .addLootPool(LootPool.builder()
                        .rolls(ConstantRange.of(1))
                        .addEntry(ItemLootEntry.builder(silkTouchItem)
                                .acceptCondition(MatchTool.builder(ItemPredicate.Builder.create()
                                        .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))))
                                .alternatively(ItemLootEntry.builder(defaultItem)
                                        .acceptFunction(ExplosionDecay.builder()))
                        )
                )
                .build();
    }

    public static LootTable addLeaveLootTable(IItemProvider leaveItem, IItemProvider saplingItem, @Nullable IItemProvider alternativeItem, float chanceMin, float chanceMax) {
        LootTable.Builder tableBuilder = LootTable.builder().setParameterSet(LootParameterSets.BLOCK);

        final float multiplier = ((chanceMax - chanceMin) / 3);
        final float chance2 = chanceMin + multiplier;
        final float chance3 = chanceMax - multiplier;
        LootPool.Builder lootPoolPrimary = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(leaveItem)
                        .acceptCondition(MatchTool.builder(ItemPredicate.Builder.create()
                                .tag(Tags.Items.SHEARS))
                                .alternative(MatchTool.builder(ItemPredicate.Builder.create()
                                        .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))))
                        .alternatively(ItemLootEntry.builder(saplingItem)
                                .acceptCondition(SurvivesExplosion.builder())
                                .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, chanceMin, chance2, chance3, chanceMax))));
        tableBuilder.addLootPool(lootPoolPrimary);

        if (alternativeItem != null) {
            LootPool.Builder lootPoolSecondary = LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(alternativeItem)
                            .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.01F, 0.025F, 0.04F, 0.055F, 0.07F))
                            .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                            .acceptFunction(ExplosionDecay.builder()))
                    .acceptCondition(MatchTool.builder(ItemPredicate.Builder.create()
                            .tag(Tags.Items.SHEARS))
                            .alternative(MatchTool.builder(ItemPredicate.Builder.create()
                                    .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)))))
                            .inverted());
            tableBuilder.addLootPool(lootPoolSecondary);
        }

        return tableBuilder.build();
    }

    public static LootTable addSilkTouchOnlyBlockLootTable(IItemProvider itemResult) {
        return LootTable.builder()
                .setParameterSet(LootParameterSets.BLOCK)
                .addLootPool(LootPool.builder()
                        .rolls(ConstantRange.of(1))
                        .addEntry(ItemLootEntry.builder(itemResult)
                                .acceptCondition(MatchTool.builder(ItemPredicate.Builder.create()
                                        .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))
                                )
                        )
                )
                .build();
    }
}
