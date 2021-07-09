package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ColdValueEventHandler {
    @SubscribeEvent
    public static void onColdValueEvent(ColdValueEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();

        if (item == Items.ICE) {
            event.setColdValue(150);
        } else if (item == Items.BLUE_ICE) {
            event.setColdValue(1400);
        } else if (item == Items.PACKED_ICE) {
            event.setColdValue(14000);
        } else if (item == Items.SNOWBALL) {
            event.setColdValue(50);
        }  else if (item == Items.SNOW) {
            event.setColdValue(120);
        } else if (item == Items.SNOW_BLOCK) {
            event.setColdValue(300);
        }
    }
}
