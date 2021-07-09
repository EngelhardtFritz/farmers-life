package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.Map;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, bus = Bus.FORGE)
public class HoeUseEventHandler {
    @SubscribeEvent
    public static void onHoeUseEvent(UseHoeEvent event) {
        final Map<Block, BlockState> HOE_LOOKUP = Maps.newHashMap(ImmutableMap.of(ModBlocks.GARDEN_DIRT.get(), ModBlocks.GARDEN_FARMLAND.get().getDefaultState()));
        ItemUseContext hoeUseContext = event.getContext();
        World world = hoeUseContext.getWorld();
        BlockPos blockPos = hoeUseContext.getPos();
        BlockState blockState = HOE_LOOKUP.get(world.getBlockState(blockPos).getBlock());
        if (blockState != null) {
            PlayerEntity playerEntity = hoeUseContext.getPlayer();
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!world.isRemote) {
                world.setBlockState(blockPos, blockState, 11);
                event.setResult(Result.ALLOW);
            }
        }
    }
}
