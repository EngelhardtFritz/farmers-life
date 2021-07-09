package com.asheriit.asheriitsfarmerslife.network.to_client;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.network.PacketBuffer;

import java.util.UUID;

public class ManipulateKeybindingsMessageToClient {
    private UUID uuId;
    private String[] originalKeys;
    private String[] keys;
    private boolean messageIsValid;

    public ManipulateKeybindingsMessageToClient(UUID uuId, String[] originalKeys, String[] keys) {
        this.uuId = uuId;
        this.originalKeys = originalKeys;
        this.keys = keys;
        this.messageIsValid = true;
    }

    public ManipulateKeybindingsMessageToClient() {
        this.messageIsValid = false;
    }

    public static ManipulateKeybindingsMessageToClient decode(PacketBuffer buffer) {
        ManipulateKeybindingsMessageToClient message = new ManipulateKeybindingsMessageToClient();
        try {
            UUID uuId = buffer.readUniqueId();
            String[] originalKeys = new String[]{buffer.readString(), buffer.readString(), buffer.readString(), buffer.readString()};
            String[] keys = new String[]{buffer.readString(), buffer.readString(), buffer.readString(), buffer.readString()};
            message.setUuId(uuId);
            message.setOriginalKeys(originalKeys);
            message.setKeys(keys);
        } catch (IllegalArgumentException e) {
            FarmersLife.LOGGER.warn("Exception while reading ManipulateKeybindingsMessageToClient: " + e);
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
        buffer.writeString(this.keys[0]);
        buffer.writeString(this.keys[1]);
        buffer.writeString(this.keys[2]);
        buffer.writeString(this.keys[3]);
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

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }

    public void setMessageIsValid(boolean messageIsValid) {
        this.messageIsValid = messageIsValid;
    }
}
