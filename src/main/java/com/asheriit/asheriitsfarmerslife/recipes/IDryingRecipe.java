package com.asheriit.asheriitsfarmerslife.recipes;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

public interface IDryingRecipe extends IRecipe<RecipeWrapper> {
    ResourceLocation DRYING_ID = new ResourceLocation(FarmersLife.MOD_ID, "drying_machine");

    @Nonnull
    @Override
    default IRecipeType<?> getType() {
        return Registry.RECIPE_TYPE.getValue(DRYING_ID).get();
    }

    @Override
    default boolean canFit(int width, int height) {
        return false;
    }
}
