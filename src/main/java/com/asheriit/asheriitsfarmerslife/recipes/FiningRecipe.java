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

public class FiningRecipe implements IRecipe<RecipeWrapper> {
    public static final ResourceLocation FINING_ID = new ResourceLocation(FarmersLife.MOD_ID, "fining");
    public static final FiningRecipe.FiningRecipeSerializer FINING_RECIPE_SERIALIZER = new FiningRecipe.FiningRecipeSerializer();

    private final ResourceLocation recipeId;
    private final FluidStack ingredientFluid;
    private final Ingredient ingredientItem;
    private final int ingredientItemCount;
    private final FluidStack outputFluid;
    private final ItemStack secondaryOutputStack;
    private final int secondaryOutputProbability;
    private final int processTime;

    public FiningRecipe(ResourceLocation recipeId, FluidStack ingredientFluid, Ingredient ingredientItem,
                        int ingredientItemCount, FluidStack outputFluid, ItemStack secondaryOutputStack,
                        int secondaryOutputProbability, int processTime) {
        this.recipeId = recipeId;
        this.ingredientFluid = ingredientFluid;
        this.ingredientItem = ingredientItem;
        this.ingredientItemCount = ingredientItemCount;
        this.outputFluid = outputFluid;
        this.secondaryOutputStack = secondaryOutputStack;
        this.secondaryOutputProbability = secondaryOutputProbability;
        this.processTime = processTime;
    }

    public boolean matches(ItemStack itemStack, FluidStack fluidStack) {
        return this.ingredientItem.test(itemStack) && itemStack.getCount() >= this.ingredientItemCount &&
                fluidStack.isFluidEqual(this.ingredientFluid) && fluidStack.getAmount() >= this.ingredientFluid.getAmount();
    }

    // Do not use
    @Override
    public boolean matches(RecipeWrapper inv, World worldIn) {
        return false;
    }

    // Do not use
    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    // Do not use
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
        return ModRecipeSerializer.FINING_RECIPE_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipeSerializer.FINING_RECIPE_TYPE;
    }

    public FluidStack getInputFluid() {
        return ingredientFluid;
    }

    public Ingredient getIngredientItem() {
        return ingredientItem;
    }

    public int getInputIngredientCount() {
        return ingredientItemCount;
    }

    public FluidStack getOutputFluid() {
        return outputFluid;
    }

    public ItemStack getSecondaryOutputStack() {
        return secondaryOutputStack;
    }

    public int getSecondaryOutputProbability() {
        return secondaryOutputProbability;
    }

    public int getProcessTime() {
        return processTime;
    }

    @Override
    public String toString() {
        return "FiningRecipe{" +
                "recipeId=" + recipeId +
                ", ingredientFluid=" + ingredientFluid +
                ", ingredientItem=" + ingredientItem +
                ", ingredientItemCount=" + ingredientItemCount +
                ", outputFluid=" + outputFluid +
                ", secondaryOutputStack=" + secondaryOutputStack +
                ", secondaryOutputProbability=" + secondaryOutputProbability +
                ", processTime=" + processTime +
                '}';
    }

    public static class FiningRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<FiningRecipe> {
        public static List<Fluid> fluidInputList = new ArrayList<>();
        public static List<Item> itemInputList = new ArrayList<>();

        @Override
        public FiningRecipe read(ResourceLocation recipeId, JsonObject json) {
            // Get required fluid input
            JsonObject ingredientFluidObject = json.getAsJsonObject("ingredientFluid");
            ResourceLocation fluidLocation = ResourceLocation.create(JSONUtils.getString(ingredientFluidObject, "fluid", "minecraft:empty"), ':');
            int ingredientFluidAmount = JSONUtils.getInt(ingredientFluidObject, "amount");
            Fluid ingredientFluid = ForgeRegistries.FLUIDS.getValue(fluidLocation);
            if (ingredientFluid == null) {
                throw new IllegalArgumentException("[FiningRecipeSerializer::read] Expected resource location of ingredient fluid but got: " + fluidLocation);
            }
            FluidStack ingredientFluidStack = new FluidStack(ingredientFluid, ingredientFluidAmount);

            // Get ingredient item and count
            JsonObject inputIngredientItemObject = json.getAsJsonObject("ingredientItem");
            Ingredient inputIngredient = CraftingHelper.getIngredient(inputIngredientItemObject);
            int inputIngredientCount = JSONUtils.getInt(inputIngredientItemObject, "count");

            // Get possible recipe output fluid
            JsonObject outputFluidObject = json.getAsJsonObject("outputFluid");
            ResourceLocation outputFluidLocation = ResourceLocation.create(JSONUtils.getString(outputFluidObject, "fluid", "minecraft:empty"), ':');
            int outputFluidAmount = JSONUtils.getInt(ingredientFluidObject, "amount");
            Fluid outputFluid = ForgeRegistries.FLUIDS.getValue(outputFluidLocation);
            if (outputFluid == null) {
                throw new IllegalArgumentException("[FiningRecipeSerializer::read] Expected resource location of output fluid but got: " + outputFluidLocation);
            }
            FluidStack outputFluidStack = new FluidStack(outputFluid, outputFluidAmount);

            // Get output itemStack
            ItemStack secondaryOutputItemStack = CraftingHelper.getItemStack(json.getAsJsonObject("outputItemStack"), false);
            // Get output probability
            int secondaryOutputProbability = JSONUtils.getInt(json.getAsJsonObject("outputItemStack"), "probability");

            // Get processTime
            int processTime = JSONUtils.getInt(json, "processTime");

            if (!fluidInputList.contains(ingredientFluid)) {
                fluidInputList.add(ingredientFluid);
            }
            ItemStack[] ingredients = inputIngredient.getMatchingStacks();
            for (ItemStack stack : ingredients) {
                Item inputItem = stack.getItem();
                if (!itemInputList.contains(inputItem)) {
                    itemInputList.add(inputItem);
                }
            }

            return new FiningRecipe(recipeId, ingredientFluidStack, inputIngredient, inputIngredientCount, outputFluidStack, secondaryOutputItemStack, secondaryOutputProbability, processTime);
        }

        @Nullable
        @Override
        public FiningRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            FluidStack ingredientFluidStack = buffer.readFluidStack();
            Ingredient inputIngredient = Ingredient.read(buffer);
            int inputIngredientCount = buffer.readInt();
            FluidStack outputFluidStack = buffer.readFluidStack();
            ItemStack secondaryOutputItemStack = buffer.readItemStack();
            int secondaryOutputProbability = buffer.readInt();
            int processTime = buffer.readInt();
            return new FiningRecipe(recipeId, ingredientFluidStack, inputIngredient, inputIngredientCount, outputFluidStack, secondaryOutputItemStack, secondaryOutputProbability, processTime);
        }

        @Override
        public void write(PacketBuffer buffer, FiningRecipe recipe) {
            buffer.writeFluidStack(recipe.getInputFluid());
            recipe.getIngredientItem().write(buffer);
            buffer.writeInt(recipe.getInputIngredientCount());
            buffer.writeFluidStack(recipe.getOutputFluid());
            buffer.writeItemStack(recipe.getSecondaryOutputStack());
            buffer.writeInt(recipe.getSecondaryOutputProbability());
            buffer.writeInt(recipe.getProcessTime());
        }
    }
}
