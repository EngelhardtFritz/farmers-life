package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.capabilities.CapabilityDrunkenState;
import com.asheriit.asheriitsfarmerslife.capabilities.DrunkenState;
import com.asheriit.asheriitsfarmerslife.network.to_client.SyncDrunkenStateMessageToClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEventHandler {
    @SubscribeEvent
    public static void onPlayerLoginEvent(PlayerEvent.PlayerLoggedInEvent event) {
        sendDrunkenStateToClient(event.getPlayer());
    }

    @SubscribeEvent
    public static void onPlayerChangingDimensionsEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        sendDrunkenStateToClient(event.getPlayer());
    }

    @SubscribeEvent
    public static void onPlayerRespawnEvent(PlayerEvent.PlayerRespawnEvent event) {
        sendDrunkenStateToClient(event.getPlayer());
    }

    public static void sendDrunkenStateToClient(PlayerEntity playerEntity) {
        if (playerEntity instanceof ServerPlayerEntity && playerEntity.getCapability(CapabilityDrunkenState.CAPABILITY_DRUNK).isPresent()) {
            DrunkenState drunkenState = playerEntity.getCapability(CapabilityDrunkenState.CAPABILITY_DRUNK).orElse(null);
            if (drunkenState != null) {
                // Send Parameters to client
                if (drunkenState.getOriginalKeys() != null && drunkenState.getOriginalKeys().length > 3 && drunkenState.getOriginalKeys()[0].length() > 3 &&
                        drunkenState.getKeys() != null && drunkenState.getKeys().length > 3) {
                    FarmersLife.LOGGER.info("Keys: [" + drunkenState.getKeys()[0] + "," + drunkenState.getKeys()[1] + "," + drunkenState.getKeys()[2] + "," + drunkenState.getKeys()[3] + "], OriginalKeys: [" +
                            drunkenState.getOriginalKeys()[0] + "," + drunkenState.getOriginalKeys()[1] + "," + drunkenState.getOriginalKeys()[2] + "," + drunkenState.getOriginalKeys()[3] + "]");
                    SyncDrunkenStateMessageToClient drunkenStateMessage = new SyncDrunkenStateMessageToClient(playerEntity.getUniqueID().toString(),
                            drunkenState.getOriginalKeys(), drunkenState.getKeys(), drunkenState.getAlcoholLevel(), drunkenState.isDrunk(), drunkenState.getAlcoholLevelTimeLeft());
                    FarmersLife.simpleChannel.send(PacketDistributor.DIMENSION.with(() -> playerEntity.dimension), drunkenStateMessage);
                }
            }
        }
    }
}
