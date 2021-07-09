package com.asheriit.asheriitsfarmerslife.network.to_client;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.network.PacketBuffer;

public class AlcoholLevelMessageToClient {
    private String uuId;
    private int alcoholLevel;
    private boolean isDrunk;
    private boolean messageIsValid;

    public AlcoholLevelMessageToClient(String uuId, int alcoholLevel, boolean isDrunk) {
        this.alcoholLevel = alcoholLevel;
        this.uuId = uuId;
        this.isDrunk = isDrunk;
        this.messageIsValid = true;
    }

    public AlcoholLevelMessageToClient() {
        this.messageIsValid = false;
    }

    public static AlcoholLevelMessageToClient decode(PacketBuffer buffer) {
        AlcoholLevelMessageToClient message = new AlcoholLevelMessageToClient();
        try {
            String uuId = buffer.readString();
            int alcoholLevel = buffer.readVarInt();
            boolean isDrunk = buffer.readBoolean();
            message.setUuId(uuId);
            message.setAlcoholLevel(alcoholLevel);
            message.setDrunk(isDrunk);
        } catch (IllegalArgumentException e) {
            FarmersLife.LOGGER.warn("Exception while reading AlcoholLevelMessageToClient: " + e);
            return message;
        }
        message.setMessageIsValid(true);
        return message;
    }

    public void encode(PacketBuffer buffer) {
        if (!this.messageIsValid) return;
        buffer.writeString(this.uuId);
        buffer.writeVarInt(this.alcoholLevel);
        buffer.writeBoolean(this.isDrunk);
    }

    public int getAlcoholLevel() {
        return alcoholLevel;
    }

    public void setAlcoholLevel(int alcoholLevel) {
        this.alcoholLevel = alcoholLevel;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }

    public void setMessageIsValid(boolean messageIsValid) {
        this.messageIsValid = messageIsValid;
    }

    public boolean isDrunk() {
        return isDrunk;
    }

    public void setDrunk(boolean drunk) {
        isDrunk = drunk;
    }

    @Override
    public String toString() {
        return "AlcoholLevelMessageToClient{" +
                "uuId='" + uuId + '\'' +
                ", alcoholLevel=" + alcoholLevel +
                ", isDrunk=" + isDrunk +
                ", messageIsValid=" + messageIsValid +
                '}';
    }
}
