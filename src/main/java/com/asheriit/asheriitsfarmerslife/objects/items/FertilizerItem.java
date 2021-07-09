package com.asheriit.asheriitsfarmerslife.objects.items;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import com.asheriit.asheriitsfarmerslife.init.ModItems;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import com.asheriit.asheriitsfarmerslife.objects.blocks.LargeCropBlock;
import com.asheriit.asheriitsfarmerslife.properties.ItemTierType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class FertilizerItem extends AbstractRarityItem {

    public FertilizerItem(Properties properties, ItemTierType itemTierType) {
        super(properties, itemTierType);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getPos();
        int stateMax = 4;

        List<BlockPos> blockPosList = this.getValidPositions(world, blockPos, ModTags.Blocks.FARMLANDS.getId(), stateMax);
        if (blockPosList.size() == 0) {
            return ActionResultType.FAIL;
        }

        boolean wasFertilized = false;
        for (BlockPos pos : blockPosList) {
            BlockState blockStateFromList = world.getBlockState(pos);
            if (blockStateFromList.getProperties().contains(ModBlockStateProperties.FERTILIZER_LEVEL_0_4) && blockStateFromList.get(ModBlockStateProperties.FERTILIZER_LEVEL_0_4) < stateMax) {
                int fertilizerLevel = blockStateFromList.get(ModBlockStateProperties.FERTILIZER_LEVEL_0_4) + this.getItemTierType().getItemTier() + 1;
                wasFertilized = true;
                if (fertilizerLevel >= stateMax) {
                    world.setBlockState(pos, blockStateFromList.with(ModBlockStateProperties.FERTILIZER_LEVEL_0_4, stateMax));
                } else {
                    world.setBlockState(pos, blockStateFromList.with(ModBlockStateProperties.FERTILIZER_LEVEL_0_4, fertilizerLevel));
                }
            }
        }

        PlayerEntity player = context.getPlayer();
        ItemStack itemStack = context.getItem();
        if (wasFertilized && player != null) {
            player.playSound(SoundEvents.BLOCK_COMPOSTER_FILL, 1.1F, 1.0F);
            itemStack.damageItem(1, player, (entity) -> {
                ItemStack emptyBag = new ItemStack(ModItems.EMPTY_LARGE_FERTILIZER_BAG.get(), 1);
                int slotPos = player.inventory.getSlotFor(itemStack);
                player.inventory.add(slotPos, emptyBag);
                entity.sendBreakAnimation(context.getHand());
            });
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        int maxDamage = stack.getMaxDamage();
        int fullness = (100 * (maxDamage - this.getDamage(stack))) / maxDamage;
        String fertilizerFullness = "(" + fullness + "%)";
        return new TranslationTextComponent(this.getTranslationKey(stack), fertilizerFullness);
    }

    private List<BlockPos> getValidPositions(IWorldReader worldIn, BlockPos pos, ResourceLocation blockTagLocation, int stateMax) {
        Tag<Block> blockTag = BlockTags.getCollection().getOrCreate(blockTagLocation);
        List<BlockPos> blockPosList = new ArrayList<>();
        BlockState clickedBlockState = worldIn.getBlockState(pos);
        Block clickedBlock = clickedBlockState.getBlock();

        ItemTierType itemTierType = this.getItemTierType();
        int range = itemTierType.getItemTier();
        int yRangeDown = 1;
        if (clickedBlock instanceof LargeCropBlock && clickedBlockState.get(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) {
            yRangeDown = 2;
        }

        for (BlockPos blockPos: BlockPos.getAllInBoxMutable(pos.add(-range,-yRangeDown,-range), pos.add(range,1, range))) {
            BlockState blockState = worldIn.getBlockState(blockPos);
            if (blockState.isIn(blockTag) && blockState.getProperties().contains(ModBlockStateProperties.FERTILIZER_LEVEL_0_4) && blockState.get(ModBlockStateProperties.FERTILIZER_LEVEL_0_4) < stateMax) {
                blockPosList.add(blockPos.toImmutable());
            }
        }

        return blockPosList;
    }
}
