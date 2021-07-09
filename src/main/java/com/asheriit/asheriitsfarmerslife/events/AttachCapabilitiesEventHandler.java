package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.capabilities.CapabilityDrunkenState;
import com.asheriit.asheriitsfarmerslife.capabilities.CapabilityProviderDrunkenState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AttachCapabilitiesEventHandler {
    @SubscribeEvent
    public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getObject();
            if (!player.getCapability(CapabilityDrunkenState.CAPABILITY_DRUNK).isPresent()) {
                FarmersLife.LOGGER.info("[AttachCapabilitiesEventHandler::onAttachCapabilitiesEvent] Attach capabilities to PlayerEntities");
                event.addCapability(new ResourceLocation(FarmersLife.MOD_ID, "capability_provider_drunk"), new CapabilityProviderDrunkenState());
            }
        }
    }
}
