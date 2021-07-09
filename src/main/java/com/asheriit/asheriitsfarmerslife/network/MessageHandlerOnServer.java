package com.asheriit.asheriitsfarmerslife.network;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.NetworkMessageConstants;
import com.asheriit.asheriitsfarmerslife.capabilities.CapabilityDrunkenState;
import com.asheriit.asheriitsfarmerslife.capabilities.DrunkenState;
import com.asheriit.asheriitsfarmerslife.network.to_server.ClearTankMessageToServer;
import com.asheriit.asheriitsfarmerslife.network.to_server.OriginalKeyBindingMessageToServer;
import com.asheriit.asheriitsfarmerslife.network.to_server.SetBooleanMessageToServer;
import com.asheriit.asheriitsfarmerslife.network.to_server.TemperatureChangeMessageToServer;
import com.asheriit.asheriitsfarmerslife.tileentity.AbstractClarificationMachineTileEntity;
import com.asheriit.asheriitsfarmerslife.tileentity.AbstractTickingFluidMachineTileEntity;
import com.asheriit.asheriitsfarmerslife.tileentity.BoilingCauldronTileEntity;
import com.asheriit.asheriitsfarmerslife.tileentity.TemperatureChamberTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageHandlerOnServer {
    // ---------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------ ClearTankMessageToServer -------------------------------------------------------
    // ---------------------------------------------------------------------------------------------------------------------------------
    /**
     * Update tank content: empty tank
     * @param message: message to receive
     * @param contextSupplier: message context
     */
    public static void onClearTankMessageReceived(final ClearTankMessageToServer message, Supplier<NetworkEvent.Context> contextSupplier) {
        // Validate that the message received is valid
        NetworkEvent.Context context = contextSupplier.get();
        LogicalSide side = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);
        // Check if received on correct side
        if (side != LogicalSide.SERVER) {
            FarmersLife.LOGGER.warn("[MessageHandlerOnServer::onClearTankMessageReceived] Message received on wrong side: " + side);
            return;
        }

        // Check if message is valid
        if (!message.isMessageValid()) {
            FarmersLife.LOGGER.warn("[MessageHandlerOnServer::onClearTankMessageReceived] Message was invalid: " + message.toString());
            return;
        }

        // We are on logical server -> check if player is present
        final ServerPlayerEntity playerEntity = context.getSender();
        if (playerEntity == null) {
            FarmersLife.LOGGER.warn("[MessageHandlerOnServer::onClearTankMessageReceived] Sending entity was null!");
            return;
        }

        // All cases handled -> schedule new task to handle execute
        context.enqueueWork(() -> processMessage(message, playerEntity));
    }

    static void processMessage(ClearTankMessageToServer message, ServerPlayerEntity playerEntity) {
        // Get world from player and create BlockPos from message content
        World world = playerEntity.world;
        BlockPos blockPos = new BlockPos(message.getTargetBlock());
        byte tankSlot = message.getTankSlot();
        if (tankSlot < 0) {
            FarmersLife.LOGGER.warn("[ClearTankMessageServerHandler::processMessage] Message includes invalid tank slot!");
            return;
        }

        // Get tile entity from position
        BlockState blockState = world.getBlockState(blockPos);
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity != null) {
            // Tile entity should be instance of any supported fluid tile entity
            if (tileEntity instanceof AbstractTickingFluidMachineTileEntity) {
                AbstractTickingFluidMachineTileEntity machineTileEntity = (AbstractTickingFluidMachineTileEntity) tileEntity;
                machineTileEntity.emptyTank(tankSlot);

                // Update for clients
                tileEntity.markDirty();
                world.notifyBlockUpdate(blockPos, blockState, blockState, Constants.BlockFlags.BLOCK_UPDATE);
            }
        }
    }


    // ---------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------ OriginalKeyBindingMessageToServer ----------------------------------------------
    // ---------------------------------------------------------------------------------------------------------------------------------
    public static void onOriginalKeyBindingMessageReceived(final OriginalKeyBindingMessageToServer message, Supplier<NetworkEvent.Context> contextSupplier) {
        // Validate that the message received is valid
        NetworkEvent.Context context = contextSupplier.get();
        LogicalSide side = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);
        // Check if received on correct side
        if (side != LogicalSide.SERVER) {
            FarmersLife.LOGGER.warn("[MessageHandlerOnServer::onOriginalKeyBindingMessageReceived] Message received on wrong side: " + side);
            return;
        }

        // Check if message is valid
        if (!message.isMessageValid()) {
            FarmersLife.LOGGER.warn("[MessageHandlerOnServer::onOriginalKeyBindingMessageReceived] Message was invalid: " + message.toString());
            return;
        }

        // We are on logical server -> check if player is present
        final ServerPlayerEntity playerEntity = context.getSender();
        if (playerEntity == null) {
            FarmersLife.LOGGER.warn("[MessageHandlerOnServer::onOriginalKeyBindingMessageReceived] Sending entity was null!");
            return;
        }

        // All cases handled -> schedule new task to handle execute
        context.enqueueWork(() -> processMessage(message, playerEntity));
    }

    private static void processMessage(OriginalKeyBindingMessageToServer message, ServerPlayerEntity playerEntity) {
        DrunkenState drunkenState = playerEntity.getCapability(CapabilityDrunkenState.CAPABILITY_DRUNK).orElse(null);
        if (drunkenState == null) {
            FarmersLife.LOGGER.warn("[OriginalKeyMessageServerHandler::processMessage] Capability not present!");
        }

        drunkenState.setOriginalKeys(message.getOriginalKeys());
    }


    // ---------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------ SetBooleanMessageToServer ------------------------------------------------------
    // ---------------------------------------------------------------------------------------------------------------------------------
    public static void onSetBooleanMessageReceived(final SetBooleanMessageToServer message, Supplier<NetworkEvent.Context> contextSupplier) {
        // Validate that the message received is valid
        NetworkEvent.Context context = contextSupplier.get();
        LogicalSide side = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);
        // Check if received on correct side
        if (side != LogicalSide.SERVER) {
            FarmersLife.LOGGER.warn("[MessageHandlerOnServer::onSetBooleanMessageReceived] Message received on wrong side: " + side);
            return;
        }

        // Check if message is valid
        if (!message.isMessageValid()) {
            FarmersLife.LOGGER.warn("[MessageHandlerOnServer::onSetBooleanMessageReceived] Message was invalid: " + message.toString());
            return;
        }

        // We are on logical server -> check if player is present
        final ServerPlayerEntity playerEntity = context.getSender();
        if (playerEntity == null) {
            FarmersLife.LOGGER.warn("[MessageHandlerOnServer::onSetBooleanMessageReceived] Sending entity was null!");
            return;
        }

        // All cases handled -> schedule new task to handle execute
        context.enqueueWork(() -> processMessage(message, playerEntity));
    }

    static void processMessage(SetBooleanMessageToServer message, ServerPlayerEntity playerEntity) {
        // Get world from player and create BlockPos from message content
        World world = playerEntity.world;
        BlockPos blockPos = new BlockPos(message.getTargetBlock());

        // Get tile entity from position
        BlockState blockState = world.getBlockState(blockPos);
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity != null) {
            // Tile entity should be instance of any supported fluid tile entity
            if (tileEntity instanceof BoilingCauldronTileEntity) {
                BoilingCauldronTileEntity boilingCauldronTileEntity = (BoilingCauldronTileEntity) tileEntity;
                boolean switchedFuelState = !boilingCauldronTileEntity.isFuelEnabled();
                boilingCauldronTileEntity.setFuelEnabled(switchedFuelState);

                // Update for clients
                boilingCauldronTileEntity.markDirty();
                world.notifyBlockUpdate(blockPos, blockState, blockState, Constants.BlockFlags.BLOCK_UPDATE);
            } else if (tileEntity instanceof TemperatureChamberTileEntity) {
                TemperatureChamberTileEntity temperatureChamberTileEntity = (TemperatureChamberTileEntity) tileEntity;
                temperatureChamberTileEntity.switchTemperatureUnit();

                temperatureChamberTileEntity.markDirty();
                world.notifyBlockUpdate(blockPos, blockState, blockState, Constants.BlockFlags.BLOCK_UPDATE);
            }
        }
    }


    // ---------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------ TemperatureChangeMessageToServer -----------------------------------------------
    // ---------------------------------------------------------------------------------------------------------------------------------
    public static void onTemperatureChangeMessageReceived(final TemperatureChangeMessageToServer message, Supplier<NetworkEvent.Context> contextSupplier) {
        // Validate that the message received is valid
        NetworkEvent.Context context = contextSupplier.get();
        LogicalSide side = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);
        // Check if received on correct side
        if (side != LogicalSide.SERVER) {
            FarmersLife.LOGGER.warn("[MessageHandlerOnServer::onTemperatureChangeMessageReceived] Message received on wrong side: " + side);
            return;
        }

        // Check if message is valid
        if (!message.isMessageValid()) {
            FarmersLife.LOGGER.warn("[MessageHandlerOnServer::onTemperatureChangeMessageReceived] Message was invalid: " + message.toString());
            return;
        }

        // We are on logical server -> check if player is present
        final ServerPlayerEntity playerEntity = context.getSender();
        if (playerEntity == null) {
            FarmersLife.LOGGER.warn("[MessageHandlerOnServer::onTemperatureChangeMessageReceived] Sending entity was null!");
            return;
        }

        // All cases handled -> schedule new task to handle execute
        context.enqueueWork(() -> processMessage(message, playerEntity));
    }

    static void processMessage(TemperatureChangeMessageToServer message, ServerPlayerEntity playerEntity) {
        // Get world from player and create BlockPos from message content
        World world = playerEntity.world;
        BlockPos blockPos = new BlockPos(message.getTargetBlock());

        // Get tile entity from position
        BlockState blockState = world.getBlockState(blockPos);
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity != null) {
            // Tile entity should be instance of any supported fluid tile entity
            if (tileEntity instanceof TemperatureChamberTileEntity) {
                TemperatureChamberTileEntity temperatureChamberTileEntity = (TemperatureChamberTileEntity) tileEntity;
                temperatureChamberTileEntity.pressArrowButton(message.shouldIncrease(), message.getPositiveAmount());
                FarmersLife.LOGGER.info("TemperatureChangeMessageToServer -> shouldIncrease: " + message.shouldIncrease() + ", getPositiveAmount: " + message.getPositiveAmount());

                // Update for clients
                temperatureChamberTileEntity.markDirty();
                world.notifyBlockUpdate(blockPos, blockState, blockState, Constants.BlockFlags.BLOCK_UPDATE);
            }
        }
    }

    public static boolean isThisProtocolAcceptedByServer(String protocolVersion) {
        return NetworkMessageConstants.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
    }
}
