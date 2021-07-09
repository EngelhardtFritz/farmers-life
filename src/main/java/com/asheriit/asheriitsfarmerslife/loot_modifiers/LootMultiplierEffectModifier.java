package com.asheriit.asheriitsfarmerslife.loot_modifiers;

import com.asheriit.asheriitsfarmerslife.init.ModEffects;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class LootMultiplierEffectModifier extends LootModifier {
    private final float chance_stage_1;
    private final float chance_stage_2;
    private final float chance_stage_3;
    private final int multiplier;

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected LootMultiplierEffectModifier(ILootCondition[] conditionsIn, float chance_stage_1, float chance_stage_2, float chance_stage_3, int multiplier) {
        super(conditionsIn);
        this.chance_stage_1 = chance_stage_1;
        this.chance_stage_2 = chance_stage_2;
        this.chance_stage_3 = chance_stage_3;
        this.multiplier = multiplier;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        // Get executing entity manually since the entity value "this" in the <entity_properties> predicate does nothing!
        Entity entity = context.get(LootParameters.THIS_ENTITY);
        if (entity == null) return generatedLoot;
        float chance = 0;
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            if (player.isPotionActive(ModEffects.LOOT_MULTIPLIER_STAGE_3.get())) {
                chance = chance_stage_3;
            } else if (player.isPotionActive(ModEffects.LOOT_MULTIPLIER_STAGE_2.get())) {
                chance = chance_stage_2;
            } else if (player.isPotionActive(ModEffects.LOOT_MULTIPLIER_STAGE_1.get())) {
                chance = chance_stage_1;
            } else {
                return generatedLoot;
            }
        }

        // Now do actually validate if loot should be multiplied
        if (context.getRandom().nextFloat() >= chance) return generatedLoot;
        List<ItemStack> modifiedLoot = new ArrayList<>(generatedLoot);
        for (int i = 0; i < modifiedLoot.size(); i++) {
            ItemStack stack = modifiedLoot.get(i);
            if (isInTags(stack.getItem())) {
                int count = stack.getCount();
                stack.setCount(count * multiplier);
                modifiedLoot.set(i, stack);
            }
        }
        return modifiedLoot;
    }

    private static boolean isInTags(Item item) {
        Block itemBlock = Block.getBlockFromItem(item);
        boolean isValid = false;
        if (itemBlock != Blocks.AIR) {
            isValid = itemBlock.isIn(BlockTags.LOGS) || itemBlock.isIn(BlockTags.BEEHIVES) ||
                    itemBlock.isIn(BlockTags.CORAL_BLOCKS) || itemBlock.isIn(BlockTags.FLOWERS) ||
                    itemBlock.isIn(BlockTags.LEAVES) || itemBlock.isIn(ModTags.Blocks.FARMLANDS) ||
                    itemBlock.isIn(Tags.Blocks.SAND) || itemBlock.isIn(Tags.Blocks.ORES) ||
                    itemBlock.isIn(Tags.Blocks.DIRT) || itemBlock.isIn(Tags.Blocks.END_STONES) ||
                    itemBlock.isIn(Tags.Blocks.GRAVEL) || itemBlock.isIn(Tags.Blocks.NETHERRACK) ||
                    itemBlock.isIn(Tags.Blocks.OBSIDIAN) || itemBlock.isIn(Tags.Blocks.STONE) ||
                    itemBlock.isIn(Tags.Blocks.SANDSTONE) || itemBlock.isIn(ModTags.Blocks.COMPRESSED);
        }
        return isValid || item.isIn(ItemTags.LOGS) || item.isIn(ItemTags.LEAVES) || item.isIn(ItemTags.SAND) || item.isIn(Tags.Items.DUSTS) ||
                item.isIn(Tags.Items.GRAVEL) || item.isIn(Tags.Items.INGOTS) || item.isIn(Tags.Items.STONE) || item.isIn(Tags.Items.SANDSTONE) ||
                item.isIn(Tags.Items.SAND) || item.isIn(Tags.Items.ORES) || item.isIn(Tags.Items.OBSIDIAN) || item.isIn(Tags.Items.NETHERRACK) ||
                item.isIn(Tags.Items.GEMS) || item.isIn(ModTags.Items.RAW_FERTILIZER);
    }

    public static class Serializer extends GlobalLootModifierSerializer<LootMultiplierEffectModifier> {
        @Override
        public LootMultiplierEffectModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
            final float chance_stage_1 = JSONUtils.getFloat(object, "chance_stage_1");
            final float chance_stage_2 = JSONUtils.getFloat(object, "chance_stage_2");
            final float chance_stage_3 = JSONUtils.getFloat(object, "chance_stage_3");
            final int multiplier = JSONUtils.getInt(object, "multiplier");
            return new LootMultiplierEffectModifier(ailootcondition, chance_stage_1, chance_stage_2, chance_stage_3, multiplier);
        }
    }
}
