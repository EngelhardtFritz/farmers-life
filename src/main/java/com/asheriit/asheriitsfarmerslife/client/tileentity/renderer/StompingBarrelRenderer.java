package com.asheriit.asheriitsfarmerslife.client.tileentity.renderer;

import com.asheriit.asheriitsfarmerslife.api.utils.models.FaceBuilder;
import com.asheriit.asheriitsfarmerslife.objects.models.PaneModel;
import com.asheriit.asheriitsfarmerslife.tileentity.StompingBarrelTileEntity;
import com.asheriit.asheriitsfarmerslife.tileentity.WinePressTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class StompingBarrelRenderer extends TileEntityRenderer<StompingBarrelTileEntity> {
    public static final ResourceLocation WATER_OVERLAY = new ResourceLocation("minecraft", "textures/block/water_overlay.png");

    public StompingBarrelRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(StompingBarrelTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        FluidTank tank = tileEntityIn.getTank(WinePressTileEntity.OUTPUT_TANK_SLOT);
        ItemStack stackToRender = tileEntityIn.getInventory().getStackInSlot(WinePressTileEntity.INPUT_ITEM_SLOT);
        // Texture UV coordinates full 16x16 texture
        Vec2f uvBottomLeft = new Vec2f(0.0F, 1.0F);
        Vec2f uvTopRight = new Vec2f(1.0F, 0.0F);
        // Minimum and maximum height for rendering the fluid
        int maxHeight = 15;
        int minHeight = 4;

        // Percentage for a single stack item and percentage of the tank fullness
        float stepWeight = ((tank.getFluidAmount() + 0.0F) / tank.getCapacity());
        float stackStepWeight = (16.0F - 5.0F) / stackToRender.getMaxStackSize();

        // Relative height of the fluid
        float fluidRelativeHeight = (maxHeight - minHeight) * stepWeight;

        // Only render items if items are in the inventory
        if (!stackToRender.isEmpty() && !tileEntityIn.outputIsFull()) {
            MatrixStack.Entry entry = matrixStackIn.getLast();
            Item itemToRender = stackToRender.getItem();
            ResourceLocation itemResourceLocation = itemToRender.getRegistryName();
            // IMPORTANT: If new recipes are added to the stomping barrel an extra texture is required
            // with the path schematic of "namespace:textures/blocks/wine_press/fluidPath.png" where the namespace is your mod_id and the fluidPath is the name/path of/to your texture file
            ResourceLocation buildResourceLocation = new ResourceLocation(itemResourceLocation.getNamespace(), "textures/blocks/wine_press/" + itemResourceLocation.getPath() + ".png");
            IVertexBuilder vertexBuffer = bufferIn.getBuffer(RenderType.getEntitySolid(buildResourceLocation));

            Matrix4f matrix = entry.getMatrix();
            Matrix3f matrixNormal = entry.getNormal();

            float renderHeight = (4.0F + stackStepWeight * stackToRender.getCount()) / 16.0F;
            float stackHeight = Math.max(4.0F / 16.0F, renderHeight - (fluidRelativeHeight / 16.0F));

            Vector3f bottomLeft = new Vector3f(1.0F / 16.0F, stackHeight, 15.0F / 16.0F);
            Vector3f topRight = new Vector3f(15.0F / 16.0F, stackHeight, 1.0F / 16.0F);

            matrixStackIn.push();
            FaceBuilder.addFaceForDirection(Direction.UP, matrix, matrixNormal, vertexBuffer, bottomLeft, topRight, uvBottomLeft, uvTopRight, 0xffffffff, combinedLightIn);
            matrixStackIn.pop();
        }

        // Only render the fluid if the tank contains a fluid
        Fluid fluidToRender = tileEntityIn.getFluidInTank(WinePressTileEntity.OUTPUT_TANK_SLOT).getFluid();
        if (fluidToRender != null && fluidToRender != Fluids.EMPTY) {
            // Calculate the height to render the fluid depending on input items
            float fluidHeight = Math.min(15.0F, 4.0F + (fluidRelativeHeight + stackStepWeight * stackToRender.getCount()));

            // Get the hex color from the fluid attributes
            int color = fluidToRender.getAttributes().getColor();
            // Get fluid texture location (In the case of this mod the resource location should always be the water texture)
            ResourceLocation fluidTexture = new ResourceLocation(fluidToRender.getAttributes().getStillTexture().getNamespace(), "textures/" + fluidToRender.getAttributes().getStillTexture().getPath() + ".png");

            // Render fluid texture plane
            Vector3f topLeft = new Vector3f(1.0F / 16, fluidHeight / 16, 1.0F / 16);
            Vector3f bottomRight = new Vector3f(15.0F / 16, fluidHeight / 16, 15.0F / 16);
            PaneModel.renderPaneModelWithAnimatedTextureUsingVertices(tileEntityIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, topLeft, bottomRight, fluidTexture, color, partialTicks);
        }
    }
}
