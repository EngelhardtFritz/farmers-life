package com.asheriit.asheriitsfarmerslife.utils.items;

import com.asheriit.asheriitsfarmerslife.tileentity.DryingMachineTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.ItemStackHandler;

public class FluidInOutItemHandler extends ItemStackHandler {
    public FluidInOutItemHandler(int size) {
        super(size);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        if (slot == DryingMachineTileEntity.INPUT_TANK_ITEM_SLOT_IN || slot == DryingMachineTileEntity.INPUT_TANK_ITEM_SLOT_OUT) {
            return FluidUtil.getFluidHandler(stack).isPresent();
        } else {
            return true;
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        if (slot == DryingMachineTileEntity.INPUT_TANK_ITEM_SLOT_IN ||
                slot == DryingMachineTileEntity.INPUT_TANK_ITEM_SLOT_OUT) {
            return 1;
        }
        return super.getSlotLimit(slot);
    }

    @Override
    public String toString() {
        return this.stacks.toString();
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
