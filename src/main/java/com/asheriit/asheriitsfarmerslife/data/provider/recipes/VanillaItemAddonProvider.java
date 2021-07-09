package com.asheriit.asheriitsfarmerslife.data.provider.recipes;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.data.conditions.EnableVanillaAdditionsCondition;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class VanillaItemAddonProvider extends RecipeProvider implements IConditionBuilder {
    public VanillaItemAddonProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ResourceLocation WHEAT_SEEDS_ID = new ResourceLocation(FarmersLife.MOD_ID, "wheat_seeds");
        ConditionalRecipe.builder()
                .addCondition(new EnableVanillaAdditionsCondition())
                .addRecipe(ShapelessRecipeBuilder.shapelessRecipe(Items.WHEAT_SEEDS, 2)
                        .addIngredient(Items.WHEAT, 1)
                        .addCriterion("has_wheat", hasItem(Items.WHEAT))::build)
                .setAdvancement(WHEAT_SEEDS_ID, ConditionalAdvancement.builder()
                        .addCondition(new EnableVanillaAdditionsCondition())
                        .addAdvancement(Advancement.Builder.builder()
                                .withCriterion("has_wheat", hasItem(Items.WHEAT))
                                .withRewards(AdvancementRewards.Builder.recipe(new ResourceLocation("minecraft", "wheat_seeds")))))
                .build(consumer, WHEAT_SEEDS_ID);
    }
}
