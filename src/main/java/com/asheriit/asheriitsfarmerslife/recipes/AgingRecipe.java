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
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AgingRecipe implements IRecipe<RecipeWrapper> {
    public static final AgingRecipe.AgingRecipeSerializer AGING_RECIPE_SERIALIZER = new AgingRecipe.AgingRecipeSerializer();
    public static final ResourceLocation AGING_ID = new ResourceLocation(FarmersLife.MOD_ID, "aging");

    private final ResourceLocation recipeId;
    private final Ingredient inputIngredient;
    private final int inputIngredientCount;
    private final ItemStack recipeOutput;
    private final int processTime;

    public AgingRecipe(ResourceLocation recipeId, Ingredient inputIngredient, int inputIngredientCount, ItemStack recipeOutput, int processTime) {
        this.recipeId = recipeId;
        this.inputIngredient = inputIngredient;
        this.inputIngredientCount = inputIngredientCount;
        this.recipeOutput = recipeOutput;
        this.processTime = processTime;
    }

    @Override
    public boolean matches(RecipeWrapper inv, World worldIn) {
        // Expect inventory to be of size one
        if (inv.getSizeInventory() != 1) {
            return false;
        }

        for (ItemStack stack : this.inputIngredient.getMatchingStacks()) {
            if (inv.getStackInSlot(0).getItem().equals(stack.getItem()) && inv.getStackInSlot(0).getCount() == this.inputIngredientCount) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv) {
        return this.recipeOutput.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.AGING_RECIPE_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipeSerializer.AGING_RECIPE_TYPE;
    }

    public Ingredient getInputIngredient() {
        return inputIngredient;
    }

    public int getInputIngredientCount() {
        return inputIngredientCount;
    }

    public int getProcessTime() {
        return processTime;
    }

    public static class AgingRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AgingRecipe> {
        public static List<Item> itemInputList = new ArrayList<>();

        @Override
        public AgingRecipe read(ResourceLocation recipeId, JsonObject json) {
            Ingredient inputIngredient = CraftingHelper.getIngredient(json.getAsJsonObject("inputIngredient"));
            int inputIngredientCount = JSONUtils.getInt(json.getAsJsonObject("inputIngredient"), "count");
            ItemStack recipeOutput = CraftingHelper.getItemStack(json.getAsJsonObject("recipeOutput"), false);
            int processTime = JSONUtils.getInt(json, "processTime");

            for (ItemStack stack : inputIngredient.getMatchingStacks()) {
                if (!itemInputList.contains(stack.getItem())) {
                    itemInputList.add(stack.getItem());
                }
            }
            if (!itemInputList.contains(recipeOutput.getItem())) {
                itemInputList.add(recipeOutput.getItem());
            }

            return new AgingRecipe(recipeId, inputIngredient, inputIngredientCount, recipeOutput, processTime);
        }

        @Nullable
        @Override
        public AgingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient inputIngredient = Ingredient.read(buffer);
            int inputIngredientCount = buffer.readVarInt();
            ItemStack recipeOutput = buffer.readItemStack();
            int processTime = buffer.readVarInt();

            return new AgingRecipe(recipeId, inputIngredient, inputIngredientCount, recipeOutput, processTime);
        }

        @Override
        public void write(PacketBuffer buffer, AgingRecipe recipe) {
            recipe.inputIngredient.write(buffer);
            buffer.writeVarInt(recipe.inputIngredientCount);
            buffer.writeItemStack(recipe.recipeOutput);
            buffer.writeVarInt(recipe.processTime);
        }
    }
}
