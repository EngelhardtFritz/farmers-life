package com.asheriit.asheriitsfarmerslife.tileentity;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.TemperatureUnit;
import com.asheriit.asheriitsfarmerslife.container.TemperatureChamberContainer;
import com.asheriit.asheriitsfarmerslife.init.FarmersLifeEventFactory;
import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.recipes.TemperatureChamberRecipe;
import com.asheriit.asheriitsfarmerslife.utils.items.TemperatureChamberItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class TemperatureChamberTileEntity extends AbstractTickingFluidMachineTileEntity<TemperatureChamberRecipe> {
    private static final ITextComponent CUSTOM_TILE_NAME = new TranslationTextComponent("tileentity." + FarmersLife.MOD_ID + ".temperature_chamber_tile_entity");
    // Slots for the ItemStackHandler and the FluidTanks
    public static final int HEAT_ITEM_SLOT = 0;
    public static final int COLD_ITEM_SLOT = 1;
    public static final int INPUT_ITEM_SLOT = 2;
    public static final int OUTPUT_ITEM_SLOT_1 = 3;
    public static final int OUTPUT_ITEM_SLOT_2 = 4;
    public static final int OUTPUT_ITEM_SLOT_3 = 5;
    public static final int INPUT_FLUID_TANK = 0;
    // Temperature Change Constants, temperature has to be calculated by dividing with 100
    public static final int TEMPERATURE_STEP_WEIGHT = 50;
    public static final int TEMPERATURE_STEP_WEIGHT_SHIFT = 1000;
    public static final int TEMPERATURE_STEP_WEIGHT_CONTROL = 10000;
    public static final int TEMPERATURE_STEP_WEIGHT_ALT = 5;
    // Cooldown times when extracting heat or cold from items and changing the fluid temperature (currentTemperature)
    public static final int BUFFER_INPUT_COOLDOWN = 20;
    public static final int TEMPERATURE_CHANGE_COOLDOWN = 20;
    // Limits for the maximum amount of heat and cold which can be stored (multiplied by 10)
    public static final int MAX_HEAT_BUFFER = 400000;
    public static final int MAX_COLD_BUFFER = 400000;
    // Modifiers for calculating how much heat or cold will be extracted from the buffers each tick (multiplied by 10)
    public static final int DEFAULT_DECREASE_SPEED = 10;
    public static final int DEFAULT_DECREASE_SPEED_MODIFIER = 2;
    // Maximum and minimum temperatures in Kelvin (multiplied by 100)
    public static final int MAX_TARGET_TEMPERATURE = 200000;
    public static final int MIN_TARGET_TEMPERATURE = 0;

    protected TemperatureChamberItemHandler inventory;
    protected TemperatureUnit temperatureUnit;
    protected int heatBuffer = 0;
    protected int coldBuffer = 0;
    protected int bufferInputCooldown = 0;
    protected int temperatureChangeCooldown = 0;
    protected int targetTempInKelvin = 27315;
    protected int currentTempInKelvin;
    protected int ticksTooHot = 0;
    protected int ticksTooCold = 0;
    protected int heatDecreasePerTick = 0;
    protected int coldDecreasePerTick = 0;
    // Capabilities
    private final LazyOptional<IItemHandlerModifiable> inventoryCapability;
    private final LazyOptional<IItemHandlerModifiable> inventoryInputCapability;
    private final LazyOptional<IItemHandlerModifiable> inventoryOutputCapability;
    private final LazyOptional<IFluidHandler> fluidTankCapability;

    public TemperatureChamberTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn, CUSTOM_TILE_NAME, 1, 4000);
        this.inventory = new TemperatureChamberItemHandler(6);
        this.inventoryCapability = LazyOptional.of(() -> this.inventory);
        this.inventoryInputCapability = LazyOptional.of(() -> new RangedWrapper(this.inventory, HEAT_ITEM_SLOT, INPUT_ITEM_SLOT + 1));
        this.inventoryOutputCapability = LazyOptional.of(() -> new RangedWrapper(this.inventory, OUTPUT_ITEM_SLOT_1, OUTPUT_ITEM_SLOT_3 + 1));
        this.fluidTankCapability = LazyOptional.of(() -> this.getTank(INPUT_FLUID_TANK));
        this.temperatureUnit = TemperatureUnit.CELSIUS;
    }

    public TemperatureChamberTileEntity() {
        this(ModTileEntityTypes.TEMPERATURE_CHAMBER_TILE_ENTITY.get());
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
    public void tick() {
        boolean markDirty = false;
        if (world != null && !world.isRemote) {
            if (this.isBlockEnabled(this.world)) {
                // Process buffers and increase buffers (every 40 ticks)
                this.processBufferInputs();
                // Process temperature change (every 10 ticks)
                // Requires fluid in the input tank to change any values
                this.processTemperature();

//                FarmersLife.LOGGER.info("TileEntity -> temperatureInKelvin: " + this.targetTempInKelvin);

                // Get recipe which should be produced
                TemperatureChamberRecipe recipeToProduce = this.getRecipe();
                if (recipeToProduce != null && this.canProcessRecipe(recipeToProduce)) {
                    // Validate if temperature too high
                    if (this.currentTempInKelvin > (recipeToProduce.getMaxTemp() * 100)) {
                        this.ticksTooHot++;
                    }
                    // Validate if temperature too cold
                    if (this.currentTempInKelvin < (recipeToProduce.getMinTemp() * 100)) {
                        this.ticksTooCold++;
                    }

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
    protected void invalidateProcessTimes() {
        super.invalidateProcessTimes();
        this.ticksTooHot = 0;
        this.ticksTooCold = 0;
    }

    @Nullable
    @Override
    public TemperatureChamberRecipe getRecipe() {
        if (world != null) {
            Set<IRecipe<?>> recipes = findRecipesByType(ModRecipeSerializer.TEMPERATURE_CHAMBER_RECIPE_TYPE, this.world);
            for (IRecipe<?> recipe : recipes) {
                TemperatureChamberRecipe temperatureChamberRecipe = (TemperatureChamberRecipe) recipe;
                ItemStack itemStack = this.inventory.getStackInSlot(INPUT_ITEM_SLOT);
                FluidStack fluidStack = this.getFluidInTank(INPUT_FLUID_TANK);
                if (temperatureChamberRecipe.matches(itemStack, fluidStack)) {
                    return temperatureChamberRecipe;
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
    public boolean canProcessRecipe(@Nullable TemperatureChamberRecipe recipe) {
        if (recipe != null) {
            ItemStack outputSlot1 = this.inventory.getStackInSlot(OUTPUT_ITEM_SLOT_1);
            ItemStack outputSlot2 = this.inventory.getStackInSlot(OUTPUT_ITEM_SLOT_2);
            ItemStack outputSlot3 = this.inventory.getStackInSlot(OUTPUT_ITEM_SLOT_3);
            if (outputSlot1.isEmpty() || outputSlot2.isEmpty() || outputSlot3.isEmpty()) {
                return true;
            }

            if (!((outputSlot1.isItemEqual(recipe.getRecipeOutput()) && (outputSlot1.getCount() + recipe.getRecipeOutput().getCount()) <= this.inventory.getSlotLimit(OUTPUT_ITEM_SLOT_1)) ||
                    (outputSlot2.isItemEqual(recipe.getRecipeOutput()) && (outputSlot2.getCount() + recipe.getRecipeOutput().getCount()) <= this.inventory.getSlotLimit(OUTPUT_ITEM_SLOT_2)) ||
                    (outputSlot3.isItemEqual(recipe.getRecipeOutput()) && (outputSlot3.getCount() + recipe.getRecipeOutput().getCount()) <= this.inventory.getSlotLimit(OUTPUT_ITEM_SLOT_3)))) {
                return false;
            }

            if (!((outputSlot1.isItemEqual(recipe.getOutputItemStackHot()) && (outputSlot1.getCount() + recipe.getOutputItemStackHot().getCount()) <= this.inventory.getSlotLimit(OUTPUT_ITEM_SLOT_1)) ||
                    (outputSlot2.isItemEqual(recipe.getOutputItemStackHot()) && (outputSlot2.getCount() + recipe.getOutputItemStackHot().getCount()) <= this.inventory.getSlotLimit(OUTPUT_ITEM_SLOT_2)) ||
                    (outputSlot3.isItemEqual(recipe.getOutputItemStackHot()) && (outputSlot3.getCount() + recipe.getOutputItemStackHot().getCount()) <= this.inventory.getSlotLimit(OUTPUT_ITEM_SLOT_3)))) {
                return false;
            }

            if (!((outputSlot1.isItemEqual(recipe.getOutputItemStackCold()) && (outputSlot1.getCount() + recipe.getOutputItemStackCold().getCount()) <= this.inventory.getSlotLimit(OUTPUT_ITEM_SLOT_1)) ||
                    (outputSlot2.isItemEqual(recipe.getOutputItemStackCold()) && (outputSlot2.getCount() + recipe.getOutputItemStackCold().getCount()) <= this.inventory.getSlotLimit(OUTPUT_ITEM_SLOT_2)) ||
                    (outputSlot3.isItemEqual(recipe.getOutputItemStackCold()) && (outputSlot3.getCount() + recipe.getOutputItemStackCold().getCount()) <= this.inventory.getSlotLimit(OUTPUT_ITEM_SLOT_3)))) {
                return false;
            }

            return true;
        }
        return false;
    }

    @Override
    public void finishRecipeProcess(@Nonnull TemperatureChamberRecipe recipe) {
        // Drain Tank
//        this.getTank(INPUT_FLUID_TANK).drain(recipe.getInputFluidStack().copy(), FluidAction.EXECUTE);

        // Decrease inputs
        Ingredient inputIngredient = recipe.getInputIngredient();
        if (inputIngredient != null) {
            int countToRemove = recipe.getInputIngredientCount();
            if (inputIngredient.test(this.inventory.getStackInSlot(INPUT_ITEM_SLOT))) {
                this.inventory.getStackInSlot(INPUT_ITEM_SLOT).shrink(countToRemove);
            }
        }

        float processTime = recipe.getProcessTime();
        float percentageTooHot = this.ticksTooHot / processTime;
        float percentageTooCold = this.ticksTooCold / processTime;
        float allowedPercentage;

        if (recipe.getRequiredPercentage() > 1.0F) {
            allowedPercentage = 1.0F - (recipe.getRequiredPercentage() / 100.0F);
        } else {
            allowedPercentage = 1.0F - recipe.getRequiredPercentage();
        }

        // Set result
        if (percentageTooHot <= allowedPercentage && percentageTooCold <= allowedPercentage) {
            // Default output
            this.mergeIntoAvailableSlot(recipe.getRecipeOutput().copy());
        }

        if (percentageTooHot > allowedPercentage) {
            // Too hot
            this.mergeIntoAvailableSlot(recipe.getOutputItemStackHot().copy());
        }

        if (percentageTooCold > allowedPercentage) {
            // Too cold
            this.mergeIntoAvailableSlot(recipe.getOutputItemStackCold().copy());
        }
    }

    @Override
    public short getProcessTimeTotal() {
        TemperatureChamberRecipe recipe = this.getRecipe();
        if (recipe != null) {
            return (short) recipe.getProcessTime();
        }
        return 0;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            ItemStack stack = this.inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return this.getFluidInTank(INPUT_FLUID_TANK).isEmpty();
    }

    @Override
    public ITextComponent getDefaultDisplayName() {
        return new TranslationTextComponent("container." + FarmersLife.MOD_ID + ".temperature_chamber");
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new TemperatureChamberContainer(windowId, playerInventory, this);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.inventory.deserializeNBT(compound.getCompound("Inventory"));
        this.temperatureUnit = TemperatureUnit.getTemperatureUnitById(compound.getByte("TemperatureUnit"));
        this.heatBuffer = compound.getInt("HeatBuffer");
        this.coldBuffer = compound.getInt("ColdBuffer");
        this.bufferInputCooldown = compound.getInt("BufferInputCooldown");
        this.temperatureChangeCooldown = compound.getInt("TemperatureChangeCooldown");
        this.targetTempInKelvin = compound.getInt("TargetTempInKelvin");
        this.currentTempInKelvin = compound.getInt("CurrentTempInKelvin");
        this.ticksTooHot = compound.getInt("TicksTooHot");
        this.ticksTooCold = compound.getInt("TicksTooCold");
        this.heatDecreasePerTick = compound.getInt("HeatDecreaseTick");
        this.coldDecreasePerTick = compound.getInt("ColdDecreaseTick");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("Inventory", this.inventory.serializeNBT());
        compound.putByte("TemperatureUnit", (byte) this.getTemperatureUnitId());
        compound.putInt("HeatBuffer", this.heatBuffer);
        compound.putInt("ColdBuffer", this.coldBuffer);
        compound.putInt("BufferInputCooldown", this.bufferInputCooldown);
        compound.putInt("TemperatureChangeCooldown", this.temperatureChangeCooldown);
        compound.putInt("TargetTempInKelvin", this.targetTempInKelvin);
        compound.putInt("CurrentTempInKelvin", this.currentTempInKelvin);
        compound.putInt("TicksTooHot", this.ticksTooHot);
        compound.putInt("TicksTooCold", this.ticksTooCold);
        compound.putInt("HeatDecreaseTick", this.heatDecreasePerTick);
        compound.putInt("ColdDecreaseTick", this.coldDecreasePerTick);
        return super.write(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        Direction.Axis axis = this.getBlockState().getProperties().contains(BlockStateProperties.HORIZONTAL_AXIS) ? this.getBlockState().get(BlockStateProperties.HORIZONTAL_AXIS) : null;
        if (axis != null && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if ((axis == Direction.Axis.X && (side == Direction.WEST || side == Direction.EAST)) ||
                    (axis == Direction.Axis.Z && (side == Direction.NORTH || side == Direction.SOUTH))) {
                return this.inventoryInputCapability.cast();
            } else if ((axis == Direction.Axis.X && (side == Direction.NORTH || side == Direction.SOUTH)) ||
                    (axis == Direction.Axis.Z && (side == Direction.WEST || side == Direction.EAST))) {
                return this.inventoryOutputCapability.cast();
            }
        } else if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if (side == Direction.DOWN || side == Direction.UP) {
                return this.fluidTankCapability.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public ItemStack emptyIOTankIfPossible(ItemStack itemStack, PlayerEntity playerEntity, boolean isInputTank, int tankIndex) {
        ItemStack resultStack = itemStack;

        if (!playerEntity.isCreative()) {
            FluidActionResult emptyInputTank = FluidUtil.tryFillContainer(itemStack, isInputTank ? getTank(tankIndex) : getTank(this.getInputTankCount() + tankIndex), Integer.MAX_VALUE, null, false);

            if (emptyInputTank.isSuccess()) {
                FluidActionResult emptiedTank = FluidUtil.tryFillContainer(itemStack, isInputTank ? getTank(tankIndex) : getTank(this.getInputTankCount() + tankIndex), Integer.MAX_VALUE, null, true);
                FluidStack fluidInTank = this.getFluidInTank(INPUT_FLUID_TANK);
                Fluid fluid = fluidInTank.getFluid();
                this.currentTempInKelvin = fluid != Fluids.EMPTY ? fluid.getAttributes().getTemperature() * 100 : 0;

                this.markDirty();
                world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
                resultStack = emptiedTank.getResult().copy();
            }
        }
        return resultStack;
    }

    @Override
    public ItemStack fillIOTankIfPossible(ItemStack itemStack, PlayerEntity playerEntity, boolean isInputTank, int tankIndex) {
        ItemStack resultStack = itemStack;
        FluidActionResult fillInputTank = FluidUtil.tryEmptyContainer(itemStack, isInputTank ? getTank(tankIndex) : getTank(this.getInputTankCount() + tankIndex), Integer.MAX_VALUE, null, true);

        if (fillInputTank.isSuccess()) {
            FluidStack fluidInTank = this.getFluidInTank(INPUT_FLUID_TANK);
            Fluid fluid = fluidInTank.getFluid();
            this.currentTempInKelvin = fluid != Fluids.EMPTY ? fluid.getAttributes().getTemperature() * 100 : 0;

            this.markDirty();
            world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
            resultStack = fillInputTank.getResult().copy();
        }

        if (playerEntity.isCreative()) {
            resultStack = itemStack;
        }

        return resultStack;
    }

    @Override
    public void emptyTank(int tankIndex) {
        this.currentTempInKelvin = 27315;
        super.emptyTank(tankIndex);
    }

    public LazyOptional<IItemHandlerModifiable> getInventoryCapability() {
        return inventoryCapability;
    }

    public LazyOptional<IFluidHandler> getFluidTankCapability() {
        return fluidTankCapability;
    }

    public int getHeatBuffer() {
        return heatBuffer;
    }

    public int getColdBuffer() {
        return coldBuffer;
    }

    public int getBufferInputCooldown() {
        return bufferInputCooldown;
    }

    public int getCurrentTempInKelvin() {
        return currentTempInKelvin;
    }

    public int getTargetTempInKelvin() {
        return targetTempInKelvin;
    }

    public int getHeatDecreasePerTick() {
        return heatDecreasePerTick;
    }

    public void setHeatDecreasePerTick(int heatDecreasePerTick) {
        this.heatDecreasePerTick = heatDecreasePerTick;
    }

    public int getColdDecreasePerTick() {
        return coldDecreasePerTick;
    }

    public void setColdDecreasePerTick(int coldDecreasePerTick) {
        this.coldDecreasePerTick = coldDecreasePerTick;
    }

    public void setTargetTempInKelvin(int targetTempInKelvin) {
        this.targetTempInKelvin = targetTempInKelvin;
    }

    public void setCurrentTempInKelvin(int currentTempInKelvin) {
        this.currentTempInKelvin = currentTempInKelvin;
    }

    public void setHeatBuffer(int heatBuffer) {
        this.heatBuffer = heatBuffer;
    }

    public void setColdBuffer(int coldBuffer) {
        this.coldBuffer = coldBuffer;
    }

    public void setBufferInputCooldown(int bufferInputCooldown) {
        this.bufferInputCooldown = bufferInputCooldown;
    }

    public int getTemperatureUnitId() {
        return temperatureUnit.getId();
    }

    public void setTemperatureUnit(int temperatureUnitId) {
        for (TemperatureUnit tempUnit : TemperatureUnit.values()) {
            if (tempUnit.getId() == temperatureUnitId) {
                this.temperatureUnit = tempUnit;
            }
        }
    }

    public void switchTemperatureUnit() {
        TemperatureUnit[] temperatureUnits = TemperatureUnit.values();
        int elementCount = temperatureUnits.length;

        int switchIndex = this.temperatureUnit.getId() + 1;
        if (switchIndex > elementCount) {
            this.temperatureUnit = TemperatureUnit.getTemperatureUnitById(0);
        } else {
            this.temperatureUnit = TemperatureUnit.getTemperatureUnitById(switchIndex);
        }
    }

    public void pressArrowButton(boolean shouldIncrease, short amount) {
        // Do nothing if the target temperature is already at minimum or maximum
        if ((shouldIncrease && this.targetTempInKelvin == MAX_TARGET_TEMPERATURE) ||
                (!shouldIncrease && this.targetTempInKelvin == MIN_TARGET_TEMPERATURE)) return;

        if (shouldIncrease) {
            if ((this.targetTempInKelvin + amount) > MAX_TARGET_TEMPERATURE) {
                this.targetTempInKelvin = MAX_TARGET_TEMPERATURE;
            } else {
                this.targetTempInKelvin += amount;
            }
        } else {
            if ((this.targetTempInKelvin - amount) < MIN_TARGET_TEMPERATURE) {
                this.targetTempInKelvin = MIN_TARGET_TEMPERATURE;
            } else {
                this.targetTempInKelvin -= amount;
            }
        }
    }

    /**
     * If the heat or the cold input slot contain items.
     * Increase buffer size by the amount from the ItemStack.
     *
     * @return if inputs for heat and cold buffer changed
     */
    private boolean processBufferInputs() {
        ItemStack heatStack = this.inventory.getStackInSlot(HEAT_ITEM_SLOT);
        ItemStack coldStack = this.inventory.getStackInSlot(COLD_ITEM_SLOT);
        // Reset the buffer if both input slots are empty
        if (heatStack.isEmpty() && coldStack.isEmpty() && this.bufferInputCooldown > 0) {
            this.bufferInputCooldown = 0;
            return false;
        }

        if (this.bufferInputCooldown == BUFFER_INPUT_COOLDOWN) {
            boolean bufferChanged = false;
            if (!heatStack.isEmpty()) {
                // Every item which could be inserted into the slot has a heat value larger than 0
                int heatValue = FarmersLifeEventFactory.getItemHeatValue(heatStack) * 10;
                if (this.heatBuffer + heatValue <= MAX_HEAT_BUFFER) {
                    this.heatBuffer += heatValue;
                    if (heatStack.getItem() instanceof BucketItem) {
                        this.inventory.setStackInSlot(HEAT_ITEM_SLOT, new ItemStack(Items.BUCKET));
                    } else {
                        heatStack.shrink(1);
                    }
                    bufferChanged = true;
                }
            }

            if (!coldStack.isEmpty()) {
                // Every item which could be inserted into the slot has a cold value larger than 0
                int coldValue = FarmersLifeEventFactory.getItemColdValue(coldStack) * 10;
                if (this.coldBuffer + coldValue <= MAX_COLD_BUFFER) {
                    this.coldBuffer += coldValue;
                    if (coldStack.getItem() instanceof BucketItem) {
                        this.inventory.setStackInSlot(COLD_ITEM_SLOT, new ItemStack(Items.BUCKET));
                    } else {
                        coldStack.shrink(1);
                    }
                    bufferChanged = true;
                }
            }

            if (bufferChanged) {
                this.bufferInputCooldown = 0;
                return true;
            }
            return false;
        } else {
            this.bufferInputCooldown++;
            return true;
        }
    }

    /**
     * Modifies the current temperature until the target temperature is reached
     * If the heat or cold buffer are not enough to supply the required amount
     * the temperature will slowly change back to the fluids original temperature
     *
     * @return if temperature has changed
     */
    private boolean processTemperature() {
        FluidStack inputTankFluidStack = this.getFluidInTank(INPUT_FLUID_TANK);
        // Do not modify the current temperature if no fluid is inside
        if (inputTankFluidStack.isEmpty()) return false;
        int fluidTemp = inputTankFluidStack.getFluid().getAttributes().getTemperature() * 100;

        if (this.temperatureChangeCooldown == TEMPERATURE_CHANGE_COOLDOWN) {
            this.temperatureChangeCooldown = 0;
//            FarmersLife.LOGGER.info("temperatureChangeCooldown: " + this.temperatureChangeCooldown);
            FarmersLife.LOGGER.info("Start Temperature Process!");
            FarmersLife.LOGGER.info("[processTemperature] coldBuffer: " + this.coldBuffer + ", heatBuffer: " + this.heatBuffer +
                    ", fluidTemp(K): " + fluidTemp + ", currentTempInKelvin: " + this.currentTempInKelvin + ", targetTempInKelvin: " + this.targetTempInKelvin);
            if (this.targetTempInKelvin == fluidTemp && fluidTemp == this.currentTempInKelvin) {
                this.coldDecreasePerTick = 0;
                this.heatDecreasePerTick = 0;
                return false;
            }

            if (fluidTemp > this.targetTempInKelvin) {
                // COOLING
                // Set current amount of heat and cold which is decreased each tick
                int tempColdDecrease = (int)((DEFAULT_DECREASE_SPEED + (((fluidTemp / 100.0F) - (this.targetTempInKelvin / 100.0F)) * DEFAULT_DECREASE_SPEED_MODIFIER)) * TEMPERATURE_CHANGE_COOLDOWN);
                // Set decrease per tick
                this.coldDecreasePerTick = Math.min(this.coldBuffer, tempColdDecrease);
                this.heatDecreasePerTick = Math.min(this.heatBuffer, (DEFAULT_DECREASE_SPEED * TEMPERATURE_CHANGE_COOLDOWN));
                // Set buffers
                this.coldBuffer -= this.coldDecreasePerTick;
                this.heatBuffer -= this.heatDecreasePerTick;

                FarmersLife.LOGGER.info("[COOLING] coldBuffer: " + this.coldBuffer + ", DEFAULT_DECREASE_SPEED: " + DEFAULT_DECREASE_SPEED +
                        ", heatBuffer: " + this.heatBuffer + ", tempColdDecrease: " + tempColdDecrease);
                FarmersLife.LOGGER.info("[COOLING] coldDecreasePerTick: " + this.coldDecreasePerTick +
                        ", heatDecreasePerTick: " + this.heatDecreasePerTick);

                if (this.coldBuffer < tempColdDecrease || this.heatBuffer < (DEFAULT_DECREASE_SPEED * TEMPERATURE_CHANGE_COOLDOWN)) {
                    // Can not support cooling this tick -> increase temp til max of fluidTemp
                    if (this.currentTempInKelvin == fluidTemp) return false;
                    if ((this.currentTempInKelvin + TEMPERATURE_STEP_WEIGHT) > fluidTemp) {
                        this.currentTempInKelvin = fluidTemp;
//                        this.currentTempInKelvin -= TEMPERATURE_STEP_WEIGHT;
                    } else {
                        this.currentTempInKelvin += TEMPERATURE_STEP_WEIGHT;
                    }
                } else {
                    if (this.currentTempInKelvin == this.targetTempInKelvin) return false;
                    if ((this.currentTempInKelvin - TEMPERATURE_STEP_WEIGHT) < this.targetTempInKelvin) {
//                        this.currentTempInKelvin = this.targetTempInKelvin;
                        this.currentTempInKelvin += TEMPERATURE_STEP_WEIGHT;
                    } else {
                        this.currentTempInKelvin -= TEMPERATURE_STEP_WEIGHT;
                    }
                }
            } else {
                // HEATING or equal
                // Set current amount of heat and cold which is decreased each tick
                int tempHeatDecrease = (int)((DEFAULT_DECREASE_SPEED + (((this.targetTempInKelvin / 100.0F) - (fluidTemp / 100.0F)) * DEFAULT_DECREASE_SPEED_MODIFIER)) * TEMPERATURE_CHANGE_COOLDOWN);
                // Set decrease per tick
                this.coldDecreasePerTick = Math.min(this.coldBuffer, (DEFAULT_DECREASE_SPEED * TEMPERATURE_CHANGE_COOLDOWN));
                this.heatDecreasePerTick = Math.min(this.heatBuffer, tempHeatDecrease);
                // Set buffers
                this.coldBuffer -= this.coldDecreasePerTick;
                this.heatBuffer -= this.heatDecreasePerTick;

                FarmersLife.LOGGER.info("[HEATING or equal] coldBuffer: " + this.coldBuffer + ", DEFAULT_DECREASE_SPEED: " + DEFAULT_DECREASE_SPEED +
                        ", heatBuffer: " + this.heatBuffer + ", tempHeatDecrease: " + tempHeatDecrease);
                FarmersLife.LOGGER.info("[HEATING or equal] coldDecreasePerTick: " + this.coldDecreasePerTick +
                        ", heatDecreasePerTick: " + this.heatDecreasePerTick);

                if (this.heatBuffer < tempHeatDecrease || this.coldBuffer < (DEFAULT_DECREASE_SPEED * TEMPERATURE_CHANGE_COOLDOWN)) {
                    // Can not support heating this tick -> decrease temp til min of fluidTemp
                    if (this.currentTempInKelvin == fluidTemp) return false;
                    if ((this.currentTempInKelvin - TEMPERATURE_STEP_WEIGHT) < fluidTemp) {
                        this.currentTempInKelvin = fluidTemp;
//                        this.currentTempInKelvin += TEMPERATURE_STEP_WEIGHT;
                    } else {
                        this.currentTempInKelvin -= TEMPERATURE_STEP_WEIGHT;
                    }
                } else {
                    if (this.currentTempInKelvin == this.targetTempInKelvin) return false;
                    if ((this.currentTempInKelvin + TEMPERATURE_STEP_WEIGHT) > this.targetTempInKelvin) {
//                        this.currentTempInKelvin = this.targetTempInKelvin;
                        this.currentTempInKelvin -= TEMPERATURE_STEP_WEIGHT;
                    } else {
                        this.currentTempInKelvin += TEMPERATURE_STEP_WEIGHT;
                    }
                }
            }

            if (this.world != null) {
                this.world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
                this.markDirty();
            }
            return true;
        } else {
//            FarmersLife.LOGGER.info("temperatureChangeCooldown: " + this.temperatureChangeCooldown);
            this.temperatureChangeCooldown++;
            return true;
        }
    }

    /**
     * Merges a recipe output into the output slots
     * Tries to merge into output slots with content first, if it can not merge it chooses the first empty output slot
     *
     * @param resultCopy: A copy of the recipe output ItemStack
     */
    private void mergeIntoAvailableSlot(ItemStack resultCopy) {
        ItemStack remaining = resultCopy;
        ItemStack outputSlot1 = this.inventory.getStackInSlot(OUTPUT_ITEM_SLOT_1);
        if (!outputSlot1.isEmpty() && outputSlot1.isItemEqual(resultCopy)) {
            remaining = this.inventory.insertItem(OUTPUT_ITEM_SLOT_1, remaining, false);
            if (remaining.isEmpty()) return;
        }

        ItemStack outputSlot2 = this.inventory.getStackInSlot(OUTPUT_ITEM_SLOT_2);
        if (!outputSlot2.isEmpty() && outputSlot2.isItemEqual(resultCopy)) {
            remaining = this.inventory.insertItem(OUTPUT_ITEM_SLOT_2, remaining, false);
            if (remaining.isEmpty()) return;
        }

        ItemStack outputSlot3 = this.inventory.getStackInSlot(OUTPUT_ITEM_SLOT_3);
        if (!outputSlot3.isEmpty() && outputSlot3.isItemEqual(resultCopy)) {
            remaining = this.inventory.insertItem(OUTPUT_ITEM_SLOT_3, remaining, false);
            if (remaining.isEmpty()) return;
        }

        if (outputSlot1.isEmpty()) {
            this.inventory.insertItem(OUTPUT_ITEM_SLOT_1, remaining, false);
            return;
        }
        if (outputSlot2.isEmpty()) {
            this.inventory.insertItem(OUTPUT_ITEM_SLOT_2, remaining, false);
            return;
        }
        if (outputSlot3.isEmpty()) {
            this.inventory.insertItem(OUTPUT_ITEM_SLOT_3, remaining, false);
        }
    }
}
