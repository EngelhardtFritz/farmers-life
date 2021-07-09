package com.asheriit.asheriitsfarmerslife.tileentity;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.container.FertilizerComposterContainer;
import com.asheriit.asheriitsfarmerslife.init.ModContainerTypes;
import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.recipes.FertilizerComposterRecipe;
import com.asheriit.asheriitsfarmerslife.utils.items.FertilizerComposterItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class FertilizerComposterTileEntity extends AbstractTickingMachineTileEntity<FertilizerComposterRecipe> {
    private static final ITextComponent CUSTOM_TILE_NAME = new TranslationTextComponent("tileentity." + FarmersLife.MOD_ID + ".fertilizer_composter_tile_entity");

    public static final int INPUT_ITEM_SLOT_1 = 0;
    public static final int INPUT_ITEM_SLOT_2 = 1;
    public static final int INPUT_ITEM_SLOT_3 = 2;
    public static final int INPUT_ITEM_SLOT_4 = 3;
    public static final int OUTPUT_ITEM_PRIMARY_SLOT = 4;
    public static final int OUTPUT_ITEM_SECONDARY_SLOT = 5;

    protected FertilizerComposterItemHandler inventory;

    private final LazyOptional<IItemHandlerModifiable> outputInventoryCapability;
    private final LazyOptional<IItemHandlerModifiable> inputInventoryCapability;
    private final LazyOptional<IItemHandlerModifiable> inventoryCapability;

    public FertilizerComposterTileEntity(TileEntityType tileEntityTypeIn) {
        super(tileEntityTypeIn, CUSTOM_TILE_NAME);
        this.inventory = new FertilizerComposterItemHandler(6);
        this.inventoryCapability = LazyOptional.of(this::getInventory);
        this.outputInventoryCapability = LazyOptional.of(() -> new RangedWrapper(this.getInventory(), OUTPUT_ITEM_PRIMARY_SLOT, OUTPUT_ITEM_SECONDARY_SLOT + 1));
        this.inputInventoryCapability = LazyOptional.of(() -> new RangedWrapper(this.getInventory(), INPUT_ITEM_SLOT_1, INPUT_ITEM_SLOT_4 + 1));
    }

    public FertilizerComposterTileEntity() {
        this(ModTileEntityTypes.FERTILIZER_COMPOSTER_TILE_ENTITY.get());
    }

    @Override
    public ITextComponent getDefaultDisplayName() {
        return new TranslationTextComponent("container." + FarmersLife.MOD_ID + ".fertilizer_composter");
    }

    @Nullable
    @Override
    public FertilizerComposterRecipe getRecipe() {
        if (world != null) {
            Set<IRecipe<?>> recipes = findRecipesByType(ModRecipeSerializer.FERTILIZER_COMPOSTER_RECIPE_TYPE, this.world);
            for (IRecipe<?> recipe : recipes) {
                FertilizerComposterRecipe fertilizerComposterRecipe = (FertilizerComposterRecipe) recipe;
                if (fertilizerComposterRecipe.matches(new RecipeWrapper(this.inventory), world)) {
                    return fertilizerComposterRecipe;
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
    public boolean canProcessRecipe(@Nullable FertilizerComposterRecipe recipe) {
        if (recipe == null) {
            return false;
        }

        boolean canProcess = false;
        if (recipe.getOutputPrimary() != null && !recipe.getOutputPrimary().isEmpty()) {
            ItemStack outputStackPrimary = this.getInventory().getStackInSlot(OUTPUT_ITEM_PRIMARY_SLOT);
            canProcess = (outputStackPrimary.isEmpty() || outputStackPrimary.getItem() == recipe.getOutputPrimary().getItem());

            if (!outputStackPrimary.isEmpty()) {
                int outputCount = recipe.getOutputPrimary().getCount();
                int countFromSlot = outputStackPrimary.getCount();
                if ((outputCount + countFromSlot) > this.getInventory().getSlotLimit(OUTPUT_ITEM_PRIMARY_SLOT)) {
                    canProcess = false;
                }
            }
        }

        if (recipe.getOutputSecondary() != null && !recipe.getOutputSecondary().isEmpty()) {
            ItemStack outputStackSecondary = this.getInventory().getStackInSlot(OUTPUT_ITEM_SECONDARY_SLOT);
            canProcess = canProcess && (outputStackSecondary.isEmpty() || outputStackSecondary.getItem() == recipe.getOutputSecondary().getItem());

            if (!outputStackSecondary.isEmpty()) {
                int outputCount = recipe.getOutputPrimary().getCount();
                int countFromSlot = outputStackSecondary.getCount();
                if ((outputCount + countFromSlot) > this.getInventory().getSlotLimit(OUTPUT_ITEM_SECONDARY_SLOT)) {
                    canProcess = false;
                }
            }
        }

        return canProcess;
    }

    @Override
    public void finishRecipeProcess(@Nonnull FertilizerComposterRecipe recipe) {
        // Remove all required ingredients from the inventory
        Map<Ingredient, Integer> inputIngredients = recipe.getInputIngredients();
        Set<Ingredient> ingredients = inputIngredients.keySet();
        for (Ingredient ingredient: ingredients) {
            int countToReduce = inputIngredients.get(ingredient);
            for (int invIndex = 0; invIndex <= INPUT_ITEM_SLOT_4; invIndex++) {
                ItemStack stackFromSlot = this.inventory.getStackInSlot(invIndex);
                if (countToReduce <= 0) {
                    break;
                }

                if (!stackFromSlot.isEmpty() && ingredient.test(stackFromSlot)) {
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

        // At this stage the primary output should always be present
        ItemStack outputPrimary = recipe.getOutputPrimary();
        this.inventory.insertItem(OUTPUT_ITEM_PRIMARY_SLOT, outputPrimary.copy(), false);
        // Add a secondary output if the recipe
        if (!recipe.getOutputSecondary().isEmpty()) {
            ItemStack outputSecondary = recipe.getOutputSecondary();
            this.inventory.insertItem(OUTPUT_ITEM_SECONDARY_SLOT, outputSecondary.copy(), false);
        }
        // TODO: play sound
    }

    @Override
    public short getProcessTimeTotal() {
        FertilizerComposterRecipe recipe = this.getRecipe();
        if (recipe != null) {
            return (short) recipe.getProcessTime();
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Nullable
    @Override
    public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new FertilizerComposterContainer(windowID, playerInventory, this);
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        final int MAX_DISTANCE_IN_BLOCKS = 16;
        return MAX_DISTANCE_IN_BLOCKS * MAX_DISTANCE_IN_BLOCKS;
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        this.inventoryCapability.invalidate();
        this.inputInventoryCapability.invalidate();
        this.outputInventoryCapability.invalidate();
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

    // ------------ CAPABILITIES ------------
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.DOWN) {
                return this.getOutputInventoryCapability().cast();
            } else {
                return this.getInputInventoryCapability().cast();
            }
        }
        return super.getCapability(cap, side);
    }
}
