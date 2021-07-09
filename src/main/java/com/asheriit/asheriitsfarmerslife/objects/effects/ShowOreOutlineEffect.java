package com.asheriit.asheriitsfarmerslife.objects.effects;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.tags.Tag;

import javax.annotation.Nullable;

public class ShowOreOutlineEffect extends Effect {
    private final Tag<Block> blockTag;

    public ShowOreOutlineEffect(Tag<Block> blockTag, EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
        this.blockTag = blockTag;
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        // Do nothing
    }

    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {
        // Do nothing
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean isInstant() {
        return false;
    }

    public Tag<Block> getBlockTag() {
        return blockTag;
    }
}
