package com.asheriit.asheriitsfarmerslife.recipes;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class DryingRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<DryingRecipe> {

    @Override
    public DryingRecipe read(ResourceLocation recipeId, JsonObject json) {
        // Output Result ItemStack
        ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "result"), false);
        // Input FluidStack
        ResourceLocation fluidResourceLocation = ResourceLocation.create(JSONUtils.getString(json.get("ingredientFluid").getAsJsonObject(), "fluid", "minecraft:empty"), ':');
        int fluidAmount = JSONUtils.getInt(json.get("ingredientFluid").getAsJsonObject(), "amount");
        FluidStack inputFluid = new FluidStack(ForgeRegistries.FLUIDS.getValue(fluidResourceLocation), fluidAmount);
        // DryingTimeTotal
        int totalDryingTime = JSONUtils.getInt(json, "dryingTimeTotal");

        return new DryingRecipe(recipeId, inputFluid, output, totalDryingTime);
    }

    @Override
    public DryingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        ItemStack output = buffer.readItemStack();
        FluidStack inputFluid = buffer.readFluidStack();
        int totalDryingTime = buffer.readInt();

        return new DryingRecipe(recipeId, inputFluid, output, totalDryingTime);
    }

    @Override
    public void write(PacketBuffer buffer, DryingRecipe recipe) {
        buffer.writeFluidStack(recipe.getInputFluid());
        buffer.writeItemStack(recipe.getRecipeOutput(), false);
        buffer.writeInt(recipe.getDryingTimeTotal());
    }
}
