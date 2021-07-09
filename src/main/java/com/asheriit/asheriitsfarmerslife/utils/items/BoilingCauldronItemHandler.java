package com.asheriit.asheriitsfarmerslife.utils.items;

import com.asheriit.asheriitsfarmerslife.recipes.BoilingRecipe;
import com.asheriit.asheriitsfarmerslife.recipes.SoakingRecipe;
import com.asheriit.asheriitsfarmerslife.tileentity.BoilingCauldronTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class BoilingCauldronItemHandler extends ItemStackHandler {
    public BoilingCauldronItemHandler(int size) {
        super(size);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (slot == BoilingCauldronTileEntity.FUEL_SLOT) {
            return ForgeHooks.getBurnTime(stack) != 0;
        } else if (slot == BoilingCauldronTileEntity.INPUT_ITEM_SLOT) {
            return BoilingRecipe.BoilingRecipeSerializer.itemInputList.contains(stack.getItem()) ||
                    SoakingRecipe.SoakingRecipeSerializer.itemInputList.contains(stack.getItem());
        }
        return super.isItemValid(slot, stack);
    }
}
