package com.asheriit.asheriitsfarmerslife.recipes;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StompingBarrelRecipe implements IRecipe<RecipeWrapper> {
    public static final StompingBarrelRecipe.StompingBarrelRecipeSerializer STOMPING_BARREL_RECIPE_SERIALIZER = new StompingBarrelRecipe.StompingBarrelRecipeSerializer();
    public static final ResourceLocation STOMPING_BARREL_ID = new ResourceLocation(FarmersLife.MOD_ID, "stomping_barrel");

    private final ResourceLocation recipeId;
    private final Ingredient inputIngredient;
    private final int inputIngredientCount;
    private final FluidStack outputFluid;
    private final int processSteps;

    public StompingBarrelRecipe(ResourceLocation recipeId, Ingredient inputIngredient, int inputIngredientCount, FluidStack outputFluid, int processSteps) {
        this.recipeId = recipeId;
        this.inputIngredient = inputIngredient;
        this.inputIngredientCount = inputIngredientCount;
        this.outputFluid = outputFluid;
        this.processSteps = processSteps;
    }

    @Override
    public boolean matches(RecipeWrapper inv, @Nonnull World worldIn) {
        ItemStack stack = inv.getStackInSlot(0);
        int count = stack.getCount();
        return this.inputIngredient.test(stack) && count >= this.inputIngredientCount;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull RecipeWrapper inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    /**
     * ITEM STACK OUTPUT IS NOT USED IN THIS RECIPE
     * @return empty stack
     */
    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    public FluidStack getRecipeFluidOutput() {
        return this.outputFluid;
    }

    public int getInputIngredientCount() {
        return this.inputIngredientCount;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.STOMPING_BARREL_RECIPE_SERIALIZER.get();
    }

    @Nonnull
    @Override
    public IRecipeType<?> getType() {
        return Registry.RECIPE_TYPE.getValue(STOMPING_BARREL_ID).get();
    }

    public int getProcessSteps() {
        return this.processSteps;
    }

    @Override
    public String toString() {
        return "StompingBarrelRecipe{" +
                "recipeId=" + recipeId +
                ", inputIngredient=" + Arrays.toString(inputIngredient.getMatchingStacks()) +
                ", inputIngredientCount=" + inputIngredientCount +
                ", outputFluid=" + outputFluid.getFluid() +
                ", processSteps=" + processSteps +
                '}';
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.withSize(1, this.inputIngredient);
    }

    public static class StompingBarrelRecipeSerializer  extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<StompingBarrelRecipe> {
        public static List<Item> ingredientList = new ArrayList<>();

        @Nonnull
        @Override
        public StompingBarrelRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            Ingredient inputIngredient = CraftingHelper.getIngredient(JSONUtils.getJsonObject(json, "ingredient"));
            int inputIngredientCount = JSONUtils.getInt(json.get("ingredient").getAsJsonObject(), "count", 1);

            ResourceLocation fluidLocation = ResourceLocation.create(JSONUtils.getString(json.get("result").getAsJsonObject(), "fluid", "minecraft:empty"), ':');
            int fluidAmount = JSONUtils.getInt(json.get("result").getAsJsonObject(), "amount");
            FluidStack outputFluid = new FluidStack(ForgeRegistries.FLUIDS.getValue(fluidLocation), fluidAmount);

            int processSteps = JSONUtils.getInt(json, "processSteps");

            StompingBarrelRecipe recipeToReturn = new StompingBarrelRecipe(recipeId, inputIngredient, inputIngredientCount, outputFluid, processSteps);
            for (ItemStack stack: recipeToReturn.inputIngredient.getMatchingStacks()) {
                if (!ingredientList.contains(stack.getItem())) {
                    ingredientList.add(stack.getItem());
                }
            }

            return recipeToReturn;
        }

        @Override
        public StompingBarrelRecipe read(@Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer) {
            Ingredient inputIngredient = Ingredient.read(buffer);
            int inputIngredientCount = buffer.readInt();
            FluidStack outputFluid = FluidStack.readFromPacket(buffer);
            ResourceLocation id = buffer.readResourceLocation();
            int processSteps = buffer.readInt();

            return new StompingBarrelRecipe(id, inputIngredient, inputIngredientCount, outputFluid, processSteps);
        }

        @Override
        public void write(@Nonnull PacketBuffer buffer, StompingBarrelRecipe recipe) {
            recipe.inputIngredient.write(buffer);
            buffer.writeInt(recipe.getInputIngredientCount());
            buffer.writeFluidStack(recipe.outputFluid);
            buffer.writeResourceLocation(recipe.recipeId);
            buffer.writeInt(recipe.processSteps);
        }
    }
}
