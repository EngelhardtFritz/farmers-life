package com.asheriit.asheriitsfarmerslife.init;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.particles.ColoredParticle;
import com.asheriit.asheriitsfarmerslife.particles.ColoredParticle.DrippingColoredParticleData;
import com.asheriit.asheriitsfarmerslife.particles.ColoredParticle.FallingColoredParticleData;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, FarmersLife.MOD_ID);

    public static final RegistryObject<ParticleType<DrippingColoredParticleData>> DRIPPING_COLORED_PARTICLE_TYPE = PARTICLE_TYPES.register("colored_particle",
            () -> new ParticleType<DrippingColoredParticleData>(false, DrippingColoredParticleData.DESERIALIZER));

    public static final RegistryObject<ParticleType<FallingColoredParticleData>> FALLING_COLORED_PARTICLE_TYPE = PARTICLE_TYPES.register("falling_colored_particle",
            () -> new ParticleType<FallingColoredParticleData>(false, FallingColoredParticleData.DESERIALIZER));

    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(ModParticles.DRIPPING_COLORED_PARTICLE_TYPE.get(), ColoredParticle.DrippingFactory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.FALLING_COLORED_PARTICLE_TYPE.get(), ColoredParticle.FallingFactory::new);
    }
}
