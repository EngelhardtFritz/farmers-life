package com.asheriit.asheriitsfarmerslife.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Animation helper class to count the ticks since the client entered a world.
 */
public class AnimationTimingHelper {
    private static long elapsedTime = 0;

    public static long getElapsedTime() {
        return elapsedTime;
    }

    @SubscribeEvent
    public static void clientTickEnd(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!Minecraft.getInstance().isGamePaused()) {
                elapsedTime++;
            }
        }
    }
}
