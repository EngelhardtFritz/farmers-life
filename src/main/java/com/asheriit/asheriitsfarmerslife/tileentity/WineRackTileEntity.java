package com.asheriit.asheriitsfarmerslife.tileentity;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.container.WineRackContainer;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WineRackTileEntity extends TileEntity implements INamedContainerProvider {
    private static final ITextComponent CUSTOM_TILE_NAME = new TranslationTextComponent("tileentity." + FarmersLife.MOD_ID + ".wine_rack_tile_entity");

    protected ItemStackHandler inventory;
    private ITextComponent customName;

    private final LazyOptional<IItemHandlerModifiable> inventoryCapability;

    public WineRackTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.setCustomName(CUSTOM_TILE_NAME);
        this.inventory = new ItemStackHandler(getCols() * getRows()) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem().isIn(ModTags.Items.WINE_BOTTLES);
            }

            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                WineRackTileEntity.this.markDirty();
            }

            @Override
            public int getSlotLimit(int slot) {
                return 16;
            }
        };
        this.inventoryCapability = LazyOptional.of(this::getInventory);
    }

    public WineRackTileEntity() {
        this(ModTileEntityTypes.WINE_RACK_TILE_ENTITY.get());
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new WineRackContainer(windowId, playerInventory, this);
    }

    @Override
    protected void invalidateCaps() {
        this.inventoryCapability.invalidate();
        super.invalidateCaps();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("Inventory", this.inventory.serializeNBT());
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.inventory.deserializeNBT(compound.getCompound("Inventory"));
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        final int MAX_DISTANCE_IN_BLOCKS = 16;
        return MAX_DISTANCE_IN_BLOCKS * MAX_DISTANCE_IN_BLOCKS;
    }

    // ------------ GETTER AND SETTER ------------
    @Override
    public ITextComponent getDisplayName() {
        return this.getName();
    }

    public ITextComponent getName() {
        return this.customName != null ? this.customName : this.getDefaultDisplayName();
    }

    public ITextComponent getDefaultDisplayName() {
        return new TranslationTextComponent("container." + FarmersLife.MOD_ID + ".wine_rack");
    }

    @Nonnull
    public IItemHandlerModifiable getInventory() {
        return this.inventory;
    }

    public LazyOptional<IItemHandlerModifiable> getInventoryCapability() {
        return this.inventoryCapability;
    }

    public int getRows() {
        return 3;
    }

    public int getCols() {
        return 3;
    }

    public void setCustomName(ITextComponent customName) {
        this.customName = customName;
    }

    // ------------ CAPABILITIES ------------
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.inventoryCapability.cast();
        }
        return super.getCapability(cap, side);
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
