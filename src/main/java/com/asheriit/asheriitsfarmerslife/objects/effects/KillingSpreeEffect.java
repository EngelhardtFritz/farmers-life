package com.asheriit.asheriitsfarmerslife.objects.effects;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class KillingSpreeEffect extends Effect {
    private final int multiplier;

    public KillingSpreeEffect(int multiplier, EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
