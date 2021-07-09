package com.asheriit.asheriitsfarmerslife.tileentity;

import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractManualMachineTileEntity<Recipe extends IRecipe<?>> extends TileEntity implements INamedContainerProvider {
    protected short processStepCurrent = -1;
    protected short processStepTotal = -1;
    private boolean wasPowered = false;
    private ITextComponent customName;

    public AbstractManualMachineTileEntity(TileEntityType<?> tileEntityTypeIn, ITextComponent customName) {
        super(tileEntityTypeIn);
        this.setCustomName(customName);
    }

    /**
     * Gets the default display name for the tile entity
     *
     * @return ITextComponent with default display name
     */
    public abstract ITextComponent getDefaultDisplayName();

    /**
     * Get the recipe to process. Used in {@link #updateRecipeProgress()}
     *
     * @return Recipe: returns the recipe to process for the current inventory
     */
    @Nullable
    public abstract Recipe getRecipe();

    /**
     * Important: the recipe can be null if no recipe was found in {@link #getRecipe() getRecipe}
     * Check if the recipe can be processed e.g. the inputs and outputs are valid before setting the output
     *
     * @param recipe: recipe to process
     * @return boolean
     */
    public abstract boolean canProcessRecipe(@Nullable Recipe recipe);

    /**
     * Finish the recipe process. Used in {@link #updateRecipeProgress()}
     * Normally the output is set and the inputs decreased according to the recipe data
     *
     * @param recipe : recipe to process
     */
    public abstract void finishRecipeProcess(@Nullable Recipe recipe);

    /**
     * @return total amount of ticks until the recipe is processed
     */
    public abstract short getProcessStepTotal();

    /**
     * Should return if the tile entity is empty to check if nbt should/can be saved to the dropped item stack
     *
     * @return if the tileEntity contains data
     */
    public abstract boolean isEmpty();

    /**
     * Helper method to get recipes by recipe type
     *
     * @param recipeType: Recipe type to filter the recipes
     * @param world:      World object
     * @return Set of all recipes matching the type
     */
    public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> recipeType, World world) {
        return world != null ? world.getRecipeManager().getRecipes()
                .stream().filter((recipe) -> recipe.getType() == recipeType).collect(Collectors.toSet()) : Collections.emptySet();
    }

    /**
     * If a recipe is found increase the progress till finished, then invalidate times.
     */
    public void updateRecipeProgress() {
        boolean markDirty = false;
        if (world != null && !world.isRemote) {
            Recipe recipeToProduce = getRecipe();
            if (recipeToProduce != null && this.canProcessRecipe(recipeToProduce)) {
                if (this.processStepCurrent == -1 || this.processStepTotal == -1) {
                    this.processStepCurrent = 1;
                    this.processStepTotal = this.getProcessStepTotal();
                } else {
                    this.processStepCurrent++;
                    if (this.processStepCurrent >= this.getProcessStepTotal()) {
                        this.finishRecipeProcess(recipeToProduce);
                        this.invalidateProcessTimes();
                    }
                }
                markDirty = true;
            } else {
                this.invalidateProcessTimes();
            }
        }

        if (markDirty) {
            this.markDirty();
            world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.processStepCurrent = compound.getShort("ProcessStepCurrent");
        this.processStepTotal = compound.getShort("ProcessStepTotal");
        this.wasPowered = compound.getBoolean("WasPowered");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putShort("ProcessStepCurrent", this.processStepCurrent);
        compound.putShort("ProcessStepTotal", this.processStepTotal);
        compound.putBoolean("WasPowered", this.wasPowered);
        return super.write(compound);
    }

    /**
     * Reset all process times.
     * Is used when no recipe is found or if the recipe is finished
     */
    private void invalidateProcessTimes() {
        this.processStepCurrent = -1;
        this.processStepTotal = -1;
    }

    // ----------- GETTER AND SETTER -----------


    public short getProcessStepCurrent() {
        return processStepCurrent;
    }

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

    public void setProcessStepCurrent(short processStepCurrent) {
        this.processStepCurrent = processStepCurrent;
    }

    public void setCustomName(ITextComponent customName) {
        this.customName = customName;
    }

    public boolean wasPowered() {
        return wasPowered;
    }

    public void setWasPowered(boolean wasPowered) {
        this.wasPowered = wasPowered;
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
