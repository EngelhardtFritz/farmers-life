package com.asheriit.asheriitsfarmerslife.properties;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IStringSerializable;

import java.util.Optional;

public enum FullnessOneToFive implements IStringSerializable {
    EMPTY(0, "0%", "empty"),
    ONE_FIFTH(1, "20%", "nearly empty"),
    TWO_FIFTH(2, "40%", "almost half full"),
    THREE_FIFTH(3, "60%", "more than half full"),
    FOUR_FIFTH(4, "80%", "almost full"),
    FULL(5, "100%", "full");

    private final byte nbtId;
    private final String name;
    private final String description;

    FullnessOneToFive(int nbtId, String name, String description) {
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

    public FullnessOneToFive decreaseFullness() {
        if (this.nbtId == 0) return this;
        for (FullnessOneToFive fullness : FullnessOneToFive.values()) {
            if (fullness.nbtId == this.nbtId - 1) {
                return fullness;
            }
        }
        FarmersLife.LOGGER.error("[FarmersLife] Could not get fullness, returning default!");
        return this;
    }

    public FullnessOneToFive increaseFullness() {
        return FullnessOneToFive.FULL;
    }

    public static FullnessOneToFive fromNBT(CompoundNBT compoundNBT, String tagName) {
        byte fullnessNbtId = 0;
        if (compoundNBT != null && compoundNBT.contains(tagName)) {
            fullnessNbtId = compoundNBT.getByte(tagName);
        }
        Optional<FullnessOneToFive> fullness = getFullnessFromId(fullnessNbtId);
        return fullness.orElse(FullnessOneToFive.FULL);
    }

    public void toNBT(CompoundNBT compoundNBT, String tagName) {
        compoundNBT.putByte(tagName, this.nbtId);
    }

    private static Optional<FullnessOneToFive> getFullnessFromId(byte id){
        for (FullnessOneToFive fullness : FullnessOneToFive.values()) {
            if (fullness.nbtId == id) {
                return Optional.of(fullness);
            }
        }
        return Optional.empty();
    }
}
