package com.asheriit.asheriitsfarmerslife.data.provider.recipes;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.data.conditions.EnableDamBlocksCondition;
import com.asheriit.asheriitsfarmerslife.data.provider.builders.FarmerShapedRecipeBuilder;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class DamBlockProvider extends RecipeProvider implements IConditionBuilder {
    public DamBlockProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ResourceLocation DIRT_SMALL_DAM_ITEM_ID = new ResourceLocation(FarmersLife.MOD_ID, "dirt_small_dam");
        ConditionalRecipe.builder()
                .addCondition(new EnableDamBlocksCondition())
                .addRecipe(FarmerShapedRecipeBuilder
                        .shapedRecipe(ModBlocks.DIRT_SMALL_DAM.get(), 6)
                        .patternLine("#  ")
                        .patternLine("#  ")
                        .setGroup("dam")
                        .key('#', Blocks.DIRT)
                        .addCriterion("has_dirt", hasItem(Blocks.DIRT))::build)
                .setAdvancement(DIRT_SMALL_DAM_ITEM_ID, ConditionalAdvancement.builder()
                        .addCondition(new EnableDamBlocksCondition())
                        .addAdvancement(Advancement.Builder.builder()
                                .withCriterion("has_dirt", hasItem(Blocks.DIRT))
                                .withRewards(AdvancementRewards.Builder.recipe(DIRT_SMALL_DAM_ITEM_ID))))
                .build(consumer, DIRT_SMALL_DAM_ITEM_ID);

        ResourceLocation DIRT_LARGE_DAM_ID = new ResourceLocation(FarmersLife.MOD_ID, "dirt_large_dam");
        ConditionalRecipe.builder()
                .addCondition(new EnableDamBlocksCondition())
                .addRecipe(FarmerShapedRecipeBuilder
                        .shapedRecipe(ModBlocks.DIRT_LARGE_DAM.get(), 1)
                        .patternLine("#  ")
                        .patternLine("#  ")
                        .setGroup("dam")
                        .key('#', ModBlocks.DIRT_SMALL_DAM.get())
                        .addCriterion("has_dirt", hasItem(ModBlocks.DIRT_SMALL_DAM.get()))::build)
                .setAdvancement(DIRT_LARGE_DAM_ID, ConditionalAdvancement.builder()
                        .addCondition(new EnableDamBlocksCondition())
                        .addAdvancement(Advancement.Builder.builder()
                                .withCriterion("has_dirt", hasItem(ModBlocks.DIRT_SMALL_DAM.get()))
                                .withRewards(AdvancementRewards.Builder.recipe(DIRT_LARGE_DAM_ID))))
                .build(consumer, DIRT_LARGE_DAM_ID);
    }
}
