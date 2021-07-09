package com.asheriit.asheriitsfarmerslife.container;

import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModContainerTypes;
import com.asheriit.asheriitsfarmerslife.tileentity.WineRackTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

public class WineRackContainer extends AbstractInventoryContainer {
    public WineRackTileEntity wineRackTileEntity;
    private IWorldPosCallable iWorldPosCallable;

    // Client Constructor
    public WineRackContainer(final int windowId, PlayerInventory inventory, final PacketBuffer extraData) {
        this(windowId, inventory, getTileEntity(inventory, extraData));
    }

    // Server Constructor
    public WineRackContainer(final int windowId, final PlayerInventory playerInventory, final WineRackTileEntity tileEntity) {
        super(ModContainerTypes.WINE_RACK_CONTAINER.get(), windowId);
        this.wineRackTileEntity = tileEntity;
        this.iWorldPosCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        wineRackTileEntity.getInventoryCapability().ifPresent((itemHandler) -> {
            this.drawSlotItemHandler(itemHandler, 0, 3, 3, 61, 17);
        });
        this.drawPlayerInventory(playerInventory, 8, 84);
        this.drawPlayerHotbar(playerInventory, 8, 142);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(iWorldPosCallable, playerIn, ModBlocks.OAK_WINE_RACK.get()) ||
                isWithinUsableDistance(iWorldPosCallable, playerIn, ModBlocks.SPRUCE_WINE_RACK.get()) ||
                isWithinUsableDistance(iWorldPosCallable, playerIn, ModBlocks.ACACIA_WINE_RACK.get()) ||
                isWithinUsableDistance(iWorldPosCallable, playerIn, ModBlocks.JUNGLE_WINE_RACK.get()) ||
                isWithinUsableDistance(iWorldPosCallable, playerIn, ModBlocks.DARK_OAK_WINE_RACK.get()) ||
                isWithinUsableDistance(iWorldPosCallable, playerIn, ModBlocks.BIRCH_WINE_RACK.get());
    }

    /**
     * Get the WineRackTileEntity if it exists for the given position
     *
     * @param playerInventory: The player inventory
     * @param data:            Extra data
     * @return Returns the TileEntity or throws an error if the state is invalid
     */
    @OnlyIn(Dist.CLIENT)
    private static WineRackTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "[WineRackContainer::getTileEntity] PlayerInventory can not be null!");
        Objects.requireNonNull(data, "[WineRackContainer::getTileEntity] PacketBuffer can not be null!");
        final TileEntity tileEntity = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileEntity instanceof WineRackTileEntity) {
            return (WineRackTileEntity) tileEntity;
        }
        throw new IllegalStateException("[WineRackContainer::getTileEntity] TileEntity is not valid: " + tileEntity);
    }
}
