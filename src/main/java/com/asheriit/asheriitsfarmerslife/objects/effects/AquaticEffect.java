package com.asheriit.asheriitsfarmerslife.objects.effects;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AquaticEffect extends Effect {
    private final float fogDensity;

    public AquaticEffect(float fogDensity, EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
        this.fogDensity = fogDensity;
    }

    public float getFogDensity() {
        return fogDensity;
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        Entity entity = Minecraft.getInstance().getRenderViewEntity();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            BlockPos eyes = new BlockPos(player.getPosX(), player.getPosYEye(), player.getPosZ());
            World world = player.getEntityWorld();
            IFluidState fluidState = world.getFluidState(eyes);
            if (fluidState.isTagged(FluidTags.WATER) && !fluidState.isTagged(ModTags.Fluids.MUST) && !fluidState.isTagged(ModTags.Fluids.FERMENTED_MUST) &&
                    !fluidState.isTagged(ModTags.Fluids.FILTERED_FERMENTED_MUST) && !fluidState.isTagged(ModTags.Fluids.WINE)) {
                this.applyAttributesModifiersToEntity(player, player.getAttributes(), 0);
                return true;
            } else {
                this.removeAttributesModifiersFromEntity(player, player.getAttributes(), 0);
            }
        }
        return super.isReady(duration, amplifier);
    }

    @Override
    public AquaticEffect addAttributesModifier(IAttribute attributeIn, String uuid, double amount, AttributeModifier.Operation operation) {
        return (AquaticEffect) super.addAttributesModifier(attributeIn, uuid, amount, operation);
    }

    @Override
    public void applyAttributesModifiersToEntity(LivingEntity entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
        if (entityLivingBaseIn.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(this.getAttributeModifierMap().get(SharedMonsterAttributes.MOVEMENT_SPEED))) {
            return;
        }
        super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
    }
}
