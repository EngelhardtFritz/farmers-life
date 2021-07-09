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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TemperatureChamberRecipe implements IRecipe<RecipeWrapper> {
    public static final TemperatureChamberRecipeSerializer TEMPERATURE_CHAMBER_RECIPE_SERIALIZER = new TemperatureChamberRecipeSerializer();
    public static final ResourceLocation BOILING_ID = new ResourceLocation(FarmersLife.MOD_ID, "temperature_chamber_recipe");

    private final ResourceLocation recipeId;
    private final Ingredient inputIngredient;
    private final int inputIngredientCount;
    private final FluidStack inputFluidStack;
    private final ItemStack outputItemStack;
    private final ItemStack outputItemStackHot;
    private final ItemStack outputItemStackCold;
    private final float minTemp;
    private final float maxTemp;
    private final float requiredPercentage;
    private final int processTime;

    public TemperatureChamberRecipe(ResourceLocation recipeId, Ingredient inputIngredient, int inputIngredientCount, FluidStack inputFluidStack, ItemStack outputItemStack, ItemStack outputItemStackHot, ItemStack outputItemStackCold, float minTemp, float maxTemp, float requiredPercentage, int processTime) {
        this.recipeId = recipeId;
        this.inputIngredient = inputIngredient;
        this.inputIngredientCount = inputIngredientCount;
        this.inputFluidStack = inputFluidStack;
        this.outputItemStack = outputItemStack;
        this.outputItemStackHot = outputItemStackHot;
        this.outputItemStackCold = outputItemStackCold;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.requiredPercentage = requiredPercentage;
        this.processTime = processTime;
    }

    // Do not use for this recipe
    @Override
    public boolean matches(RecipeWrapper inv, World worldIn) {
        return false;
    }

    public boolean matches(@Nonnull ItemStack itemStackIn, @Nonnull FluidStack fluidStackIn) {
        return !itemStackIn.isEmpty() && this.inputIngredient.test(itemStackIn) && itemStackIn.getCount() >= this.inputIngredientCount &&
                !fluidStackIn.isEmpty() && fluidStackIn.isFluidEqual(this.inputFluidStack) && fluidStackIn.getAmount() >= this.inputFluidStack.getAmount();
    }

    // Do not use!
    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv) {
        return this.getRecipeOutput().copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }


    /**
     * If used as output make sure to call ItemStack#copy() to receive a copy of the ItemStack
     *
     * @return Original ItemStack
     */
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
        return ModRecipeSerializer.TEMPERATURE_CHAMBER_RECIPE_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipeSerializer.TEMPERATURE_CHAMBER_RECIPE_TYPE;
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

    /**
     * If used as output make sure to call ItemStack#copy() to receive a copy of the ItemStack
     *
     * @return Original ItemStack
     */
    public ItemStack getOutputItemStackHot() {
        return outputItemStackHot;
    }

    /**
     * If used as output make sure to call ItemStack#copy() to receive a copy of the ItemStack
     *
     * @return Original ItemStack
     */
    public ItemStack getOutputItemStackCold() {
        return outputItemStackCold;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public int getProcessTime() {
        return processTime;
    }

    public float getRequiredPercentage() {
        return requiredPercentage;
    }

    @Override
    public String toString() {
        return "TemperatureChamberRecipe{" +
                "recipeId=" + recipeId +
                ", inputIngredient=" + inputIngredient +
                ", inputIngredientCount=" + inputIngredientCount +
                ", inputFluidStack=" + inputFluidStack +
                ", outputItemStack=" + outputItemStack +
                ", outputItemStackHot=" + outputItemStackHot +
                ", outputItemStackCold=" + outputItemStackCold +
                ", minTemp=" + minTemp +
                ", maxTemp=" + maxTemp +
                ", requiredPercentage=" + requiredPercentage +
                ", processTime=" + processTime +
                '}';
    }

    public static class TemperatureChamberRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<TemperatureChamberRecipe> {
        public static List<Fluid> fluidInputList = new ArrayList<>();
        public static List<Item> itemInputList = new ArrayList<>();

        @Override
        public TemperatureChamberRecipe read(ResourceLocation recipeId, JsonObject json) {
            // Get input ingredient with count
            JsonObject inputIngredientObject = json.getAsJsonObject("inputIngredient");
            if (inputIngredientObject == null) {
                throw new IllegalArgumentException("[TemperatureChamberRecipeSerializer::read] Expected ingredient json object but got: " + inputIngredientObject.toString());
            }
            Ingredient inputIngredient = CraftingHelper.getIngredient(inputIngredientObject);
            int inputIngredientCount = JSONUtils.getInt(inputIngredientObject, "count");

            for (ItemStack stack : inputIngredient.getMatchingStacks()) {
                if (!itemInputList.contains(stack.getItem())) {
                    itemInputList.add(stack.getItem());
                }
            }

            // Get input fluid stack
            JsonObject ingredientFluidObject = json.getAsJsonObject("ingredientFluid");
            if (ingredientFluidObject == null) {
                throw new IllegalArgumentException("[TemperatureChamberRecipeSerializer::read] Expected fluid json object but got: " + ingredientFluidObject.toString());
            }
            ResourceLocation inputFluidLocation = ResourceLocation.create(JSONUtils.getString(ingredientFluidObject, "fluid", "minecraft:empty"), ':');
            Fluid ingredientFluid = ForgeRegistries.FLUIDS.getValue(inputFluidLocation);
            if (ingredientFluid == null) {
                throw new IllegalArgumentException("[TemperatureChamberRecipeSerializer::read] Expected resource location of a fluid but got: null");
            }
            int inputFluidAmount = JSONUtils.getInt(ingredientFluidObject, "amount");
            FluidStack inputFluidStack = new FluidStack(ingredientFluid, inputFluidAmount);

            if (!fluidInputList.contains(ingredientFluid)) {
                fluidInputList.add(ingredientFluid);
            }

            // Get output fluid stack
            ItemStack outputItemStack = CraftingHelper.getItemStack(json.get("result").getAsJsonObject(), false);
            ItemStack outputItemStackHot = CraftingHelper.getItemStack(json.get("resultHot").getAsJsonObject(), false);
            ItemStack outputItemStackCold = CraftingHelper.getItemStack(json.get("resultCold").getAsJsonObject(), false);

            JsonObject tempObject = json.getAsJsonObject("temperaturesInKelvin");
            if (tempObject == null) {
                throw new IllegalArgumentException("[TemperatureChamberRecipeSerializer::read] Expected temperatures_included object but got: null");
            }

            // Get temperatures
            float minTemp = JSONUtils.getFloat(tempObject, "min");
            float maxTemp = JSONUtils.getFloat(tempObject, "max");
            float requiredPercentage = JSONUtils.getFloat(tempObject, "requiredPercentageInRange");
            // Get process time
            int processTime = JSONUtils.getInt(json, "processTime");

            return new TemperatureChamberRecipe(recipeId, inputIngredient, inputIngredientCount, inputFluidStack, outputItemStack, outputItemStackHot, outputItemStackCold, minTemp, maxTemp, requiredPercentage, processTime);
        }

        @Nullable
        @Override
        public TemperatureChamberRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient inputIngredient = Ingredient.read(buffer);
            int inputIngredientCount = buffer.readVarInt();
            FluidStack inputFluidStack = buffer.readFluidStack();
            ItemStack outputItemStack = buffer.readItemStack();
            ItemStack outputItemStackHot = buffer.readItemStack();
            ItemStack outputItemStackCold = buffer.readItemStack();
            float minTemp = buffer.readFloat();
            float maxTemp = buffer.readFloat();
            float requiredPercentage = buffer.readFloat();
            int processTime = buffer.readVarInt();
            return new TemperatureChamberRecipe(recipeId, inputIngredient, inputIngredientCount, inputFluidStack, outputItemStack, outputItemStackHot, outputItemStackCold, minTemp, maxTemp, requiredPercentage, processTime);
        }

        @Override
        public void write(PacketBuffer buffer, TemperatureChamberRecipe recipe) {
            recipe.getInputIngredient().write(buffer);
            buffer.writeVarInt(recipe.getInputIngredientCount());
            buffer.writeFluidStack(recipe.getInputFluidStack());
            buffer.writeItemStack(recipe.getRecipeOutput());
            buffer.writeItemStack(recipe.getOutputItemStackHot());
            buffer.writeItemStack(recipe.getOutputItemStackCold());
            buffer.writeFloat(recipe.getMinTemp());
            buffer.writeFloat(recipe.getMaxTemp());
            buffer.writeFloat(recipe.getRequiredPercentage());
            buffer.writeVarInt(recipe.getProcessTime());
        }
    }
}
