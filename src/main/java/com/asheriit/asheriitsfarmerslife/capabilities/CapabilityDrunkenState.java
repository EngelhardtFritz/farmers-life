package com.asheriit.asheriitsfarmerslife.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityDrunkenState {
    @CapabilityInject(DrunkenState.class)
    public static Capability<DrunkenState> CAPABILITY_DRUNK = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(
                DrunkenState.class,
                new DrunkenState.DrunkenStateNBTStorage(),
                DrunkenState::createInstance);
    }
}
