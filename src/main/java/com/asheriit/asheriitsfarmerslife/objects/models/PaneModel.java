package com.asheriit.asheriitsfarmerslife.objects.models;

import com.asheriit.asheriitsfarmerslife.api.utils.models.FaceBuilder;
import com.asheriit.asheriitsfarmerslife.tileentity.StompingBarrelTileEntity;
import com.asheriit.asheriitsfarmerslife.tileentity.WinePressTileEntity;
import com.asheriit.asheriitsfarmerslife.api.utils.AnimationTimingHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.*;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;

public class PaneModel {

    public static void renderPaneModelWithAnimatedTextureUsingVertices(WinePressTileEntity tileEntity, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLight, int combinedOverlay,
                                                                       Vector3f topLeft, Vector3f bottomRight, ResourceLocation texture, int color, float partialTicks) {
        matrixStack.push();
        Matrix4f matrixPos = matrixStack.getLast().getMatrix();
        Matrix3f matrixNormal = matrixStack.getLast().getNormal();

        int currentAnimation = tileEntity.getCurrentUVIndex();
        float currentTicks = AnimationTimingHelper.getElapsedTime() + partialTicks;
        float ticksToNextAnimationSwitch = tileEntity.getNextTextureAnimationTime();

        if (currentTicks >= ticksToNextAnimationSwitch) {
            tileEntity.setNextTextureAnimationTime(currentTicks + tileEntity.getFrametime());
            currentAnimation++;
            if (currentAnimation == 32) {
                currentAnimation = 0;
            }
            tileEntity.setCurrentUVIndex(currentAnimation);
        }

        Vector3f topRight = new Vector3f(bottomRight.getX(), topLeft.getY(), topLeft.getZ());
        Vector3f bottomLeft = new Vector3f(topLeft.getX(), topLeft.getY(), bottomRight.getZ());
        Vector3f normal = Direction.UP.toVector3f();
        IVertexBuilder vertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntityTranslucent(texture));
        FaceBuilder.addVertex(matrixPos, matrixNormal, vertexBuilder, bottomLeft, new Vec2f(0, (1 + currentAnimation) / 32.0F), normal, color, combinedLight);
        FaceBuilder.addVertex(matrixPos, matrixNormal, vertexBuilder, bottomRight, new Vec2f(1, (1 + currentAnimation) / 32.0F), normal, color, combinedLight);
        FaceBuilder.addVertex(matrixPos, matrixNormal, vertexBuilder, topRight, new Vec2f(1, currentAnimation / 32.0F), normal, color, combinedLight);
        FaceBuilder.addVertex(matrixPos, matrixNormal, vertexBuilder, topLeft, new Vec2f(0, currentAnimation / 32.0F), normal, color, combinedLight);

        matrixStack.pop();
    }

    public static void renderPaneModelWithAnimatedTextureUsingVertices(StompingBarrelTileEntity tileEntity, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLight, int combinedOverlay,
                                                                       Vector3f topLeft, Vector3f bottomRight, ResourceLocation texture, int color, float partialTicks) {
        matrixStack.push();
        Matrix4f matrixPos = matrixStack.getLast().getMatrix();
        Matrix3f matrixNormal = matrixStack.getLast().getNormal();

        int currentAnimation = tileEntity.getCurrentUVIndex();
        float currentTicks = AnimationTimingHelper.getElapsedTime() + partialTicks;
        float ticksToNextAnimationSwitch = tileEntity.getNextTextureAnimationTime();

        if (currentTicks >= ticksToNextAnimationSwitch) {
            tileEntity.setNextTextureAnimationTime(currentTicks + tileEntity.getFrametime());
            currentAnimation++;
            if (currentAnimation == 32) {
                currentAnimation = 0;
            }
            tileEntity.setCurrentUVIndex(currentAnimation);
        }

        Vector3f topRight = new Vector3f(bottomRight.getX(), topLeft.getY(), topLeft.getZ());
        Vector3f bottomLeft = new Vector3f(topLeft.getX(), topLeft.getY(), bottomRight.getZ());
        Vector3f normal = Direction.UP.toVector3f();
        IVertexBuilder vertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntityTranslucent(texture));
        FaceBuilder.addVertex(matrixPos, matrixNormal, vertexBuilder, bottomLeft, new Vec2f(0, (1 + currentAnimation) / 32.0F), normal, color, combinedLight);
        FaceBuilder.addVertex(matrixPos, matrixNormal, vertexBuilder, bottomRight, new Vec2f(1, (1 + currentAnimation) / 32.0F), normal, color, combinedLight);
        FaceBuilder.addVertex(matrixPos, matrixNormal, vertexBuilder, topRight, new Vec2f(1, currentAnimation / 32.0F), normal, color, combinedLight);
        FaceBuilder.addVertex(matrixPos, matrixNormal, vertexBuilder, topLeft, new Vec2f(0, currentAnimation / 32.0F), normal, color, combinedLight);
        matrixStack.pop();
    }
}
