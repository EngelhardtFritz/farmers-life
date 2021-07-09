package com.asheriit.asheriitsfarmerslife.network.to_client;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.network.PacketBuffer;

import java.util.Arrays;

public class SyncDrunkenStateMessageToClient {
    private String[] originalKeys;
    private String[] keys;
    private int alcoholLevel;
    private boolean isDrunk;
    private int alcoholLevelTimeLeft;
    private String uuId;
    private boolean messageIsValid;

    public SyncDrunkenStateMessageToClient(String uuId, String[] originalKeys, String[] keys, int alcoholLevel, boolean isDrunk, int alcoholLevelTimeLeft) {
        this.originalKeys = originalKeys;
        this.keys = keys;
        if (keys.length > 0 && keys[0].length() < 3) {
            this.keys = new String[]{
                    "key.forward",
                    "key.left",
                    "key.right",
                    "key.back"
            };
        }

        this.alcoholLevel = alcoholLevel;
        this.isDrunk = isDrunk;
        this.alcoholLevelTimeLeft = alcoholLevelTimeLeft;
        this.uuId = uuId;
        this.messageIsValid = true;
    }

    public SyncDrunkenStateMessageToClient() {
        this.messageIsValid = false;
    }

    public String[] getOriginalKeys() {
        return originalKeys;
    }

    public void setOriginalKeys(String[] originalKeys) {
        this.originalKeys = originalKeys;
    }

    public int getAlcoholLevel() {
        return alcoholLevel;
    }

    public void setAlcoholLevel(int alcoholLevel) {
        this.alcoholLevel = alcoholLevel;
    }

    public boolean isDrunk() {
        return isDrunk;
    }

    public void setDrunk(boolean drunk) {
        isDrunk = drunk;
    }

    public int getAlcoholLevelTimeLeft() {
        return alcoholLevelTimeLeft;
    }

    public void setAlcoholLevelTimeLeft(int alcoholLevelTimeLeft) {
        this.alcoholLevelTimeLeft = alcoholLevelTimeLeft;
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

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public static SyncDrunkenStateMessageToClient decode(PacketBuffer buffer) {
        SyncDrunkenStateMessageToClient drunkenStateMessageToClient = new SyncDrunkenStateMessageToClient();
        try {
            String[] originalKeys = new String[]{buffer.readString(), buffer.readString(), buffer.readString(), buffer.readString()};
            String[] keys = new String[]{buffer.readString(), buffer.readString(), buffer.readString(), buffer.readString()};
            String uuId = buffer.readString();
            int alcoholLevel = buffer.readInt();
            boolean isDrunk = buffer.readBoolean();
            int alcoholLevelTimeLeft = buffer.readInt();
            drunkenStateMessageToClient.setOriginalKeys(originalKeys);
            drunkenStateMessageToClient.setKeys(keys);
            drunkenStateMessageToClient.setUuId(uuId);
            drunkenStateMessageToClient.setAlcoholLevel(alcoholLevel);
            drunkenStateMessageToClient.isDrunk = isDrunk;
            drunkenStateMessageToClient.setAlcoholLevelTimeLeft(alcoholLevelTimeLeft);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            FarmersLife.LOGGER.warn("Exception while reading SyncDrunkenStateMessageToClient: " + e);
            return drunkenStateMessageToClient;
        }
        drunkenStateMessageToClient.setMessageIsValid(true);
        return drunkenStateMessageToClient;
    }

    public void encode(PacketBuffer buffer) {
        if (!this.messageIsValid) return;

        buffer.writeString(this.originalKeys[0]);
        buffer.writeString(this.originalKeys[1]);
        buffer.writeString(this.originalKeys[2]);
        buffer.writeString(this.originalKeys[3]);
        buffer.writeString(this.keys[0]);
        buffer.writeString(this.keys[1]);
        buffer.writeString(this.keys[2]);
        buffer.writeString(this.keys[3]);
        buffer.writeString(this.uuId);
        buffer.writeInt(this.alcoholLevel);
        buffer.writeBoolean(this.isDrunk);
        buffer.writeInt(this.alcoholLevelTimeLeft);
    }

    @Override
    public String toString() {
        return "SyncDrunkenStateMessageToClient{" +
                "originalKeys=" + Arrays.toString(originalKeys) +
                ", keys=" + Arrays.toString(keys) +
                ", alcoholLevel=" + alcoholLevel +
                ", isDrunk=" + isDrunk +
                ", alcoholLevelTimeLeft=" + alcoholLevelTimeLeft +
                ", uuId='" + uuId + '\'' +
                ", messageIsValid=" + messageIsValid +
                '}';
    }
}
