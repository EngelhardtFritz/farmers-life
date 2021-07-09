package com.asheriit.asheriitsfarmerslife.recipes;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
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
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SoakingRecipe implements IBoilingCauldronRecipe, IRecipe<RecipeWrapper> {
    public static final SoakingRecipeSerializer SOAKING_RECIPE_SERIALIZER = new SoakingRecipeSerializer();
    public static final ResourceLocation SOAKING_ID = new ResourceLocation(FarmersLife.MOD_ID, "soaking_recipe");

    private final ResourceLocation recipeId;
    private final Ingredient inputIngredient;
    private final int inputIngredientCount;
    private final FluidStack inputFluidStack;
    private final ItemStack outputItemStack;
    private final FluidStack outputFluidStack;
    private final int processTime;
    private final boolean requiresLid;

    public SoakingRecipe(ResourceLocation recipeId, Ingredient inputIngredient, int inputIngredientCount, FluidStack inputFluidStack, ItemStack outputItemStack, FluidStack outputFluidStack, int processTime, boolean requiresLid) {
        this.recipeId = recipeId;
        this.inputIngredient = inputIngredient;
        this.inputIngredientCount = inputIngredientCount;
        this.inputFluidStack = inputFluidStack;
        this.outputItemStack = outputItemStack;
        this.outputFluidStack = outputFluidStack;
        this.processTime = processTime;
        this.requiresLid = requiresLid;
    }

    // Do not use for this recipe
    @Override
    public boolean matches(RecipeWrapper inv, World worldIn) {
        return false;
    }

    @Override
    public boolean isBoilingRecipe() {
        return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        if (this.inputIngredient != null) {
            list.add(this.inputIngredient);
        }
        return list;
    }

    @Override
    public boolean matches(ItemStack itemStackIn, FluidStack fluidStackIn, boolean hasLid) {
        if ((this.inputIngredient.equals(Ingredient.EMPTY) && this.inputFluidStack.isEmpty()) ||
                ((!hasLid && this.requiresLid) || (hasLid && !this.requiresLid))) return false;

        int matchingCount = 0;
        for (ItemStack stack : this.inputIngredient.getMatchingStacks()) {
            if (!stack.isEmpty() && !itemStackIn.isEmpty() && itemStackIn.getItem().equals(stack.getItem())) {
                matchingCount += itemStackIn.getCount();
            }
        }

        return ((this.inputIngredient.equals(Ingredient.EMPTY) && itemStackIn.isEmpty()) ||
                (!this.inputIngredient.equals(Ingredient.EMPTY) && !itemStackIn.isEmpty() && matchingCount >= this.inputIngredientCount)) &&
                fluidStackIn.isFluidEqual(this.inputFluidStack) &&
                fluidStackIn.getAmount() >= this.inputFluidStack.getAmount();
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
    public ItemStack getRecipeOutput() {
        return this.outputItemStack;
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.SOAKING_RECIPE_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipeSerializer.SOAKING_RECIPE_TYPE;
    }

    public Ingredient getInputIngredient() {
        return inputIngredient;
    }

    public int getInputIngredientCount() {
        return inputIngredientCount;
    }

    public FluidStack getInputFluidStack() {
        return inputFluidStack;
    }

    public ItemStack getOutputItemStack() {
        return outputItemStack;
    }

    public FluidStack getOutputFluidStack() {
        return outputFluidStack;
    }

    public int getProcessTime() {
        return processTime;
    }

    public boolean doesRequireLid() {
        return requiresLid;
    }

    @Override
    public String toString() {
        return "SoakingRecipe{" +
                "recipeId=" + recipeId +
                ", inputIngredient=" + inputIngredient +
                ", inputIngredientCount=" + inputIngredientCount +
                ", inputFluidStack=" + inputFluidStack +
                ", outputItemStack=" + outputItemStack +
                ", outputFluidStack=" + outputFluidStack +
                ", processTime=" + processTime +
                ", requiresLid=" + requiresLid +
                '}';
    }

    public static class SoakingRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SoakingRecipe> {
        public static List<Fluid> fluidInputList = new ArrayList<>();
        public static List<Item> itemInputList = new ArrayList<>();

        @Override
        public SoakingRecipe read(ResourceLocation recipeId, JsonObject json) {
            // Get input ingredient with count (is allowed to be null)
            JsonObject inputIngredientObject = json.getAsJsonObject("inputIngredient");
            Ingredient inputIngredient = Ingredient.EMPTY;
            int inputIngredientCount = 0;
            if (inputIngredientObject != null) {
                inputIngredient = CraftingHelper.getIngredient(inputIngredientObject);
                inputIngredientCount = JSONUtils.getInt(inputIngredientObject, "count");

                for (ItemStack stack : inputIngredient.getMatchingStacks()) {
                    if (!itemInputList.contains(stack.getItem())) {
                        itemInputList.add(stack.getItem());
                    }
                }
            }

            // Get input fluid stack (is allowed to be null)
            JsonObject ingredientFluidObject = json.getAsJsonObject("ingredientFluid");
            FluidStack inputFluidStack = FluidStack.EMPTY;
            if (ingredientFluidObject != null) {
                ResourceLocation inputFluidLocation = ResourceLocation.create(JSONUtils.getString(ingredientFluidObject, "fluid", "minecraft:empty"), ':');
                int inputFluidAmount = JSONUtils.getInt(ingredientFluidObject, "amount");
                Fluid ingredientFluid = ForgeRegistries.FLUIDS.getValue(inputFluidLocation);
                if (ingredientFluid == null) {
                    throw new IllegalArgumentException("[SoakingRecipeSerializer::read] Expected resource location of ingredient fluid but got: " + inputFluidLocation);
                }
                inputFluidStack = new FluidStack(ingredientFluid, inputFluidAmount);

                if (!fluidInputList.contains(ingredientFluid)) {
                    fluidInputList.add(ingredientFluid);
                }
            }

            // If both input ingredients are null in a recipe it is invalid
            if (inputIngredient == Ingredient.EMPTY && inputFluidStack == FluidStack.EMPTY) {
                throw new IllegalArgumentException("[SoakingRecipeSerializer::read] Expected ingredient or fluid as input but got null!");
            }

            // Get output fluid stack (is allowed to be null)
            JsonObject outputFluidStackObject = json.getAsJsonObject("outputFluid");
            FluidStack outputFluidStack = FluidStack.EMPTY;
            if (outputFluidStackObject != null) {
                ResourceLocation outputFluidLocation = ResourceLocation.create(JSONUtils.getString(outputFluidStackObject, "fluid", "minecraft:empty"), ':');
                int outputFluidAmount = JSONUtils.getInt(ingredientFluidObject, "amount");
                Fluid outputFluid = ForgeRegistries.FLUIDS.getValue(outputFluidLocation);
                if (outputFluid == null) {
                    throw new IllegalArgumentException("[SoakingRecipeSerializer::read] Expected resource location of output fluid but got: " + outputFluidLocation);
                }
                outputFluidStack = new FluidStack(outputFluid, outputFluidAmount);
            }

            // Get output item stack (is allowed to be null)
            JsonObject outputItemStackObject = json.getAsJsonObject("result");
            ItemStack outputItemStack = ItemStack.EMPTY;
            if (outputItemStackObject != null) {
                outputItemStack = CraftingHelper.getItemStack(outputItemStackObject, false);
            }

            // If both output Stacks are null in a recipe it is invalid
            if (outputItemStack.isEmpty() && outputFluidStack.isEmpty()) {
                throw new IllegalArgumentException("[SoakingRecipeSerializer::read] Expected output fluid or item stack to be not null!");
            }

            final int processTime = JSONUtils.getInt(json, "processTime");
            final boolean requiresLid = JSONUtils.getBoolean(json, "requiresLid");
            return new SoakingRecipe(recipeId, inputIngredient, inputIngredientCount, inputFluidStack, outputItemStack, outputFluidStack, processTime, requiresLid);
        }

        @Nullable
        @Override
        public SoakingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient inputIngredient = Ingredient.read(buffer);
            int inputIngredientCount = buffer.readVarInt();
            FluidStack inputFluidStack = buffer.readFluidStack();
            ItemStack outputItemStack = buffer.readItemStack();
            FluidStack outputFluidStack = buffer.readFluidStack();
            int processTime = buffer.readVarInt();
            boolean requiresLid = buffer.readBoolean();
            return new SoakingRecipe(recipeId, inputIngredient, inputIngredientCount, inputFluidStack, outputItemStack, outputFluidStack, processTime, requiresLid);
        }

        @Override
        public void write(PacketBuffer buffer, SoakingRecipe recipe) {
            recipe.getInputIngredient().write(buffer);
            buffer.writeVarInt(recipe.getInputIngredientCount());
            buffer.writeFluidStack(recipe.getInputFluidStack());
            buffer.writeItemStack(recipe.getOutputItemStack());
            buffer.writeFluidStack(recipe.getOutputFluidStack());
            buffer.writeVarInt(recipe.getProcessTime());
            buffer.writeBoolean(recipe.doesRequireLid());
        }
    }
}
