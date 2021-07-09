package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LivingFallEventHandler {
    @SubscribeEvent
    public static void onLivingFallEvent(LivingFallEvent event) {
        Entity entity = event.getEntityLiving();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            if (player.isPotionActive(ModEffects.GRAVITY_STAGE_1.get()) ||
                    player.isPotionActive(ModEffects.GRAVITY_STAGE_2.get()) ||
                    player.isPotionActive(ModEffects.GRAVITY_STAGE_3.get())) {
                event.setCanceled(true);
            }
        }
    }
}
