package com.asheriit.asheriitsfarmerslife.container;

import com.asheriit.asheriitsfarmerslife.api.utils.container.FunctionalIntReferenceHolder;
import com.asheriit.asheriitsfarmerslife.api.utils.slots.FluidInputItemSlot;
import com.asheriit.asheriitsfarmerslife.api.utils.slots.FluidOutputItemSlot;
import com.asheriit.asheriitsfarmerslife.api.utils.slots.GenericResultSlot;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModContainerTypes;
import com.asheriit.asheriitsfarmerslife.tileentity.DryingMachineTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.Objects;

public class DryingMachineContainer extends AbstractInventoryContainer {
    public DryingMachineTileEntity dryingMachineTileEntity;
    public FunctionalIntReferenceHolder currentProcessTime;
    private IWorldPosCallable iWorldPosCallable;

    // Client Constructor
    public DryingMachineContainer(final int windowId, PlayerInventory inventory, final PacketBuffer extraData) {
        this(windowId, inventory, getTileEntity(inventory, extraData));
    }

    // Server Constructor
    public DryingMachineContainer(final int windowId, final PlayerInventory playerInventory, final DryingMachineTileEntity tileEntity) {
        super(ModContainerTypes.DRYING_MACHINE_CONTAINER.get(), windowId);
        this.dryingMachineTileEntity = tileEntity;
        this.iWorldPosCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        dryingMachineTileEntity.getInventoryCapability().ifPresent((itemHandler) -> {
            addSlot(new FluidOutputItemSlot(itemHandler, dryingMachineTileEntity.getTank(DryingMachineTileEntity.INPUT_TANK), DryingMachineTileEntity.INPUT_TANK_ITEM_SLOT_OUT, 37, 46));
            addSlot(new FluidInputItemSlot(itemHandler, DryingMachineTileEntity.INPUT_TANK_ITEM_SLOT_IN, 37, 23));
            addSlot(new GenericResultSlot(itemHandler, DryingMachineTileEntity.OUTPUT_ITEM_SLOT, 119, 35));
        });
        this.drawPlayerInventory(playerInventory, 8, 84);
        this.drawPlayerHotbar(playerInventory, 8, 142);

        this.trackInt(currentProcessTime = new FunctionalIntReferenceHolder(() -> this.dryingMachineTileEntity.getProcessTimeCurrent(), (value) -> this.dryingMachineTileEntity.setProcessTimeCurrent((short) value)));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.iWorldPosCallable, playerIn, ModBlocks.DRYING_MACHINE.get());
    }

    /**
     * @param tankSlot: The tank slot
     * @return returns the FluidStack for the given tank slot
     */
    @OnlyIn(Dist.CLIENT)
    public FluidStack getFluidStackForTank(int tankSlot) {
        if (tankSlot < 0) {
            return FluidStack.EMPTY;
        }
        return this.dryingMachineTileEntity.getFluidInTank(tankSlot);
    }

    /**
     * @param tankSlot: The tank slot
     * @return returns the FluidTank for the given tank slot
     */
    @OnlyIn(Dist.CLIENT)
    public FluidTank getFluidTankForSlot(int tankSlot) {
        if (tankSlot < 0) {
            throw new IllegalStateException("[DryingMachineContainer#getFluidTankForSlot] The tank slot has to be a valid value! It was " + tankSlot);
        }
        return this.dryingMachineTileEntity.getTank(tankSlot);
    }

    /**
     * Get the DryingMachineTileEntity if it exists for the given position
     *
     * @param playerInventory: The player inventory
     * @param data:            Extra data
     * @return Returns the TileEntity or throws an error if the state is invalid
     */
    @OnlyIn(Dist.CLIENT)
    private static DryingMachineTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "[DryingMachineContainer] PlayerInventory can not be null!");
        Objects.requireNonNull(data, "[DryingMachineContainer] PacketBuffer can not be null!");
        final TileEntity tileEntity = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileEntity instanceof DryingMachineTileEntity) {
            return (DryingMachineTileEntity) tileEntity;
        }
        throw new IllegalStateException("[DryingMachineContainer] TileEntity is not valid: " + tileEntity);
    }
}
