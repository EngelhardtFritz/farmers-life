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
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MortarAndPestleRecipe implements IRecipe<RecipeWrapper> {
    public static final MortarAndPestleRecipe.MortarAndPestleRecipeSerializer MORTAR_AND_PESTLE_RECIPE_SERIALIZER = new MortarAndPestleRecipe.MortarAndPestleRecipeSerializer();
    public static final ResourceLocation MORTAR_AND_PESTLE_RECIPE_SERIALIZER_ID = new ResourceLocation(FarmersLife.MOD_ID, "mortar_and_pestle");

    private final ResourceLocation recipeId;
    private Map<Ingredient, Integer> inputIngredients;
    private final ItemStack result;

    public MortarAndPestleRecipe(ResourceLocation recipeId, Map<Ingredient, Integer> inputIngredients, ItemStack outputPrimary) {
        this.recipeId = recipeId;
        this.inputIngredients = inputIngredients;
        this.result = outputPrimary;
    }

    @Override
    public boolean matches(RecipeWrapper inv, World worldIn) {
        if (this.inputIngredients.size() <= 0) {
            return false;
        }

        for (Ingredient ingredient : this.inputIngredients.keySet()) {
            int requiredCount = this.inputIngredients.get(ingredient);
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

    @Override
    public ItemStack getRecipeOutput() {
        return this.result;
    }

    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv) {
        return this.getRecipeOutput().copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.MORTAR_AND_PESTLE_RECIPE_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipeSerializer.MORTAR_AND_PESTLE_RECIPE_TYPE;
    }

    public Map<Ingredient, Integer> getInputIngredients() {
        return inputIngredients;
    }

    public ItemStack getOutputPrimary() {
        return result;
    }

    public static class MortarAndPestleRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MortarAndPestleRecipe> {
        public static List<Item> itemInputList = new ArrayList<>();

        @Override
        public MortarAndPestleRecipe read(ResourceLocation recipeId, JsonObject json) {
            JsonArray inputObject = JSONUtils.getJsonArray(json, "ingredients");

            Map<Ingredient, Integer> inputIngredients = new LinkedHashMap<>();
            for (int i = 0; i < inputObject.size(); i++) {
                JsonElement element = inputObject.get(i);
                Ingredient inputIngredient = CraftingHelper.getIngredient(element);
                int count = JSONUtils.getInt(element.getAsJsonObject(), "count", 1);
                inputIngredients.put(inputIngredient, count);

                for (ItemStack stack : inputIngredient.getMatchingStacks()) {
                    if (!itemInputList.contains(stack.getItem())) {
                        itemInputList.add(stack.getItem());
                    }
                }
            }

            ItemStack primaryOutput = CraftingHelper.getItemStack(json.get("result").getAsJsonObject(), false);
            return new MortarAndPestleRecipe(recipeId, inputIngredients, primaryOutput);
        }

        @Nullable
        @Override
        public MortarAndPestleRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            int ingredientCount = buffer.readByte();
            Map<Ingredient, Integer> inputIngredients = new LinkedHashMap<>();
            for (int i = 0; i < ingredientCount; ++i) {
                Ingredient ingredient = Ingredient.read(buffer);
                int count = buffer.readVarInt();
                inputIngredients.put(ingredient, count);
            }
            ItemStack primaryOutput = buffer.readItemStack();
            return new MortarAndPestleRecipe(recipeId, inputIngredients, primaryOutput);
        }

        @Override
        public void write(PacketBuffer buffer, MortarAndPestleRecipe recipe) {
            buffer.writeByte(recipe.getInputIngredients().size());
            recipe.getInputIngredients().forEach((ingredient, count) -> {
                ingredient.write(buffer);
                buffer.writeVarInt(count);
            });
            buffer.writeItemStack(recipe.getOutputPrimary(), false);
        }
    }
}
