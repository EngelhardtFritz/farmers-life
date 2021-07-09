package com.asheriit.asheriitsfarmerslife.network.to_server;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;

public class TemperatureChangeMessageToServer {
    private boolean messageIsValid;
    private Vec3d targetBlock;
    private boolean shouldIncrease;
    private short positiveAmount;

    public TemperatureChangeMessageToServer(Vec3d targetBlock, boolean shouldIncrease, short positiveAmount) {
        this.targetBlock = targetBlock;
        this.shouldIncrease = shouldIncrease;
        this.messageIsValid = true;
        this.positiveAmount = positiveAmount < 0 ? (short)-positiveAmount : positiveAmount;
    }

    public TemperatureChangeMessageToServer() {
        this.messageIsValid = false;
    }

    public static TemperatureChangeMessageToServer decode(PacketBuffer buffer) {
        TemperatureChangeMessageToServer temperatureChangeMessageToServer = new TemperatureChangeMessageToServer();
        try {
            temperatureChangeMessageToServer.targetBlock = new Vec3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
            temperatureChangeMessageToServer.shouldIncrease = buffer.readBoolean();
            temperatureChangeMessageToServer.positiveAmount = buffer.readShort();
        } catch (IndexOutOfBoundsException e) {
            FarmersLife.LOGGER.warn("[TemperatureChangeMessageToServer::decode] Exception while reading message data: " + e);
            return temperatureChangeMessageToServer;
        }
        temperatureChangeMessageToServer.messageIsValid = true;
        return temperatureChangeMessageToServer;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeDouble(this.targetBlock.getX());
        buffer.writeDouble(this.targetBlock.getY());
        buffer.writeDouble(this.targetBlock.getZ());
        buffer.writeBoolean(this.shouldIncrease);
        buffer.writeShort(this.positiveAmount);
    }

    // -------- GETTER --------
    public boolean isMessageValid() {
        return messageIsValid;
    }

    public Vec3d getTargetBlock() {
        return targetBlock;
    }

    public boolean shouldIncrease() {
        return shouldIncrease;
    }

    public short getPositiveAmount() {
        return positiveAmount;
    }

    @Override
    public String toString() {
        return "TemperatureChangeMessageToServer{" +
                "messageIsValid=" + messageIsValid +
                ", targetBlock=" + targetBlock +
                ", shouldIncrease=" + shouldIncrease +
                ", positiveAmount=" + positiveAmount +
                '}';
    }
}
