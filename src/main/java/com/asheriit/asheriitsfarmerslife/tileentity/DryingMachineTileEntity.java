package com.asheriit.asheriitsfarmerslife.tileentity;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.container.DryingMachineContainer;
import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.recipes.DryingRecipe;
import com.asheriit.asheriitsfarmerslife.recipes.FiltrationRecipe;
import com.asheriit.asheriitsfarmerslife.utils.items.FluidInOutItemHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
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
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.Set;

public class DryingMachineTileEntity extends AbstractTickingFluidMachineTileEntity<DryingRecipe> {
    private static final ITextComponent CUSTOM_TILE_NAME = new TranslationTextComponent("tileentity." + FarmersLife.MOD_ID + ".drying_machine_tile_entity");

    public static final int INPUT_TANK = 0;
    public static final int INPUT_TANK_ITEM_SLOT_IN = 0;
    public static final int INPUT_TANK_ITEM_SLOT_OUT = 1;
    public static final int OUTPUT_ITEM_SLOT = 2;
    private static final int TICKS_TO_FILL_OR_EMPTY = 5;

    private final LazyOptional<IItemHandlerModifiable> inventoryCapability;
    private final LazyOptional<IFluidHandler> fluidTankCapability;

    protected FluidInOutItemHandler inventory;
    private int currentTicks = 0;

