package com.asheriit.asheriitsfarmerslife.tileentity;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.FacingHelper;
import com.asheriit.asheriitsfarmerslife.container.BoilingCauldronContainer;
import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.recipes.IBoilingCauldronRecipe;
import com.asheriit.asheriitsfarmerslife.utils.items.BoilingCauldronItemHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class BoilingCauldronTileEntity extends AbstractTickingFluidMachineTileEntity<IBoilingCauldronRecipe> {
    private static final ITextComponent CUSTOM_TILE_NAME = new TranslationTextComponent("tileentity." + FarmersLife.MOD_ID + ".boiling_cauldron_tile_entity");

    public static final int FUEL_SLOT = 0;
    public static final int INPUT_ITEM_SLOT = 1;
    public static final int OUTPUT_ITEM_SLOT = 2;
    public static final int INPUT_FLUID_TANK = 0;
    public static final int OUTPUT_FLUID_TANK = 1;

    protected BoilingCauldronItemHandler inventory;

    private final LazyOptional<IItemHandlerModifiable> inventoryCapability;
    private final LazyOptional<IFluidHandler> fluidTankCapability;

    private boolean fuelEnabled = true;
    private int burnTimeLeft = 0;
    private int burnTimeTotal = 0;

    public BoilingCauldronTileEntity(TileEntityType tileEntityTypeIn) {
        super(tileEntityTypeIn, CUSTOM_TILE_NAME, 2, 4000);
        this.inventory = new BoilingCauldronItemHandler(3);
        this.inventoryCapability = LazyOptional.of(() -> this.inventory);
        this.fluidTankCapability = LazyOptional.of(() -> this);
    }

    public BoilingCauldronTileEntity() {
        this(ModTileEntityTypes.BOILING_CAULDRON_TILE_ENTITY.get());
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
    public void tick() {
        boolean markDirty = false;
        if (world != null && !world.isRemote) {
            if (this.isBlockEnabled(this.world)) {
                // Get recipe which should be produced
                this.updateBurnTime();
                IBoilingCauldronRecipe recipeToProduce = this.getRecipe();
                if (recipeToProduce != null) {
                    this.updateRecipeBurnTimeIfIsBoiling(recipeToProduce);
                }

                if (recipeToProduce != null && this.canProcessRecipe(recipeToProduce)) {
                    if (this.processTimeCurrent == -1 || this.processTimeTotal == -1) {
                        this.processTimeCurrent = 0;
                        this.processTimeTotal = this.getProcessTimeTotal();
                    } else {
                        this.processTimeCurrent++;
                        if (this.processTimeCurrent == this.getProcessTimeTotal()) {
                            this.finishRecipeProcess(recipeToProduce);
                            markDirty = true;
                            this.invalidateProcessTimes();
                        }
                    }
                } else {
                    this.invalidateProcessTimes();
                }
                if (this.updateBlockState()) {
                    markDirty = true;
                }
            }
        }
        if (markDirty) {
            this.markDirty();
            world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    @Nullable
    @Override
    public IBoilingCauldronRecipe getRecipe() {
        if (world != null) {
            Set<IRecipe<?>> recipes = findRecipesByType(this.fuelEnabled ? ModRecipeSerializer.BOILING_RECIPE_TYPE : ModRecipeSerializer.SOAKING_RECIPE_TYPE, this.world);
            for (IRecipe<?> recipe : recipes) {
                IBoilingCauldronRecipe boilingCauldronRecipe = (IBoilingCauldronRecipe) recipe;
                if (boilingCauldronRecipe.matches(this.inventory.getStackInSlot(INPUT_ITEM_SLOT), this.getFluidInTank(INPUT_FLUID_TANK), getBlockState().get(ModBlockStateProperties.HAS_LID))) {
                    return boilingCauldronRecipe;
                }
            }
        }
        return null;
    }

    @Override
    public boolean isBlockEnabled(World world) {
        return true;
    }

    @Override
    public boolean canProcessRecipe(@Nullable IBoilingCauldronRecipe recipe) {
        ItemStack outputItemStack = this.inventory.getStackInSlot(OUTPUT_ITEM_SLOT);
        FluidStack outputFluidStack = this.getFluidInTank(OUTPUT_FLUID_TANK);

        boolean canSetResults = recipe != null && (outputItemStack.isEmpty() ||
                (outputItemStack.getItem().equals(recipe.getRecipeOutput().getItem()) &&
                        (outputItemStack.getCount() + recipe.getRecipeOutput().getCount()) <= this.inventory.getSlotLimit(OUTPUT_ITEM_SLOT))) &&
                (outputFluidStack.isEmpty() || (outputFluidStack.isFluidEqual(recipe.getOutputFluidStack()) &&
                        (outputFluidStack.getAmount() + recipe.getOutputFluidStack().getAmount()) <= this.getTankCapacity(OUTPUT_FLUID_TANK)));

        if (canSetResults && (recipe.isBoilingRecipe() && this.burnTimeLeft <= 0)) {
            this.updateRecipeBurnTimeIfIsBoiling(recipe);
        }

        if (recipe != null && ((!recipe.isBoilingRecipe() && (this.fuelEnabled || this.burnTimeLeft > 0)) ||
                (recipe.isBoilingRecipe() && (!this.fuelEnabled || this.burnTimeLeft <= 0)))) return false;

        return canSetResults;
    }

    @Override
    public void finishRecipeProcess(@Nonnull IBoilingCauldronRecipe recipe) {
        if ((!recipe.isBoilingRecipe() && (this.fuelEnabled || this.burnTimeLeft > 0)) ||
                (recipe.isBoilingRecipe() && (!this.fuelEnabled || this.burnTimeLeft <= 0))) return;

        // Decrease inputs
        Ingredient inputIngredient = recipe.getInputIngredient();
        if (inputIngredient != null) {
            int countToRemove = recipe.getInputIngredientCount();
            if (inputIngredient.test(this.inventory.getStackInSlot(INPUT_ITEM_SLOT))) {
                this.inventory.getStackInSlot(INPUT_ITEM_SLOT).shrink(countToRemove);
            }
        }

        FluidStack inputFluidStack = recipe.getInputFluidStack();
        if (!inputFluidStack.isEmpty() && inputFluidStack.getAmount() > 0) {
            this.getTank(INPUT_FLUID_TANK).drain(inputFluidStack.copy(), FluidAction.EXECUTE);
        }

        // Set outputs
        ItemStack recipeOutput = recipe.getRecipeOutput();
        if (!recipeOutput.isEmpty()) {
            this.inventory.insertItem(OUTPUT_ITEM_SLOT, recipeOutput.copy(), false);
        }

        FluidStack recipeOutputFluidStack = recipe.getOutputFluidStack();
        if (!recipeOutputFluidStack.isEmpty()) {
            this.getTank(OUTPUT_FLUID_TANK).fill(recipeOutputFluidStack.copy(), FluidAction.EXECUTE);
        }
        // TODO: play sound
    }

    @Override
    public short getProcessTimeTotal() {
        IBoilingCauldronRecipe recipe = this.getRecipe();
        if (recipe != null) {
            return (short) recipe.getProcessTime();
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        boolean inventoryIsEmpty = true;
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            ItemStack stack = this.inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                inventoryIsEmpty = false;
            }
        }
        return inventoryIsEmpty && this.getTank(INPUT_FLUID_TANK).isEmpty() && this.getTank(OUTPUT_FLUID_TANK).isEmpty();
    }

    @Override
    public ITextComponent getDefaultDisplayName() {
        return new TranslationTextComponent("container." + FarmersLife.MOD_ID + ".boiling_cauldron");
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BoilingCauldronContainer(windowId, playerInventory, this);
    }

    @Override
    public void read(CompoundNBT compound) {
        this.fuelEnabled = compound.getBoolean("FuelEnabled");
        this.burnTimeLeft = compound.getInt("BurnTimeLeft");
        this.burnTimeTotal = compound.getInt("BurnTimeTotal");
        this.inventory.deserializeNBT(compound.getCompound("Inventory"));
        super.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putBoolean("FuelEnabled", this.fuelEnabled);
        compound.putInt("BurnTimeLeft", this.burnTimeLeft);
        compound.putInt("BurnTimeTotal", this.burnTimeTotal);
        compound.put("Inventory", this.inventory.serializeNBT());
        return super.write(compound);
    }

    @Override
    protected void invalidateCaps() {
        this.inventoryCapability.invalidate();
        this.fluidTankCapability.invalidate();
        super.invalidateCaps();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (FacingHelper.HORIZONTAL_DIRECTIONS.contains(side)) {
                return this.inventoryCapability.cast();
            }
        } else if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if (side == Direction.DOWN || side == Direction.UP) {
                return this.fluidTankCapability.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    public LazyOptional<IItemHandlerModifiable> getInventoryCapability() {
        return inventoryCapability;
    }

    public LazyOptional<IFluidHandler> getFluidTankCapability() {
        return fluidTankCapability;
    }

    public BoilingCauldronItemHandler getInventory() {
        return inventory;
    }

    public boolean isFuelEnabled() {
        return fuelEnabled;
    }

    public int getBurnTimeLeft() {
        return burnTimeLeft;
    }

    public int getBurnTimeTotal() {
        return burnTimeTotal;
    }

    public void setBurnTimeLeft(int burnTimeLeft) {
        this.burnTimeLeft = burnTimeLeft;
    }

    public void setFuelEnabled(boolean fuelEnabled) {
        // If the current and the given bool are not equal the state changed!
        if (this.fuelEnabled != fuelEnabled) {
            this.invalidateProcessTimes();
        }
        this.fuelEnabled = fuelEnabled;
    }

    public void setBurnTimeTotal(int burnTimeTotal) {
        this.burnTimeTotal = burnTimeTotal;
    }

    private boolean updateBurnTime() {
        if (this.burnTimeLeft > 0) {
            this.burnTimeLeft--;
            return true;
        }
        return false;
    }

    private void updateRecipeBurnTimeIfIsBoiling(IBoilingCauldronRecipe recipe) {
        if (this.burnTimeLeft == 0 && this.fuelEnabled && recipe.isBoilingRecipe()) {
            int stackBurnTime = ForgeHooks.getBurnTime(this.inventory.getStackInSlot(FUEL_SLOT));
            if (stackBurnTime > 0) {
                this.inventory.extractItem(FUEL_SLOT, 1, false);
            }
            this.burnTimeLeft = stackBurnTime;
            this.burnTimeTotal = Math.max(stackBurnTime, 0);
        }
    }

    private boolean updateBlockState() {
        BlockState state = this.getBlockState();
        BlockPos pos = this.getPos();
        boolean isLit = state.get(BlockStateProperties.LIT);
        if (this.burnTimeLeft > 0 && !isLit) {
            BlockState newState = state.with(BlockStateProperties.LIT, true);
            world.setBlockState(pos, newState, Constants.BlockFlags.BLOCK_UPDATE);
            return true;
        } else if (this.burnTimeLeft <= 0 && isLit) {
            BlockState newState = state.with(BlockStateProperties.LIT, false);
            world.setBlockState(pos, newState, Constants.BlockFlags.BLOCK_UPDATE);
            return true;
        }
        return false;
    }
}
