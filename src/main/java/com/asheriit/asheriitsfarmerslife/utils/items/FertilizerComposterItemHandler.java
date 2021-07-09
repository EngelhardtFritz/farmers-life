package com.asheriit.asheriitsfarmerslife.utils.items;

import com.asheriit.asheriitsfarmerslife.recipes.FertilizerComposterRecipe;
import com.asheriit.asheriitsfarmerslife.tileentity.FertilizerComposterTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class FertilizerComposterItemHandler extends ItemStackHandler {

    public FertilizerComposterItemHandler(int size) {
        super(size);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        switch (slot) {
            case FertilizerComposterTileEntity.INPUT_ITEM_SLOT_1:
            case FertilizerComposterTileEntity.INPUT_ITEM_SLOT_2:
            case FertilizerComposterTileEntity.INPUT_ITEM_SLOT_3:
            case FertilizerComposterTileEntity.INPUT_ITEM_SLOT_4:
                return FertilizerComposterRecipe.FertilizerComposterRecipeSerializer.ingredientList.contains(stack.getItem());
            default:
                return true;
        }
    }

    public boolean isEmpty() {
        for (ItemStack stack : this.stacks) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
