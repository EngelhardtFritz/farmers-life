package com.asheriit.asheriitsfarmerslife.container;

import com.asheriit.asheriitsfarmerslife.api.utils.container.FunctionalIntReferenceHolder;
import com.asheriit.asheriitsfarmerslife.api.utils.slots.FluidInputItemSlot;
import com.asheriit.asheriitsfarmerslife.api.utils.slots.FluidOutputItemSlot;
import com.asheriit.asheriitsfarmerslife.api.utils.slots.GenericResultSlot;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModContainerTypes;
import com.asheriit.asheriitsfarmerslife.tileentity.FiningMachineTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class FiningMachineContainer extends AbstractInventoryContainer {
    public FiningMachineTileEntity finingMachineTileEntity;
    public FunctionalIntReferenceHolder currentProcessTime;
    private IWorldPosCallable iWorldPosCallable;

    // Client Constructor
    public FiningMachineContainer(final int windowId, PlayerInventory inventory, final PacketBuffer extraData) {
        this(windowId, inventory, getTileEntity(inventory, extraData));
    }

    // Server Constructor
    public FiningMachineContainer(final int windowId, final PlayerInventory playerInventory, final FiningMachineTileEntity tileEntity) {
        super(ModContainerTypes.FINING_MACHINE_CONTAINER.get(), windowId);
        this.finingMachineTileEntity = tileEntity;
        this.iWorldPosCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        finingMachineTileEntity.getInventoryCapability().ifPresent((itemHandler) -> {
            addSlot(new SlotItemHandler(itemHandler, FiningMachineTileEntity.INPUT_ITEM_SLOT, 79, 36));
            addSlot(new FluidInputItemSlot(itemHandler, FiningMachineTileEntity.INPUT_TANK_INPUT_ITEM_SLOT, 24, 19));
            addSlot(new FluidInputItemSlot(itemHandler, FiningMachineTileEntity.OUTPUT_TANK_INPUT_ITEM_SLOT, 136, 19));
            addSlot(new FluidOutputItemSlot(itemHandler, this.finingMachineTileEntity.getTank(FiningMachineTileEntity.INPUT_TANK), FiningMachineTileEntity.INPUT_TANK_OUTPUT_ITEM_SLOT, 24, 52));
            addSlot(new FluidOutputItemSlot(itemHandler, this.finingMachineTileEntity.getTank(FiningMachineTileEntity.OUTPUT_TANK), FiningMachineTileEntity.OUTPUT_TANK_OUTPUT_ITEM_SLOT, 136, 52));
            addSlot(new GenericResultSlot(itemHandler, FiningMachineTileEntity.OUTPUT_ITEM_SLOT, 79, 74));
        });
        this.drawPlayerInventory(playerInventory, 8, 106);
        this.drawPlayerHotbar(playerInventory, 8, 164);

        this.trackInt(currentProcessTime = new FunctionalIntReferenceHolder(() -> this.finingMachineTileEntity.getProcessTimeCurrent(), (value) -> this.finingMachineTileEntity.setProcessTimeCurrent((short) value)));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.iWorldPosCallable, playerIn, ModBlocks.FINING_MACHINE.get());
    }

    /**
     * Get the DryingMachineTileEntity if it exists for the given position
     *
     * @param playerInventory: The player inventory
     * @param data:            Extra data
     * @return Returns the TileEntity or throws an error if the state is invalid
     */
    @OnlyIn(Dist.CLIENT)
    private static FiningMachineTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "[FiningMachineContainer] PlayerInventory can not be null!");
        Objects.requireNonNull(data, "[FiningMachineContainer] PacketBuffer can not be null!");
        final TileEntity tileEntity = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileEntity instanceof FiningMachineTileEntity) {
            return (FiningMachineTileEntity) tileEntity;
        }
        throw new IllegalStateException("[FiningMachineContainer] TileEntity is not valid: " + tileEntity);
    }
}
