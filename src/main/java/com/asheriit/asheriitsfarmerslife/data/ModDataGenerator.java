package com.asheriit.asheriitsfarmerslife.data;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.data.provider.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {

    @SubscribeEvent
    public static void data(GatherDataEvent event) {
        final DataGenerator generator = event.getGenerator();

        if (event.includeServer()) {
            generator.addProvider(new ModBlockTagsProvider(generator));
            generator.addProvider(new ModItemTagsProvider(generator));
            generator.addProvider(new ModFluidTagProvider(generator));

            generator.addProvider(new ModAdvancementProvider(generator));
            generator.addProvider(new ModRecipeProvider(generator));
            generator.addProvider(new ModCookingRecipeProvider(generator));
            generator.addProvider(new ModConditionalRecipeProvider(generator));

            generator.addProvider(new ModLootTableProvider(generator));
        }

    }

}
