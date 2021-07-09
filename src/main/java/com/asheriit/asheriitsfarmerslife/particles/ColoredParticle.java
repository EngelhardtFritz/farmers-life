package com.asheriit.asheriitsfarmerslife.particles;

import com.asheriit.asheriitsfarmerslife.init.ModParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.particle.*;
import net.minecraft.fluid.IFluidState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class ColoredParticle extends SpriteTexturedParticle {
    protected ColoredParticleData colorData;

    public ColoredParticle(World world, double xCoordIn, double yCoordIn, double zCoordIn, ColoredParticleData particleData, IAnimatedSprite sprite) {
        super(world, xCoordIn, yCoordIn, zCoordIn);
        this.colorData = particleData;
        this.setSize(0.01F, 0.01F);
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.ageControl();
        if (!this.isExpired) {
            this.motionY -= (double) this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);
            this.calculateExtraInformation();
            if (!this.isExpired) {
                this.motionX *= (double) 0.98F;
                this.motionY *= (double) 0.98F;
                this.motionZ *= (double) 0.98F;
                BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
                IFluidState ifluidstate = this.world.getFluidState(blockpos);
                if (this.posY < (double) ((float) blockpos.getY() + ifluidstate.getActualHeight(this.world, blockpos))) {
                    this.setExpired();
                }
            }
        }
    }

    protected void ageControl() {
        if (this.maxAge-- <= 0) {
            this.setExpired();
        }
    }

    protected void calculateExtraInformation() {
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    static class DrippingColoredParticle extends ColoredParticle {
        public DrippingColoredParticle(World world, double xCoordIn, double yCoordIn, double zCoordIn, ColoredParticleData particleData, IAnimatedSprite sprite) {
            super(world, xCoordIn, yCoordIn, zCoordIn, particleData, sprite);
            this.particleGravity = 0.06F * 0.02F;
            this.maxAge = 40;
        }

        @Override
        protected void ageControl() {
            if (this.maxAge-- <= 0) {
                this.setExpired();
                this.world.addParticle(new FallingColoredParticleData(this.colorData.getRed(), this.colorData.getGreen(), this.colorData.getBlue(), this.colorData.getAlpha(), this.colorData.getParticleMinHeight()),
                        this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ);
            }
        }

        @Override
        protected void calculateExtraInformation() {
            this.motionX *= 0.02D;
            this.motionY *= 0.02D;
            this.motionZ *= 0.02D;
        }
    }

    static class FallingColoredParticle extends ColoredParticle {
        public FallingColoredParticle(World world, double xCoordIn, double yCoordIn, double zCoordIn, ColoredParticleData particleData, IAnimatedSprite sprite) {
            super(world, xCoordIn, yCoordIn, zCoordIn, particleData, sprite);
            this.particleGravity = 0.06F;
            this.maxAge = (int) (64.0D / (Math.random() * 0.8D + 0.2D));
        }

        @Override
        protected void ageControl() {
        }

        @Override
        protected void calculateExtraInformation() {
            if (this.onGround || (this.posY < this.colorData.getParticleMinHeight())) {
                this.setExpired();
                // TODO: NOTE add splash colored particle if required at this location
//            this.world.addParticle(this.field_228335_a_, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    // -------------------------------- Factory Classes --------------------------------
    @OnlyIn(Dist.CLIENT)
    public static class DrippingFactory implements IParticleFactory<DrippingColoredParticleData> {
        protected final IAnimatedSprite spriteSet;

        public DrippingFactory(IAnimatedSprite spriteIn) {
            this.spriteSet = spriteIn;
        }

        @Nullable
        @Override
        public Particle makeParticle(DrippingColoredParticleData typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DrippingColoredParticle drippingColoredParticle = new DrippingColoredParticle(worldIn, x, y, z, typeIn, this.spriteSet);
            drippingColoredParticle.setColor(typeIn.getRed(), typeIn.getGreen(), typeIn.getBlue());
            drippingColoredParticle.selectSpriteRandomly(this.spriteSet);
            return drippingColoredParticle;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class FallingFactory implements IParticleFactory<FallingColoredParticleData> {
        protected final IAnimatedSprite spriteSet;

        public FallingFactory(IAnimatedSprite spriteIn) {
            this.spriteSet = spriteIn;
        }

        @Nullable
        @Override
        public Particle makeParticle(FallingColoredParticleData typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            FallingColoredParticle fallingColoredParticle = new FallingColoredParticle(worldIn, x, y, z, typeIn, this.spriteSet);
            fallingColoredParticle.setColor(typeIn.getRed(), typeIn.getGreen(), typeIn.getBlue());
            fallingColoredParticle.selectSpriteRandomly(this.spriteSet);
            return fallingColoredParticle;
        }
    }


    // -------------------------------- ColoredData classes --------------------------------
    public static class DrippingColoredParticleData extends ColoredParticleData {
        public static final IParticleData.IDeserializer<DrippingColoredParticleData> DESERIALIZER = new IParticleData.IDeserializer<DrippingColoredParticleData>() {
            @Override
            public DrippingColoredParticleData deserialize(ParticleType<DrippingColoredParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
                reader.expect(' ');
                float red = (float) reader.readDouble();
                reader.expect(' ');
                float green = (float) reader.readDouble();
                reader.expect(' ');
                float blue = (float) reader.readDouble();
                reader.expect(' ');
                float alpha = (float) reader.readDouble();
                reader.expect(' ');
                float particleMinHeight = (float) reader.readDouble();
                return new DrippingColoredParticleData(red, green, blue, alpha, particleMinHeight);
            }

            @Override
            public DrippingColoredParticleData read(ParticleType<DrippingColoredParticleData> particleTypeIn, PacketBuffer buffer) {
                return new DrippingColoredParticleData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
            }
        };

        public DrippingColoredParticleData(float red, float green, float blue, float alpha, float particleMinHeight) {
            super(red, green, blue, alpha, particleMinHeight);
        }

        @Override
        public ParticleType<?> getType() {
            return ModParticles.DRIPPING_COLORED_PARTICLE_TYPE.get();
        }
    }

    public static class FallingColoredParticleData extends ColoredParticleData {
        public static final IParticleData.IDeserializer<FallingColoredParticleData> DESERIALIZER = new IParticleData.IDeserializer<FallingColoredParticleData>() {
            @Override
            public FallingColoredParticleData deserialize(ParticleType<FallingColoredParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
                reader.expect(' ');
                float red = (float) reader.readDouble();
                reader.expect(' ');
                float green = (float) reader.readDouble();
                reader.expect(' ');
                float blue = (float) reader.readDouble();
                reader.expect(' ');
                float alpha = (float) reader.readDouble();
                reader.expect(' ');
                float particleMinHeight = (float) reader.readDouble();
                return new FallingColoredParticleData(red, green, blue, alpha, particleMinHeight);
            }

            @Override
            public FallingColoredParticleData read(ParticleType<FallingColoredParticleData> particleTypeIn, PacketBuffer buffer) {
                return new FallingColoredParticleData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
            }
        };

        public FallingColoredParticleData(float red, float green, float blue, float alpha, float particleMinHeight) {
            super(red, green, blue, alpha, particleMinHeight);
        }

        @Override
        public ParticleType<?> getType() {
            return ModParticles.FALLING_COLORED_PARTICLE_TYPE.get();
        }
    }
}
