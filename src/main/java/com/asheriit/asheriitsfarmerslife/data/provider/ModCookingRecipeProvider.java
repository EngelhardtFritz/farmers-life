package com.asheriit.asheriitsfarmerslife.data.provider;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModItems;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ModCookingRecipeProvider extends RecipeProvider {
    public ModCookingRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        this.generateGlassCookingRecipes(consumer);
    }

    private void generateGlassCookingRecipes(Consumer<IFinishedRecipe> consumer) {
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(Tags.Items.GLASS_PANES), ModItems.MOLTEN_GLASS.get(), 0.15F, 200)
                .addCriterion("has_glass_pane", hasItem(Tags.Items.GLASS_PANES))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "molten_glass"));
    }
}
