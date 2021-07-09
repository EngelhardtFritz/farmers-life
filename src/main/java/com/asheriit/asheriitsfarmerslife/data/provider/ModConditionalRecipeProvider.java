package com.asheriit.asheriitsfarmerslife.data.provider;

import com.asheriit.asheriitsfarmerslife.data.provider.recipes.DamBlockProvider;
import com.asheriit.asheriitsfarmerslife.data.provider.recipes.VanillaItemAddonProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

// Default scheme for adding conditional recipes with advancements
/*
ResourceLocation _ID = new ResourceLocation(FarmersLife.MOD_ID, "");
ConditionalRecipe.builder()
        .addCondition()
        .addRecipe(::build)
.setAdvancement(_ID, ConditionalAdvancement.builder()
        .addCondition()
        .addAdvancement(Advancement.Builder.builder()
                .withCriterion()
                .withRewards(AdvancementRewards.Builder.recipe(_ID))))
        .build(consumer, _ID);
*/
public class ModConditionalRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModConditionalRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
        generatorIn.addProvider(new VanillaItemAddonProvider(generatorIn));
        generatorIn.addProvider(new DamBlockProvider(generatorIn));
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        // Do nothing!
    }
}
