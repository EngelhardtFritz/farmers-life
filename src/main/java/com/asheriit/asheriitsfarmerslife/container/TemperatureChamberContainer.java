package com.asheriit.asheriitsfarmerslife.container;

import com.asheriit.asheriitsfarmerslife.api.utils.container.FunctionalIntReferenceHolder;
import com.asheriit.asheriitsfarmerslife.api.utils.slots.GenericResultSlot;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModContainerTypes;
import com.asheriit.asheriitsfarmerslife.tileentity.TemperatureChamberTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class TemperatureChamberContainer extends AbstractInventoryContainer {
    public TemperatureChamberTileEntity temperatureChamberTileEntity;
    public FunctionalIntReferenceHolder currentProcessTime;
    public FunctionalIntReferenceHolder heatBuffer;
    public FunctionalIntReferenceHolder coldBuffer;
    public FunctionalIntReferenceHolder bufferCooldown;
    public FunctionalIntReferenceHolder temperatureUnit;
    public FunctionalIntReferenceHolder currentTemperature;
    public FunctionalIntReferenceHolder targetTemperature;
    public FunctionalIntReferenceHolder coldDecreasePerTick;
    public FunctionalIntReferenceHolder heatDecreasePerTick;
    private IWorldPosCallable iWorldPosCallable;

    // Client Constructor
    public TemperatureChamberContainer(final int windowId, PlayerInventory inventory, final PacketBuffer extraData) {
        this(windowId, inventory, getTileEntity(inventory, extraData));
    }

    // Server Constructor
    public TemperatureChamberContainer(final int windowId, final PlayerInventory playerInventory, final TemperatureChamberTileEntity tileEntity) {
        super(ModContainerTypes.TEMPERATURE_CHAMBER_CONTAINER.get(), windowId);
        this.temperatureChamberTileEntity = tileEntity;
        this.iWorldPosCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        tileEntity.getInventoryCapability().ifPresent((itemHandler) -> {
            addSlot(new SlotItemHandler(itemHandler, TemperatureChamberTileEntity.HEAT_ITEM_SLOT, 15, 52));
            addSlot(new SlotItemHandler(itemHandler, TemperatureChamberTileEntity.COLD_ITEM_SLOT, 39, 52));
            addSlot(new SlotItemHandler(itemHandler, TemperatureChamberTileEntity.INPUT_ITEM_SLOT, 125, 36));
            addSlot(new GenericResultSlot(itemHandler, TemperatureChamberTileEntity.OUTPUT_ITEM_SLOT_1, 163, 18));
            addSlot(new GenericResultSlot(itemHandler, TemperatureChamberTileEntity.OUTPUT_ITEM_SLOT_2, 163, 36));
            addSlot(new GenericResultSlot(itemHandler, TemperatureChamberTileEntity.OUTPUT_ITEM_SLOT_3, 163, 54));
        });
        this.drawPlayerInventory(playerInventory, 16, 102);
        this.drawPlayerHotbar(playerInventory, 16, 160);

        this.trackInt(currentProcessTime = new FunctionalIntReferenceHolder(() -> this.temperatureChamberTileEntity.getProcessTimeCurrent(), (value) -> this.temperatureChamberTileEntity.setProcessTimeCurrent((short) value)));
        this.trackInt(heatBuffer = new FunctionalIntReferenceHolder(() -> this.temperatureChamberTileEntity.getHeatBuffer(), (value) -> this.temperatureChamberTileEntity.setHeatBuffer(value)));
        this.trackInt(coldBuffer = new FunctionalIntReferenceHolder(() -> this.temperatureChamberTileEntity.getColdBuffer(), (value) -> this.temperatureChamberTileEntity.setColdBuffer(value)));
        this.trackInt(bufferCooldown = new FunctionalIntReferenceHolder(() -> this.temperatureChamberTileEntity.getBufferInputCooldown(), (value) -> this.temperatureChamberTileEntity.setBufferInputCooldown(value)));
        this.trackInt(temperatureUnit = new FunctionalIntReferenceHolder(() -> this.temperatureChamberTileEntity.getTemperatureUnitId(), (value) -> this.temperatureChamberTileEntity.setTemperatureUnit(value)));
        this.trackInt(currentTemperature = new FunctionalIntReferenceHolder(() -> this.temperatureChamberTileEntity.getCurrentTempInKelvin(), (value) -> this.temperatureChamberTileEntity.setCurrentTempInKelvin(value)));
        this.trackInt(targetTemperature = new FunctionalIntReferenceHolder(() -> this.temperatureChamberTileEntity.getTargetTempInKelvin(), (value) -> this.temperatureChamberTileEntity.setTargetTempInKelvin(value)));
        this.trackInt(coldDecreasePerTick = new FunctionalIntReferenceHolder(() -> this.temperatureChamberTileEntity.getColdDecreasePerTick(), (value) -> this.temperatureChamberTileEntity.setColdDecreasePerTick(value)));
        this.trackInt(heatDecreasePerTick = new FunctionalIntReferenceHolder(() -> this.temperatureChamberTileEntity.getHeatDecreasePerTick(), (value) -> this.temperatureChamberTileEntity.setHeatDecreasePerTick(value)));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.iWorldPosCallable, playerIn, ModBlocks.TEMPERATURE_CHAMBER.get());
    }

    /**
     * Get the TemperatureChamberTileEntity if it exists for the given position
     *
     * @param playerInventory: The player inventory
     * @param data:            Extra data
     * @return Returns the TileEntity or throws an error if the state is invalid
     */
    @OnlyIn(Dist.CLIENT)
    private static TemperatureChamberTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "[TemperatureChamberContainer] PlayerInventory can not be null!");
        Objects.requireNonNull(data, "[TemperatureChamberContainer] PacketBuffer can not be null!");
        final TileEntity tileEntity = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileEntity instanceof TemperatureChamberTileEntity) {
            return (TemperatureChamberTileEntity) tileEntity;
        }
        throw new IllegalStateException("[TemperatureChamberContainer] TileEntity is not valid: " + tileEntity);
    }
}
