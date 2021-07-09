package com.asheriit.asheriitsfarmerslife.tileentity;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractClarificationMachineTileEntity<Recipe extends IRecipe<?>> extends AbstractTickingFluidMachineTileEntity<Recipe> {
    public static final int INPUT_ITEM_SLOT = 0;
    public static final int OUTPUT_ITEM_SLOT = 1;
    public static final int INPUT_TANK_INPUT_ITEM_SLOT = 2;
    public static final int INPUT_TANK_OUTPUT_ITEM_SLOT = 3;
    public static final int OUTPUT_TANK_INPUT_ITEM_SLOT = 4;
    public static final int OUTPUT_TANK_OUTPUT_ITEM_SLOT = 5;
    public static final int INPUT_TANK = 0;
    public static final int OUTPUT_TANK = 1;

    private final LazyOptional<IFluidHandler> inputFluidTankCapability;
    private final LazyOptional<IFluidHandler> outputFluidTankCapability;

    public AbstractClarificationMachineTileEntity(TileEntityType tileEntityTypeIn, ITextComponent customName) {
        super(tileEntityTypeIn, customName, 2, 4000);
        this.inputFluidTankCapability = LazyOptional.of(() -> this.getTank(AbstractClarificationMachineTileEntity.INPUT_TANK));
        this.outputFluidTankCapability = LazyOptional.of(() -> this.getTank(AbstractClarificationMachineTileEntity.OUTPUT_TANK));
    }

    @Override
    public int getInputTankCount() {
        return 1;
    }

    @Override
    public int getOutputTankCount() {
        return 1;
    }

    @Override
    public boolean isBlockEnabled(World world) {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    // ------------ HELPER METHODS ------------
    public void fillContainerItem(int slot, int tankSlot, IItemHandlerModifiable inventory) {
        // Handle red slot (extract fluid)
        FluidActionResult emptyInputTank = FluidUtil.tryFillContainer(inventory.getStackInSlot(slot), getTank(tankSlot), Integer.MAX_VALUE, null, false);
        if (emptyInputTank.isSuccess()) {
            FluidActionResult emptiedTank = FluidUtil.tryFillContainer(inventory.getStackInSlot(slot), getTank(tankSlot), Integer.MAX_VALUE, null, true);
            inventory.setStackInSlot(slot, emptiedTank.getResult().copy());
            this.markDirty();
            this.world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    // ------------ CAPABILITIES ------------
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if (side == Direction.UP) {
                return this.inputFluidTankCapability.cast();
            } else if (side == Direction.DOWN) {
                return this.outputFluidTankCapability.cast();
            }
        }
        return super.getCapability(cap, side);
    }
}
