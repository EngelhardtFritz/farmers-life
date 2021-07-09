package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModEffects;
import com.asheriit.asheriitsfarmerslife.objects.effects.KillingSpreeEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LivingExperienceDropEventHandler {
    @SubscribeEvent
    public static void onLivingExperienceDropEvent(LivingExperienceDropEvent event) {
        PlayerEntity player = event.getAttackingPlayer();
        if (player != null && (player.isPotionActive(ModEffects.KILLING_SPREE_STAGE_1.get()) ||
                player.isPotionActive(ModEffects.KILLING_SPREE_STAGE_2.get()) || player.isPotionActive(ModEffects.KILLING_SPREE_STAGE_3.get()))) {

            List<KillingSpreeEffect> activeEffects = new ArrayList<>();
            if (player.isPotionActive(ModEffects.KILLING_SPREE_STAGE_3.get())) {
                activeEffects.add(ModEffects.KILLING_SPREE_STAGE_3.get());
            }
            if (player.isPotionActive(ModEffects.KILLING_SPREE_STAGE_2.get())) {
                activeEffects.add(ModEffects.KILLING_SPREE_STAGE_2.get());
            }
            if (player.isPotionActive(ModEffects.KILLING_SPREE_STAGE_1.get())) {
                activeEffects.add(ModEffects.KILLING_SPREE_STAGE_1.get());
            }

            int multiplier = 1;
            for (int i = 0; i < activeEffects.size(); i++) {
                KillingSpreeEffect effect = activeEffects.get(i);
                if (i > 0) {
                    multiplier += (int) (effect.getMultiplier() * 0.5F);
                } else {
                    multiplier = effect.getMultiplier();
                }
            }

            int experienceToDrop = event.getDroppedExperience();
            event.setDroppedExperience(experienceToDrop * multiplier);
        }
    }
}
