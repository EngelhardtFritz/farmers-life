package com.asheriit.asheriitsfarmerslife.client.tileentity.renderer;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.models.FaceBuilder;
import com.asheriit.asheriitsfarmerslife.events.ModelRegistryEventHandler;
import com.asheriit.asheriitsfarmerslife.tileentity.AbstractClarificationMachineTileEntity;
import com.asheriit.asheriitsfarmerslife.tileentity.FiltrationMachineTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;

public class FiltrationMachineRenderer extends TileEntityRenderer<FiltrationMachineTileEntity> {
    public static final ResourceLocation FILTRATION_TEXTURE = new ResourceLocation(FarmersLife.MOD_ID, "textures/blocks/filtration_machine/filtration_filter.png");

    public FiltrationMachineRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(FiltrationMachineTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        IItemHandlerModifiable inventory = tileEntityIn.getInventory();
        ItemStack inputIngredientStack = inventory.getStackInSlot(AbstractClarificationMachineTileEntity.INPUT_ITEM_SLOT);

        if (!inputIngredientStack.isEmpty()) {
            Matrix4f matrixPos = matrixStackIn.getLast().getMatrix();
            Matrix3f matrixNormal = matrixStackIn.getLast().getNormal();
            IVertexBuilder vertexBuilder = bufferIn.getBuffer(RenderType.getEntityCutout(FILTRATION_TEXTURE));
            float min = 2.0F / 16.0F;
            float max = 14.0F / 16.0F;
            float height = 13.0F / 16.0F;
            Vector3f bottomLeftVertex = new Vector3f(min, height, max);
            Vector3f topRightVertex = new Vector3f(max, height, min);
            Vec2f uvBottomLeft = new Vec2f(min, max);
            Vec2f uvTopRight = new Vec2f(max, min);
            final int color = 0xFFFFFFFF;

            matrixStackIn.push();
            FaceBuilder.addFaceForDirection(Direction.UP, matrixPos, matrixNormal, vertexBuilder, bottomLeftVertex, topRightVertex, uvBottomLeft, uvTopRight, color, combinedLightIn);
            bottomLeftVertex.add(0, -(1.0F / 16.0F), 0);
            topRightVertex.add(0, -(1.0F / 16.0F), 0);
            FaceBuilder.addFaceForDirection(Direction.DOWN, matrixPos, matrixNormal, vertexBuilder, bottomLeftVertex, topRightVertex, uvBottomLeft, uvTopRight, color, combinedLightIn);
            matrixStackIn.pop();
        }

        FluidTank inputTank = tileEntityIn.getTank(AbstractClarificationMachineTileEntity.INPUT_TANK);
        FluidStack inputStack = inputTank.getFluid();
        if (!inputStack.isEmpty() || inputStack.getAmount() > 0) {
            // Get default must plane
            IBakedModel bakedModel = Minecraft.getInstance().getModelManager().getModel(ModelRegistryEventHandler.GENERIC_PLANE_MUST_STILL);
            IVertexBuilder vertexBuffer = bufferIn.getBuffer(RenderType.getTranslucent());
            int color = inputStack.getFluid().getAttributes().getColor();
            float red = ((color >> 16) & 0xFF) / 255f;
            float green = ((color >> 8) & 0xFF) / 255f;
            float blue = (color & 0xFF) / 255f;
            float renderHeight = (13.1F + ((15.0F - 13.1F) * ((float) inputStack.getAmount() / inputTank.getCapacity()))) / 16.0F;

            matrixStackIn.push();
            matrixStackIn.translate(0, renderHeight,0);
            MatrixStack.Entry currentMatrix = matrixStackIn.getLast();
            Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(currentMatrix, vertexBuffer, null, bakedModel, red, green, blue, combinedLightIn, combinedOverlayIn, EmptyModelData.INSTANCE);
            matrixStackIn.pop();
        }

        FluidTank outputTank = tileEntityIn.getTank(AbstractClarificationMachineTileEntity.OUTPUT_TANK);
        FluidStack outputStack = outputTank.getFluid();
        if (!outputStack.isEmpty() || outputStack.getAmount() > 0) {
            // Get default must plane
            IBakedModel bakedModel = Minecraft.getInstance().getModelManager().getModel(ModelRegistryEventHandler.GENERIC_PLANE_MUST_STILL);
            IVertexBuilder vertexBuffer = bufferIn.getBuffer(RenderType.getTranslucent());
            int color = outputStack.getFluid().getAttributes().getColor();
            float red = ((color >> 16) & 0xFF) / 255f;
            float green = ((color >> 8) & 0xFF) / 255f;
            float blue = (color & 0xFF) / 255f;
            float renderHeight = (1.1F + ((6.0F - 1.1F) * ((float) outputStack.getAmount() / outputTank.getCapacity()))) / 16.0F;

            matrixStackIn.push();
            matrixStackIn.translate(0, renderHeight,0);
            MatrixStack.Entry currentMatrix = matrixStackIn.getLast();
            Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(currentMatrix, vertexBuffer, null, bakedModel, red, green, blue, combinedLightIn, combinedOverlayIn, EmptyModelData.INSTANCE);
            matrixStackIn.pop();
        }
    }
}