    public DryingMachineTileEntity(final TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn, CUSTOM_TILE_NAME, 1, 4000);
        this.inventory = new FluidInOutItemHandler(3);
        this.inventoryCapability = LazyOptional.of(this::getInventory);
        this.fluidTankCapability = LazyOptional.of(() -> this);
    }

    public DryingMachineTileEntity() {
        this(ModTileEntityTypes.DRYING_MACHINE_TILE_ENTITY.get());
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

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        FluidTank fluidTank = this.getTank(tank);
        return fluidTank != null && fluidTank.isFluidValid(stack) && stack.getFluid() == Fluids.WATER;
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
        return new TranslationTextComponent("container." + FarmersLife.MOD_ID + ".drying_machine");
    }

    @Override
    public DryingRecipe getRecipe() {
        if (world != null) {
            Set<IRecipe<?>> recipes = findRecipesByType(ModRecipeSerializer.DRYING_RECIPE_TYPE, this.world);
            for (IRecipe<?> recipe : recipes) {
                DryingRecipe dryingRecipe = (DryingRecipe) recipe;
                if (dryingRecipe.fluidStackMatches(getTank(INPUT_TANK).getFluid())) {
                    return dryingRecipe;
                }
            }
        }
        return null;
    }

    @Override
    public boolean isBlockEnabled(World world) {
        return !world.isBlockPowered(this.getPos());
    }

    @Override
    public boolean canProcessRecipe(DryingRecipe recipe) {
        if (recipe == null) {
            return false;
        }
//        String text = "";
//        if (!this.getTank(INPUT_TANK).drain(recipe.getInputFluid().copy(), FluidAction.SIMULATE).isEmpty()) {
//            text += "Tank is not empty after the recipe drains the input. ";
//        }
//        if (this.inventory.getStackInSlot(OUTPUT_ITEM_SLOT).isEmpty()) {
//            text += "\n The result slot is currently empty. ";
//        } else if (this.inventory.getStackInSlot(OUTPUT_ITEM_SLOT).getItem() == recipe.getRecipeOutput().getItem()) {
//            text += "\n The result stack is equal to the recipe output";
//        }
//        if ((this.inventory.getStackInSlot(OUTPUT_ITEM_SLOT).getCount() + recipe.getRecipeOutput().getCount()) <= this.inventory.getSlotLimit(OUTPUT_ITEM_SLOT)) {
//            text += "\n The slot limit of the stack is reached! Empty container to continue!";
//        }
//
//        if (!(!this.getTank(INPUT_TANK).drain(recipe.getInputFluid().copy(), FluidAction.SIMULATE).isEmpty() &&
//                (this.inventory.getStackInSlot(OUTPUT_ITEM_SLOT).getCount() + recipe.getRecipeOutput().getCount()) <= this.inventory.getSlotLimit(OUTPUT_ITEM_SLOT) &&
//                (this.inventory.getStackInSlot(OUTPUT_ITEM_SLOT).isEmpty() || this.inventory.getStackInSlot(OUTPUT_ITEM_SLOT).getItem() == recipe.getRecipeOutput().getItem()))) {
//            FarmersLife.LOGGER.info(text);
//        }

        return !this.getTank(INPUT_TANK).drain(recipe.getInputFluid().copy(), FluidAction.SIMULATE).isEmpty() &&
                (this.inventory.getStackInSlot(OUTPUT_ITEM_SLOT).getCount() + recipe.getRecipeOutput().getCount()) <= this.inventory.getSlotLimit(OUTPUT_ITEM_SLOT) &&
                (this.inventory.getStackInSlot(OUTPUT_ITEM_SLOT).isEmpty() || this.inventory.getStackInSlot(OUTPUT_ITEM_SLOT).getItem() == recipe.getRecipeOutput().getItem());
    }

    @Override
    public void finishRecipeProcess(@Nonnull DryingRecipe recipe) {
        // Drain the FluidTank with the specified amount
        this.getTank(INPUT_TANK).drain(recipe.getInputFluid().copy(), FluidAction.EXECUTE);
        ItemStack result = recipe.getRecipeOutput();
        // Set a copy of the result into the inventory slot (IMPORTANT: it has to be a copy)
        this.inventory.insertItem(OUTPUT_ITEM_SLOT, result.copy(), false);
        // TODO: play sound
    }

    @Override
    public short getProcessTimeTotal() {
        DryingRecipe recipe = this.getRecipe();
        if (recipe != null) {
            return (short) recipe.getDryingTimeTotal();
        }
        return 0;
    }

    @Override
    public void tick() {
        if (this.hasWorld() && world != null && !world.isRemote) {
            if (this.currentTicks == TICKS_TO_FILL_OR_EMPTY) {
                this.currentTicks = 0;
                this.updateBlockFullnessState();

                ItemStack stackInSlot = inventory.getStackInSlot(INPUT_TANK_ITEM_SLOT_IN);
                LazyOptional<FluidStack> optionalFluidStack = FluidUtil.getFluidContained(stackInSlot);
                if (optionalFluidStack.isPresent()) {
                    optionalFluidStack.ifPresent(fluidStack -> {
                        if (isFluidValid(INPUT_TANK, fluidStack)) {
                            FluidActionResult fillInputTank = FluidUtil.tryEmptyContainer(inventory.getStackInSlot(INPUT_TANK_ITEM_SLOT_IN), getTank(INPUT_TANK), Integer.MAX_VALUE, null, true);
                            if (fillInputTank.isSuccess()) {
                                this.inventory.setStackInSlot(INPUT_TANK_ITEM_SLOT_IN, fillInputTank.getResult().copy());
                                this.markDirty();
                                world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
                            }
                        }
                    });
                }

                FluidActionResult emptyInputTank = FluidUtil.tryFillContainer(inventory.getStackInSlot(INPUT_TANK_ITEM_SLOT_OUT), getTank(INPUT_TANK), Integer.MAX_VALUE, null, false);
                if (emptyInputTank.isSuccess()) {
                    FluidActionResult emptiedTank = FluidUtil.tryFillContainer(inventory.getStackInSlot(INPUT_TANK_ITEM_SLOT_OUT), getTank(INPUT_TANK), Integer.MAX_VALUE, null, true);
                    this.inventory.setStackInSlot(INPUT_TANK_ITEM_SLOT_OUT, emptiedTank.getResult().copy());
                    this.markDirty();
                    world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
                }
            } else {
                this.currentTicks++;
            }
        }

        super.tick();
    }

    private void updateBlockFullnessState() {
        FluidTank tank = this.getTank(INPUT_TANK);
        int amount = tank.getFluidAmount();
        int maxCapacity = tank.getCapacity();
        int capacitySplit = (maxCapacity / 4);
        BlockState newState = getBlockState();
        if (!newState.getProperties().contains(ModBlockStateProperties.FULLNESS_0_4)) {
            return;
        }

        if (amount == 0) {
            newState = newState.with(ModBlockStateProperties.FULLNESS_0_4, 0);
        } else if (amount <= capacitySplit) {
            newState = newState.with(ModBlockStateProperties.FULLNESS_0_4, 1);
        } else if (amount <= capacitySplit * 2) {
            newState = newState.with(ModBlockStateProperties.FULLNESS_0_4, 2);
        } else if (amount <= capacitySplit * 3) {
            newState = newState.with(ModBlockStateProperties.FULLNESS_0_4, 3);
        } else if (amount <= capacitySplit * 4) {
            newState = newState.with(ModBlockStateProperties.FULLNESS_0_4, 4);
        }

        if (getBlockState() == newState) {
            return;
        }

        world.setBlockState(getPos(), newState, Constants.BlockFlags.BLOCK_UPDATE);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        Direction facing = this.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING);
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && (side == Direction.DOWN ||
                ((facing == Direction.EAST || facing == Direction.WEST) && (side == Direction.NORTH || side == Direction.SOUTH)) ||
                ((facing == Direction.NORTH || facing == Direction.SOUTH) && (side == Direction.EAST || side == Direction.WEST)))) {
            return inventoryCapability.cast();
        }
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && side == Direction.UP) {
            return fluidTankCapability.cast();
        }
        return super.getCapability(cap, side);
    }

    /**
     * Creates the container on the server
     *
     * @param windowID:        The container id
     * @param playerInventory: Player inventory
     * @param playerEntity:    Player entity
     * @return Returns the newly created container
     */
    @Override
    public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new DryingMachineContainer(windowID, playerInventory, this);
    }

    @Override
    public boolean isEmpty() {
        return this.getFluidInTank(INPUT_TANK).isEmpty() /**&& this.inventory.isEmpty()**/;
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        final int MAX_DISTANCE_IN_BLOCKS = 32;
        return MAX_DISTANCE_IN_BLOCKS * MAX_DISTANCE_IN_BLOCKS;
    }

    @Override
    protected void invalidateCaps() {
        this.inventoryCapability.invalidate();
        this.fluidTankCapability.invalidate();
        super.invalidateCaps();
    }

    public final IItemHandlerModifiable getInventory() {
        return this.inventory;
    }

    public LazyOptional<IItemHandlerModifiable> getInventoryCapability() {
        return this.inventoryCapability;
    }

    public LazyOptional<IFluidHandler> getFluidTankCapability() {
        return this.fluidTankCapability;
    }
}
