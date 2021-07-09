package com.asheriit.asheriitsfarmerslife.container;

import com.asheriit.asheriitsfarmerslife.api.utils.container.FunctionalIntReferenceHolder;
import com.asheriit.asheriitsfarmerslife.api.utils.slots.FluidInputItemSlot;
import com.asheriit.asheriitsfarmerslife.api.utils.slots.FluidOutputItemSlot;
import com.asheriit.asheriitsfarmerslife.api.utils.slots.GenericResultSlot;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModContainerTypes;
import com.asheriit.asheriitsfarmerslife.tileentity.BottlingMachineTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class BottlingMachineContainer extends AbstractInventoryContainer {
    public BottlingMachineTileEntity bottlingMachineTileEntity;
    public FunctionalIntReferenceHolder currentProcessTime;
    private IWorldPosCallable iWorldPosCallable;

    public BottlingMachineContainer(final int windowId, PlayerInventory inventory, final PacketBuffer extraData) {
        this(windowId, inventory, getTileEntity(inventory, extraData));
    }

    public BottlingMachineContainer(final int windowId, final PlayerInventory playerInventory, final BottlingMachineTileEntity tileEntity) {
        super(ModContainerTypes.BOTTLING_MACHINE_CONTAINER.get(), windowId);
        this.bottlingMachineTileEntity = tileEntity;
        this.iWorldPosCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        tileEntity.getInventoryCapability().ifPresent((itemHandler) -> {
            addSlot(new SlotItemHandler(itemHandler, BottlingMachineTileEntity.INPUT_ITEM_SLOT_1, 58, 20));
            addSlot(new SlotItemHandler(itemHandler, BottlingMachineTileEntity.INPUT_ITEM_SLOT_2, 80, 16));
            addSlot(new SlotItemHandler(itemHandler, BottlingMachineTileEntity.INPUT_ITEM_SLOT_3, 102, 20));
            addSlot(new SlotItemHandler(itemHandler, BottlingMachineTileEntity.INPUT_ITEM_SLOT_4, 80, 53));
            addSlot(new FluidInputItemSlot(itemHandler, BottlingMachineTileEntity.INPUT_TANK_INPUT_ITEM, 8, 18));
            addSlot(new FluidOutputItemSlot(itemHandler, this.bottlingMachineTileEntity.getTank(BottlingMachineTileEntity.INPUT_TANK), BottlingMachineTileEntity.INPUT_TANK_OUTPUT_ITEM, 8, 51));
            addSlot(new GenericResultSlot(itemHandler, BottlingMachineTileEntity.OUTPUT_ITEM_SLOT, 134, 53));
        });
        this.drawPlayerInventory(playerInventory, 8, 84);
        this.drawPlayerHotbar(playerInventory, 8, 142);

        this.trackInt(currentProcessTime = new FunctionalIntReferenceHolder(() -> this.bottlingMachineTileEntity.getProcessTimeCurrent(), (value) -> this.bottlingMachineTileEntity.setProcessTimeCurrent((short) value)));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(iWorldPosCallable, playerIn, ModBlocks.BOTTLING_MACHINE.get());
    }

    /**
     * Get the BottlingMachineTileEntity if it exists for the given position
     *
     * @param playerInventory: The player inventory
     * @param data:            Extra data
     * @return Returns the TileEntity or throws an error if the state is invalid
     */
    @OnlyIn(Dist.CLIENT)
    private static BottlingMachineTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "[BottlingMachineContainer] PlayerInventory can not be null!");
        Objects.requireNonNull(data, "[BottlingMachineContainer] PacketBuffer can not be null!");
        final TileEntity tileEntity = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileEntity instanceof BottlingMachineTileEntity) {
            return (BottlingMachineTileEntity) tileEntity;
        }
        throw new IllegalStateException("[BottlingMachineContainer] TileEntity is not valid: " + tileEntity);
    }
}
