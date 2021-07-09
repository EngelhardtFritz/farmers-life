package com.asheriit.asheriitsfarmerslife.tileentity;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.container.BottlingMachineContainer;
import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.recipes.BottlingRecipe;
import com.asheriit.asheriitsfarmerslife.recipes.FiltrationRecipe;
import com.asheriit.asheriitsfarmerslife.utils.items.BottlingItemHandler;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BottlingMachineTileEntity extends AbstractTickingFluidMachineTileEntity<BottlingRecipe> {
    private static final ITextComponent CUSTOM_TILE_NAME = new TranslationTextComponent("tileentity." + FarmersLife.MOD_ID + ".bottling_machine_tile_entity");

    public static final int INPUT_ITEM_SLOT_1 = 0;
    public static final int INPUT_ITEM_SLOT_2 = 1;
    public static final int INPUT_ITEM_SLOT_3 = 2;
    public static final int INPUT_ITEM_SLOT_4 = 3;
    public static final int OUTPUT_ITEM_SLOT = 4;
    public static final int INPUT_TANK_INPUT_ITEM = 5;
    public static final int INPUT_TANK_OUTPUT_ITEM = 6;
    public static final int INPUT_TANK = 0;

    protected BottlingItemHandler inventory;

    private final LazyOptional<IItemHandlerModifiable> inventoryCapability;
    private final LazyOptional<IItemHandlerModifiable> inputInventoryCapability;
    private final LazyOptional<IItemHandlerModifiable> outputInventoryCapability;

    public BottlingMachineTileEntity(TileEntityType tileEntityTypeIn) {
        super(tileEntityTypeIn, CUSTOM_TILE_NAME, 1, 4000);
        this.inventory = new BottlingItemHandler(7) {
            @Override
            protected void onContentsChanged(int slot) {
                if (slot == BottlingMachineTileEntity.INPUT_TANK_INPUT_ITEM) {
                    BottlingMachineTileEntity.this.emptyContainerItem(BottlingMachineTileEntity.INPUT_TANK_INPUT_ITEM, BottlingMachineTileEntity.INPUT_TANK, this);
                } else if (slot == BottlingMachineTileEntity.INPUT_TANK_OUTPUT_ITEM) {
                    BottlingMachineTileEntity.this.fillContainerItem(BottlingMachineTileEntity.INPUT_TANK_OUTPUT_ITEM, BottlingMachineTileEntity.INPUT_TANK, this);
                } else if (slot == BottlingMachineTileEntity.INPUT_ITEM_SLOT_4) {
                    // Slot which should contain wine glass bottle
                    BlockState state = BottlingMachineTileEntity.this.getBlockState();
                    BlockPos pos = BottlingMachineTileEntity.this.getPos();
                    if ((!this.getStackInSlot(BottlingMachineTileEntity.INPUT_ITEM_SLOT_4).isEmpty() ||
                            !this.getStackInSlot(BottlingMachineTileEntity.OUTPUT_ITEM_SLOT).isEmpty()) &&
                            !state.get(BlockStateProperties.HAS_BOTTLE_0)) {
                        BottlingMachineTileEntity.this.world.setBlockState(pos, state.with(BlockStateProperties.HAS_BOTTLE_0, true), 2);
                    } else if (this.getStackInSlot(BottlingMachineTileEntity.INPUT_ITEM_SLOT_4).isEmpty() &&
                            this.getStackInSlot(BottlingMachineTileEntity.OUTPUT_ITEM_SLOT).isEmpty() &&
                            state.get(BlockStateProperties.HAS_BOTTLE_0)){
                        BottlingMachineTileEntity.this.world.setBlockState(pos, state.with(BlockStateProperties.HAS_BOTTLE_0, false), 2);
                    }
                }
                super.onContentsChanged(slot);
            }
        };
        this.inventoryCapability = LazyOptional.of(() -> this.inventory);
        this.inputInventoryCapability = LazyOptional.of(() -> new RangedWrapper(this.inventory, INPUT_ITEM_SLOT_1, INPUT_ITEM_SLOT_4 + 1));
        this.outputInventoryCapability = LazyOptional.of(() -> new RangedWrapper(this.inventory, OUTPUT_ITEM_SLOT, OUTPUT_ITEM_SLOT + 1));
    }

    public BottlingMachineTileEntity() {
        this(ModTileEntityTypes.BOTTLING_MACHINE_TILE_ENTITY.get());
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
        return new TranslationTextComponent("container." + FarmersLife.MOD_ID + ".bottling_machine");
    }

    @Nullable
    @Override
    public BottlingRecipe getRecipe() {
        if (world != null) {
            Set<IRecipe<?>> recipes = findRecipesByType(ModRecipeSerializer.BOTTLING_RECIPE_TYPE, this.world);
            List<Pair<BottlingRecipe, Integer>> recipeIntegerPair = new ArrayList<>();
            for (IRecipe<?> recipe : recipes) {
                BottlingRecipe bottlingRecipe = (BottlingRecipe) recipe;
                if (bottlingRecipe.matches(this.getFluidInTank(INPUT_TANK), new RecipeWrapper(this.inventory), world)) {
                    recipeIntegerPair.add(Pair.of(bottlingRecipe, bottlingRecipe.getDistinctIngredients().size()));
                }
            }
            for (Pair<BottlingRecipe, Integer> pair: recipeIntegerPair) {
                FarmersLife.LOGGER.info("Matched Recipes: " + pair.getKey().getId().getPath() + ", Matching ingredients: " + pair.getValue());
            }

            BottlingRecipe recipeToReturn = null;
            int curMaxIngredients = 0;
            for (Pair<BottlingRecipe, Integer> pair : recipeIntegerPair) {
                if (curMaxIngredients < pair.getValue()) {
                    recipeToReturn = pair.getKey();
                    curMaxIngredients = pair.getValue();
                }
            }
            return recipeToReturn;
        }
        return null;
    }

    @Override
    public boolean isBlockEnabled(World world) {
        return true;
    }

    @Override
    public boolean canProcessRecipe(@Nullable BottlingRecipe recipe) {
        if (recipe == null) {
            return false;
        }

        ItemStack recipeOutput = recipe.getRecipeOutput();
        ItemStack outputSlot = this.inventory.getStackInSlot(OUTPUT_ITEM_SLOT);
        return outputSlot.isEmpty() || (outputSlot.getItem().equals(recipe.getRecipeOutput().getItem()) &&
                ((recipeOutput.getCount() + outputSlot.getCount()) < this.inventory.getSlotLimit(OUTPUT_ITEM_SLOT)));
    }

    @Override
    public void finishRecipeProcess(@Nonnull BottlingRecipe recipe) {
        List<Pair<Ingredient, Integer>> ingredients = recipe.getDistinctIngredients();
        for (Pair<Ingredient, Integer> ingredientIntegerPair: ingredients) {
            int countToReduce = ingredientIntegerPair.getValue();
            for (int invIndex = 0; invIndex <= INPUT_ITEM_SLOT_4; invIndex++) {
                ItemStack stackFromSlot = this.inventory.getStackInSlot(invIndex);
                if (countToReduce <= 0) {
                    break;
                }

                if (!stackFromSlot.isEmpty() && ingredientIntegerPair.getKey().test(stackFromSlot)) {
                    int countFromStack = stackFromSlot.getCount();
                    if ((countFromStack - countToReduce) < 0) {
                        this.inventory.extractItem(invIndex, countFromStack, false);
                        countToReduce = countToReduce - countFromStack;
                    } else {
                        this.inventory.extractItem(invIndex, countToReduce, false);
                        countToReduce = 0;
                    }
                }
            }
        }

        FluidStack fluidStack = recipe.getInputFluidStack();
        if (!fluidStack.isEmpty() || fluidStack.getAmount() > 0) {
            this.getTank(INPUT_TANK).drain(recipe.getInputFluidStack().copy(), FluidAction.EXECUTE);
        }

        this.inventory.insertItem(OUTPUT_ITEM_SLOT, recipe.getRecipeOutput().copy(), false);
    }

    @Override
    public short getProcessTimeTotal() {
        BottlingRecipe recipe = this.getRecipe();
        if (recipe != null) {
            return (short) recipe.getProcessTime();
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return this.getFluidInTank(BottlingMachineTileEntity.INPUT_TANK).getAmount() == 0;
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BottlingMachineContainer(windowId, playerInventory, this);
    }

    @Override
    protected void invalidateCaps() {
        this.inventoryCapability.invalidate();
        this.inputInventoryCapability.invalidate();
        this.outputInventoryCapability.invalidate();
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

    public void emptyContainerItem(int slot, int tankSlot, IItemHandlerModifiable inventory) {
        // Handle blue slot (insert fluid)
        ItemStack stackInSlot = inventory.getStackInSlot(slot);
        LazyOptional<FluidStack> optionalFluidStack = FluidUtil.getFluidContained(stackInSlot);
        if (optionalFluidStack.isPresent()) {
            optionalFluidStack.ifPresent(fluidStack -> {
                if (isFluidValid(tankSlot, fluidStack) && BottlingRecipe.BottlerRecipeSerializer.fluidInputList.contains(fluidStack.getFluid())) {
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

    public void fillContainerItem(int slot, int tankSlot, IItemHandlerModifiable inventory) {
        // Handle red slot (extract fluid)
        FluidActionResult emptyInputTank = FluidUtil.tryFillContainer(inventory.getStackInSlot(slot), getTank(tankSlot), Integer.MAX_VALUE, null, false);
        if (emptyInputTank.isSuccess()) {
            FluidActionResult emptiedTank = FluidUtil.tryFillContainer(inventory.getStackInSlot(slot), getTank(tankSlot), Integer.MAX_VALUE, null, true);
            inventory.setStackInSlot(slot, emptiedTank.getResult().copy());
            this.markDirty();
            this.world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    // ------------ GETTER AND SETTER ------------
    public LazyOptional<IItemHandlerModifiable> getInventoryCapability() {
        return this.inventoryCapability;
    }

    public LazyOptional<IItemHandlerModifiable> getInputInventoryCapability() {
        return this.inputInventoryCapability;
    }

    public LazyOptional<IItemHandlerModifiable> getOutputInventoryCapability() {
        return this.outputInventoryCapability;
    }

    @Nonnull
    public IItemHandlerModifiable getInventory() {
        return this.inventory;
    }

    // CAPABILITIES
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        // TODO:
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {

        } else if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {

        }
        return super.getCapability(cap, side);
    }
}
