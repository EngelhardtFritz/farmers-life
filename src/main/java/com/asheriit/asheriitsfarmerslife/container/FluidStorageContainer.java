package com.asheriit.asheriitsfarmerslife.container;

import com.asheriit.asheriitsfarmerslife.api.utils.slots.FluidInputItemSlot;
import com.asheriit.asheriitsfarmerslife.api.utils.slots.FluidOutputItemSlot;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModContainerTypes;
import com.asheriit.asheriitsfarmerslife.tileentity.FluidStorageTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

public class FluidStorageContainer extends AbstractInventoryContainer {
    public FluidStorageTileEntity fluidStorageTileEntity;
    private IWorldPosCallable iWorldPosCallable;

    // Client Constructor
    public FluidStorageContainer(final int windowId, PlayerInventory inventory, final PacketBuffer extraData) {
        this(windowId, inventory, getTileEntity(inventory, extraData));
    }

    // Server Constructor
    public FluidStorageContainer(final int windowId, final PlayerInventory playerInventory, final FluidStorageTileEntity tileEntity) {
        super(ModContainerTypes.FLUID_STORAGE_CONTAINER.get(), windowId);
        this.fluidStorageTileEntity = tileEntity;
        this.iWorldPosCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        this.fluidStorageTileEntity.getInventoryCapability().ifPresent((itemHandler) -> {
            addSlot(new FluidOutputItemSlot(itemHandler, this.fluidStorageTileEntity.getTank(FluidStorageTileEntity.INPUT_OUTPUT_FLUID_TANK), FluidStorageTileEntity.INPUT_TANK_ITEM_SLOT_OUT, 39, 50));
            addSlot(new FluidInputItemSlot(itemHandler, FluidStorageTileEntity.INPUT_TANK_ITEM_SLOT_IN, 121, 50));
        });
        this.drawPlayerInventory(playerInventory, 8, 84);
        this.drawPlayerHotbar(playerInventory, 8, 142);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.iWorldPosCallable, playerIn, ModBlocks.WOOD_FLUID_STORAGE_BARREL.get());
    }

    /**
     * Get the FermentingBarrelTileEntity if it exists for the given position
     *
     * @param playerInventory: The player inventory
     * @param data:            Extra data
     * @return Returns the TileEntity or throws an error if the state is invalid
     */
    @OnlyIn(Dist.CLIENT)
    private static FluidStorageTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "[FluidStorageContainer] PlayerInventory can not be null!");
        Objects.requireNonNull(data, "[FluidStorageContainer] PacketBuffer can not be null!");
        final TileEntity tileEntity = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileEntity instanceof FluidStorageTileEntity) {
            return (FluidStorageTileEntity) tileEntity;
        }
        throw new IllegalStateException("[FluidStorageContainer] TileEntity is not valid: " + tileEntity);
    }
}
