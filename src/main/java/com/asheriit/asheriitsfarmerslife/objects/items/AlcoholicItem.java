package com.asheriit.asheriitsfarmerslife.objects.items;

import com.asheriit.asheriitsfarmerslife.capabilities.CapabilityDrunkenState;
import com.asheriit.asheriitsfarmerslife.capabilities.DrunkenState;
import com.asheriit.asheriitsfarmerslife.init.ModEffects;
import com.asheriit.asheriitsfarmerslife.init.ModItems;
import com.asheriit.asheriitsfarmerslife.properties.AlcoholicStrength;
import com.asheriit.asheriitsfarmerslife.utils.AlcoholicPotionHelper;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AlcoholicItem extends Item implements IItemColor {
    private final AlcoholicStrength alcoholicStrength;
    private final Pair<Supplier<EffectInstance>, Float> drunkEffect;
    private final int itemColor;

    public AlcoholicItem(int itemColor, AlcoholProperties alcoholProperties) {
        super(alcoholProperties.properties);
        this.itemColor = itemColor;
        this.alcoholicStrength = alcoholProperties.alcoholicStrength;
        this.drunkEffect = alcoholProperties.drunkEffect;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        PlayerEntity player = entityLiving instanceof PlayerEntity ? (PlayerEntity) entityLiving : null;

        if (player != null) {
            player.addStat(Stats.ITEM_USED.get(this));
            worldIn.playSound((PlayerEntity) null, player.getPosX(), player.getPosYEye(), player.getPosZ(), SoundEvents.ENTITY_WANDERING_TRADER_DRINK_POTION, SoundCategory.NEUTRAL, 1.0F, 1.0F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.4F);
            if (this.isFood()) {
                Item item = stack.getItem();
                if (item.isFood()) {
                    for (Pair<EffectInstance, Float> pair : item.getFood().getEffects()) {
                        if (!worldIn.isRemote && pair.getLeft() != null && worldIn.rand.nextFloat() < pair.getRight()) {
                            EffectInstance instance = pair.getKey();
                            if (instance.getPotion().isInstant()) {
                                instance.getPotion().affectEntity(player, player, entityLiving, instance.getAmplifier(), 1.0D);
                            } else {
                                handleReplacingEffects(player, pair.getKey());
                            }
                        }
                    }
                }

                if (!player.abilities.isCreativeMode) {
                    stack.shrink(1);
                }
            } else {
                if (!player.abilities.isCreativeMode) {
                    stack.shrink(1);
                }
            }

            DrunkenState drunkenState = player.getCapability(CapabilityDrunkenState.CAPABILITY_DRUNK).orElse(null);
            if (drunkenState != null) {
                final boolean shouldBeDrunk = (drunkenState.getAlcoholLevel() + this.alcoholicStrength.level) >= drunkenState.getMaxAlcoholLevel();
                drunkenState.increaseDrunkLevel(this.alcoholicStrength.level, player, shouldBeDrunk, this.drunkEffect);
            }
        }

        if (player == null || !player.abilities.isCreativeMode) {
            if (stack.isEmpty()) {
                return new ItemStack(ModItems.CORKED_GLASS_BOTTLE.get());
            }

            if (player != null) {
                player.inventory.addItemStackToInventory(new ItemStack(ModItems.CORKED_GLASS_BOTTLE.get()));
            }
        }
        return stack;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getUseDuration(ItemStack stack) {
        return 30;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.isFood()) {
            Item item = stack.getItem();
            List<EffectInstance> effects = new ArrayList<>();
            List<Integer> probabilities = new ArrayList<>();
            if (item.isFood()) {
                for (Pair<EffectInstance, Float> effect : item.getFood().getEffects()) {
                    EffectInstance instance = effect.getKey();
                    if (!effects.contains(instance)) {
                        effects.add(instance);
                        probabilities.add((int) (effect.getValue() * 100));
                    }
                }
                AlcoholicPotionHelper.addPotionTooltip(effects, tooltip, 1.0F, probabilities);
                AlcoholicPotionHelper.addWineBottleSpecialInformation(tooltip, effects, this.alcoholicStrength.level, probabilities);
            }
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return false;
//        return super.hasEffect(stack) || !AlcoholicPotionHelper.hasEffects(stack).isEmpty();
    }

    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        return this.itemColor;
    }

    public static class AlcoholProperties {
        private Properties properties = new Properties();
        private AlcoholicStrength alcoholicStrength = AlcoholicStrength.WEAK;
        private Pair<Supplier<EffectInstance>, Float> drunkEffect = null;

        public AlcoholProperties properties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public AlcoholProperties alcoholicStrength(AlcoholicStrength alcoholicStrength) {
            this.alcoholicStrength = alcoholicStrength;
            return this;
        }

        public AlcoholProperties drunkEffect(Supplier<EffectInstance> effectIn, float probability) {
            this.drunkEffect = Pair.of(effectIn, probability);
            return this;
        }
    }

    private static void handleReplacingEffects(PlayerEntity player, EffectInstance effectInstanceToAdd) {
        Effect effectToAdd = effectInstanceToAdd.getPotion();
        if (effectToAdd.equals(ModEffects.SHOW_ORES_STAGE_1.get())) {
            if (player.isPotionActive(ModEffects.SHOW_ORES_STAGE_2.get())) {
                player.removePotionEffect(ModEffects.SHOW_ORES_STAGE_2.get());
            } else if (player.isPotionActive(ModEffects.SHOW_ORES_STAGE_3.get())) {
                player.removePotionEffect(ModEffects.SHOW_ORES_STAGE_3.get());
            }
        } else if (effectToAdd.equals(ModEffects.SHOW_ORES_STAGE_2.get())) {
            if (player.isPotionActive(ModEffects.SHOW_ORES_STAGE_1.get())) {
                player.removePotionEffect(ModEffects.SHOW_ORES_STAGE_1.get());
            } else if (player.isPotionActive(ModEffects.SHOW_ORES_STAGE_3.get())) {
                player.removePotionEffect(ModEffects.SHOW_ORES_STAGE_3.get());
            }
        } else if (effectToAdd.equals(ModEffects.SHOW_ORES_STAGE_3.get())) {
            if (player.isPotionActive(ModEffects.SHOW_ORES_STAGE_1.get())) {
                player.removePotionEffect(ModEffects.SHOW_ORES_STAGE_1.get());
            } else if (player.isPotionActive(ModEffects.SHOW_ORES_STAGE_2.get())) {
                player.removePotionEffect(ModEffects.SHOW_ORES_STAGE_2.get());
            }
        } else if (effectToAdd.equals(ModEffects.GRAVITY_STAGE_1.get())) {
            if (player.isPotionActive(ModEffects.GRAVITY_STAGE_2.get())) {
                player.removePotionEffect(ModEffects.GRAVITY_STAGE_2.get());
            } else if (player.isPotionActive(ModEffects.GRAVITY_STAGE_3.get())) {
                player.removePotionEffect(ModEffects.GRAVITY_STAGE_3.get());
            }
        } else if (effectToAdd.equals(ModEffects.GRAVITY_STAGE_2.get())) {
            if (player.isPotionActive(ModEffects.GRAVITY_STAGE_1.get())) {
                player.removePotionEffect(ModEffects.GRAVITY_STAGE_1.get());
            } else if (player.isPotionActive(ModEffects.GRAVITY_STAGE_3.get())) {
                player.removePotionEffect(ModEffects.GRAVITY_STAGE_3.get());
            }
        } else if (effectToAdd.equals(ModEffects.GRAVITY_STAGE_3.get())) {
            if (player.isPotionActive(ModEffects.GRAVITY_STAGE_1.get())) {
                player.removePotionEffect(ModEffects.GRAVITY_STAGE_1.get());
            } else if (player.isPotionActive(ModEffects.GRAVITY_STAGE_2.get())) {
                player.removePotionEffect(ModEffects.GRAVITY_STAGE_2.get());
            }
        } else if (effectToAdd.equals(ModEffects.AQUATIC_STAGE_1.get())) {
            if (player.isPotionActive(ModEffects.AQUATIC_STAGE_2.get())) {
                player.removePotionEffect(ModEffects.AQUATIC_STAGE_2.get());
            } else if (player.isPotionActive(ModEffects.AQUATIC_STAGE_3.get())) {
                player.removePotionEffect(ModEffects.AQUATIC_STAGE_3.get());
            }
        } else if (effectToAdd.equals(ModEffects.AQUATIC_STAGE_2.get())) {
            if (player.isPotionActive(ModEffects.AQUATIC_STAGE_1.get())) {
                player.removePotionEffect(ModEffects.AQUATIC_STAGE_1.get());
            } else if (player.isPotionActive(ModEffects.AQUATIC_STAGE_3.get())) {
                player.removePotionEffect(ModEffects.AQUATIC_STAGE_3.get());
            }
        } else if (effectToAdd.equals(ModEffects.AQUATIC_STAGE_3.get())) {
            if (player.isPotionActive(ModEffects.AQUATIC_STAGE_1.get())) {
                player.removePotionEffect(ModEffects.AQUATIC_STAGE_1.get());
            } else if (player.isPotionActive(ModEffects.AQUATIC_STAGE_2.get())) {
                player.removePotionEffect(ModEffects.AQUATIC_STAGE_2.get());
            }
        }
        player.addPotionEffect(effectInstanceToAdd);
    }
}
