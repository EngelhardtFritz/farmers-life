package com.asheriit.asheriitsfarmerslife.container;

import com.asheriit.asheriitsfarmerslife.api.utils.container.FunctionalIntReferenceHolder;
import com.asheriit.asheriitsfarmerslife.api.utils.slots.GenericResultSlot;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModContainerTypes;
import com.asheriit.asheriitsfarmerslife.tileentity.BoilingCauldronTileEntity;
import com.asheriit.asheriitsfarmerslife.utils.slots.FuelSlotItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class BoilingCauldronContainer extends AbstractInventoryContainer {
    public BoilingCauldronTileEntity boilingCauldronTileEntity;
    public FunctionalIntReferenceHolder currentProcessTime;
    public FunctionalIntReferenceHolder burnTimeLeft;
    public FunctionalIntReferenceHolder totalBurnTime;
    private IWorldPosCallable iWorldPosCallable;

    // Client Constructor
    public BoilingCauldronContainer(final int windowId, PlayerInventory inventory, final PacketBuffer extraData) {
        this(windowId, inventory, getTileEntity(inventory, extraData));
    }

    // Server Constructor
    public BoilingCauldronContainer(final int windowId, final PlayerInventory playerInventory, final BoilingCauldronTileEntity tileEntity) {
        super(ModContainerTypes.BOILING_CAULDRON_CONTAINER.get(), windowId);
        this.boilingCauldronTileEntity = tileEntity;
        this.iWorldPosCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        tileEntity.getInventoryCapability().ifPresent((itemHandler) -> {
            addSlot(new SlotItemHandler(itemHandler, BoilingCauldronTileEntity.INPUT_ITEM_SLOT, 48, 35));
            addSlot(new FuelSlotItemHandler(itemHandler, BoilingCauldronTileEntity.FUEL_SLOT, 80, 70));
            addSlot(new GenericResultSlot(itemHandler, BoilingCauldronTileEntity.OUTPUT_ITEM_SLOT, 112, 35));
        });
        this.drawPlayerInventory(playerInventory, 8, 102);
        this.drawPlayerHotbar(playerInventory, 8, 160);

        this.trackInt(currentProcessTime = new FunctionalIntReferenceHolder(() -> this.boilingCauldronTileEntity.getProcessTimeCurrent(), (value) -> this.boilingCauldronTileEntity.setProcessTimeCurrent((short) value)));
        this.trackInt(burnTimeLeft = new FunctionalIntReferenceHolder(() -> this.boilingCauldronTileEntity.getBurnTimeLeft(), (value) -> this.boilingCauldronTileEntity.setBurnTimeLeft(value)));
        this.trackInt(totalBurnTime = new FunctionalIntReferenceHolder(() -> this.boilingCauldronTileEntity.getBurnTimeTotal(), (value) -> this.boilingCauldronTileEntity.setBurnTimeTotal(value)));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.iWorldPosCallable, playerIn, ModBlocks.BOILING_CAULDRON.get());
    }

    /**
     * Get the BoilingCauldronTileEntity if it exists for the given position
     *
     * @param playerInventory: The player inventory
     * @param data:            Extra data
     * @return Returns the TileEntity or throws an error if the state is invalid
     */
    @OnlyIn(Dist.CLIENT)
    private static BoilingCauldronTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "[BoilingCauldronContainer] PlayerInventory can not be null!");
        Objects.requireNonNull(data, "[BoilingCauldronContainer] PacketBuffer can not be null!");
        final TileEntity tileEntity = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileEntity instanceof BoilingCauldronTileEntity) {
            return (BoilingCauldronTileEntity) tileEntity;
        }
        throw new IllegalStateException("[BoilingCauldronContainer] TileEntity is not valid: " + tileEntity);
    }
}
