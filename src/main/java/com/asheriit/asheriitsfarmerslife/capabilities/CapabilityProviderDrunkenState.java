package com.asheriit.asheriitsfarmerslife.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityProviderDrunkenState implements ICapabilitySerializable<INBT> {
    private static final Direction NO_SPECIFIC_SIDE = null;
    private DrunkenState drunkenState = new DrunkenState();

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (CapabilityDrunkenState.CAPABILITY_DRUNK == cap) {
            return (LazyOptional<T>) LazyOptional.of(() -> this.drunkenState);
        }
        return LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CapabilityDrunkenState.CAPABILITY_DRUNK.writeNBT(this.drunkenState, NO_SPECIFIC_SIDE);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityDrunkenState.CAPABILITY_DRUNK.readNBT(this.drunkenState, NO_SPECIFIC_SIDE, nbt);
    }
}
