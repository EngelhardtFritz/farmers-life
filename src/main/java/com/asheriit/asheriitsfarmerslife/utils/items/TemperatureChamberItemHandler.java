package com.asheriit.asheriitsfarmerslife.utils.items;

import com.asheriit.asheriitsfarmerslife.init.FarmersLifeEventFactory;
import com.asheriit.asheriitsfarmerslife.recipes.TemperatureChamberRecipe;
import com.asheriit.asheriitsfarmerslife.tileentity.TemperatureChamberTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class TemperatureChamberItemHandler extends ItemStackHandler {
    public TemperatureChamberItemHandler(int size) {
        super(size);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (slot == TemperatureChamberTileEntity.HEAT_ITEM_SLOT) {
            return FarmersLifeEventFactory.getItemHeatValue(stack) > 0;
        } else if (slot == TemperatureChamberTileEntity.COLD_ITEM_SLOT) {
            return FarmersLifeEventFactory.getItemColdValue(stack) > 0;
        } else if (slot == TemperatureChamberTileEntity.INPUT_ITEM_SLOT) {
            return TemperatureChamberRecipe.TemperatureChamberRecipeSerializer.itemInputList.contains(stack.getItem());
        }
        return super.isItemValid(slot, stack);
    }
}
