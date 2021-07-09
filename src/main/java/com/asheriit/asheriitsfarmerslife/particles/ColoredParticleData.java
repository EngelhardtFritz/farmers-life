package com.asheriit.asheriitsfarmerslife.particles;

import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Locale;

public abstract class ColoredParticleData implements IParticleData {
    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;
    private final float particleMinHeight;

    public ColoredParticleData(float red, float green, float blue, float alpha, float particleMinHeight) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = MathHelper.clamp(alpha, 0.01F, 4.0F);
        this.particleMinHeight = particleMinHeight;
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeFloat(this.red);
        buffer.writeFloat(this.green);
        buffer.writeFloat(this.blue);
        buffer.writeFloat(this.alpha);
        buffer.writeFloat(this.particleMinHeight);
    }

    @Override
    public String getParameters() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %.2f",
                Registry.PARTICLE_TYPE.getKey(this.getType()), this.red, this.green, this.blue, this.alpha, this.particleMinHeight);
    }

    @OnlyIn(Dist.CLIENT)
    public float getRed() {
        return this.red;
    }

    @OnlyIn(Dist.CLIENT)
    public float getGreen() {
        return this.green;
    }

    @OnlyIn(Dist.CLIENT)
    public float getBlue() {
        return this.blue;
    }

    @OnlyIn(Dist.CLIENT)
    public float getAlpha() {
        return this.alpha;
    }

    @OnlyIn(Dist.CLIENT)
    public float getParticleMinHeight() {
        return this.particleMinHeight;
    }
}
