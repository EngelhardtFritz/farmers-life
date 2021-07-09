package com.asheriit.asheriitsfarmerslife.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractFluidStorageTileEntity extends TileEntity implements INamedContainerProvider, IFluidHandler {
    private ITextComponent customName;
    private final FluidTank[] tanks;

    public AbstractFluidStorageTileEntity(TileEntityType<?> tileEntityTypeIn, ITextComponent customName, int tankCount, int tankCapacity) {
        super(tileEntityTypeIn);
        this.setCustomName(customName);
        FluidTank[] fluidTanks = new FluidTank[tankCount];
        for (int i = 0; i < tankCount; i++) {
            fluidTanks[i] = new FluidTank(tankCapacity);
        }
        this.tanks = fluidTanks;
    }

    /**
     * Get the amount of tanks which count as input tank
     *
     * @return count of input tanks
     */
    public abstract int getInputTankCount();

    /**
     * Get the amount of tanks which count as output tank
     *
     * @return count of output tanks
     */
    public abstract int getOutputTankCount();

    /**
     * Gets the default display name for the tile entity
     *
     * @return ITextComponent with default display name
     */
    public abstract ITextComponent getDefaultDisplayName();

    /**
     * Should return if the tile entity is empty to check if nbt should/can be saved to the dropped item stack
     *
     * @return if the tileEntity contains data
     */
    public abstract boolean isEmpty();

    @Override
    public ITextComponent getDisplayName() {
        return this.getName();
    }

    public ITextComponent getName() {
        return this.customName != null ? this.customName : this.getDefaultDisplayName();
    }

    public ITextComponent getCustomName() {
        return this.customName;
    }

    public void setCustomName(ITextComponent customName) {
        this.customName = customName;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        ListNBT listNBT = compound.getList("FluidTanks", Constants.NBT.TAG_COMPOUND);
        for (int index = 0; index < tanks.length && index < listNBT.size(); index++) {
            tanks[index].setFluid(FluidStack.loadFluidStackFromNBT((CompoundNBT) listNBT.get(index)));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        ListNBT listNBT = new ListNBT();
        for (FluidTank fluidTank : tanks) {
            listNBT.add(fluidTank.writeToNBT(new CompoundNBT()));
        }
        compound.put("FluidTanks", listNBT);
        return super.write(compound);
    }

    /**
     * Implementation of the {@link IFluidHandler IFluidHandler} methods
     *
     * @return amount of all tanks
     */
    @Override
    public int getTanks() {
        return tanks.length;
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        FluidTank fluidTank = this.getTank(tank);
        if (fluidTank == null) {
            return FluidStack.EMPTY;
        }
        return fluidTank.getFluid();
    }

    @Override
    public int getTankCapacity(int tank) {
        FluidTank fluidTank = this.getTank(tank);
        if (fluidTank == null) {
            return 0;
        }
        return fluidTank.getTankCapacity(tank);
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        FluidTank fluidTank = this.getTank(tank);
        return fluidTank != null && fluidTank.isFluidValid(stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        // Iterate over all tanks fill
        for (int index = 0; index < this.getInputTankCount(); index++) {
            FluidStack fluidFromTank = getFluidInTank(index);
            if (isFluidValid(index, resource) && (fluidFromTank.isEmpty() || resource.isFluidEqual(fluidFromTank))) {
                return tanks[index].fill(resource, action);
            }
        }
        return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.isEmpty()) {
            return FluidStack.EMPTY;
        }
        int allTanksCount = this.getInputTankCount() + this.getOutputTankCount();
        for (int index = 0; index < allTanksCount; index++) {
            if (resource.isFluidEqual(getFluidInTank(index))) {
                FluidTank fluidTank = getTank(index);
                if (fluidTank == null) {
                    throw new IllegalArgumentException("[AbstractFluidMachineTileEntity#drain(FluidStack, FluidAction)] The tank for the given index does not exist.");
                }
                return fluidTank.drain(resource, action);
            }
        }
        return FluidStack.EMPTY;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        int allTanksCount = this.getInputTankCount() + this.getOutputTankCount();
        for (int index = 0; index < allTanksCount; index++) {
            FluidTank fluidTank = getTank(index);
            if (fluidTank == null) {
                throw new IllegalArgumentException("[AbstractFluidMachineTileEntity#drain(int, FluidAction)] The tank for the given index does not exist.");
            }
            int fluidAmount = fluidTank.getFluidAmount();
            if (fluidAmount > 0) {
                return fluidTank.drain(maxDrain, action);
            }
        }
        return FluidStack.EMPTY;
    }
    // End of IFluidHandler Implementation

    /**
     * Get FluidTank for the given index
     *
     * @param tankIndex: FluidTank index
     * @return found FluidTank
     */
    public FluidTank getTank(int tankIndex) {
        if (tankIndex < 0 || tankIndex >= tanks.length) {
            return null;
        }
        return tanks[tankIndex];
    }

    /**
     * Try to empty the input or output FluidTank
     *
     * @param itemStack:    ItemStack to check if it can contain a fluid
     * @param playerEntity: PlayerEntity to check if creative mode
     * @param isInputTank:  Boolean
     * @param tankIndex:    FluidTank index
     * @return ItemStack with empty or filled itemStack
     */
    public ItemStack emptyIOTankIfPossible(ItemStack itemStack, PlayerEntity playerEntity, boolean isInputTank, int tankIndex) {
        ItemStack resultStack = itemStack;

        if (!playerEntity.isCreative()) {
            FluidActionResult emptyInputTank = FluidUtil.tryFillContainer(itemStack, getInputOrOutputFluidTank(isInputTank, tankIndex), Integer.MAX_VALUE, null, false);

            if (emptyInputTank.isSuccess()) {
                FluidActionResult emptiedTank = FluidUtil.tryFillContainer(itemStack, getInputOrOutputFluidTank(isInputTank, tankIndex), Integer.MAX_VALUE, null, true);
                this.markDirty();
                world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
                resultStack = emptiedTank.getResult().copy();
            }
        }
        return resultStack;
    }


    /**
     * Try to fill the input or output FluidTank
     *
     * @param itemStack:    ItemStack to check if it can contain a fluid
     * @param playerEntity: PlayerEntity to check if creative mode
     * @param isInputTank:  Boolean
     * @param tankIndex:    FluidTank index
     * @return ItemStack with empty or filled itemStack
     */
    public ItemStack fillIOTankIfPossible(ItemStack itemStack, PlayerEntity playerEntity, boolean isInputTank, int tankIndex) {
        ItemStack resultStack = itemStack;
        FluidActionResult fillInputTank = FluidUtil.tryEmptyContainer(itemStack, getInputOrOutputFluidTank(isInputTank, tankIndex), Integer.MAX_VALUE, null, true);

        if (fillInputTank.isSuccess()) {
            this.markDirty();
            world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
            resultStack = fillInputTank.getResult().copy();
        }

        if (playerEntity.isCreative()) {
            resultStack = itemStack;
        }

        return resultStack;
    }


    /**
     * Get the input or output FluidTank for the given index
     *
     * @param isInputTank: True if it is the input tank, false otherwise
     * @param tankIndex:   Index of the tank to get
     * @return FluidTank if found, null otherwise
     */
    private FluidTank getInputOrOutputFluidTank(boolean isInputTank, int tankIndex) {
        return isInputTank ? getTank(tankIndex) : getTank(this.getInputTankCount() + tankIndex);
    }

    // ----------- SYNC PACKETS START -----------
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(getPos(), 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundNBT nbt) {
        this.read(nbt);
    }
    // ----------- SYNC PACKETS END -----------
}
