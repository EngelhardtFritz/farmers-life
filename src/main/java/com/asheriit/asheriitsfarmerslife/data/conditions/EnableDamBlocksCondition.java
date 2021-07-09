package com.asheriit.asheriitsfarmerslife.data.conditions;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.config.FarmersLifeConfig;
import com.google.gson.JsonObject;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class EnableDamBlocksCondition implements ICondition {
    private static final ResourceLocation NAME = new ResourceLocation(FarmersLife.MOD_ID, "load_dam_block_recipes");
    private final boolean loadRecipe;

    public EnableDamBlocksCondition() {
        this.loadRecipe = this.test();
    }

    public EnableDamBlocksCondition(boolean loadRecipe) {
        this.loadRecipe = loadRecipe;
    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test() {
        return FarmersLifeConfig.COMMON.enableDamBlockRecipes.get();
    }

    @Override
    public String toString() {
        return "EnableDamBlocksCondition{" +
                "load_recipe=" + loadRecipe +
                '}';
    }

    public static class Serializer implements IConditionSerializer<EnableDamBlocksCondition> {
        public static final EnableDamBlocksCondition.Serializer INSTANCE = new EnableDamBlocksCondition.Serializer();

        @Override
        public void write(JsonObject json, EnableDamBlocksCondition value) {
            json.addProperty("load_recipe", value.loadRecipe);
        }

        @Override
        public EnableDamBlocksCondition read(JsonObject json) {
            return new EnableDamBlocksCondition(JSONUtils.getBoolean(json, "load_recipe"));
        }

        @Override
        public ResourceLocation getID() {
            return EnableDamBlocksCondition.NAME;
        }
    }
}
