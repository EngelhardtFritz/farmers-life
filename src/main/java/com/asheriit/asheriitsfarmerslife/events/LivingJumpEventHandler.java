package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LivingJumpEventHandler {
    @SubscribeEvent
    public static void onLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
        Entity entity = event.getEntityLiving();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            if (player.onGround) {
                if (!player.isPotionActive(ModEffects.GRAVITY_STAGE_1.get()) &&
                        !player.isPotionActive(ModEffects.GRAVITY_STAGE_2.get()) &&
                        !player.isPotionActive(ModEffects.GRAVITY_STAGE_3.get())) {
                    return;
                }

                if (player.isPotionActive(ModEffects.GRAVITY_STAGE_3.get())) {
                    player.addVelocity(0, 0.32F, 0);
                } else if (player.isPotionActive(ModEffects.GRAVITY_STAGE_2.get())) {
                    player.addVelocity(0, 0.22F, 0);
                } else if (player.isPotionActive(ModEffects.GRAVITY_STAGE_1.get())) {
                    player.addVelocity(0, 0.13F, 0);
                }
            }
        }
    }
}
