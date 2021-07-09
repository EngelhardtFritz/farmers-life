package com.asheriit.asheriitsfarmerslife.init;

import com.asheriit.asheriitsfarmerslife.events.ColdValueEvent;
import com.asheriit.asheriitsfarmerslife.events.HeatValueEvent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;

public class FarmersLifeEventFactory {
    public static int getItemHeatValue(@Nonnull ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return 0;
        }

        HeatValueEvent event = new HeatValueEvent(itemStack, 0);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getHeatValue();
    }

    public static int getItemColdValue(@Nonnull ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return 0;
        }
        ColdValueEvent event = new ColdValueEvent(itemStack, 0);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getColdValue();
    }
}
