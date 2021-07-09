package com.asheriit.asheriitsfarmerslife.objects.effects;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AntitoxinEffect extends Effect {
    public AntitoxinEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
    }

    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {
        FarmersLife.LOGGER.info("Remove negative effect");
        if (entityLivingBaseIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityLivingBaseIn;
            Collection<EffectInstance> potionEffects = player.getActivePotionEffects();
            List<Effect> activeHarmfullEffects = new ArrayList<>();
            for (EffectInstance effectInstance : potionEffects) {
                if (effectInstance.getPotion().getEffectType() == EffectType.HARMFUL) {
                    activeHarmfullEffects.add(effectInstance.getPotion());
                }
            }
            FarmersLife.LOGGER.info("Harmful effects: " + activeHarmfullEffects.toString());
            if (activeHarmfullEffects.isEmpty()) {
                return;
            }

            int effectsToRemove = 0;
            if (this == ModEffects.ANTITOXIN_STAGE_1.get()) {
                effectsToRemove = 1;
            } else if (this == ModEffects.ANTITOXIN_STAGE_2.get()) {
                effectsToRemove = 2;
            } else if (this == ModEffects.ANTITOXIN_STAGE_3.get()) {
                effectsToRemove = activeHarmfullEffects.size();
            }

            if (!entityLivingBaseIn.world.isRemote) {
                for (Effect effect : activeHarmfullEffects) {
                    if (effectsToRemove <= 0) return;
                    player.removePotionEffect(effect);
                    effectsToRemove--;
                }
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean isInstant() {
        return true;
    }
}
