package com.asheriit.asheriitsfarmerslife.container;

import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModContainerTypes;
import com.asheriit.asheriitsfarmerslife.tileentity.AgingWineRackTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

public class AgingWineRackContainer extends AbstractInventoryContainer {
    public AgingWineRackTileEntity agingWineRackTileEntity;
    private IWorldPosCallable iWorldPosCallable;

    // Client Constructor
    public AgingWineRackContainer(final int windowId, PlayerInventory inventory, final PacketBuffer extraData) {
        this(windowId, inventory, getTileEntity(inventory, extraData));
    }

    // Server Constructor
    public AgingWineRackContainer(final int windowId, final PlayerInventory playerInventory, final AgingWineRackTileEntity tileEntity) {
        super(ModContainerTypes.AGING_WINE_RACK_CONTAINER.get(), windowId);
        this.agingWineRackTileEntity = tileEntity;
        this.iWorldPosCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        agingWineRackTileEntity.getInventoryCapability().ifPresent((itemHandler) -> {
            this.drawSlotItemHandler(itemHandler, 0, 3, 3, 61, 17);
        });
        this.drawPlayerInventory(playerInventory, 8, 84);
        this.drawPlayerHotbar(playerInventory, 8, 142);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(iWorldPosCallable, playerIn, ModBlocks.AGING_WINE_RACK.get());
    }

    /**
     * Get the AgingWineRackTileEntity if it exists for the given position
     *
     * @param playerInventory: The player inventory
     * @param data:            Extra data
     * @return Returns the TileEntity or throws an error if the state is invalid
     */
    @OnlyIn(Dist.CLIENT)
    private static AgingWineRackTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "[AgingWineRackContainer::getTileEntity] PlayerInventory can not be null!");
        Objects.requireNonNull(data, "[AgingWineRackContainer::getTileEntity] PacketBuffer can not be null!");
        final TileEntity tileEntity = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileEntity instanceof AgingWineRackTileEntity) {
            return (AgingWineRackTileEntity) tileEntity;
        }
        throw new IllegalStateException("[AgingWineRackContainer::getTileEntity] TileEntity is not valid: " + tileEntity);
    }
}
