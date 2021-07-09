package com.asheriit.asheriitsfarmerslife.client.tileentity.renderer;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.models.FaceBuilder;
import com.asheriit.asheriitsfarmerslife.tileentity.FertilizerComposterTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.items.IItemHandlerModifiable;

public class FertilizerComposterRenderer extends TileEntityRenderer<FertilizerComposterTileEntity> {
    public static final ResourceLocation COMPOSTER_TEXTURE = new ResourceLocation(FarmersLife.MOD_ID, "textures/blocks/fertilizer_composter/fertilizer_composter_compost.png");

    public FertilizerComposterRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(FertilizerComposterTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        IItemHandlerModifiable inventory = tileEntityIn.getInventory();
        final int slotCount = 4;
        int maxItemCount = 0;
        float curItemCount = 0;

        for (int index = 0; index < slotCount; index++) {
            maxItemCount += inventory.getSlotLimit(index);
            curItemCount += inventory.getStackInSlot(index).getCount();
        }
        curItemCount = Math.min(maxItemCount, curItemCount * 2);

        if (maxItemCount > 0 && curItemCount > 0) {
            // calculate render height
            int maxHeight = 15;
            int minHeight = 4;
            int heightRange = maxHeight - minHeight;
            float itemCountPercentage = curItemCount / maxItemCount;
            float renderHeight = (4.0F + (heightRange * itemCountPercentage)) / 16.0F;
            // Set variables for rendering a single face
            MatrixStack.Entry matrixStack = matrixStackIn.getLast();
            Matrix4f matrix = matrixStack.getMatrix();
            Matrix3f matrixNormal = matrixStack.getNormal();
            IVertexBuilder vertexBuilder = bufferIn.getBuffer(RenderType.getEntitySolid(COMPOSTER_TEXTURE));
            float smallValue = 2.0F / 16.0F;
            float largeValue = 14.0F / 16.0F;
            Vector3f bottomLeft = new Vector3f(smallValue, renderHeight, largeValue);
            Vector3f topRight = new Vector3f(largeValue, renderHeight, smallValue);
            Vec2f uvBotLeft = new Vec2f(smallValue, largeValue);
            Vec2f uvTopRight = new Vec2f(largeValue, smallValue);
            int fullColor = 0xFFFFFFFF;

            matrixStackIn.push();
            FaceBuilder.addFaceForDirection(Direction.UP, matrix, matrixNormal, vertexBuilder, bottomLeft, topRight, uvBotLeft, uvTopRight, fullColor, combinedLightIn);
            matrixStackIn.pop();
        }
    }
}
