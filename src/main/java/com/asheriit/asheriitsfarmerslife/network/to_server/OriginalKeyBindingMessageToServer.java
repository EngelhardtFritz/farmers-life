package com.asheriit.asheriitsfarmerslife.network.to_server;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.network.PacketBuffer;

public class OriginalKeyBindingMessageToServer {
    private String[] originalKeys;
    private boolean messageIsValid;

    public OriginalKeyBindingMessageToServer(String[] originalKeys) {
        this.originalKeys = originalKeys;
        this.messageIsValid = true;
    }

    public OriginalKeyBindingMessageToServer() {
        this.messageIsValid = false;
    }

    // -------- GETTER --------
    public String[] getOriginalKeys() {
        return originalKeys;
    }

    public void setOriginalKeys(String[] originalKeys) {
        this.originalKeys = originalKeys;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }

    // -------- METHODS --------
    public static OriginalKeyBindingMessageToServer decode(PacketBuffer buffer) {
        OriginalKeyBindingMessageToServer originalKeyBindingMessageToServer = new OriginalKeyBindingMessageToServer();
        try {
            String[] originalKeys = new String[]{buffer.readString(), buffer.readString(), buffer.readString(), buffer.readString()};
            originalKeyBindingMessageToServer.setOriginalKeys(originalKeys);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            FarmersLife.LOGGER.warn("[OriginalKeyBindingMessageToServer::decode] Exception while reading OriginalKeyBindingMessageToServer: " + e);
            return originalKeyBindingMessageToServer;
        }
        originalKeyBindingMessageToServer.messageIsValid = true;
        return originalKeyBindingMessageToServer;
    }

    public void encode(PacketBuffer buffer) {
        if (!this.messageIsValid) return;
        buffer.writeString(this.originalKeys[0]);
        buffer.writeString(this.originalKeys[1]);
        buffer.writeString(this.originalKeys[2]);
        buffer.writeString(this.originalKeys[3]);
    }
}
