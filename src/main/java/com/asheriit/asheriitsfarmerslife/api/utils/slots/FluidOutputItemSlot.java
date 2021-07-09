package com.asheriit.asheriitsfarmerslife.api.utils.slots;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class FluidOutputItemSlot extends SlotItemHandler {
    private FluidTank tankToCheck;

    public FluidOutputItemSlot(IItemHandler itemHandler, FluidTank tankToCheck, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.tankToCheck = tankToCheck;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        LazyOptional<IFluidHandlerItem> handler = FluidUtil.getFluidHandler(stack);

        if (handler.isPresent()) {
            FluidActionResult result = FluidUtil.tryFillContainer(stack, tankToCheck, Integer.MAX_VALUE, null, false);
            return result.isSuccess();
        }
        return false;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
