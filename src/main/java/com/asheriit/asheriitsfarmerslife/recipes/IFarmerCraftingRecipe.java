package com.asheriit.asheriitsfarmerslife.recipes;

import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;

public interface IFarmerCraftingRecipe extends IRecipe<CraftingInventory> {
    default IRecipeType<?> getType() {
        return ModRecipeSerializer.FARMER_CRAFTING_TYPE;
    }
}
