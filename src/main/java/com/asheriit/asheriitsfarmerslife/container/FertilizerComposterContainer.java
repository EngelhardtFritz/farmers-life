package com.asheriit.asheriitsfarmerslife.container;

import com.asheriit.asheriitsfarmerslife.api.utils.container.FunctionalIntReferenceHolder;
import com.asheriit.asheriitsfarmerslife.api.utils.slots.GenericResultSlot;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModContainerTypes;
import com.asheriit.asheriitsfarmerslife.tileentity.FertilizerComposterTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class FertilizerComposterContainer extends AbstractInventoryContainer {
    public FertilizerComposterTileEntity fertilizerComposterTileEntity;
    public FunctionalIntReferenceHolder currentProcessTime;
    private IWorldPosCallable iWorldPosCallable;

    // Client Constructor
    public FertilizerComposterContainer(final int windowId, PlayerInventory inventory, final PacketBuffer extraData) {
        this(windowId, inventory, getTileEntity(inventory, extraData));
    }

    // Server Constructor
    public FertilizerComposterContainer(final int windowId, final PlayerInventory playerInventory, final FertilizerComposterTileEntity tileEntity) {
        super(ModContainerTypes.FERTILIZER_COMPOSTER_CONTAINER.get(), windowId);
        this.fertilizerComposterTileEntity = tileEntity;
        this.iWorldPosCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        fertilizerComposterTileEntity.getInventoryCapability().ifPresent((itemHandler) -> {
            addSlot(new SlotItemHandler(itemHandler, FertilizerComposterTileEntity.INPUT_ITEM_SLOT_1, 25, 25));
            addSlot(new SlotItemHandler(itemHandler, FertilizerComposterTileEntity.INPUT_ITEM_SLOT_2, 25, 45));
            addSlot(new SlotItemHandler(itemHandler, FertilizerComposterTileEntity.INPUT_ITEM_SLOT_3, 45, 25));
            addSlot(new SlotItemHandler(itemHandler, FertilizerComposterTileEntity.INPUT_ITEM_SLOT_4, 45, 45));
            addSlot(new GenericResultSlot(itemHandler, FertilizerComposterTileEntity.OUTPUT_ITEM_PRIMARY_SLOT, 116, 35));
            addSlot(new GenericResultSlot(itemHandler, FertilizerComposterTileEntity.OUTPUT_ITEM_SECONDARY_SLOT, 139, 35));
        });
        this.drawPlayerInventory(playerInventory, 8, 84);
        this.drawPlayerHotbar(playerInventory, 8, 142);

        this.trackInt(currentProcessTime = new FunctionalIntReferenceHolder(() -> this.fertilizerComposterTileEntity.getProcessTimeCurrent(), (value) -> this.fertilizerComposterTileEntity.setProcessTimeCurrent((short) value)));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.iWorldPosCallable, playerIn, ModBlocks.FERTILIZER_COMPOSTER.get());
    }

    /**
     * Get the DryingMachineTileEntity if it exists for the given position
     *
     * @param playerInventory: The player inventory
     * @param data:            Extra data
     * @return Returns the TileEntity or throws an error if the state is invalid
     */
    @OnlyIn(Dist.CLIENT)
    private static FertilizerComposterTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "[FertilizerComposterContainer] PlayerInventory can not be null!");
        Objects.requireNonNull(data, "[FertilizerComposterContainer] PacketBuffer can not be null!");
        final TileEntity tileEntity = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileEntity instanceof FertilizerComposterTileEntity) {
            return (FertilizerComposterTileEntity) tileEntity;
        }
        throw new IllegalStateException("[FertilizerComposterContainer] TileEntity is not valid: " + tileEntity);
    }
}
