package com.asheriit.asheriitsfarmerslife.tileentity;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.container.FiltrationMachineContainer;
import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.recipes.FiltrationRecipe;
import com.asheriit.asheriitsfarmerslife.utils.items.ClarificationItemHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class FiltrationMachineTileEntity extends AbstractClarificationMachineTileEntity<FiltrationRecipe> {
    private static final ITextComponent CUSTOM_TILE_NAME = new TranslationTextComponent("tileentity." + FarmersLife.MOD_ID + ".filtration_machine_tile_entity");

    protected ClarificationItemHandler inventory;

    private final LazyOptional<IItemHandlerModifiable> outputInventoryCapability;
    private final LazyOptional<IItemHandlerModifiable> inputInventoryCapability;
    private final LazyOptional<IItemHandlerModifiable> inOutInventoryCapability;
    private final LazyOptional<IItemHandlerModifiable> inventoryCapability;

    private boolean isActive = false;

    public FiltrationMachineTileEntity(TileEntityType tileEntityTypeIn) {
        super(tileEntityTypeIn, CUSTOM_TILE_NAME);
        this.inventory = new ClarificationItemHandler(6, FiltrationRecipe.FiltrationRecipeSerializer.itemInputList) {
            @Override
            protected void onContentsChanged(int slot) {
                if (slot == AbstractClarificationMachineTileEntity.INPUT_TANK_INPUT_ITEM_SLOT) {
                    ItemStack itemStack = FiltrationMachineTileEntity.this.inventory.getStackInSlot(AbstractClarificationMachineTileEntity.INPUT_ITEM_SLOT);
                    if (!itemStack.isEmpty()) {
                        FiltrationMachineTileEntity.this.emptyContainerItem(AbstractClarificationMachineTileEntity.INPUT_TANK_INPUT_ITEM_SLOT, AbstractClarificationMachineTileEntity.INPUT_TANK, this);
                    }
                } else if (slot == AbstractClarificationMachineTileEntity.OUTPUT_TANK_INPUT_ITEM_SLOT) {
                    FiltrationMachineTileEntity.this.emptyContainerItem(AbstractClarificationMachineTileEntity.OUTPUT_TANK_INPUT_ITEM_SLOT, AbstractClarificationMachineTileEntity.OUTPUT_TANK, this);
                } else if (slot == AbstractClarificationMachineTileEntity.INPUT_TANK_OUTPUT_ITEM_SLOT) {
                    FiltrationMachineTileEntity.this.fillContainerItem(AbstractClarificationMachineTileEntity.INPUT_TANK_OUTPUT_ITEM_SLOT, AbstractClarificationMachineTileEntity.INPUT_TANK, this);
                } else if (slot == AbstractClarificationMachineTileEntity.OUTPUT_TANK_OUTPUT_ITEM_SLOT) {
                    FiltrationMachineTileEntity.this.fillContainerItem(AbstractClarificationMachineTileEntity.OUTPUT_TANK_OUTPUT_ITEM_SLOT, AbstractClarificationMachineTileEntity.OUTPUT_TANK, this);
                }
            }
        };
        this.inventoryCapability = LazyOptional.of(this::getInventory);
        this.inputInventoryCapability = LazyOptional.of(() -> new RangedWrapper(this.getInventory(), INPUT_ITEM_SLOT, INPUT_ITEM_SLOT + 1));
        this.outputInventoryCapability = LazyOptional.of(() -> new RangedWrapper(this.getInventory(), OUTPUT_ITEM_SLOT, OUTPUT_ITEM_SLOT + 1));
        this.inOutInventoryCapability = LazyOptional.of(() -> new RangedWrapper(this.getInventory(), INPUT_ITEM_SLOT, OUTPUT_ITEM_SLOT + 1));
    }

    public FiltrationMachineTileEntity() {
        this(ModTileEntityTypes.FILTRATION_MACHINE_TILE_ENTITY.get());
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new FiltrationMachineContainer(windowId, playerInventory, this);
    }

    @Override
    public ITextComponent getDefaultDisplayName() {
        return new TranslationTextComponent("container." + FarmersLife.MOD_ID + ".filtration_machine");
    }

    @Nullable
    @Override
    public FiltrationRecipe getRecipe() {
        if (world != null) {
            Set<IRecipe<?>> recipes = findRecipesByType(ModRecipeSerializer.FILTRATION_RECIPE_TYPE, this.world);
            for (IRecipe<?> recipe : recipes) {
                FiltrationRecipe filtrationRecipe = (FiltrationRecipe) recipe;
                ItemStack itemStack = this.inventory.getStackInSlot(AbstractClarificationMachineTileEntity.INPUT_ITEM_SLOT);
                if (filtrationRecipe.matches(itemStack, getTank(INPUT_TANK).getFluid())) {
                    return filtrationRecipe;
                }
            }
        }
        return null;
    }

    @Override
    public boolean canProcessRecipe(@Nullable FiltrationRecipe recipe) {
        if (recipe == null) {
            this.isActive = false;
            return false;
        }

        FluidTank tank = this.getTank(AbstractClarificationMachineTileEntity.OUTPUT_TANK);
        FluidStack outputFluidStack = recipe.getOutputFluid();
        boolean hasSecondaryOutput = !recipe.getSecondaryOutputStack().isEmpty();

        if (hasSecondaryOutput) {
            ItemStack outputInventorySlot = this.getInventory().getStackInSlot(AbstractClarificationMachineTileEntity.OUTPUT_ITEM_SLOT);
            if (!(outputInventorySlot.isEmpty() || (outputInventorySlot.isItemEqual(recipe.getSecondaryOutputStack()) &&
                    ((outputInventorySlot.getCount() + recipe.getSecondaryOutputStack().getCount()) <= this.getInventory().getSlotLimit(AbstractClarificationMachineTileEntity.OUTPUT_ITEM_SLOT))))) {
                this.isActive = false;
                return false;
            }
        }

        if (tank.fill(outputFluidStack, FluidAction.SIMULATE) >= outputFluidStack.getAmount()) {
            this.isActive = true;
            return true;
        }
        this.isActive = false;
        return false;
    }

    @Override
    public void finishRecipeProcess(@Nonnull FiltrationRecipe recipe) {
        // Decrease the ingredient stack from inventory by the amount defined in the recipe
        this.getInventory().getStackInSlot(AbstractClarificationMachineTileEntity.INPUT_ITEM_SLOT).shrink(recipe.getInputIngredientCount());

        // Drain the FluidTank with the specified amount
        this.getTank(INPUT_TANK).drain(recipe.getInputFluid().copy(), FluidAction.EXECUTE);
        this.getTank(OUTPUT_TANK).fill(recipe.getOutputFluid().copy(), FluidAction.EXECUTE);

        ItemStack secondaryOutput = recipe.getSecondaryOutputStack();
        if (!secondaryOutput.isEmpty()) {
            int outputChance = recipe.getSecondaryOutputProbability();
            if (outputChance < 0) return;
            int randomValue = this.world.getRandom().nextInt(100); // Value between 0 and 99
            if (randomValue < outputChance) {
                // Set a copy of the result into the inventory slot (IMPORTANT: it has to be a copy)
                this.inventory.insertItem(AbstractClarificationMachineTileEntity.OUTPUT_ITEM_SLOT, secondaryOutput.copy(), false);
            }
        }
        // TODO: play sound
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        this.inOutInventoryCapability.invalidate();
        this.inputInventoryCapability.invalidate();
        this.inventoryCapability.invalidate();
        this.outputInventoryCapability.invalidate();
    }

    // ------------ READ AND WRITE ------------
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putBoolean("isActive", this.isActive);
        compound.put("Inventory", this.inventory.serializeNBT());
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.isActive = compound.getBoolean("isActive");
        this.inventory.deserializeNBT(compound.getCompound("Inventory"));
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        final int MAX_DISTANCE_IN_BLOCKS = 16;
        return MAX_DISTANCE_IN_BLOCKS * MAX_DISTANCE_IN_BLOCKS;
    }

    public void emptyContainerItem(int slot, int tankSlot, IItemHandlerModifiable inventory) {
        // Handle blue slot (insert fluid)
        ItemStack stackInSlot = inventory.getStackInSlot(slot);
        LazyOptional<FluidStack> optionalFluidStack = FluidUtil.getFluidContained(stackInSlot);
        if (optionalFluidStack.isPresent()) {
            optionalFluidStack.ifPresent(fluidStack -> {
                if (isFluidValid(tankSlot, fluidStack) && FiltrationRecipe.FiltrationRecipeSerializer.fluidInputList.contains(fluidStack.getFluid())) {
                    FluidActionResult fillInputTank = FluidUtil.tryEmptyContainer(inventory.getStackInSlot(slot), getTank(tankSlot), Integer.MAX_VALUE, null, true);
                    if (fillInputTank.isSuccess()) {
                        inventory.setStackInSlot(slot, fillInputTank.getResult().copy());
                        this.markDirty();
                        this.world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
                    }
                }
            });
        }
    }

    // ------------ GETTER AND SETTER ------------
    public LazyOptional<IItemHandlerModifiable> getInventoryCapability() {
        return this.inventoryCapability;
    }

    @Nonnull
    public IItemHandlerModifiable getInventory() {
        return this.inventory;
    }

    @Override
    public short getProcessTimeTotal() {
        FiltrationRecipe recipe = this.getRecipe();
        if (recipe != null) {
            return (short) recipe.getProcessTime();
        }
        return 0;
    }

    public boolean isActive() {
        return isActive;
    }

    // ------------ CAPABILITIES ------------
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        BlockState blockState = this.getBlockState();
        Direction facingDirection = blockState.getProperties().contains(BlockStateProperties.HORIZONTAL_FACING) ? blockState.get(BlockStateProperties.HORIZONTAL_FACING) : null;
        if (facingDirection == null) {
            return super.getCapability(cap, side);
        }
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == facingDirection) {
                return this.inputInventoryCapability.cast();
            } else if (side == facingDirection.getOpposite()) {
                return this.outputInventoryCapability.cast();
            } else if (side != Direction.UP && side != Direction.DOWN) {
                return this.inOutInventoryCapability.cast();
            }
        }
        return super.getCapability(cap, side);
    }
}
