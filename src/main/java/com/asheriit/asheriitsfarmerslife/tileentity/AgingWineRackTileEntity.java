package com.asheriit.asheriitsfarmerslife.tileentity;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.container.AgingWineRackContainer;
import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.recipes.AgingRecipe;
import com.asheriit.asheriitsfarmerslife.utils.items.AgingItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Set;

public class AgingWineRackTileEntity extends AbstractNamedContainerTileEntity implements ITickableTileEntity {
    private static final ITextComponent CUSTOM_TILE_NAME = new TranslationTextComponent("tileentity." + FarmersLife.MOD_ID + ".aging_wine_rack_tile_entity");

    protected AgingItemHandler inventory;
    private final LazyOptional<IItemHandlerModifiable> inventoryCapability;
    private final RangedWrapper[] rangedRecipeWrappers = new RangedWrapper[getCols() * getRows()];

    private int[] processTimesPerSlot = new int[getCols() * getRows()];
    private int[] processTimesTotalPerSlot = new int[getCols() * getRows()];

    public AgingWineRackTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn, CUSTOM_TILE_NAME);
        this.inventory = new AgingItemHandler(getCols() * getRows());
        this.inventoryCapability = LazyOptional.of(this::getInventory);
        Arrays.fill(processTimesPerSlot, 0);
        Arrays.fill(processTimesTotalPerSlot, 0);
        for (int i = 0; i < getCols() * getRows(); i++) {
            rangedRecipeWrappers[i] = new RangedWrapper(this.inventory, i, i + 1);
        }
    }

    public AgingWineRackTileEntity() {
        this(ModTileEntityTypes.AGING_WINE_RACK_TILE_ENTITY.get());
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new AgingWineRackContainer(windowId, playerInventory, this);
    }

    @Override
    public void tick() {
        boolean markDirty = false;
        if (world != null && !world.isRemote) {
            // Get recipe which should be produced
            for (int i = 0; i < this.processTimesPerSlot.length; i++) {
                AgingRecipe recipeToProduce = this.getRecipeForSlot(i);
                if (recipeToProduce != null && this.canProcessRecipe(recipeToProduce, i)) {
                    if (this.processTimesTotalPerSlot[i] == 0) {
                        this.processTimesTotalPerSlot[i] = this.getProcessTimeTotalForSlot(i);
                    } else {
                        this.processTimesPerSlot[i]++;
                        if (this.processTimesPerSlot[i] == this.getProcessTimeTotalForSlot(i)) {
                            this.finishRecipeProcess(recipeToProduce, i);
                            this.invalidateProcessTime(i);
                        }
                    }
                    markDirty = true;
                } else {
                    this.invalidateProcessTime(i);
                }
            }
        }

        if (markDirty) {
            this.markDirty();
            world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    private void invalidateProcessTime(int slot) {
        this.processTimesPerSlot[slot] = 0;
        this.processTimesTotalPerSlot[slot] = 0;
    }

    @Nullable
    public AgingRecipe getRecipeForSlot(int slot) {
        if (world != null) {
            Set<IRecipe<?>> recipes = AbstractTickingMachineTileEntity.findRecipesByType(ModRecipeSerializer.AGING_RECIPE_TYPE, this.world);
            for (IRecipe<?> recipe : recipes) {
                AgingRecipe agingRecipe = (AgingRecipe) recipe;
                if (agingRecipe.matches(new RecipeWrapper(this.rangedRecipeWrappers[slot]), this.world)) {
                    return agingRecipe;
                }
            }
        }
        return null;
    }

    public boolean canProcessRecipe(@Nullable AgingRecipe recipe, int slot) {
        if (recipe == null) {
            return false;
        }

        ItemStack outputSlotStack = this.inventory.getStackInSlot(slot);
        return outputSlotStack.isEmpty() || (outputSlotStack.getCount() == 1 && recipe.getRecipeOutput().getCount() == 1);
    }

    public void finishRecipeProcess(AgingRecipe recipe, int slot) {
        this.inventory.getStackInSlot(slot).shrink(recipe.getRecipeOutput().getCount());
        this.inventory.insertItem(slot, recipe.getRecipeOutput().copy(), false);
    }

    public short getProcessTimeTotalForSlot(int slot) {
        AgingRecipe recipe = this.getRecipeForSlot(slot);
        if (recipe != null) {
            return (short) recipe.getProcessTime();
        }
        return 0;
    }

    @Override
    protected void invalidateCaps() {
        this.inventoryCapability.invalidate();
        super.invalidateCaps();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("Inventory", this.inventory.serializeNBT());
        for (int i = 0; i < this.getCols() * this.getRows(); i++) {
            compound.putInt("ProcessTime" + i, this.processTimesPerSlot[i]);
            compound.putInt("ProcessTimeTotal" + i, this.processTimesTotalPerSlot[i]);
        }
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.inventory.deserializeNBT(compound.getCompound("Inventory"));
        for (int i = 0; i < this.getCols() * this.getRows(); i++) {
            this.processTimesPerSlot[i] = compound.getInt("ProcessTime" + i);
            this.processTimesTotalPerSlot[i] = compound.getInt("ProcessTimeTotal" + i);
        }
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        final int MAX_DISTANCE_IN_BLOCKS = 10;
        return MAX_DISTANCE_IN_BLOCKS * MAX_DISTANCE_IN_BLOCKS;
    }

    // ------------ GETTER AND SETTER ------------
    public ITextComponent getDefaultDisplayName() {
        return new TranslationTextComponent("container." + FarmersLife.MOD_ID + ".aging_wine_rack");
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

    public int[] getProcessTimesPerSlot() {
        return processTimesPerSlot;
    }

    public int[] getProcessTimesTotalPerSlot() {
        return processTimesTotalPerSlot;
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
