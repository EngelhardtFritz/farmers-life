package com.asheriit.asheriitsfarmerslife.data.conditions;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.config.FarmersLifeConfig;
import com.google.gson.JsonObject;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class EnableVanillaAdditionsCondition implements ICondition {
    private static final ResourceLocation NAME = new ResourceLocation(FarmersLife.MOD_ID, "load_vanilla_additions_recipes");
    private final boolean loadRecipe;

    public EnableVanillaAdditionsCondition() {
        this.loadRecipe = this.test();
    }

    public EnableVanillaAdditionsCondition(boolean loadRecipe) {
        this.loadRecipe = loadRecipe;
    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test() {
        return FarmersLifeConfig.COMMON.enableVanillaAdditionsRecipes.get();
    }

    @Override
    public String toString() {
        return "AddVanillaAdditionsRecipesCondition{" +
                "load_recipe=" + loadRecipe +
                '}';
    }

    public static class Serializer implements IConditionSerializer<EnableVanillaAdditionsCondition> {
        public static final EnableVanillaAdditionsCondition.Serializer INSTANCE = new EnableVanillaAdditionsCondition.Serializer();

        @Override
        public void write(JsonObject json, EnableVanillaAdditionsCondition value) {
            json.addProperty("load_recipe", value.loadRecipe);
        }

        @Override
        public EnableVanillaAdditionsCondition read(JsonObject json) {
            return new EnableVanillaAdditionsCondition(JSONUtils.getBoolean(json, "load_recipe"));
        }

        @Override
        public ResourceLocation getID() {
            return EnableVanillaAdditionsCondition.NAME;
        }
    }
}
