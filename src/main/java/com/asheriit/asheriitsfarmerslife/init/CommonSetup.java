package com.asheriit.asheriitsfarmerslife.init;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.NetworkMessageConstants;
import com.asheriit.asheriitsfarmerslife.network.MessageHandlerOnClient;
import com.asheriit.asheriitsfarmerslife.network.MessageHandlerOnServer;
import com.asheriit.asheriitsfarmerslife.network.to_client.AlcoholLevelMessageToClient;
import com.asheriit.asheriitsfarmerslife.network.to_client.ManipulateKeybindingsMessageToClient;
import com.asheriit.asheriitsfarmerslife.network.to_client.ResetKeybindingsMessageToClient;
import com.asheriit.asheriitsfarmerslife.network.to_client.SyncDrunkenStateMessageToClient;
import com.asheriit.asheriitsfarmerslife.network.to_server.ClearTankMessageToServer;
import com.asheriit.asheriitsfarmerslife.network.to_server.OriginalKeyBindingMessageToServer;
import com.asheriit.asheriitsfarmerslife.network.to_server.SetBooleanMessageToServer;
import com.asheriit.asheriitsfarmerslife.network.to_server.TemperatureChangeMessageToServer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;

import java.util.Optional;

public class CommonSetup {
    public static void registerNetworkMessageHandler() {
        FarmersLife.simpleChannel = NetworkRegistry.newSimpleChannel(NetworkMessageConstants.SIMPLE_CHANNEL_RL,
                () -> NetworkMessageConstants.MESSAGE_PROTOCOL_VERSION,
                MessageHandlerOnClient::isThisProtocolAcceptedByClient,
                MessageHandlerOnServer::isThisProtocolAcceptedByServer);

        registerMessagesToClient();
        registerMessagesToServer();
    }

    private static void registerMessagesToClient() {
        FarmersLife.simpleChannel.registerMessage(NetworkMessageConstants.SYNC_DRUNKEN_STATE_MESSAGE_ID, SyncDrunkenStateMessageToClient.class,
                SyncDrunkenStateMessageToClient::encode, SyncDrunkenStateMessageToClient::decode,
                MessageHandlerOnClient::onSyncDrunkenStateMessageReceived, Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        FarmersLife.simpleChannel.registerMessage(NetworkMessageConstants.DRUNKEN_LEVEL_MESSAGE_ID, AlcoholLevelMessageToClient.class,
                AlcoholLevelMessageToClient::encode, AlcoholLevelMessageToClient::decode,
                MessageHandlerOnClient::onResetKeybindingsMessageReceived, Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        FarmersLife.simpleChannel.registerMessage(NetworkMessageConstants.RESET_KEYBINDINGS_MESSAGE_ID, ResetKeybindingsMessageToClient.class,
                ResetKeybindingsMessageToClient::encode, ResetKeybindingsMessageToClient::decode,
                MessageHandlerOnClient::onResetKeybindingsMessageReceived, Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        FarmersLife.simpleChannel.registerMessage(NetworkMessageConstants.MANIPULATE_KEYBINDINGS_MESSAGE_ID, ManipulateKeybindingsMessageToClient.class,
                ManipulateKeybindingsMessageToClient::encode, ManipulateKeybindingsMessageToClient::decode,
                MessageHandlerOnClient::onManipulateKeybindingsMessageReceived, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    private static void registerMessagesToServer() {
        FarmersLife.simpleChannel.registerMessage(NetworkMessageConstants.CLEAR_TANK_MESSAGE_ID, ClearTankMessageToServer.class,
                ClearTankMessageToServer::encode, ClearTankMessageToServer::decode,
                MessageHandlerOnServer::onClearTankMessageReceived, Optional.of(NetworkDirection.PLAY_TO_SERVER));

        FarmersLife.simpleChannel.registerMessage(NetworkMessageConstants.SYNC_ORIGINAL_KEYS_MESSAGE_ID, OriginalKeyBindingMessageToServer.class,
                OriginalKeyBindingMessageToServer::encode, OriginalKeyBindingMessageToServer::decode,
                MessageHandlerOnServer::onOriginalKeyBindingMessageReceived, Optional.of(NetworkDirection.PLAY_TO_SERVER));

        FarmersLife.simpleChannel.registerMessage(NetworkMessageConstants.SET_BOOLEAN_MESSAGE_ID, SetBooleanMessageToServer.class,
                SetBooleanMessageToServer::encode, SetBooleanMessageToServer::decode,
                MessageHandlerOnServer::onSetBooleanMessageReceived, Optional.of(NetworkDirection.PLAY_TO_SERVER));

        FarmersLife.simpleChannel.registerMessage(NetworkMessageConstants.TEMPERATURE_CHANGE_MESSAGE_ID, TemperatureChangeMessageToServer.class,
                TemperatureChangeMessageToServer::encode, TemperatureChangeMessageToServer::decode,
                MessageHandlerOnServer::onTemperatureChangeMessageReceived, Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }
}
