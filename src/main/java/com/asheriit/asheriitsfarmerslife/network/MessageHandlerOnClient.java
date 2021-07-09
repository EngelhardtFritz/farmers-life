package com.asheriit.asheriitsfarmerslife.network;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.NetworkMessageConstants;
import com.asheriit.asheriitsfarmerslife.capabilities.CapabilityDrunkenState;
import com.asheriit.asheriitsfarmerslife.capabilities.DrunkenState;
import com.asheriit.asheriitsfarmerslife.network.to_client.AlcoholLevelMessageToClient;
import com.asheriit.asheriitsfarmerslife.network.to_client.ManipulateKeybindingsMessageToClient;
import com.asheriit.asheriitsfarmerslife.network.to_client.ResetKeybindingsMessageToClient;
import com.asheriit.asheriitsfarmerslife.network.to_client.SyncDrunkenStateMessageToClient;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class MessageHandlerOnClient {
    // ---------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------ SyncDrunkenStateMessageToClient ------------------------------------------------
    // ---------------------------------------------------------------------------------------------------------------------------------
    public static void onSyncDrunkenStateMessageReceived(final SyncDrunkenStateMessageToClient message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            FarmersLife.LOGGER.warn("SyncDrunkenStateMessageToClient received on wrong side:" + context.getDirection().getReceptionSide());
            return;
        }

        if (!message.isMessageValid()) {
            FarmersLife.LOGGER.warn("SyncDrunkenStateMessageToClient was invalid" + message.toString());
            return;
        }

        Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            FarmersLife.LOGGER.warn("SyncDrunkenStateMessageToClient context could not provide a ClientWorld.");
            return;
        }

        context.enqueueWork(() -> processMessage(clientWorld.get(), message));
    }


    private static void processMessage(ClientWorld worldClient, SyncDrunkenStateMessageToClient message) {
        List<AbstractClientPlayerEntity> players = worldClient.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) players.get(i);
            if (clientPlayerEntity.getUniqueID().toString().equals(message.getUuId())) {
                DrunkenState drunkenState = clientPlayerEntity.getCapability(CapabilityDrunkenState.CAPABILITY_DRUNK).orElse(null);
                if (drunkenState != null) {
                    drunkenState.setOriginalKeys(message.getOriginalKeys());
                    drunkenState.setKeys(message.getKeys());
                    drunkenState.setAlcoholLevel(message.getAlcoholLevel());
                    drunkenState.setDrunk(message.isDrunk());
                    drunkenState.setAlcoholLevelTimeLeft(message.getAlcoholLevelTimeLeft());
                }
            }
        }
    }


    // -----------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------ DrunkenLevelMessageToClient ------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------------------
    public static void onResetKeybindingsMessageReceived(final AlcoholLevelMessageToClient message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            FarmersLife.LOGGER.warn("DrunkenLevelMessageToClient received on wrong side:" + context.getDirection().getReceptionSide());
            return;
        }

        if (!message.isMessageValid()) {
            FarmersLife.LOGGER.warn("DrunkenLevelMessageToClient was invalid" + message.toString());
            return;
        }

        Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            FarmersLife.LOGGER.warn("DrunkenLevelMessageToClient context could not provide a ClientWorld.");
            return;
        }

        context.enqueueWork(() -> processMessage(clientWorld.get(), message));
    }

    private static void processMessage(ClientWorld worldClient, AlcoholLevelMessageToClient message) {
        List<AbstractClientPlayerEntity> players = worldClient.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) players.get(i);
            if (clientPlayerEntity.getUniqueID().toString().equals(message.getUuId())) {
                DrunkenState drunkenState = clientPlayerEntity.getCapability(CapabilityDrunkenState.CAPABILITY_DRUNK).orElse(null);
                if (drunkenState != null) {
                    drunkenState.setAlcoholLevel(message.getAlcoholLevel());
                    drunkenState.setDrunk(message.isDrunk());
                }
            }
        }
    }


    // ---------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------ ResetKeybindingsMessageToClient ------------------------------------------------
    // ---------------------------------------------------------------------------------------------------------------------------------
    public static void onResetKeybindingsMessageReceived(final ResetKeybindingsMessageToClient message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            FarmersLife.LOGGER.warn("ResetKeybindingsMessageToClient received on wrong side:" + context.getDirection().getReceptionSide());
            return;
        }

        if (!message.isMessageValid()) {
            FarmersLife.LOGGER.warn("ResetKeybindingsMessageToClient was invalid" + message.toString());
            return;
        }

        Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            FarmersLife.LOGGER.warn("ResetKeybindingsMessageToClient context could not provide a ClientWorld.");
            return;
        }

        context.enqueueWork(() -> processMessage(clientWorld.get(), message));
    }

    private static void processMessage(ClientWorld worldClient, ResetKeybindingsMessageToClient message) {
        List<AbstractClientPlayerEntity> players = worldClient.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) players.get(i);
            if (clientPlayerEntity.getUniqueID().equals(message.getUuId())) {
                DrunkenState drunkenState = clientPlayerEntity.getCapability(CapabilityDrunkenState.CAPABILITY_DRUNK).orElse(null);
                if (drunkenState != null) {
                    drunkenState.resetMovementKeybindings(message.getOriginalKeys());
                }
            }
        }
    }


    // --------------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------ ManipulateKeybindingsMessageToClient ------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------------------------------
    public static void onManipulateKeybindingsMessageReceived(final ManipulateKeybindingsMessageToClient message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            FarmersLife.LOGGER.warn("ManipulateKeybindingsMessageToClient received on wrong side:" + context.getDirection().getReceptionSide());
            return;
        }

        if (!message.isMessageValid()) {
            FarmersLife.LOGGER.warn("ManipulateKeybindingsMessageToClient was invalid" + message.toString());
            return;
        }

        Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            FarmersLife.LOGGER.warn("ResetKeybindingsMessageToClient context could not provide a ClientWorld.");
            return;
        }

        context.enqueueWork(() -> processMessage(clientWorld.get(), message));
    }

    private static void processMessage(ClientWorld worldClient, ManipulateKeybindingsMessageToClient message) {
        List<AbstractClientPlayerEntity> players = worldClient.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) players.get(i);
            if (clientPlayerEntity.getUniqueID().equals(message.getUuId())) {
                DrunkenState drunkenState = clientPlayerEntity.getCapability(CapabilityDrunkenState.CAPABILITY_DRUNK).orElse(null);
                if (drunkenState != null) {
                    drunkenState.setOriginalKeys(message.getOriginalKeys());
                    drunkenState.setKeys(message.getKeys());
                    if (drunkenState.isDrunk()) {
                        drunkenState.manipulateKeybindings();
                    }
                }
            }
        }
    }

    public static boolean isThisProtocolAcceptedByClient(String protocolVersion) {
        return NetworkMessageConstants.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
    }
}
