package com.asheriit.asheriitsfarmerslife.network.to_server;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;

public class SetBooleanMessageToServer {
    private Vec3d targetBlock;
    private boolean messageIsValid;

    public SetBooleanMessageToServer(Vec3d targetBlock) {
        this.targetBlock = targetBlock;
        this.messageIsValid = true;
    }

    public SetBooleanMessageToServer() {
        this.messageIsValid = false;
    }

    // -------- GETTER --------
    public Vec3d getTargetBlock() {
        return targetBlock;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }

    // -------- METHODS --------
    public static SetBooleanMessageToServer decode(PacketBuffer buffer) {
        SetBooleanMessageToServer setBooleanMessageToServer = new SetBooleanMessageToServer();
        try {
            double posX = buffer.readDouble();
            double posY = buffer.readDouble();
            double posZ = buffer.readDouble();
            setBooleanMessageToServer.targetBlock = new Vec3d(posX, posY, posZ);
        } catch (IllegalArgumentException e) {
            FarmersLife.LOGGER.warn("[SetBooleanMessageToServer::decode] Exception while reading ClearTankMessageToServer: " + e);
            return setBooleanMessageToServer;
        }
        setBooleanMessageToServer.messageIsValid = true;
        return setBooleanMessageToServer;
    }

    public void encode(PacketBuffer buffer) {
        if (!this.messageIsValid) return;
        buffer.writeDouble(this.targetBlock.getX());
        buffer.writeDouble(this.targetBlock.getY());
        buffer.writeDouble(this.targetBlock.getZ());
    }

    @Override
    public String toString() {
        return "SetBooleanMessageToServer{" +
                "targetBlock=" + targetBlock +
                ", messageIsValid=" + messageIsValid +
                '}';
    }
}
