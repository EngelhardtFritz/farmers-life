package com.asheriit.asheriitsfarmerslife.container;

import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModContainerTypes;
import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
import com.asheriit.asheriitsfarmerslife.recipes.IFarmerCraftingRecipe;
import com.asheriit.asheriitsfarmerslife.utils.slots.FarmerCraftingResultSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FarmerWorkbenchContainer extends AbstractInventoryContainer {
    private final CraftingInventory craftMatrix = new CraftingInventory(this, 3, 3);
    private final CraftResultInventory craftResult = new CraftResultInventory();
    private final IWorldPosCallable worldPosCallable;
    private final PlayerEntity player;
    private static Map<ServerPlayerEntity, IFarmerCraftingRecipe> playerToLastUsedRecipeMap;

    public FarmerWorkbenchContainer(final int windowId, PlayerInventory inventory, final PacketBuffer extraData) {
        this(windowId, inventory, IWorldPosCallable.DUMMY);
    }

    public FarmerWorkbenchContainer(final int windowId, final PlayerInventory playerInventory, IWorldPosCallable worldPosCallable) {
        super(ModContainerTypes.FARMER_WORKBENCH_CONTAINER.get(), windowId);
        this.worldPosCallable = worldPosCallable;
        this.player = playerInventory.player;
        playerToLastUsedRecipeMap = new HashMap<>();

        this.addSlot(new FarmerCraftingResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }

        this.drawPlayerInventory(playerInventory, 8, 84);
        this.drawPlayerHotbar(playerInventory, 8, 142);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.worldPosCallable, playerIn, ModBlocks.FARMER_WORKBENCH.get());
    }

    /**
     * Called when the container is closed.
     */
    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.worldPosCallable.consume((world, blockPos) -> {
            this.clearContainer(playerIn, world, this.craftMatrix);
        });
    }

    protected static void updateCraftingResult(int id, World worldIn, PlayerEntity playerIn, CraftingInventory inventoryIn, CraftResultInventory inventoryResult) {
        if (!worldIn.isRemote) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) playerIn;
            ItemStack itemstack = ItemStack.EMPTY;
            IFarmerCraftingRecipe lastUsedRecipe = null;

            if (playerToLastUsedRecipeMap != null && playerToLastUsedRecipeMap.get(serverplayerentity) != null) {
                lastUsedRecipe = playerToLastUsedRecipeMap.get(serverplayerentity);
            }

            if (lastUsedRecipe != null && lastUsedRecipe.matches(inventoryIn, worldIn)) {
                if (inventoryResult.canUseRecipe(worldIn, serverplayerentity, lastUsedRecipe)) {
                    itemstack = lastUsedRecipe.getCraftingResult(inventoryIn);
                }
            } else {
                Optional<IFarmerCraftingRecipe> optional = worldIn.getServer().getRecipeManager().getRecipe(ModRecipeSerializer.FARMER_CRAFTING_TYPE, inventoryIn, worldIn);
                if (optional.isPresent()) {
                    IFarmerCraftingRecipe iFarmerCraftingRecipe = optional.get();
                    if (inventoryResult.canUseRecipe(worldIn, serverplayerentity, iFarmerCraftingRecipe)) {
                        itemstack = iFarmerCraftingRecipe.getCraftingResult(inventoryIn);
                        playerToLastUsedRecipeMap.put(serverplayerentity, iFarmerCraftingRecipe);
                    }
                }
            }

            inventoryResult.setInventorySlotContents(0, itemstack);
            serverplayerentity.connection.sendPacket(new SSetSlotPacket(id, 0, itemstack));
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        this.worldPosCallable.consume((world, blockPos) -> {
            updateCraftingResult(this.windowId, world, this.player, this.craftMatrix, this.craftResult);
        });
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 0) {
                this.worldPosCallable.consume((p_217067_2_, p_217067_3_) -> {
                    itemstack1.getItem().onCreated(itemstack1, p_217067_2_, playerIn);
                });
                if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index >= 10 && index < 46) {
                if (!this.mergeItemStack(itemstack1, 1, 10, false)) {
                    if (index < 37) {
                        if (!this.mergeItemStack(itemstack1, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.mergeItemStack(itemstack1, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.mergeItemStack(itemstack1, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);
            if (index == 0) {
                playerIn.dropItem(itemstack2, false);
            }
        }

        return itemstack;
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
     * null for the initial slot that was double-clicked.
     */
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
    }
}
