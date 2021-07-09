package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BoneMealEventHandler {
    @SubscribeEvent
    public static void onBoneMealUseEvent(BonemealEvent event) {
        BlockState state = event.getBlock();
        Block block = state.getBlock();

        if (/*block instanceof IGrowable &&*/ block instanceof GrassBlock) {
            World world = event.getWorld();
            BlockPos pos = event.getPos();
            Random rand = world.getRandom();

            int rolls = 40;
            int maxRange = 5;
            int spawnChance = 4;

            for (int i = 0; i < rolls; i++) {
                int offsetX = maxRange - rand.nextInt(maxRange * 2 + 1);
                int offsetZ = maxRange - rand.nextInt(maxRange * 2 + 1);

                if (offsetX == 0 && offsetZ == 0) {
                    continue;
                }

                BlockPos posToValidate = new BlockPos(pos.getX() + offsetX, pos.getY(), pos.getZ() + offsetZ);
                BlockState stateToValidate = world.getBlockState(posToValidate);
                Block blockToValidate = stateToValidate.getBlock();

                if (blockToValidate instanceof GrassBlock && rand.nextInt(spawnChance) == 0) {
                    BlockPos posToValidateUp = posToValidate.up();
                    if (!world.isRemote && (world.getBlockState(posToValidateUp).getBlock() instanceof AirBlock)) {
                        world.setBlockState(posToValidateUp, ModBlocks.SHORT_GRASS.get().getDefaultState(), 2);
                    }
                }
            }
        }
    }
}
