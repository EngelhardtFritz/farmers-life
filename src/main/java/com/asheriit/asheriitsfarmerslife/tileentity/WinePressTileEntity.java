package com.asheriit.asheriitsfarmerslife.tileentity;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.AnimationTimingHelper;
import com.asheriit.asheriitsfarmerslife.container.WinePressContainer;
import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.recipes.WinePressRecipe;
import com.asheriit.asheriitsfarmerslife.utils.items.WinePressItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class WinePressTileEntity extends AbstractManualFluidMachineTileEntity<WinePressRecipe> {
    private static final ITextComponent CUSTOM_TILE_NAME = new TranslationTextComponent("tileentity." + FarmersLife.MOD_ID + ".wine_press_tile_entity");

    public static final int INPUT_ITEM_SLOT = 0;
    public static final int OUTPUT_TANK_SLOT = 0;
    public static final int ANIMATION_LENGTH = 5;
    public static final int FRAMETIME = 5;

    protected WinePressItemHandler<WinePressTileEntity> inventory;
    protected BlockPos slave;

    private float nextAllowedInteraction;
    private float nextTextureAnimation;
    private int currentUVIndex;

    private final LazyOptional<IItemHandlerModifiable> inventoryCapability;
    private final LazyOptional<IFluidHandler> fluidTankCapability;

    public WinePressTileEntity(final TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn, CUSTOM_TILE_NAME, 1, 2000);
        this.slave = null;
        this.currentUVIndex = 0;
        this.nextTextureAnimation = AnimationTimingHelper.getElapsedTime() + this.getFrametime();
        this.nextAllowedInteraction = AnimationTimingHelper.getElapsedTime();
        this.inventory = new WinePressItemHandler(1, this);
        this.inventoryCapability = LazyOptional.of(this::getInventory);
        this.fluidTankCapability = LazyOptional.of(() -> this);
    }

    public WinePressTileEntity() {
        this(ModTileEntityTypes.WINE_PRESS_TILE_ENTITY.get());
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(this.getPos(), this.getPos().add(1, 2, 1));
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
        return new TranslationTextComponent("container." + FarmersLife.MOD_ID + ".wine_press");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("Inventory", this.inventory.serializeNBT());
        compound.putInt("SlaveX", this.slave.getX());
        compound.putInt("SlaveY", this.slave.getY());
        compound.putInt("SlaveZ", this.slave.getZ());
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.inventory.deserializeNBT(compound.getCompound("Inventory"));
        this.slave = new BlockPos(compound.getInt("SlaveX"), compound.getInt("SlaveY"), compound.getInt("SlaveZ"));
    }

    @Override
    public WinePressRecipe getRecipe() {
        if (world != null) {
            Set<IRecipe<?>> recipes = findRecipesByType(ModRecipeSerializer.WINE_PRESS_RECIPE_TYPE, this.world);
            for (IRecipe<?> recipe : recipes) {
                WinePressRecipe winePressRecipe = (WinePressRecipe) recipe;
                if (winePressRecipe.matches(new RecipeWrapper(this.inventory), world)) {
                    return winePressRecipe;
                }
            }
        }
        return null;
    }

    @Override
    public boolean canProcessRecipe(WinePressRecipe recipe) {
        if (recipe == null) {
            return false;
        }

        return getTank(INPUT_ITEM_SLOT).fill(recipe.getRecipeFluidOutput().copy(), FluidAction.SIMULATE) > 0;
    }

    @Override
    public void finishRecipeProcess(WinePressRecipe recipe) {
        // Reduce stack by the amount required from the recipe
        this.inventory.extractItem(INPUT_ITEM_SLOT, recipe.getInputIngredientCount(), false);
        FluidStack result = recipe.getRecipeFluidOutput();
        // Set a copy of the result into the inventory slot (IMPORTANT: it has to be a copy)
        this.getTank(OUTPUT_TANK_SLOT).fill(result.copy(), FluidAction.EXECUTE);
        // TODO: play sound
    }

    @Override
    public void updateRecipeProgress() {
        super.updateRecipeProgress();

        // Activate animation
        BlockPos slavePos = this.getSlave();
        if (slavePos != null) {
            TileEntity te = this.world.getTileEntity(slavePos);

            if (te instanceof WinePressHandleTileEntity) {
                WinePressHandleTileEntity tileEntity = (WinePressHandleTileEntity) te;
                // Important: trigger the animation if the recipe progress has been updated
                // Can only process if the animation timer is finished
                tileEntity.setAnimationActivated(true);
                tileEntity.setAnimationStartStep(this.processStepCurrent);
            }
        }
    }

    @Override
    public short getProcessStepTotal() {
        WinePressRecipe recipe = this.getRecipe();
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
        return new WinePressContainer(windowId, playerInventory, this);
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        final int MAX_DISTANCE_IN_BLOCKS = 32;
        return MAX_DISTANCE_IN_BLOCKS * MAX_DISTANCE_IN_BLOCKS;
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

    public BlockPos getSlave() {
        return this.slave;
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

    public float getNextAllowedInteraction() {
        return nextAllowedInteraction;
    }

    public void setNextAllowedInteraction(float nextAllowedInteraction) {
        this.nextAllowedInteraction = nextAllowedInteraction;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        Direction blockFacing = this.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING);
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side == blockFacing) {
            return this.inventoryCapability.cast();
        }
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && side == Direction.DOWN) {
            return this.fluidTankCapability.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        this.fluidTankCapability.invalidate();
        this.inventoryCapability.invalidate();
    }

    public void addSlave(BlockPos slavePos) {
        if (slavePos == null) {
            throw new IllegalMonitorStateException("[WinePressTileEntity::addSlave] Given BlockPos was null");
        }

        this.slave = slavePos;
    }

    public void removeSlave() {
        this.slave = null;
    }

    public boolean outputIsFull() {
        return this.getFluidInTank(WinePressTileEntity.OUTPUT_TANK_SLOT).getAmount() == this.getTankCapacity(WinePressTileEntity.OUTPUT_TANK_SLOT);
    }
}
