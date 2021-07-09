package com.asheriit.asheriitsfarmerslife.utils;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.FormattingHelper;
import com.asheriit.asheriitsfarmerslife.init.ModEffects;
import com.asheriit.asheriitsfarmerslife.objects.items.AlcoholicItem;
import com.google.common.collect.Lists;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlcoholicPotionHelper {
    /**
     * Get all effect instances for the given AlcoholicItem
     * @param stack: ItemStack to check effects for
     * @return List of found EffectInstances
     */
    public static List<EffectInstance> hasEffects(ItemStack stack) {
        List<EffectInstance> list = Lists.newArrayList();
        if (stack.getItem() instanceof AlcoholicItem) {
            AlcoholicItem potion = (AlcoholicItem) stack.getItem();
            if (potion.isFood() && !potion.getFood().getEffects().isEmpty()) {
                for (Pair<EffectInstance, Float> pair : potion.getFood().getEffects()) {
                    if (!list.contains(pair.getKey())) {
                        list.add(pair.getKey());
                    }
                }
            }
        }
        return list;
    }

    /**
     * Contrary to the {@link PotionUtils addPotionTooltip} method this one expects the list of effect instances instead of the item
     * @param effects: List of effects on the item
     * @param tooltip: Current list of tooltips
     * @param durationFactor: Duration factor
     * @param probabilities: Probabilities of activating the effects
     */
    public static void addPotionTooltip(List<EffectInstance> effects, List<ITextComponent> tooltip, float durationFactor, List<Integer> probabilities) {
        List<Tuple<String, AttributeModifier>> list1 = Lists.newArrayList();
        if (effects.isEmpty()) {
            tooltip.add((new TranslationTextComponent("effect.none")).applyTextStyle(TextFormatting.GRAY));
        } else {
            for(EffectInstance effectinstance : effects) {
                ITextComponent itextcomponent = new TranslationTextComponent(effectinstance.getEffectName());
                Effect effect = effectinstance.getPotion();
                Map<IAttribute, AttributeModifier> map = effect.getAttributeModifierMap();
                if (!map.isEmpty()) {
                    for(Map.Entry<IAttribute, AttributeModifier> entry : map.entrySet()) {
                        AttributeModifier attributemodifier = entry.getValue();
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), effect.getAttributeModifierAmount(effectinstance.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                        list1.add(new Tuple<>(entry.getKey().getName(), attributemodifier1));
                    }
                }

                if (effectinstance.getAmplifier() > 0) {
                    itextcomponent.appendText(" ").appendSibling(new TranslationTextComponent("potion.potency." + effectinstance.getAmplifier()));
                }

                if (effectinstance.getDuration() > 20) {
                    itextcomponent.appendText(" (").appendText(EffectUtils.getPotionDurationString(effectinstance, durationFactor)).appendText(")");
                }

                tooltip.add(itextcomponent.applyTextStyle(effect.getEffectType().getColor()));
            }
        }

        if (!list1.isEmpty()) {
            tooltip.add(new StringTextComponent(""));
            tooltip.add((new TranslationTextComponent("potion.whenDrank")).appendText(" (" + probabilities.get(0) + "%)").applyTextStyle(TextFormatting.DARK_PURPLE));

            for(Tuple<String, AttributeModifier> tuple : list1) {
                AttributeModifier attributemodifier2 = tuple.getB();
                double d0 = attributemodifier2.getAmount();
                double d1;
                if (attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    d1 = attributemodifier2.getAmount();
                } else {
                    d1 = attributemodifier2.getAmount() * 100.0D;
                }

                if (d0 > 0.0D) {
                    tooltip.add((new TranslationTextComponent("attribute.modifier.plus." + attributemodifier2.getOperation().getId(), ItemStack.DECIMALFORMAT.format(d1), new TranslationTextComponent("attribute.name." + (String)tuple.getA()))).applyTextStyle(TextFormatting.BLUE));
                } else if (d0 < 0.0D) {
                    d1 = d1 * -1.0D;
                    tooltip.add((new TranslationTextComponent("attribute.modifier.take." + attributemodifier2.getOperation().getId(), ItemStack.DECIMALFORMAT.format(d1), new TranslationTextComponent("attribute.name." + (String)tuple.getA()))).applyTextStyle(TextFormatting.RED));
                }
            }
        }
    }

    /**
     * Add additional information about AlcoholicItem Effects
     * Displays the probability of the EffectInstance taking effect
     *
     * @param tooltip: List of tooltip entries
     * @param effectInstances: List of EffectInstances present on the item
     * @param alcoholLevel: Player alcohol level
     * @param probabilities: List of Integer values for probability of each EffectInstance taking effect
     */
    public static void addWineBottleSpecialInformation(List<ITextComponent> tooltip, List<EffectInstance> effectInstances, int alcoholLevel, List<Integer> probabilities) {
        for (int i = 0; i < effectInstances.size(); i++) {
            EffectInstance effectInstance = effectInstances.get(i);
            int probabilityInPercent = probabilities.get(i);
            TranslationTextComponent effectInfoTranslation = new TranslationTextComponent("potion." + FarmersLife.MOD_ID + "." + effectInstance.getPotion().getRegistryName().getPath());
            TranslationTextComponent drunkInformation = new TranslationTextComponent("potion." + FarmersLife.MOD_ID + ".alcoholic_item.whenDrank");
            if (effectsForInformation.contains(effectInstance.getPotion())) {
                addDefaultTooltip(tooltip, probabilityInPercent);
                tooltip.add(effectInfoTranslation.applyTextStyle(TextFormatting.BLUE));
            } else if (effectsForAdditionalInformation.contains(effectInstance.getPotion())) {
                tooltip.add(effectInfoTranslation.applyTextStyle(TextFormatting.BLUE));
            }
            tooltip.add(new StringTextComponent(FormattingHelper.formatInt(alcoholLevel)).appendText(drunkInformation.getFormattedText()).applyTextStyle(TextFormatting.RED));
        }
    }

    /**
     * Add Generic tooltip information for when the potion/AlcoholicItem is used
     * @param tooltip: List of tooltips
     * @param probabilityInPercent: Probability of Potion being successful
     */
    private static void addDefaultTooltip(List<ITextComponent> tooltip, int probabilityInPercent) {
        tooltip.add(new StringTextComponent(""));
        tooltip.add((new TranslationTextComponent("potion.whenDrank")).appendText(" (" + probabilityInPercent + "%)").applyTextStyle(TextFormatting.DARK_PURPLE));
    }

    public static final List<Effect> effectsForInformation = new ArrayList<>();
    public static final List<Effect> effectsForAdditionalInformation = new ArrayList<>();

    static {
        effectsForInformation.add(ModEffects.ANTITOXIN_STAGE_1.get());
        effectsForInformation.add(ModEffects.ANTITOXIN_STAGE_2.get());
        effectsForInformation.add(ModEffects.ANTITOXIN_STAGE_3.get());
        effectsForInformation.add(ModEffects.SHOW_ORES_STAGE_1.get());
        effectsForInformation.add(ModEffects.SHOW_ORES_STAGE_2.get());
        effectsForInformation.add(ModEffects.SHOW_ORES_STAGE_3.get());
        effectsForInformation.add(ModEffects.LOOT_MULTIPLIER_STAGE_1.get());
        effectsForInformation.add(ModEffects.LOOT_MULTIPLIER_STAGE_2.get());
        effectsForInformation.add(ModEffects.LOOT_MULTIPLIER_STAGE_3.get());
        effectsForInformation.add(ModEffects.KILLING_SPREE_STAGE_1.get());
        effectsForInformation.add(ModEffects.KILLING_SPREE_STAGE_2.get());
        effectsForInformation.add(ModEffects.KILLING_SPREE_STAGE_3.get());

        effectsForAdditionalInformation.add(ModEffects.AQUATIC_STAGE_1.get());
        effectsForAdditionalInformation.add(ModEffects.AQUATIC_STAGE_2.get());
        effectsForAdditionalInformation.add(ModEffects.AQUATIC_STAGE_3.get());
    }
}
