package com.asheriit.asheriitsfarmerslife.recipes;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FertilizerComposterRecipe implements IRecipe<RecipeWrapper> {
    public static final FertilizerComposterRecipe.FertilizerComposterRecipeSerializer FERTILIZER_COMPOSTER_RECIPE_SERIALIZER = new FertilizerComposterRecipe.FertilizerComposterRecipeSerializer();
    public static final ResourceLocation FERTILIZER_COMPOSTER_ID = new ResourceLocation(FarmersLife.MOD_ID, "fertilizer_composter");

    private final ResourceLocation recipeId;
    private Map<Ingredient, Integer> inputIngredients;
    private final ItemStack outputPrimary;
    private final ItemStack outputSecondary;
    private final int processTime;

    public FertilizerComposterRecipe(ResourceLocation recipeId, Map<Ingredient, Integer> inputIngredients, ItemStack outputPrimary,
                                     ItemStack outputSecondary, int processTime) {
        this.recipeId = recipeId;
        this.inputIngredients = inputIngredients;
        this.outputPrimary = outputPrimary;
        this.outputSecondary = outputSecondary;
        this.processTime = processTime;
    }

    @Override
    public boolean matches(RecipeWrapper inv, World worldIn) {
        if (this.getInputIngredients().size() <= 0) {
            return false;
        }

        for (Ingredient ingredient : this.getInputIngredients().keySet()) {
            int requiredCount = this.getInputIngredients().get(ingredient);
            int count = 0;
            for (int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack stack = inv.getStackInSlot(i);
                if (!stack.isEmpty() && ingredient.test(stack)) {
                    count += stack.getCount();
                }
            }
            if (count < requiredCount) {
                return false;
            }
        }
        return true;
    }


    /**
     * DO NOT USE THIS OUTPUT
     */
    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv) {
        return this.outputPrimary.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    /**
     * DO NOT USE THIS OUTPUT
     */
    @Override
    public ItemStack getRecipeOutput() {
        return this.outputPrimary;
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.FERTILIZER_COMPOSTER_RECIPE_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return Registry.RECIPE_TYPE.getValue(FERTILIZER_COMPOSTER_ID).get();
    }

    public List<ItemStack> getOutputs() {
        List<ItemStack> outputs = new ArrayList<>();
        if ((this.outputPrimary == null && this.outputPrimary.isEmpty()) || (this.outputSecondary == null && this.outputSecondary.isEmpty())) {
            return outputs;
        }

        if (this.outputPrimary != null && !this.outputPrimary.isEmpty()) {
            outputs.add(this.outputPrimary);
        }
        if (this.outputSecondary != null && !this.outputSecondary.isEmpty()) {
            outputs.add(this.outputSecondary);
        }
        return outputs;
    }

    public ItemStack getOutputPrimary() {
        return outputPrimary;
    }

    public ItemStack getOutputSecondary() {
        return outputSecondary;
    }

    public int getProcessTime() {
        return processTime;
    }

    public Map<Ingredient, Integer> getInputIngredients() {
        return inputIngredients;
    }

    @Override
    public String toString() {
        return "FertilizerComposterRecipe{" +
                "recipeId=" + recipeId +
                ", inputIngredients=" + inputIngredients +
                ", outputPrimary=" + outputPrimary +
                ", outputSecondary=" + outputSecondary +
                ", processTime=" + processTime +
                '}';
    }

    public static class FertilizerComposterRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<FertilizerComposterRecipe> {
        public static List<Item> ingredientList = new ArrayList<>();

        @Override
        public FertilizerComposterRecipe read(ResourceLocation recipeId, JsonObject json) {
            JsonArray inputObject = JSONUtils.getJsonArray(json, "inputIngredients");

            Map<Ingredient, Integer> inputIngredients = new LinkedHashMap<>();
            for (int i = 0; i < inputObject.size(); i++) {
                JsonElement element = inputObject.get(i);
                Ingredient inputIngredient = CraftingHelper.getIngredient(element);
                int count = JSONUtils.getInt(element.getAsJsonObject(), "count", 1);
                inputIngredients.put(inputIngredient, count);

                for (ItemStack stack : inputIngredient.getMatchingStacks()) {
                    if (!ingredientList.contains(stack.getItem())) {
                        ingredientList.add(stack.getItem());
                    }
                }
            }

            ItemStack primaryOutput = CraftingHelper.getItemStack(json.get("primaryOutput").getAsJsonObject(), false);
            JsonElement secondaryOutputObject = json.get("secondaryOutput");
            ItemStack secondaryOutput = ItemStack.EMPTY;
            if (secondaryOutputObject != null) {
                secondaryOutput = CraftingHelper.getItemStack(secondaryOutputObject.getAsJsonObject(), false);
            }
            int processTime = JSONUtils.getInt(json, "processTime");

            return new FertilizerComposterRecipe(recipeId, inputIngredients, primaryOutput, secondaryOutput, processTime);
        }

        @Nullable
        @Override
        public FertilizerComposterRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            int ingredientCount = buffer.readByte();
            Map<Ingredient, Integer> inputIngredients = new LinkedHashMap<>();
            for (int i = 0; i < ingredientCount; ++i) {
                Ingredient ingredient = Ingredient.read(buffer);
                int count = buffer.readVarInt();
                inputIngredients.put(ingredient, count);
            }
            ItemStack primaryOutput = buffer.readItemStack();
            ItemStack secondaryOutput = buffer.readItemStack();
            int processTime = buffer.readInt();
            return new FertilizerComposterRecipe(recipeId, inputIngredients, primaryOutput, secondaryOutput, processTime);
        }

        @Override
        public void write(PacketBuffer buffer, FertilizerComposterRecipe recipe) {
            buffer.writeByte(recipe.getInputIngredients().size());
            recipe.getInputIngredients().forEach((ingredient, count) -> {
                ingredient.write(buffer);
                buffer.writeVarInt(count);
            });
            buffer.writeItemStack(recipe.getOutputPrimary(), false);
            buffer.writeItemStack(recipe.getOutputSecondary(), false);
            buffer.writeInt(recipe.getProcessTime());
        }
    }
}
