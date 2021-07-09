package com.asheriit.asheriitsfarmerslife.client.tileentity.renderer;

import com.asheriit.asheriitsfarmerslife.events.ModelRegistryEventHandler;
import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import com.asheriit.asheriitsfarmerslife.tileentity.BoilingCauldronTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class BoilingCauldronRenderer extends TileEntityRenderer<BoilingCauldronTileEntity> {
    public BoilingCauldronRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(BoilingCauldronTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        FluidTank inputTank = tileEntityIn.getTank(BoilingCauldronTileEntity.INPUT_FLUID_TANK);
        FluidTank outputTank = tileEntityIn.getTank(BoilingCauldronTileEntity.OUTPUT_FLUID_TANK);
        if ((inputTank.isEmpty() && outputTank.isEmpty()) || (tileEntityIn.getBlockState().getProperties().contains(ModBlockStateProperties.HAS_LID) &&
                tileEntityIn.getBlockState().get(ModBlockStateProperties.HAS_LID))) {
            return;
        }

        FluidStack inputStack;
        int capacity;
        if ((!inputTank.isEmpty() && !outputTank.isEmpty() && inputTank.getFluidAmount() > outputTank.getFluidAmount()) ||
                (!inputTank.isEmpty() && outputTank.isEmpty())) {
            inputStack = inputTank.getFluid();
            capacity = inputTank.getCapacity();
        } else {
            inputStack = outputTank.getFluid();
            capacity = outputTank.getCapacity();
        }

        // Get default must plane
        IBakedModel bakedModel = Minecraft.getInstance().getModelManager().getModel(ModelRegistryEventHandler.GENERIC_PLANE_WATER_STILL);
        IVertexBuilder vertexBuffer = bufferIn.getBuffer(RenderType.getTranslucent());
        int color = inputStack.getFluid().getAttributes().getColor();
        float red = ((color >> 16) & 0xFF) / 255f;
        float green = ((color >> 8) & 0xFF) / 255f;
        float blue = (color & 0xFF) / 255f;
        float renderHeight = (7.0F + ((15.0F - 7.0F) * ((float) inputStack.getAmount() / capacity))) / 16.0F;

        matrixStackIn.push();
        matrixStackIn.translate(0, renderHeight, 0);
        MatrixStack.Entry currentMatrix = matrixStackIn.getLast();
        Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(currentMatrix, vertexBuffer, null, bakedModel, red, green, blue, combinedLightIn, combinedOverlayIn, EmptyModelData.INSTANCE);
        matrixStackIn.pop();
    }
}
