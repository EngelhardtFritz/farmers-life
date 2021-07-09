package com.asheriit.asheriitsfarmerslife.container;

import com.asheriit.asheriitsfarmerslife.api.utils.container.FunctionalIntReferenceHolder;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModContainerTypes;
import com.asheriit.asheriitsfarmerslife.tileentity.WinePressTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Objects;

public class WinePressContainer extends AbstractInventoryContainer {
    public WinePressTileEntity winePressTileEntity;
    public FunctionalIntReferenceHolder currentProgressSteps;
    private IWorldPosCallable iWorldPosCallable;

    // Client Constructor
    public WinePressContainer(final int windowId, PlayerInventory inventory, final PacketBuffer extraData) {
        this(windowId, inventory, getTileEntity(inventory, extraData));
    }

    // Server Constructor
    public WinePressContainer(final int windowId, final PlayerInventory playerInventory, final WinePressTileEntity tileEntity) {
        super(ModContainerTypes.WINE_PRESS_CONTAINER.get(), windowId);
        this.winePressTileEntity = tileEntity;
        this.iWorldPosCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        winePressTileEntity.getInventoryCapability().ifPresent((itemHandler) -> {
            addSlot(new SlotItemHandler(itemHandler, WinePressTileEntity.INPUT_ITEM_SLOT, 44, 35));
        });
        this.drawPlayerInventory(playerInventory, 8, 84);
        this.drawPlayerHotbar(playerInventory, 8, 142);

        this.trackInt(currentProgressSteps = new FunctionalIntReferenceHolder(() -> this.winePressTileEntity.getProcessStepCurrent(), (value) -> this.winePressTileEntity.setProcessStepCurrent((short) value)));
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return isWithinUsableDistance(this.iWorldPosCallable, playerIn, ModBlocks.WOOD_WINE_PRESS.get());
    }

    /**
     * Get the WinePressTileEntity if it exists for the given position
     *
     * @param playerInventory: The player inventory
     * @param data:            Extra data
     * @return Returns the TileEntity or throws an error if the state is invalid
     */
    @OnlyIn(Dist.CLIENT)
    private static WinePressTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "[WinePressContainer::getTileEntity] PlayerInventory can not be null!");
        Objects.requireNonNull(data, "[WinePressContainer::getTileEntity] PacketBuffer can not be null!");
        final TileEntity tileEntity = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileEntity instanceof WinePressTileEntity) {
            return (WinePressTileEntity) tileEntity;
        }
        throw new IllegalStateException("[WinePressContainer::getTileEntity] TileEntity is not valid: " + tileEntity);
    }

    /**
     * @param tankSlot: The tank slot
     * @return returns the FluidTank for the given tank slot
     */
    @OnlyIn(Dist.CLIENT)
    public FluidTank getFluidTankForSlot(int tankSlot) {
        if (tankSlot < 0) {
            throw new IllegalStateException("[WinePressContainer::getFluidTankForSlot] The tank slot has to be a valid value! It was " + tankSlot);
        }
        return this.winePressTileEntity.getTank(tankSlot);
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
        return this.winePressTileEntity.getFluidInTank(tankSlot);
    }
}
