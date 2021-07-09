package com.asheriit.asheriitsfarmerslife.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public abstract class AbstractInventoryContainer extends Container {
    public AbstractInventoryContainer(ContainerType<?> type, int id) {
        super(type, id);
    }

    /**
     * Draw the inventory inside a container for the given slots
     * The inventory has 36 slots (0-8 are hotbar slots, 9-35 are main inventory slots)
     *
     * @param playerInventory: The main inventory from the player (36 slots)
     * @param startX:          X-coordinate to start drawing the slots
     * @param startY:          Y-coordinate to start drawing the slots
     */
    protected void drawPlayerInventory(PlayerInventory playerInventory, int startX, int startY) {
        int maxCols = 9;
        int maxRows = 3;

        for (int verticalIndex = 0; verticalIndex < maxRows; verticalIndex++) {
            for (int horizontalIndex = 0; horizontalIndex < maxCols; horizontalIndex++) {
                addSlot(new Slot(playerInventory, horizontalIndex + verticalIndex * 9 + 9, startX + horizontalIndex * 18, startY + verticalIndex * 18));
            }
        }
    }

    /**
     * Draw the player hotbar
     *
     * @param playerInventory: The main inventory from the player (36 slots)
     * @param startX:          X-coordinate to start drawing the slots
     * @param startY:          Y-coordinate to draw the slots
     */
    protected void drawPlayerHotbar(PlayerInventory playerInventory, int startX, int startY) {
        int hotbarSize = 9;   // 0-8    -> hotbar slots
        for (int hotbarIndex = 0; hotbarIndex < hotbarSize; hotbarIndex++) {
            addSlot(new Slot(playerInventory, hotbarIndex, startX + hotbarIndex * 18, startY));
        }
    }

    /**
     * Draw default slots for given cols and rows starting at x and y
     *
     * @param itemHandler: ItemHandler
     * @param cols:        Number of cols to draw
     * @param rows:        Number of rows to draw
     * @param startX:      X-coordinate to start drawing the slots
     * @param startY:      Y-coordinate to start drawing the slots
     */
    protected void drawSlotItemHandler(IItemHandler itemHandler, int startIndex, int cols, int rows, int startX, int startY) {
        for (int verticalIndex = 0; verticalIndex < cols; verticalIndex++) {
            for (int horizontalIndex = 0; horizontalIndex < rows; horizontalIndex++) {
                addSlot(new SlotItemHandler(itemHandler, startIndex + horizontalIndex + (verticalIndex * 3), startX + horizontalIndex * 18, startY + verticalIndex * 18));
            }
        }
    }

    /**
     * Default way to transfer stacks between slots
     *
     * @param playerIn: the player executing
     * @param index:    slot index
     * @return the changed item stack
     */
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        /*
         * Slot explanation:
         * 0    - x:
         * x    - x+8:     HotBar slots
         * x+8  - x+16:    1. row of inventory slots
         * x+16 - x+24:    2. row of inventory slots
         * x+24 - x+32:    3. row of inventory slots
         */
        ItemStack stackToReturn = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            final ItemStack itemStackSlot = slot.getStack();
            stackToReturn = itemStackSlot.copy();

            final int machineSlots = this.inventorySlots.size() - playerIn.inventory.mainInventory.size();
            if (index < machineSlots) {
                if (!mergeItemStack(itemStackSlot, machineSlots, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(itemStackSlot, 0, machineSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStackSlot.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemStackSlot.getCount() == stackToReturn.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemStackSlot);
        }
        return stackToReturn;
    }
}
