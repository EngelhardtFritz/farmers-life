package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HeatValueEventHandler {
    @SubscribeEvent
    public static void onHeatValueEvent(HeatValueEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();

        if (item == Items.BLAZE_POWDER) {
            event.setHeatValue(300);
        } else if (item == Items.BLAZE_ROD) {
            event.setHeatValue(800);
        } else if (item == Items.MAGMA_CREAM) {
            event.setHeatValue(500);
        } else if (item == Items.MAGMA_BLOCK) {
            event.setHeatValue(2400);
        } else if (item == Items.LAVA_BUCKET) {
            event.setHeatValue(38000);
        } else if (item == Items.GLOWSTONE_DUST) {
            event.setHeatValue(50);
        } else if (item == Items.GLOWSTONE) {
            event.setHeatValue(500);
        }
    }
}
