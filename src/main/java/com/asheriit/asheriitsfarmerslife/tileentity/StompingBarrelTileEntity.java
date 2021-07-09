package com.asheriit.asheriitsfarmerslife.tileentity;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.AnimationTimingHelper;
import com.asheriit.asheriitsfarmerslife.container.StompingBarrelContainer;
import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.recipes.StompingBarrelRecipe;
import com.asheriit.asheriitsfarmerslife.utils.items.WinePressItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class StompingBarrelTileEntity extends AbstractManualFluidMachineTileEntity<StompingBarrelRecipe> {
    private static final ITextComponent CUSTOM_TILE_NAME = new TranslationTextComponent("tileentity." + FarmersLife.MOD_ID + ".stomping_barrel_tile_entity");

    public static final int INPUT_ITEM_SLOT = 0;
    public static final int OUTPUT_TANK_SLOT = 0;
    public static final int FRAMETIME = 5;

    protected WinePressItemHandler<StompingBarrelTileEntity> inventory;

    private float nextTextureAnimation;
    private int currentUVIndex;

    private final LazyOptional<IItemHandlerModifiable> inventoryCapability;
    private final LazyOptional<IFluidHandler> fluidTankCapability;

    public StompingBarrelTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn, CUSTOM_TILE_NAME, 1, 2000);
        this.currentUVIndex = 0;
        this.nextTextureAnimation = AnimationTimingHelper.getElapsedTime() + this.getFrametime();
        this.inventory = new WinePressItemHandler(1, this);
        this.inventoryCapability = LazyOptional.of(this::getInventory);
        this.fluidTankCapability = LazyOptional.of(() -> this);
    }

    public StompingBarrelTileEntity() {
        this(ModTileEntityTypes.STOMPING_BARREL_TILE_ENTITY.get());
    }

    @Override
    public int getInputTankCount() {
        return 0;
    }

    @Override
    public int getOutputTankCount() {
        return 1;
    }

    @Override
    public ITextComponent getDefaultDisplayName() {
        return new TranslationTextComponent("container." + FarmersLife.MOD_ID + ".stomping_barrel");
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
    public StompingBarrelRecipe getRecipe() {
        if (world != null) {
            Set<IRecipe<?>> recipes = findRecipesByType(ModRecipeSerializer.STOMPING_BARREL_RECIPE_TYPE, this.world);
            for (IRecipe<?> recipe : recipes) {
                StompingBarrelRecipe stompingBarrelRecipe = (StompingBarrelRecipe) recipe;
                if (stompingBarrelRecipe.matches(new RecipeWrapper(this.inventory), world)) {
                    return stompingBarrelRecipe;
                }
            }
        }
        return null;
    }

    @Override
    public boolean canProcessRecipe(StompingBarrelRecipe recipe) {
        if (recipe == null) {
            return false;
        }

        return getTank(INPUT_ITEM_SLOT).fill(recipe.getRecipeFluidOutput().copy(), FluidAction.SIMULATE) > 0;
    }

    @Override
    public void finishRecipeProcess(StompingBarrelRecipe recipe) {
        // Reduce stack by the amount required from the recipe
        this.inventory.extractItem(INPUT_ITEM_SLOT, recipe.getInputIngredientCount(), false);
        FluidStack result = recipe.getRecipeFluidOutput();
        // Set a copy of the result into the inventory slot (IMPORTANT: it has to be a copy)
        this.getTank(OUTPUT_TANK_SLOT).fill(result.copy(), FluidAction.EXECUTE);
        // TODO: play sound
    }

    @Override
    public short getProcessStepTotal() {
        StompingBarrelRecipe recipe = this.getRecipe();
        if (recipe != null) {
            return (short) recipe.getProcessSteps();
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new StompingBarrelContainer(windowId, playerInventory, this);
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        final int MAX_DISTANCE_IN_BLOCKS = 16;
        return MAX_DISTANCE_IN_BLOCKS * MAX_DISTANCE_IN_BLOCKS;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return LazyOptional.empty();
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        this.fluidTankCapability.invalidate();
        this.inventoryCapability.invalidate();
    }

    public LazyOptional<IItemHandlerModifiable> getInventoryCapability() {
        return this.inventoryCapability;
    }

    public LazyOptional<IFluidHandler> getFluidTankCapability() {
        return this.fluidTankCapability;
    }

    @Nonnull
    public IItemHandlerModifiable getInventory() {
        return this.inventory;
    }

    public int getFrametime() {
        return FRAMETIME;
    }

    public float getNextTextureAnimationTime() {
        return nextTextureAnimation;
    }

    public void setNextTextureAnimationTime(float timePassed) {
        this.nextTextureAnimation = timePassed;
    }

    public int getCurrentUVIndex() {
        return currentUVIndex;
    }

    public void setCurrentUVIndex(int currentUVIndex) {
        this.currentUVIndex = currentUVIndex;
    }

    public boolean outputIsFull() {
        return this.getFluidInTank(StompingBarrelTileEntity.OUTPUT_TANK_SLOT).getAmount() == this.getTankCapacity(StompingBarrelTileEntity.OUTPUT_TANK_SLOT);
    }
}
