package com.asheriit.asheriitsfarmerslife.recipes;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModRecipeSerializer;
import com.google.gson.JsonArray;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FermentingRecipe implements IRecipe<RecipeWrapper> {
    public static final FermentingRecipeSerializer FERMENTING_RECIPE_SERIALIZER = new FermentingRecipeSerializer();
    public static final ResourceLocation FERMENTING_ID = new ResourceLocation(FarmersLife.MOD_ID, "fermenting");

    private final ResourceLocation recipeId;
    private final Ingredient inputIngredient;
    private final int inputIngredientCount;
    private final FluidStack ingredientFluid;
    private final FluidStack outputFluid;
    private final int processTime;

    public FermentingRecipe(ResourceLocation recipeId, Ingredient inputIngredient, int inputIngredientCount, FluidStack ingredientFluid, FluidStack outputFluid, int processTime) {
        this.recipeId = recipeId;
        this.inputIngredient = inputIngredient;
        this.inputIngredientCount = inputIngredientCount;
        this.ingredientFluid = ingredientFluid;
        this.outputFluid = outputFluid;
        this.processTime = processTime;
    }

    /**
     * Checks if fluid type and amount are equal to that of the recipe
     *
     * @param stack: Input fluid stack
     * @param inputSlot: Input ItemStack
     * @return true if stacks match
     */
    public boolean fluidStackMatches(FluidStack stack, @Nullable ItemStack inputSlot) {
        if (stack.isEmpty() || ((inputSlot == null || inputSlot.isEmpty()) && this.getInputIngredient() != Ingredient.EMPTY)) {
            return false;
        }

        if (this.getInputIngredient() == Ingredient.EMPTY) {
            return stack.isFluidEqual(this.ingredientFluid) && stack.getAmount() >= this.getIngredientFluid().getAmount();
        }

        // At this point the inputSlot should never be null or empty
        return stack.isFluidEqual(this.ingredientFluid) && stack.getAmount() >= this.getIngredientFluid().getAmount() &&
                this.inputIngredient.test(inputSlot) && inputSlot.getCount() > this.inputIngredientCount;
    }

    @Override
    public boolean matches(RecipeWrapper inv, World worldIn) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.FERMENTING_RECIPE_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipeSerializer.FERMENTATION_RECIPE_TYPE;
    }

    public FluidStack getIngredientFluid() {
        return ingredientFluid;
    }

    public Ingredient getInputIngredient() {
        return inputIngredient;
    }

    public int getInputIngredientCount() {
        return inputIngredientCount;
    }

    public FluidStack getOutputFluid() {
        return outputFluid;
    }

    public int getProcessTime() {
        return processTime;
    }

    @Override
    public String toString() {
        return "FermentingRecipe{" +
                "recipeId=" + recipeId +
                ", inputIngredient=" + inputIngredient +
                ", inputIngredientCount=" + inputIngredientCount +
                ", ingredientFluid=" + ingredientFluid +
                ", outputFluid=" + outputFluid +
                ", processTime=" + processTime +
                '}';
    }

    public static class FermentingRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<FermentingRecipe> {
        public static List<Fluid> fluidInputList = new ArrayList<>();
        public static List<Item> itemInputList = new ArrayList<>();

        @Override
        public FermentingRecipe read(ResourceLocation recipeId, JsonObject json) {
            // Get required fluid input
            JsonObject ingredientFluidObject = json.getAsJsonObject("ingredientFluid");
            ResourceLocation fluidLocation = ResourceLocation.create(JSONUtils.getString(ingredientFluidObject, "fluid", "minecraft:empty"), ':');
            int ingredientFluidAmount = JSONUtils.getInt(ingredientFluidObject, "amount");
            Fluid ingredientFluid = ForgeRegistries.FLUIDS.getValue(fluidLocation);
            if (ingredientFluid == null) {
                throw new IllegalArgumentException("[FermentingRecipeSerializer::read] Expected resource location of ingredient fluid but got: " + fluidLocation);
            }
            FluidStack ingredientFluidStack = new FluidStack(ingredientFluid, ingredientFluidAmount);

            // Get Ingredient ItemStack (can be null)
            JsonArray inputIngredientObject = json.getAsJsonArray("inputIngredient");
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

            // Get possible recipe output fluid
            JsonObject outputFluidObject = json.getAsJsonObject("outputFluid");
            ResourceLocation outputFluidLocation = ResourceLocation.create(JSONUtils.getString(outputFluidObject, "fluid", "minecraft:empty"), ':');
            int outputFluidAmount = JSONUtils.getInt(ingredientFluidObject, "amount");
            Fluid outputFluid = ForgeRegistries.FLUIDS.getValue(outputFluidLocation);
            if (outputFluid == null) {
                throw new IllegalArgumentException("[FermentingRecipeSerializer::read] Expected resource location of output fluid but got: " + outputFluidLocation);
            }
            FluidStack outputFluidStack = new FluidStack(outputFluid, outputFluidAmount);

            int processTime = JSONUtils.getInt(json, "processTime");

            if (!fluidInputList.contains(ingredientFluid)) {
                fluidInputList.add(ingredientFluid);
            }

            return new FermentingRecipe(recipeId, inputIngredient, inputIngredientCount, ingredientFluidStack, outputFluidStack, processTime);
        }

        @Nullable
        @Override
        public FermentingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            FluidStack ingredientFluidStack = buffer.readFluidStack();
            Ingredient inputIngredient = Ingredient.read(buffer);
            int inputIngredientCount = buffer.readShort();
            FluidStack outputFluidStack = buffer.readFluidStack();
            int processTime = buffer.readInt();
            return new FermentingRecipe(recipeId, inputIngredient, inputIngredientCount, ingredientFluidStack, outputFluidStack, processTime);
        }

        @Override
        public void write(PacketBuffer buffer, FermentingRecipe recipe) {
            buffer.writeFluidStack(recipe.getIngredientFluid());
            recipe.getInputIngredient().write(buffer);
            buffer.writeShort(recipe.getInputIngredientCount());
            buffer.writeFluidStack(recipe.getOutputFluid());
            buffer.writeInt(recipe.getProcessTime());
        }
    }
}
