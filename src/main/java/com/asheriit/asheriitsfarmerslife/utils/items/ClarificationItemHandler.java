package com.asheriit.asheriitsfarmerslife.utils.items;

import com.asheriit.asheriitsfarmerslife.tileentity.AbstractClarificationMachineTileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class ClarificationItemHandler extends ItemStackHandler {
    public List<Item> itemInputList;

    public ClarificationItemHandler(int size, List<Item> itemInputList) {
        super(size);
        this.itemInputList = itemInputList;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        switch (slot) {
            case AbstractClarificationMachineTileEntity.INPUT_ITEM_SLOT:
                return this.itemInputList.contains(stack.getItem());
            case AbstractClarificationMachineTileEntity.INPUT_TANK_INPUT_ITEM_SLOT:
            case AbstractClarificationMachineTileEntity.INPUT_TANK_OUTPUT_ITEM_SLOT:
            case AbstractClarificationMachineTileEntity.OUTPUT_TANK_INPUT_ITEM_SLOT:
            case AbstractClarificationMachineTileEntity.OUTPUT_TANK_OUTPUT_ITEM_SLOT:
                return FluidUtil.getFluidHandler(stack).isPresent();
            default:
                return true;
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        switch (slot) {
            case AbstractClarificationMachineTileEntity.INPUT_TANK_INPUT_ITEM_SLOT:
            case AbstractClarificationMachineTileEntity.INPUT_TANK_OUTPUT_ITEM_SLOT:
            case AbstractClarificationMachineTileEntity.OUTPUT_TANK_INPUT_ITEM_SLOT:
            case AbstractClarificationMachineTileEntity.OUTPUT_TANK_OUTPUT_ITEM_SLOT:
                return 1;
            case AbstractClarificationMachineTileEntity.INPUT_ITEM_SLOT:
            case AbstractClarificationMachineTileEntity.OUTPUT_ITEM_SLOT:
                return 64;
            default:
                return super.getSlotLimit(slot);
        }
    }

    @Override
    public String toString() {
        return this.stacks.toString();
    }
}
