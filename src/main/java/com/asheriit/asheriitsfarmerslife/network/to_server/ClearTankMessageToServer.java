package com.asheriit.asheriitsfarmerslife.network.to_server;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;

public class ClearTankMessageToServer {
    private Vec3d targetBlock;
    private byte tankSlot;
    private boolean messageIsValid;

    public ClearTankMessageToServer(Vec3d targetBlock, byte tankSlot) {
        this.targetBlock = targetBlock;
        this.tankSlot = tankSlot;
        this.messageIsValid = true;
    }

    public ClearTankMessageToServer() {
        this.messageIsValid = false;
    }

    // -------- GETTER --------
    public Vec3d getTargetBlock() {
        return targetBlock;
    }

    public byte getTankSlot() {
        return tankSlot;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }

    // -------- METHODS --------
    public static ClearTankMessageToServer decode(PacketBuffer buffer) {
        ClearTankMessageToServer clearTankMessageToServer = new ClearTankMessageToServer();
        try {
            double posX = buffer.readDouble();
            double posY = buffer.readDouble();
            double posZ = buffer.readDouble();
            clearTankMessageToServer.targetBlock = new Vec3d(posX, posY, posZ);
            clearTankMessageToServer.tankSlot = buffer.readByte();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            FarmersLife.LOGGER.warn("[ClearTankMessageToServer::decode] Exception while reading ClearTankMessageToServer: " + e);
            return clearTankMessageToServer;
        }
        clearTankMessageToServer.messageIsValid = true;
        return clearTankMessageToServer;
    }

    public void encode(PacketBuffer buffer) {
        if (!this.messageIsValid) return;
        buffer.writeDouble(this.targetBlock.getX());
        buffer.writeDouble(this.targetBlock.getY());
        buffer.writeDouble(this.targetBlock.getZ());
        buffer.writeByte(this.tankSlot);
    }

    @Override
    public String toString() {
        return "ClearTankMessageToServer{" +
                "targetBlock=" + targetBlock +
                ", tankSlot=" + tankSlot +
                ", messageIsValid=" + messageIsValid +
                '}';
    }
}
