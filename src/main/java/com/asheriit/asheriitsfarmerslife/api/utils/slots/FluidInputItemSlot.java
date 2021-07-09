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

public class FluidInputItemSlot extends SlotItemHandler {

    public FluidInputItemSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        LazyOptional<IFluidHandlerItem> handler = FluidUtil.getFluidHandler(stack);
        FluidTank tank = new FluidTank(1000);

        if (handler.isPresent()) {
            FluidActionResult result = FluidUtil.tryEmptyContainer(stack, tank, Integer.MAX_VALUE, null, false);
            return result.isSuccess();
        }
        return false;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
