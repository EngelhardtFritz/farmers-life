package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.data.conditions.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RecipeSerializerRegistryEventHandler {
    @SubscribeEvent
    public static void registerRecipeSerialziers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        CraftingHelper.register(EnableVanillaAdditionsCondition.Serializer.INSTANCE);
        CraftingHelper.register(EnableDamBlocksCondition.Serializer.INSTANCE);
    }
}
