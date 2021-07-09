package com.asheriit.asheriitsfarmerslife.utils.items;

import com.asheriit.asheriitsfarmerslife.init.ModTags;
import com.asheriit.asheriitsfarmerslife.recipes.BottlingRecipe;
import com.asheriit.asheriitsfarmerslife.tileentity.BottlingMachineTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class BottlingItemHandler extends ItemStackHandler {
    public BottlingItemHandler(int size) {
        super(size);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        switch (slot) {
            case BottlingMachineTileEntity.INPUT_ITEM_SLOT_1:
            case BottlingMachineTileEntity.INPUT_ITEM_SLOT_2:
            case BottlingMachineTileEntity.INPUT_ITEM_SLOT_3:
                return BottlingRecipe.BottlerRecipeSerializer.itemInputList.contains(stack.getItem()) && !stack.getItem().isIn(ModTags.Items.WINE_BOTTLES);
            case BottlingMachineTileEntity.INPUT_ITEM_SLOT_4:
                return BottlingRecipe.BottlerRecipeSerializer.itemInputList.contains(stack.getItem()) && stack.getItem().isIn(ModTags.Items.WINE_BOTTLES);
            default:
                return true;
        }
    }
}
