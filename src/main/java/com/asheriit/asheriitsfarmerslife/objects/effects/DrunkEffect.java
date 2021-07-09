package com.asheriit.asheriitsfarmerslife.objects.effects;

import com.asheriit.asheriitsfarmerslife.init.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

import javax.annotation.Nullable;

public class DrunkEffect extends Effect {
    public DrunkEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        // Well you drank too much, what did you expect?
        if (this == ModEffects.DRUNK_STAGE_1.get()) {
            if (entityLivingBaseIn.getHealth() > 1.0F) {
                entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, 1.0F);
            }
        } else if (this == ModEffects.DRUNK_STAGE_2.get()) {
            if (entityLivingBaseIn.getHealth() > 1.0F && entityLivingBaseIn instanceof PlayerEntity) {
                entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, 1.0F);
                ((PlayerEntity)entityLivingBaseIn).addExhaustion(0.002F * (float)(amplifier + 1));
            }
        } else if (this == ModEffects.DRUNK_STAGE_3.get()) {
            if (entityLivingBaseIn.getHealth() > 1.0F && entityLivingBaseIn instanceof PlayerEntity) {
                entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, 1.5F);
                ((PlayerEntity)entityLivingBaseIn).addExhaustion(0.005F * (float)(amplifier + 1));
            }
        }
    }

    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {
        // Do nothing
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        if (this == ModEffects.DRUNK_STAGE_1.get() || this == ModEffects.DRUNK_STAGE_2.get() || this == ModEffects.DRUNK_STAGE_3.get()) {
            int j = 50 >> amplifier;
            if (j > 0) {
                return duration % j == 0;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInstant() {
        return false;
    }
}
