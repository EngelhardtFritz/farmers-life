package com.asheriit.asheriitsfarmerslife.init;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.objects.effects.*;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

public class ModEffects {
    public static final DeferredRegister<Effect> EFFECT_TYPES = DeferredRegister.create(ForgeRegistries.POTIONS, FarmersLife.MOD_ID);

    public static final RegistryObject<Effect> DRUNK_STAGE_1 = EFFECT_TYPES.register("drunk_stage_1_effect",
            () -> new DrunkEffect(EffectType.HARMFUL, 0xFF8ba43e));

    public static final RegistryObject<Effect> DRUNK_STAGE_2 = EFFECT_TYPES.register("drunk_stage_2_effect",
            () -> new DrunkEffect(EffectType.HARMFUL, 0xFF6d8030));

    public static final RegistryObject<Effect> DRUNK_STAGE_3 = EFFECT_TYPES.register("drunk_stage_3_effect",
            () -> new DrunkEffect(EffectType.HARMFUL, 0xFF414d1d));

    public static final RegistryObject<Effect> ANTITOXIN_STAGE_1 = EFFECT_TYPES.register("antitoxin_stage_1_effect",
            () -> new AntitoxinEffect(EffectType.BENEFICIAL, 0xFFbd59c4));

    public static final RegistryObject<Effect> ANTITOXIN_STAGE_2 = EFFECT_TYPES.register("antitoxin_stage_2_effect",
            () -> new AntitoxinEffect(EffectType.BENEFICIAL, 0xFF7d3b81));

    public static final RegistryObject<Effect> ANTITOXIN_STAGE_3 = EFFECT_TYPES.register("antitoxin_stage_3_effect",
            () -> new AntitoxinEffect(EffectType.BENEFICIAL, 0xFF4c244e));

    public static final RegistryObject<ShowOreOutlineEffect> SHOW_ORES_STAGE_1 = EFFECT_TYPES.register("show_ores_stage_1",
            () -> new ShowOreOutlineEffect(Tags.Blocks.ORES, EffectType.BENEFICIAL, 0xFF9a8b25));

    public static final RegistryObject<ShowOreOutlineEffect> SHOW_ORES_STAGE_2 = EFFECT_TYPES.register("show_ores_stage_2",
            () -> new ShowOreOutlineEffect(Tags.Blocks.ORES, EffectType.BENEFICIAL, 0xFFb4a22b));

    public static final RegistryObject<ShowOreOutlineEffect> SHOW_ORES_STAGE_3 = EFFECT_TYPES.register("show_ores_stage_3",
            () -> new ShowOreOutlineEffect(Tags.Blocks.ORES, EffectType.BENEFICIAL, 0xFFd2bd32));

    public static final RegistryObject<Effect> LOOT_MULTIPLIER_STAGE_1 = EFFECT_TYPES.register("loot_multiplier_stage_1",
            () -> new GenericEffect(EffectType.BENEFICIAL, 0xFFdc9613));

    public static final RegistryObject<Effect> LOOT_MULTIPLIER_STAGE_2 = EFFECT_TYPES.register("loot_multiplier_stage_2",
            () -> new GenericEffect(EffectType.BENEFICIAL, 0xFFe9b115));

    public static final RegistryObject<Effect> LOOT_MULTIPLIER_STAGE_3 = EFFECT_TYPES.register("loot_multiplier_stage_3",
            () -> new GenericEffect(EffectType.BENEFICIAL, 0xFFfdf55f));

