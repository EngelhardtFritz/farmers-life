package com.asheriit.asheriitsfarmerslife.container;

import com.asheriit.asheriitsfarmerslife.api.utils.container.FunctionalIntReferenceHolder;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModContainerTypes;
import com.asheriit.asheriitsfarmerslife.tileentity.FermentingBarrelTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class FermentingBarrelContainer extends AbstractInventoryContainer {
    public FermentingBarrelTileEntity fermentingBarrelTileEntity;
    public FunctionalIntReferenceHolder currentProcessTime;
    private IWorldPosCallable iWorldPosCallable;

    // Client Constructor
    public FermentingBarrelContainer(final int windowId, PlayerInventory inventory, final PacketBuffer extraData) {
        this(windowId, inventory, getTileEntity(inventory, extraData));
    }

    // Server Constructor
    public FermentingBarrelContainer(final int windowId, final PlayerInventory playerInventory, final FermentingBarrelTileEntity tileEntity) {
        super(ModContainerTypes.FERMENTING_BARREL_CONTAINER.get(), windowId);
        this.fermentingBarrelTileEntity = tileEntity;
        this.iWorldPosCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        this.fermentingBarrelTileEntity.getInventoryCapability().ifPresent((itemHandler) -> {
            addSlot(new SlotItemHandler(itemHandler, FermentingBarrelTileEntity.INPUT_ITEM_SLOT_1, 31, 64));
        });
        this.drawPlayerInventory(playerInventory, 8, 100);
        this.drawPlayerHotbar(playerInventory, 8, 158);

        this.trackInt(currentProcessTime = new FunctionalIntReferenceHolder(() -> this.fermentingBarrelTileEntity.getProcessTimeCurrent(), (value) -> this.fermentingBarrelTileEntity.setProcessTimeCurrent((short) value)));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.iWorldPosCallable, playerIn, ModBlocks.FERMENTING_BARREL.get());
    }

    /**
     * Get the FermentingBarrelTileEntity if it exists for the given position
     *
     * @param playerInventory: The player inventory
     * @param data:            Extra data
     * @return Returns the TileEntity or throws an error if the state is invalid
     */
    @OnlyIn(Dist.CLIENT)
    private static FermentingBarrelTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "[FermentingBarrelContainer] PlayerInventory can not be null!");
        Objects.requireNonNull(data, "[FermentingBarrelContainer] PacketBuffer can not be null!");
        final TileEntity tileEntity = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileEntity instanceof FermentingBarrelTileEntity) {
            return (FermentingBarrelTileEntity) tileEntity;
        }
        throw new IllegalStateException("[FermentingBarrelContainer] TileEntity is not valid: " + tileEntity);
    }
}
