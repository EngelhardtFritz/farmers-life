package com.asheriit.asheriitsfarmerslife.tileentity;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.container.FluidStorageContainer;
import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.utils.items.FluidInOutItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;

public class FluidStorageTileEntity extends AbstractFluidStorageTileEntity {
    private static final ITextComponent CUSTOM_TILE_NAME = new TranslationTextComponent("tileentity." + FarmersLife.MOD_ID + ".fluid_storage_tile_entity");

    public static final int INPUT_OUTPUT_FLUID_TANK = 0;
    public static final int INPUT_TANK_ITEM_SLOT_IN = 0;
    public static final int INPUT_TANK_ITEM_SLOT_OUT = 1;

    private final LazyOptional<IFluidHandler> fluidTankCapability;
    private final LazyOptional<IItemHandlerModifiable> inventoryCapability;

    protected FluidInOutItemHandler inventory;

    public FluidStorageTileEntity(TileEntityType<?> tileEntityTypeIn, int tankCapacity) {
        super(tileEntityTypeIn, CUSTOM_TILE_NAME, 1, tankCapacity);
        this.inventory = new FluidInOutItemHandler(2) {
            @Override
            protected void onContentsChanged(int slot) {
                if (slot == FluidStorageTileEntity.INPUT_TANK_ITEM_SLOT_IN) {
                    // Handle blue slot (insert fluid)
                    ItemStack stackInSlot = inventory.getStackInSlot(INPUT_TANK_ITEM_SLOT_IN);
                    LazyOptional<FluidStack> optionalFluidStack = FluidUtil.getFluidContained(stackInSlot);
                    if (optionalFluidStack.isPresent()) {
                        optionalFluidStack.ifPresent(fluidStack -> {
                            if (isFluidValid(INPUT_OUTPUT_FLUID_TANK, fluidStack)) {
                                FluidActionResult fillInputTank = FluidUtil.tryEmptyContainer(inventory.getStackInSlot(INPUT_TANK_ITEM_SLOT_IN), getTank(INPUT_OUTPUT_FLUID_TANK), Integer.MAX_VALUE, null, true);
                                if (fillInputTank.isSuccess()) {
                                    FluidStorageTileEntity.this.inventory.setStackInSlot(INPUT_TANK_ITEM_SLOT_IN, fillInputTank.getResult().copy());
                                    FluidStorageTileEntity.this.markDirty();
                                    FluidStorageTileEntity.this.world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
                                }
                            }
                        });
                    }
                } else if (slot == FluidStorageTileEntity.INPUT_TANK_ITEM_SLOT_OUT) {
                    // Handle red slot (extract fluid)
                    FluidActionResult emptyInputTank = FluidUtil.tryFillContainer(inventory.getStackInSlot(INPUT_TANK_ITEM_SLOT_OUT), getTank(INPUT_OUTPUT_FLUID_TANK), Integer.MAX_VALUE, null, false);
                    if (emptyInputTank.isSuccess()) {
                        FluidActionResult emptiedTank = FluidUtil.tryFillContainer(inventory.getStackInSlot(INPUT_TANK_ITEM_SLOT_OUT), getTank(INPUT_OUTPUT_FLUID_TANK), Integer.MAX_VALUE, null, true);
                        FluidStorageTileEntity.this.inventory.setStackInSlot(INPUT_TANK_ITEM_SLOT_OUT, emptiedTank.getResult().copy());
                        FluidStorageTileEntity.this.markDirty();
                        FluidStorageTileEntity.this.world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
                    }
                }
            }
        };
        this.fluidTankCapability = LazyOptional.of(() -> this);
        this.inventoryCapability = LazyOptional.of(() -> this.inventory);
    }

    public FluidStorageTileEntity(int tankCapacity) {
        this(ModTileEntityTypes.WOOD_FLUID_STORAGE_BARREL_TILE_ENTITY.get(), tankCapacity);
    }

    @Override
    public int getInputTankCount() {
        return 1;
    }

    @Override
    public int getOutputTankCount() {
        return 0;
    }

    @Override
    public ITextComponent getDefaultDisplayName() {
        return new TranslationTextComponent("container." + FarmersLife.MOD_ID + ".fluid_storage");
    }

    @Override
    public boolean isEmpty() {
        if (this.getFluidInTank(FluidStorageTileEntity.INPUT_OUTPUT_FLUID_TANK).getAmount() == 0) {
            return true;
        }
        return false;
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        this.fluidTankCapability.invalidate();
        this.inventoryCapability.invalidate();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inventory", this.inventory.serializeNBT());
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.inventory.deserializeNBT(compound.getCompound("inventory"));
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new FluidStorageContainer(windowId, playerInventory, this);
    }

    public LazyOptional<IItemHandlerModifiable> getInventoryCapability() {
        return this.inventoryCapability;
    }

    public LazyOptional<IFluidHandler> getFluidTankCapability() {
        return this.fluidTankCapability;
    }

    public final IItemHandlerModifiable getInventory() {
        return this.inventory;
    }
}
