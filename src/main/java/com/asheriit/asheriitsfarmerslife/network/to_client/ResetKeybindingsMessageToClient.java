package com.asheriit.asheriitsfarmerslife.network.to_client;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.network.PacketBuffer;

import java.util.UUID;

public class ResetKeybindingsMessageToClient {
    private UUID uuId;
    private String[] originalKeys;
    private boolean messageIsValid;

    public ResetKeybindingsMessageToClient(UUID uuId, String[] originalKeys) {
        this.uuId = uuId;
        this.originalKeys = originalKeys;
        this.messageIsValid = true;
    }

    public ResetKeybindingsMessageToClient() {
        this.messageIsValid = false;
    }

    public static ResetKeybindingsMessageToClient decode(PacketBuffer buffer) {
        ResetKeybindingsMessageToClient message = new ResetKeybindingsMessageToClient();
        try {
            UUID uuId = buffer.readUniqueId();
            String[] originalKeys = new String[]{buffer.readString(), buffer.readString(), buffer.readString(), buffer.readString()};
            message.setUuId(uuId);
            message.setOriginalKeys(originalKeys);
        } catch (IllegalArgumentException e) {
            FarmersLife.LOGGER.warn("Exception while reading ResetKeybindingsMessageToClient: " + e);
            return message;
        }
        message.setMessageIsValid(true);
        return message;
    }

    public void encode(PacketBuffer buffer) {
        if (!this.messageIsValid) return;
        buffer.writeUniqueId(this.uuId);
        buffer.writeString(this.originalKeys[0]);
        buffer.writeString(this.originalKeys[1]);
        buffer.writeString(this.originalKeys[2]);
        buffer.writeString(this.originalKeys[3]);
    }

    public UUID getUuId() {
        return uuId;
    }

    public void setUuId(UUID uuId) {
        this.uuId = uuId;
    }

    public String[] getOriginalKeys() {
        return originalKeys;
    }

    public void setOriginalKeys(String[] originalKeys) {
        this.originalKeys = originalKeys;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }

    public void setMessageIsValid(boolean messageIsValid) {
        this.messageIsValid = messageIsValid;
    }
}
