package com.asheriit.asheriitsfarmerslife.tileentity;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractTickingMachineTileEntity<Recipe extends IRecipe<?>> extends AbstractNamedContainerTileEntity implements ITickableTileEntity {
    protected short processTimeCurrent = -1;
    protected short processTimeTotal = -1;

    public AbstractTickingMachineTileEntity(TileEntityType<?> tileEntityTypeIn, ITextComponent customName) {
        super(tileEntityTypeIn, customName);
        this.setCustomName(customName);
    }

    /**
     * Get the recipe to process. Used in {@link #tick()}
     *
     * @return Recipe: returns the recipe to process for the current inventory or null if no recipe could be found
     */
    @Nullable
    public abstract Recipe getRecipe();

    /**
     * Method to check if an additional condition is required to enable the block. Used in {@link #tick()}
     * For example the block can only run if the block is powered by redstone
     *
     * @param world world object to validate if block should be enabled
     * @return true if the block is enabled, false otherwise
     */
    public abstract boolean isBlockEnabled(World world);

    /**
     * Important: the recipe can be null if no recipe was found in {@link #getRecipe() getRecipe}
     * Check if the recipe can be processed e.g. the inputs and outputs are valid before setting the output
     *
     * @param recipe: recipe to process
     * @return boolean
     */
    public abstract boolean canProcessRecipe(@Nullable Recipe recipe);

    /**
     * Finish the recipe process. Used in {@link #tick()}
     * Set the outputs and decrease the inputs according to the recipe data
     *
     * @param recipe : recipe to process
     */
    public abstract void finishRecipeProcess(@Nonnull Recipe recipe);

    /**
     * @return total amount of ticks until the recipe is processed
     */
    public abstract short getProcessTimeTotal();

    /**
     * Should return if the tile entity is empty to check if nbt should/can be saved to the dropped item stack
     *
     * @return if the tileEntity contains data
     */
    public abstract boolean isEmpty();

    /**
     * Get Recipes by their corresponding recipe type
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
     * Tries to find and finish a recipe if the block is enabled.
     * Marks the block as dirty when the recipe is finished so that the block is updated.
     */
    @Override
    public void tick() {
        boolean markDirty = false;
        if (world != null && !world.isRemote) {
            if (this.isBlockEnabled(this.world)) {
                // Get recipe which should be produced
                Recipe recipeToProduce = this.getRecipe();
                if (recipeToProduce != null && this.canProcessRecipe(recipeToProduce)) {
                    if (this.processTimeCurrent == -1 || this.processTimeTotal == -1) {
                        this.processTimeCurrent = 0;
                        this.processTimeTotal = this.getProcessTimeTotal();
                    } else {
                        this.processTimeCurrent++;
                        if (this.processTimeCurrent == this.getProcessTimeTotal()) {
                            this.finishRecipeProcess(recipeToProduce);
                            this.invalidateProcessTimes();
                            markDirty = true;
                        }
                    }
                } else {
                    this.invalidateProcessTimes();
                }
            }
        }

        if (markDirty) {
            this.markDirty();
            world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.processTimeCurrent = compound.getShort("ProcessTimeCurrent");
        this.processTimeTotal = compound.getShort("ProcessTimeTotal");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putShort("ProcessTimeCurrent", this.processTimeCurrent);
        compound.putShort("ProcessTimeTotal", this.processTimeTotal);
        return super.write(compound);
    }

    /**
     * Reset all process times.
     * Is used when no recipe is found or if the recipe is finished
     */
    protected void invalidateProcessTimes() {
        this.processTimeCurrent = -1;
        this.processTimeTotal = -1;
    }

    public short getProcessTimeCurrent() {
        return processTimeCurrent;
    }

    public void setProcessTimeCurrent(short processTimeCurrent) {
        this.processTimeCurrent = processTimeCurrent;
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
