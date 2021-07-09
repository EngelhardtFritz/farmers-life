package com.asheriit.asheriitsfarmerslife.objects.items;

import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class SpadeItem extends ShovelItem {
    public SpadeItem(IItemTier itemTier, float attackDamage, float attackSpeed, Properties builder) {
        super(itemTier, attackDamage, attackSpeed, builder.addToolType(ToolType.SHOVEL, itemTier.getHarvestLevel()));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        BlockPos pos = context.getPos();
        World world = context.getWorld();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        PlayerEntity player = context.getPlayer();

        if (block == Blocks.DIRT && player != null) {
            BlockState submergedDirtFarmland = ModBlocks.SUBMERGED_DIRT_FARMLAND.get().getDefaultState();

            if (!world.isRemote) {
                world.setBlockState(pos, submergedDirtFarmland, 2);
                context.getItem().damageItem(1, player, (playerEntity) -> {
                    playerEntity.sendBreakAnimation(context.getHand());
                });
            }

            return ActionResultType.SUCCESS;
        } else if (block instanceof GrassBlock && player != null) {
            BlockState submergedGrassFarmland = ModBlocks.SUBMERGED_GRASS_FARMLAND.get().getDefaultState();

            if (!world.isRemote) {
                world.setBlockState(pos, submergedGrassFarmland, 2);
                context.getItem().damageItem(1, player, (playerEntity) -> {
                    playerEntity.sendBreakAnimation(context.getHand());
                });
            }

            return ActionResultType.SUCCESS;
        }

        return super.onItemUse(context);
    }
}
