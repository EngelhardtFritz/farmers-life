package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TextureStitchEventHandler {
    public static ResourceLocation ENERGY_LOC = new ResourceLocation(FarmersLife.MOD_ID, "gui/container/energy_tank");

    @SubscribeEvent
    public static void stitchEvent(TextureStitchEvent.Pre event) {
        if (event.getMap().getTextureLocation().equals(PlayerContainer.LOCATION_BLOCKS_TEXTURE)) {
            event.addSprite(ENERGY_LOC);
        }
    }
}
