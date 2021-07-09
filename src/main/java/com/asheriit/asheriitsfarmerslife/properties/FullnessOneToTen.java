package com.asheriit.asheriitsfarmerslife.properties;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IStringSerializable;

import java.util.Optional;

public enum FullnessOneToTen implements IStringSerializable {
    EMPTY(0, "0%", "empty"),
    ONE_TENTH(1, "10%", "nearly empty"),
    ONE_FIFTH(2, "20%", "nearly empty"),
    THREE_TENTH(3, "30%", "nearly empty"),
    TWO_FIFTH(4, "40%", "nearly half full"),
    ONE_HALF(5, "50%", "half full"),
    THREE_FIFTH(6, "60%", "more than half full"),
    SEVEN_TENTH(7, "70%", "almost full"),
    FOUR_FIFTH(8, "80%", "almost full"),
    NINE_TENTH(9, "90%", "almost full"),
    FULL(10, "100%", "full");

    private final byte nbtId;
    private final String name;
    private final String description;

    FullnessOneToTen(int nbtId, String name, String description) {
        this.nbtId = (byte)nbtId;
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public byte getPropertyOverrideValue() {
        return this.nbtId;
    }

    @Override
    public String toString() {
        return this.description + ", " + this.name;
    }

    public FullnessOneToTen decreaseFullness() {
        if (this.nbtId == 0) return this;
        for (FullnessOneToTen fullness : FullnessOneToTen.values()) {
            if (fullness.nbtId == this.nbtId - 1) {
                return fullness;
            }
        }
        FarmersLife.LOGGER.error("[FarmersLife] Could not get fullness, returning default!");
        return this;
    }

    public FullnessOneToTen increaseFullness() {
        return FullnessOneToTen.FULL;
    }

    public static FullnessOneToTen fromNBT(CompoundNBT compoundNBT, String tagName) {
        byte fullnessNbtId = 0;
        if (compoundNBT != null && compoundNBT.contains(tagName)) {
            fullnessNbtId = compoundNBT.getByte(tagName);
        }
        Optional<FullnessOneToTen> fullness = getFullnessFromId(fullnessNbtId);
        return fullness.orElse(FullnessOneToTen.FULL);
    }

    public void toNBT(CompoundNBT compoundNBT, String tagName) {
        compoundNBT.putByte(tagName, this.nbtId);
    }

    private static Optional<FullnessOneToTen> getFullnessFromId(byte id){
        for (FullnessOneToTen fullness : FullnessOneToTen.values()) {
            if (fullness.nbtId == id) {
                return Optional.of(fullness);
            }
        }
        return Optional.empty();
    }
}
