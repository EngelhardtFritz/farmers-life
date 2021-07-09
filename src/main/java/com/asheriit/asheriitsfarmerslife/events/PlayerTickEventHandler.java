package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.capabilities.CapabilityDrunkenState;
import com.asheriit.asheriitsfarmerslife.capabilities.DrunkenState;
import com.asheriit.asheriitsfarmerslife.init.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerTickEventHandler {

    @SubscribeEvent
    public static void onPlayerTickHandler(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        LogicalSide side = event.side;

        if (event.phase == TickEvent.Phase.END) {
            handleDrunkenStateCapability(player, side);
        }
    }

    private static void handleDrunkenStateCapability(PlayerEntity player, LogicalSide side) {
        // ------------------ Handle DrunkenState Capability ------------------
        // Keybindings are in class KeyBinding
        final DrunkenState drunkenState = player.getCapability(CapabilityDrunkenState.CAPABILITY_DRUNK).orElse(null);
        if (drunkenState != null) {
            if (side.isServer() && !player.world.isRemote) {
                ServerPlayerEntity playerEntity = (ServerPlayerEntity) player;
                drunkenState.reduceAlcoholLevel(playerEntity);

                if (!playerEntity.isPotionActive(ModEffects.DRUNK_STAGE_1.get()) && !playerEntity.isPotionActive(ModEffects.DRUNK_STAGE_2.get()) &&
                        !playerEntity.isPotionActive(ModEffects.DRUNK_STAGE_3.get()) && drunkenState.isDrunk()) {
                    drunkenState.removeDrunkEffect(playerEntity);
                }

                if (drunkenState.isDrunk()) {
                    int remainingDuration = -1;
                    if (player.isPotionActive(ModEffects.DRUNK_STAGE_1.get())) {
                        remainingDuration = player.getActivePotionEffect(ModEffects.DRUNK_STAGE_1.get()).getDuration();
                    } else if (player.isPotionActive(ModEffects.DRUNK_STAGE_2.get())) {
                        remainingDuration = player.getActivePotionEffect(ModEffects.DRUNK_STAGE_2.get()).getDuration();
                    } else if (player.isPotionActive(ModEffects.DRUNK_STAGE_3.get())) {
                        remainingDuration = player.getActivePotionEffect(ModEffects.DRUNK_STAGE_3.get()).getDuration();
                    }

                    // 200: time keybindings are manipulated
                    if (remainingDuration > 200 && (remainingDuration % 200) == 0) {
                        drunkenState.updateManipulatedKeybindingsFromServer((ServerPlayerEntity) player);
                    }
                }
            }
        }
    }
}
