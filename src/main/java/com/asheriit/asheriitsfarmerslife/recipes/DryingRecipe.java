package com.asheriit.asheriitsfarmerslife.recipes;

import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class DryingRecipe implements IDryingRecipe {
    private final ResourceLocation recipeId;
    private final FluidStack inputFluid;
    private final ItemStack result;
    private final int dryingTimeTotal;

    public DryingRecipe(ResourceLocation recipeTypeId, FluidStack ingredientFluid, ItemStack result, int dryingTimeTotal) {
        this.recipeId = recipeTypeId;
        this.inputFluid = ingredientFluid;
        this.result = result;
        this.dryingTimeTotal = dryingTimeTotal;
    }

    public FluidStack getInputFluid() {
        return this.inputFluid;
    }

    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv) {
        return this.result.copy();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.result;
    }

    public int getDryingTimeTotal() {
        return this.dryingTimeTotal;
    }

    @Override
    public boolean matches(RecipeWrapper inv, World worldIn) {
        return false;
    }

    public boolean fluidStackMatches(FluidStack stack) {
        if (stack == null) {
            return false;
        }
        return stack.isFluidEqual(this.inputFluid) && stack.getAmount() >= this.inputFluid.getAmount();
    }

    @Override
    public String toString() {
        return "DryingRecipe{" +
                "recipeId=" + recipeId +
                ", inputFluid=" + inputFluid +
                ", output=" + result +
                ", dryingTimeTotal=" + dryingTimeTotal +
                '}';
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.DRYING_RECIPE_SERIALIZER.get();
    }
}
