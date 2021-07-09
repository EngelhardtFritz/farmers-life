package com.asheriit.asheriitsfarmerslife.objects.items;

import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RawFertilizerItem extends Item {
    public RawFertilizerItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getPos();
        BlockState blockState = world.getBlockState(blockPos);

        if (!blockState.getProperties().contains(ModBlockStateProperties.FERTILIZER_LEVEL_0_4)) {
            return ActionResultType.FAIL;
        }

        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        }

        int stateMax = 4;
        if (blockState.get(ModBlockStateProperties.FERTILIZER_LEVEL_0_4) < stateMax) {
            int fertilizerLevel = blockState.get(ModBlockStateProperties.FERTILIZER_LEVEL_0_4) + 1;

            if (fertilizerLevel >= stateMax) {
                world.setBlockState(blockPos, blockState.with(ModBlockStateProperties.FERTILIZER_LEVEL_0_4, stateMax));
            } else {
                world.setBlockState(blockPos, blockState.with(ModBlockStateProperties.FERTILIZER_LEVEL_0_4, fertilizerLevel));
            }

            ItemStack usedItem = context.getItem();
            usedItem.shrink(1);
        }

        return ActionResultType.SUCCESS;
    }
}
