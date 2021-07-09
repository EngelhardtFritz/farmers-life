package com.asheriit.asheriitsfarmerslife.tileentity;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.container.FermentingBarrelContainer;
import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.objects.blocks.machines.FermentationBarrelBlock;
import com.asheriit.asheriitsfarmerslife.recipes.FermentingRecipe;
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
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class FermentingBarrelTileEntity extends AbstractTickingFluidMachineTileEntity<FermentingRecipe> {
    private static final ITextComponent CUSTOM_TILE_NAME = new TranslationTextComponent("tileentity." + FarmersLife.MOD_ID + ".fermenting_barrel_tile_entity");

    public static final int INPUT_OUTPUT_FLUID_TANK = 0;
    public static final int INPUT_ITEM_SLOT_1 = 0;

    private final LazyOptional<IFluidHandler> fluidTankCapability;
    private final LazyOptional<IItemHandlerModifiable> inventoryCapability;

    protected ItemStackHandler inventory;

    public FermentingBarrelTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn, CUSTOM_TILE_NAME, 1, 2000);
        this.inventory = new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == INPUT_ITEM_SLOT_1) {
                    return FermentingRecipe.FermentingRecipeSerializer.itemInputList.contains(stack.getItem());
                }
                return super.isItemValid(slot, stack);
            }
        };
        this.fluidTankCapability = LazyOptional.of(() -> this);
        this.inventoryCapability = LazyOptional.of(() -> this.inventory);
    }

    public FermentingBarrelTileEntity() {
        this(ModTileEntityTypes.FERMENTING_BARREL_TILE_ENTITY.get());
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
        return new TranslationTextComponent("container." + FarmersLife.MOD_ID + ".fermenting_barrel");
    }

    @Nullable
    @Override
    public FermentingRecipe getRecipe() {
        if (world != null) {
            Set<IRecipe<?>> recipes = findRecipesByType(ModRecipeSerializer.FERMENTATION_RECIPE_TYPE, this.world);
            for (IRecipe<?> recipe : recipes) {
                FermentingRecipe fermentingRecipe = (FermentingRecipe) recipe;
                FluidStack fluidFromTank = this.getFluidInTank(INPUT_OUTPUT_FLUID_TANK);
                ItemStack inputSlotStack = this.inventory.getStackInSlot(INPUT_ITEM_SLOT_1);
                if (fermentingRecipe.fluidStackMatches(fluidFromTank, inputSlotStack)) {
                    return fermentingRecipe;
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
    public boolean canProcessRecipe(@Nullable FermentingRecipe recipe) {
        FluidStack stack = this.getFluidInTank(INPUT_OUTPUT_FLUID_TANK);
        FluidStack ingredientFluid = recipe.getIngredientFluid();
        if (ingredientFluid != null) {
            return stack.isFluidEqual(ingredientFluid) && stack.getAmount() >= ingredientFluid.getAmount();
        }
        return false;
    }

    @Override
    public void finishRecipeProcess(@Nonnull FermentingRecipe recipe) {
        FluidStack fluidFromTank = this.getFluidInTank(INPUT_OUTPUT_FLUID_TANK);
        FluidStack outputFluidCopy = recipe.getOutputFluid().copy();
        FluidStack outputFluid = new FluidStack(outputFluidCopy.getFluid(), fluidFromTank.getAmount());
        // Remove input ingredient
        this.inventory.getStackInSlot(INPUT_ITEM_SLOT_1).shrink(recipe.getInputIngredientCount());
        // Remove fluid and set output fluid
        this.getTank(INPUT_OUTPUT_FLUID_TANK).drain(recipe.getIngredientFluid().copy(), FluidAction.EXECUTE);
        this.getTank(INPUT_OUTPUT_FLUID_TANK).fill(outputFluid, FluidAction.EXECUTE);
    }

    @Override
    public short getProcessTimeTotal() {
        FermentingRecipe recipe = this.getRecipe();
        if (recipe != null) {
            return (short) recipe.getProcessTime();
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        if (this.getFluidInTank(FermentingBarrelTileEntity.INPUT_OUTPUT_FLUID_TANK).getAmount() == 0) {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new FermentingBarrelContainer(windowId, playerInventory, this);
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

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        Direction facingValue = this.getBlockState().get(FermentationBarrelBlock.FACING);
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side != facingValue && side != facingValue.getOpposite()) {
            return this.inventoryCapability.cast();
        } else if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && side != facingValue) {
            return this.fluidTankCapability.cast();
        }
        return super.getCapability(cap, side);
    }

    // ---------------------- HELPER METHODS ----------------------
    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        this.fluidTankCapability.invalidate();
        this.inventoryCapability.invalidate();
    }

    // ---------------------- GETTER ----------------------
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
