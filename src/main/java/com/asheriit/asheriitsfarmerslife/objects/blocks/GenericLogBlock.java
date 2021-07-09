package com.asheriit.asheriitsfarmerslife.objects.blocks;

import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class GenericLogBlock extends LogBlock {
    private final RenewableStrippedLogBlock strippedLogBlock;

    public GenericLogBlock(RenewableStrippedLogBlock strippedLogBlock, MaterialColor verticalColorIn, Properties properties) {
        super(verticalColorIn, properties);
        this.strippedLogBlock = strippedLogBlock;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        Item itemInHand = player.getHeldItem(handIn).getItem();
        if (handIn == Hand.MAIN_HAND && itemInHand instanceof AxeItem) {
            if (worldIn.isRemote) {
                return ActionResultType.SUCCESS;
            } else {
                worldIn.setBlockState(pos, strippedLogBlock.getDefaultState().with(BlockStateProperties.AXIS, state.get(BlockStateProperties.AXIS)).with(BlockStateProperties.AGE_0_2, 0), Constants.BlockFlags.BLOCK_UPDATE);
                if (state.getBlock() == ModBlocks.STRIPPED_CORK_OAK_LOG.get()) {
                    // TODO: spawn cork
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
