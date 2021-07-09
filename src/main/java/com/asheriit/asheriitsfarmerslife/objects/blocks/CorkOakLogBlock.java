package com.asheriit.asheriitsfarmerslife.objects.blocks;

import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class CorkOakLogBlock extends LogBlock {
    public CorkOakLogBlock(MaterialColor verticalColorIn, Properties properties) {
        super(verticalColorIn, properties);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        Item itemInHand = player.getHeldItem(handIn).getItem();
        if (handIn == Hand.MAIN_HAND && itemInHand instanceof AxeItem) {
            if (worldIn.isRemote) {
                return ActionResultType.SUCCESS;
            } else {
                worldIn.setBlockState(pos, ModBlocks.STRIPPED_CORK_OAK_LOG.get().getDefaultState().with(BlockStateProperties.AXIS, state.get(BlockStateProperties.AXIS)).with(BlockStateProperties.AGE_0_2, 0), Constants.BlockFlags.BLOCK_UPDATE);
                if (state.getBlock() == ModBlocks.CORK_OAK_LOG.get()) {
                    int randValue = worldIn.rand.nextInt(4) == 0 ? 2 : 1;
                    ItemStack barkStack = new ItemStack(ModItems.CORK_BARK.get(), randValue);
                    spawnAsEntity(worldIn, pos, barkStack);
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
