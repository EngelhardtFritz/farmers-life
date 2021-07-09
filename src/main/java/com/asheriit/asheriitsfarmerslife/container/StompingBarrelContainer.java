package com.asheriit.asheriitsfarmerslife.container;

import com.asheriit.asheriitsfarmerslife.api.utils.container.FunctionalIntReferenceHolder;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModContainerTypes;
import com.asheriit.asheriitsfarmerslife.tileentity.StompingBarrelTileEntity;
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

public class StompingBarrelContainer extends AbstractInventoryContainer {
    public StompingBarrelTileEntity stompingBarrelTileEntity;
    public FunctionalIntReferenceHolder currentProgressSteps;
    private IWorldPosCallable iWorldPosCallable;

    // Client Constructor
    public StompingBarrelContainer(final int windowId, PlayerInventory inventory, final PacketBuffer extraData) {
        this(windowId, inventory, getTileEntity(inventory, extraData));
    }

    // Server Constructor
    public StompingBarrelContainer(final int windowId, final PlayerInventory playerInventory, final StompingBarrelTileEntity tileEntity) {
        super(ModContainerTypes.STOMPING_BARREL_CONTAINER.get(), windowId);
        this.stompingBarrelTileEntity = tileEntity;
        this.iWorldPosCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        stompingBarrelTileEntity.getInventoryCapability().ifPresent((itemHandler) -> {
            addSlot(new SlotItemHandler(itemHandler, StompingBarrelTileEntity.INPUT_ITEM_SLOT, 44, 35));
        });
        this.drawPlayerInventory(playerInventory, 8, 84);
        this.drawPlayerHotbar(playerInventory, 8, 142);

        this.trackInt(currentProgressSteps = new FunctionalIntReferenceHolder(() -> this.stompingBarrelTileEntity.getProcessStepCurrent(), (value) -> this.stompingBarrelTileEntity.setProcessStepCurrent((short) value)));
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return isWithinUsableDistance(this.iWorldPosCallable, playerIn, ModBlocks.STOMPING_BARREL.get());
    }

    /**
     * Get the StompingBarrelTileEntity if it exists for the given position
     *
     * @param playerInventory: The player inventory
     * @param data:            Extra data
     * @return Returns the TileEntity or throws an error if the state is invalid
     */
    @OnlyIn(Dist.CLIENT)
    private static StompingBarrelTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "[StompingBarrelContainer::getTileEntity] PlayerInventory can not be null!");
        Objects.requireNonNull(data, "[StompingBarrelContainer::getTileEntity] PacketBuffer can not be null!");
        final TileEntity tileEntity = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileEntity instanceof StompingBarrelTileEntity) {
            return (StompingBarrelTileEntity) tileEntity;
        }
        throw new IllegalStateException("[StompingBarrelContainer::getTileEntity] TileEntity is not valid: " + tileEntity);
    }

    /**
     * @param tankSlot: The tank slot
     * @return returns the FluidTank for the given tank slot
     */
    @OnlyIn(Dist.CLIENT)
    public FluidTank getFluidTankForSlot(int tankSlot) {
        if (tankSlot < 0) {
            throw new IllegalStateException("[StompingBarrelContainer::getFluidTankForSlot] The tank slot has to be a valid value! It was " + tankSlot);
        }
        return this.stompingBarrelTileEntity.getTank(tankSlot);
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
        return this.stompingBarrelTileEntity.getFluidInTank(tankSlot);
    }
}