    public static final RegistryObject<Effect> GRAVITY_STAGE_1 = EFFECT_TYPES.register("gravity_stage_1",
            () -> (new GenericEffect(EffectType.BENEFICIAL, 0xFF91c7ff))
                    .addAttributesModifier(PlayerEntity.ENTITY_GRAVITY, UUID.nameUUIDFromBytes((FarmersLife.MOD_ID + "_slow_falling").getBytes()).toString(), -0.02D, AttributeModifier.Operation.ADDITION)
                    .addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, UUID.nameUUIDFromBytes((FarmersLife.MOD_ID + "_move_stage_1").getBytes()).toString(), (double)0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<Effect> GRAVITY_STAGE_2 = EFFECT_TYPES.register("gravity_stage_2",
            () -> (new GenericEffect(EffectType.BENEFICIAL, 0xFF5099e5))
                    .addAttributesModifier(PlayerEntity.ENTITY_GRAVITY, UUID.nameUUIDFromBytes((FarmersLife.MOD_ID + "_slower_falling").getBytes()).toString(), -0.04D, AttributeModifier.Operation.ADDITION)
                    .addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, UUID.nameUUIDFromBytes((FarmersLife.MOD_ID + "_move_stage_2").getBytes()).toString(), (double)0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<Effect> GRAVITY_STAGE_3 = EFFECT_TYPES.register("gravity_stage_3",
            () -> (new GenericEffect(EffectType.BENEFICIAL, 0xFF1e73cc))
                    .addAttributesModifier(PlayerEntity.ENTITY_GRAVITY, UUID.nameUUIDFromBytes((FarmersLife.MOD_ID + "_slowest_falling").getBytes()).toString(), -0.03D, AttributeModifier.Operation.ADDITION)
                    .addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, UUID.nameUUIDFromBytes((FarmersLife.MOD_ID + "_move_stage_3").getBytes()).toString(), (double)0.3F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<KillingSpreeEffect> KILLING_SPREE_STAGE_1 = EFFECT_TYPES.register("killing_spree_stage_1",
            () -> new KillingSpreeEffect(2, EffectType.BENEFICIAL, 0xFFf5ff8f));

    public static final RegistryObject<KillingSpreeEffect> KILLING_SPREE_STAGE_2 = EFFECT_TYPES.register("killing_spree_stage_2",
            () -> new KillingSpreeEffect(4, EffectType.BENEFICIAL, 0xFFd8e45a));

    public static final RegistryObject<KillingSpreeEffect> KILLING_SPREE_STAGE_3 = EFFECT_TYPES.register("killing_spree_stage_3",
            () -> new KillingSpreeEffect(8, EffectType.BENEFICIAL, 0xFFc0ff95));

    public static final RegistryObject<AquaticEffect> AQUATIC_STAGE_1 = EFFECT_TYPES.register("aquatic_stage_1",
            () -> (new AquaticEffect(0.05F, EffectType.BENEFICIAL, 0xFF8abdff))
                    .addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, UUID.nameUUIDFromBytes((FarmersLife.MOD_ID + "aquatic_move_stage_1").getBytes()).toString(), (double)0.4F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<AquaticEffect> AQUATIC_STAGE_2 = EFFECT_TYPES.register("aquatic_stage_2",
            () -> (new AquaticEffect(0.03F, EffectType.BENEFICIAL, 0xFF7caae5))
                    .addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, UUID.nameUUIDFromBytes((FarmersLife.MOD_ID + "aquatic_move_stage_2").getBytes()).toString(), (double)0.8F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<AquaticEffect> AQUATIC_STAGE_3 = EFFECT_TYPES.register("aquatic_stage_3",
            () -> (new AquaticEffect(0.01F, EffectType.BENEFICIAL, 0xFF5a96e5))
                    .addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, UUID.nameUUIDFromBytes((FarmersLife.MOD_ID + "aquatic_move_stage_3").getBytes()).toString(), (double)1.2F, AttributeModifier.Operation.MULTIPLY_TOTAL));


    // Effect Ideas:
    /*
        - Make player immortal for short amount of time
        - Loved by animals effect -> make all animals walk towards the player       -> ABANDONED
        - Mixing of existing potions
        - Increase step height
        - Magnet effect for looting items
        - Molotov potion
        - Vodka -> throwable
        - Ice potion -> make ice from water -> throwable
     */
}
