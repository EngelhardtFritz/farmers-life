package com.asheriit.asheriitsfarmerslife.utils.items;

import com.asheriit.asheriitsfarmerslife.recipes.AgingRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class AgingItemHandler extends ItemStackHandler {
    public AgingItemHandler(int size) {
        super(size);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return AgingRecipe.AgingRecipeSerializer.itemInputList.contains(stack.getItem());
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }
}
