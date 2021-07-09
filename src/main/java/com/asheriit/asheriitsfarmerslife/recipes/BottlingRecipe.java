package com.asheriit.asheriitsfarmerslife.recipes;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BottlingRecipe implements IRecipe<RecipeWrapper> {
    public static final BottlingRecipe.BottlerRecipeSerializer BOTTLING_RECIPE_SERIALIZER = new BottlingRecipe.BottlerRecipeSerializer();
    public static final ResourceLocation BOTTLING_ID = new ResourceLocation(FarmersLife.MOD_ID, "bottling");

    private final ResourceLocation recipeId;
    private final List<Pair<Ingredient, Integer>> inputIngredients;
    private final FluidStack inputFluidStack;
    private final ItemStack outputItemStack;
    private final int processTime;

    public BottlingRecipe(ResourceLocation recipeId, List<Pair<Ingredient, Integer>> inputIngredients, FluidStack inputFluidStack, ItemStack outputItemStack, int processTime) {
        this.recipeId = recipeId;
        this.inputIngredients = inputIngredients;
        this.inputFluidStack = inputFluidStack;
        this.outputItemStack = outputItemStack;
        this.processTime = processTime;
    }

    @Override
    public boolean matches(RecipeWrapper inv, World worldIn) {
        if (this.getInputIngredients().size() <= 0) {
            return false;
        }

        int slots = inv.getSizeInventory();
        for (int i = 0; i < this.getInputIngredients().size(); i++) {
            Pair<Ingredient, Integer> ingredientIntegerPair = this.getInputIngredients().get(i);
            int requiredCount = ingredientIntegerPair.getValue();
            int count = 0;
            for (int j = 0; j < slots; j++) {
                ItemStack stack = inv.getStackInSlot(j);
                if (!stack.isEmpty() && ingredientIntegerPair.getKey().test(stack)) {
                    count += stack.getCount();
                }
            }
            if (count < requiredCount) {
                return false;
            }
        }
        return true;
    }

    public boolean matches(FluidStack fluidStack, RecipeWrapper inv, World worldIn) {
        boolean itemStackMatches = this.matches(inv, worldIn);
        if (itemStackMatches && (!this.getInputFluidStack().isEmpty() && this.getInputFluidStack().getAmount() > 0)) {
            return this.inputFluidStack.isFluidEqual(fluidStack) && fluidStack.getAmount() >= this.inputFluidStack.getAmount();
        }
        return itemStackMatches;
    }

    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv) {
        return this.outputItemStack.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.outputItemStack;
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.BOTTLING_RECIPE_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipeSerializer.BOTTLING_RECIPE_TYPE;
    }

    public ResourceLocation getRecipeId() {
        return recipeId;
    }

    public List<Pair<Ingredient, Integer>> getInputIngredients() {
        return inputIngredients;
    }

    public List<Pair<Ingredient, Integer>> getDistinctIngredients() {
        List<Pair<Ingredient, Integer>> listOfIngredientCountPairs = new ArrayList<>();
        for (int i = 0; i < this.inputIngredients.size(); i++) {
            Pair<Ingredient, Integer> ingredientIntegerPair = this.inputIngredients.get(i);
            if (i == 0) {
                listOfIngredientCountPairs.add(ingredientIntegerPair);
            } else {
                Ingredient ingredient = ingredientIntegerPair.getKey();
                int count = ingredientIntegerPair.getValue();
                for (int j = 0; j < listOfIngredientCountPairs.size(); j++) {
                    Pair<Ingredient, Integer> distinctEntry = listOfIngredientCountPairs.get(j);
                    int distinctCount = distinctEntry.getValue();
                    if (ingredient == distinctEntry.getKey()) {
                        distinctEntry.setValue(distinctCount + count);
                        listOfIngredientCountPairs.set(j, distinctEntry);
                    }
                }
            }
        }
        return listOfIngredientCountPairs;
    }

    public FluidStack getInputFluidStack() {
        return inputFluidStack;
    }

    public ItemStack getOutputItemStack() {
        return outputItemStack;
    }

    public int getProcessTime() {
        return processTime;
    }

    public static class BottlerRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<BottlingRecipe> {
        public static List<Fluid> fluidInputList = new ArrayList<>();
        public static List<Item> itemInputList = new ArrayList<>();

        @Override
        public BottlingRecipe read(ResourceLocation recipeId, JsonObject json) {
            JsonArray ingredientPairs = json.getAsJsonArray("ingredientItems");
            List<Pair<Ingredient, Integer>> inputIngredients = new ArrayList<>();
            // Get all item ingredients with count
            for (JsonElement element : ingredientPairs) {
                Ingredient inputIngredient = CraftingHelper.getIngredient(element);
                int inputIngredientCount = JSONUtils.getInt(element.getAsJsonObject(), "count");
                Pair<Ingredient, Integer> pair = Pair.of(inputIngredient, inputIngredientCount);
                inputIngredients.add(pair);

                for (ItemStack stack : inputIngredient.getMatchingStacks()) {
                    Item item = stack.getItem();
                    if (!itemInputList.contains(item)) {
                        itemInputList.add(item);
                    }
                }
            }

            // Get input ingredient fluid with amount
            JsonObject ingredientFluidObject = json.getAsJsonObject("ingredientFluid");
            FluidStack inputFluidStack = FluidStack.EMPTY;
            if (ingredientFluidObject != null) {
                ResourceLocation inputFluidLocation = ResourceLocation.create(JSONUtils.getString(ingredientFluidObject, "fluid", "minecraft:empty"), ':');
                int inputFluidAmount = JSONUtils.getInt(ingredientFluidObject, "amount");
                Fluid ingredientFluid = ForgeRegistries.FLUIDS.getValue(inputFluidLocation);
                if (ingredientFluid == null) {
                    throw new IllegalArgumentException("[BottlerRecipeSerializer::read] Expected resource location of ingredient fluid but got: " + inputFluidLocation);
                }
                inputFluidStack = new FluidStack(ingredientFluid, inputFluidAmount);

                if (!fluidInputList.contains(ingredientFluid)) {
                    fluidInputList.add(ingredientFluid);
                }
            }

            // Get result ItemStack and process time
            ItemStack outputItemStack = CraftingHelper.getItemStack(json.getAsJsonObject("result"), false);
            int processTime = JSONUtils.getInt(json, "processTime");

            return new BottlingRecipe(recipeId, inputIngredients, inputFluidStack, outputItemStack, processTime);
        }

        @Nullable
        @Override
        public BottlingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            byte ingredientCount = buffer.readByte();
            List<Pair<Ingredient, Integer>> inputIngredients = new ArrayList<>();
            for (byte b = 0; b < ingredientCount; b++) {
                Ingredient ingredient = Ingredient.read(buffer);
                int count = buffer.readVarInt();
                inputIngredients.add(Pair.of(ingredient, count));
            }
            FluidStack inputFluidStack = buffer.readFluidStack();
            ItemStack outputItemStack = buffer.readItemStack();
            int processTime = buffer.readInt();
            return new BottlingRecipe(recipeId, inputIngredients, inputFluidStack, outputItemStack, processTime);
        }

        @Override
        public void write(PacketBuffer buffer, BottlingRecipe recipe) {
            buffer.writeByte(recipe.getInputIngredients().size());
            recipe.getInputIngredients().forEach((ingredientIntegerPair) -> {
                Ingredient ingredient = ingredientIntegerPair.getKey();
                int count = ingredientIntegerPair.getValue();

                ingredient.write(buffer);
                buffer.writeVarInt(count);
            });
            buffer.writeFluidStack(recipe.getInputFluidStack());
            buffer.writeItemStack(recipe.getOutputItemStack());
            buffer.writeInt(recipe.getProcessTime());
        }
    }
}
