package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LootTableLoadEventHandler {
    @SubscribeEvent
    public static void onLootTableLoadEvent(LootTableLoadEvent event) {
        ResourceLocation lootTableName = event.getName();
        // TODO: Add new animal waste loot to entities (animals, enemies)
    }
}
