package com.asheriit.asheriitsfarmerslife.init;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.loot_modifiers.LootMultiplierEffectModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModGlobalLootModifiers {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLOBAL_LOOT_MODIFIER_SERIALIZER_TYPES = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, FarmersLife.MOD_ID);

    public static final RegistryObject<GlobalLootModifierSerializer<LootMultiplierEffectModifier>> LOOT_MULTIPLIER_EFFECT_SERIALIZER = GLOBAL_LOOT_MODIFIER_SERIALIZER_TYPES.register("loot_multiplier_effect_serializer", LootMultiplierEffectModifier.Serializer::new);
}
