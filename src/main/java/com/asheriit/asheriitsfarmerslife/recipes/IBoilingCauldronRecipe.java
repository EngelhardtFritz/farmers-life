package com.asheriit.asheriitsfarmerslife.recipes;

import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public interface IBoilingCauldronRecipe extends IRecipe<RecipeWrapper> {
    default IRecipeType<?> getType() {
        return ModRecipeSerializer.BOILING_SOAKING_TYPE;
    }

    boolean isBoilingRecipe();

    boolean matches(ItemStack itemStackIn, FluidStack fluidStackIn, boolean hasLid);

    int getProcessTime();

    Ingredient getInputIngredient();

    int getInputIngredientCount();

    FluidStack getInputFluidStack();

    ItemStack getOutputItemStack();

    FluidStack getOutputFluidStack();

    boolean doesRequireLid();
}
