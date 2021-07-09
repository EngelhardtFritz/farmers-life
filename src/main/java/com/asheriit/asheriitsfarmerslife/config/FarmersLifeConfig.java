package com.asheriit.asheriitsfarmerslife.config;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FarmersLifeConfig {

    public static class Common {
        public final ForgeConfigSpec.BooleanValue enableVanillaAdditionsRecipes;
        public final ForgeConfigSpec.BooleanValue enableDamBlockRecipes;
//        public final ForgeConfigSpec.IntValue filtrationTankCapacity;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Farmers Life Mod Configurations")
                    .comment("The values of this file can be changed to any of the defined values.")
                    .push("FarmersLife");
            builder.push("Recipe Configuration");

            enableVanillaAdditionsRecipes = builder
                    .comment("Enable/Disable all recipes which add recipes to vanilla items (wheat seeds). [default=true]")
                    .worldRestart()
                    .define("enable_vanilla_additions", true);
            enableDamBlockRecipes = builder
                    .comment("Enable/Disable all dam block recipes. [default=true]")
                    .worldRestart()
                    .define("enable_dam_blocks", true);
//            filtrationTankCapacity = builder
//                    .comment("Set the input tank size of the FiltrationMachine block. [default=4000]")
//                    .worldRestart()
//                    .defineInRange("filtrationTankCapacity", 4000, 1000, 20000);

            builder.pop(2);
        }
    }

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading event) {

    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.Reloading event) {

    }
}
