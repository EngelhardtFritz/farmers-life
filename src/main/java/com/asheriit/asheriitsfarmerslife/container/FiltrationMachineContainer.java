package com.asheriit.asheriitsfarmerslife.container;

import com.asheriit.asheriitsfarmerslife.api.utils.container.FunctionalIntReferenceHolder;
import com.asheriit.asheriitsfarmerslife.api.utils.slots.FluidInputItemSlot;
import com.asheriit.asheriitsfarmerslife.api.utils.slots.FluidOutputItemSlot;
import com.asheriit.asheriitsfarmerslife.api.utils.slots.GenericResultSlot;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModContainerTypes;
import com.asheriit.asheriitsfarmerslife.tileentity.FiltrationMachineTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class FiltrationMachineContainer extends AbstractInventoryContainer {
    public FiltrationMachineTileEntity filtrationMachineTileEntity;
    public FunctionalIntReferenceHolder currentProcessTime;
    private IWorldPosCallable iWorldPosCallable;

    // Client Constructor
    public FiltrationMachineContainer(final int windowId, PlayerInventory inventory, final PacketBuffer extraData) {
        this(windowId, inventory, getTileEntity(inventory, extraData));
    }

    // Server Constructor
    public FiltrationMachineContainer(final int windowId, final PlayerInventory playerInventory, final FiltrationMachineTileEntity tileEntity) {
        super(ModContainerTypes.FILTRATION_MACHINE_CONTAINER.get(), windowId);
        this.filtrationMachineTileEntity = tileEntity;
        this.iWorldPosCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        filtrationMachineTileEntity.getInventoryCapability().ifPresent((itemHandler) -> {
            addSlot(new SlotItemHandler(itemHandler, FiltrationMachineTileEntity.INPUT_ITEM_SLOT, 79, 36));
            addSlot(new FluidInputItemSlot(itemHandler, FiltrationMachineTileEntity.INPUT_TANK_INPUT_ITEM_SLOT, 24, 19));
            addSlot(new FluidInputItemSlot(itemHandler, FiltrationMachineTileEntity.OUTPUT_TANK_INPUT_ITEM_SLOT, 136, 19));
            addSlot(new FluidOutputItemSlot(itemHandler, this.filtrationMachineTileEntity.getTank(FiltrationMachineTileEntity.INPUT_TANK), FiltrationMachineTileEntity.INPUT_TANK_OUTPUT_ITEM_SLOT, 24, 52));
            addSlot(new FluidOutputItemSlot(itemHandler, this.filtrationMachineTileEntity.getTank(FiltrationMachineTileEntity.OUTPUT_TANK), FiltrationMachineTileEntity.OUTPUT_TANK_OUTPUT_ITEM_SLOT, 136, 52));
            addSlot(new GenericResultSlot(itemHandler, FiltrationMachineTileEntity.OUTPUT_ITEM_SLOT, 79, 74));
        });
        this.drawPlayerInventory(playerInventory, 8, 106);
        this.drawPlayerHotbar(playerInventory, 8, 164);

        this.trackInt(currentProcessTime = new FunctionalIntReferenceHolder(() -> this.filtrationMachineTileEntity.getProcessTimeCurrent(), (value) -> this.filtrationMachineTileEntity.setProcessTimeCurrent((short) value)));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.iWorldPosCallable, playerIn, ModBlocks.FILTRATION_MACHINE.get());
    }

    /**
     * Calculates the scaled height for the fluid tank
     *
     * @return height of the tank to draw
     */
    @OnlyIn(Dist.CLIENT)
    public int getScaledInputTankHeight(int tankIndex, int tankHeight) {
        int minRenderHeight = 1;
        FluidStack tankFluidStack = this.filtrationMachineTileEntity.getFluidInTank(tankIndex);
        int fluidAmountInTank = tankFluidStack.getAmount();
        int fluidAmountMax = this.filtrationMachineTileEntity.getTankCapacity(tankIndex);

        if (fluidAmountInTank == 0 || tankFluidStack.getFluid() == null) {
            return 0;
        }

        return Math.max(minRenderHeight, (fluidAmountInTank * tankHeight) / fluidAmountMax);
    }

    /**
     * Get the DryingMachineTileEntity if it exists for the given position
     *
     * @param playerInventory: The player inventory
     * @param data:            Extra data
     * @return Returns the TileEntity or throws an error if the state is invalid
     */
    @OnlyIn(Dist.CLIENT)
    private static FiltrationMachineTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "[FiltrationMachineContainer] PlayerInventory can not be null!");
        Objects.requireNonNull(data, "[FiltrationMachineContainer] PacketBuffer can not be null!");
        final TileEntity tileEntity = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileEntity instanceof FiltrationMachineTileEntity) {
            return (FiltrationMachineTileEntity) tileEntity;
        }
        throw new IllegalStateException("[FiltrationMachineContainer] TileEntity is not valid: " + tileEntity);
    }
}
