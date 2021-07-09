package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.RenderTypeHelper;
import com.asheriit.asheriitsfarmerslife.init.ModEffects;
import com.asheriit.asheriitsfarmerslife.objects.effects.ShowOreOutlineEffect;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RenderWorldLastEventHandler {
    @SubscribeEvent
    public static void onRenderWorldLastEvent(RenderWorldLastEvent event) {
        Entity entity = Minecraft.getInstance().getRenderViewEntity();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (!player.isPotionActive(ModEffects.SHOW_ORES_STAGE_1.get()) &&
                    !player.isPotionActive(ModEffects.SHOW_ORES_STAGE_2.get()) &&
                    !player.isPotionActive(ModEffects.SHOW_ORES_STAGE_3.get())) {
                return;
            }

            int range;
            float red;
            float green;
            float blue;
            final float alpha = 0.1F;
            ShowOreOutlineEffect effect;
            if (player.isPotionActive(ModEffects.SHOW_ORES_STAGE_3.get())) {
                range = 20;
                red = 0.9F;
                green = 0.2F;
                blue = 0.2F;
                effect = ModEffects.SHOW_ORES_STAGE_3.get();
            } else if (player.isPotionActive(ModEffects.SHOW_ORES_STAGE_2.get())) {
                range = 12;
                red = 0.9F;
                green = 0.64F;
                blue = 0.3F;
                effect = ModEffects.SHOW_ORES_STAGE_2.get();
            } else if (player.isPotionActive(ModEffects.SHOW_ORES_STAGE_1.get())) {
                range = 6;
                red = 0.9F;
                green = 0.9F;
                blue = 0.3F;
                effect = ModEffects.SHOW_ORES_STAGE_1.get();
            } else {
                return;
            }

            if (effect.getBlockTag() == null) {
                return;
            }

            Vec3d playerPos = new Vec3d(player.getPosX(), player.getPosY(), player.getPosZ());
            Vec3d projectedView = Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView();
            MatrixStack matrixStack = event.getMatrixStack();
            BlockPos zeroPos = new BlockPos(playerPos);
            World world = player.getEntityWorld();

            IRenderTypeBuffer.Impl buffer = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
            matrixStack.push();
            matrixStack.translate(-projectedView.x, -projectedView.y, -projectedView.z);
            RenderSystem.disableDepthTest();
            IVertexBuilder vertexBuilder = buffer.getBuffer(RenderTypeHelper.LINE_RENDERER_WITHOUT_DEPTH_TEST);

            Direction playerFacingDirection = player.getAdjustedHorizontalFacing();
            // TODO: Improve slightly by using the rotation yaw and adding four additional directions: NorthEast, NorthWest, SouthEast, SouthWest!
            int minRangeX = range;
            int minRangeZ = range;
            int maxRangeX = range;
            int maxRangeZ = range;
            int rangeY = range;
            if (playerPos.getY() < range) {
                rangeY = (int) Math.round(playerPos.getY() - 1);
                if (rangeY < 0) rangeY = 0;
            }

            if (playerFacingDirection == Direction.NORTH) {
                maxRangeZ = 0;
                minRangeZ *= 1.2;
                minRangeX /= 1.5;
                maxRangeX /= 1.5;
            } else if (playerFacingDirection == Direction.EAST) {
                minRangeX = 0;
                maxRangeX *= 1.2;
                minRangeZ /= 1.5;
                maxRangeZ /= 1.5;
            } else if (playerFacingDirection == Direction.SOUTH) {
                minRangeZ = 0;
                maxRangeZ *= 1.2;
                minRangeX /= 1.5;
                maxRangeX /= 1.5;
            } else if (playerFacingDirection == Direction.WEST) {
                maxRangeX = 0;
                minRangeX *= 1.2;
                minRangeZ /= 1.5;
                maxRangeZ /= 1.5;
            }

            // TODO: May render in full clusters instead of rendering each ore block
            int maxBlocksToDraw = 400;
            for (int x = -minRangeX; x < maxRangeX; x++) {
                for (int y = -rangeY; y < range / 2; y++) {
                    for (int z = -minRangeZ; z < maxRangeZ; z++) {
                        BlockPos toValidate = new BlockPos(zeroPos.getX() + x, zeroPos.getY() + y, zeroPos.getZ() + z);
                        if (world.getBlockState(toValidate).isIn(effect.getBlockTag()) && maxBlocksToDraw != 0) {
                            maxBlocksToDraw--;
                            WorldRenderer.drawBoundingBox(matrixStack, vertexBuilder, toValidate.getX(), toValidate.getY(), toValidate.getZ(),
                                    toValidate.getX() + 1, toValidate.getY() + 1, toValidate.getZ() + 1, red, green, blue, alpha);
                        }
                    }
                }
            }
            matrixStack.pop();
            buffer.finish();
        }
    }
}
