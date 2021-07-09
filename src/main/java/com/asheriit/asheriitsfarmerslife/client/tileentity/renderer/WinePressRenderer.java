package com.asheriit.asheriitsfarmerslife.client.tileentity.renderer;

import com.asheriit.asheriitsfarmerslife.api.utils.AnimationTimingHelper;
import com.asheriit.asheriitsfarmerslife.api.utils.models.FaceBuilder;
import com.asheriit.asheriitsfarmerslife.events.ModelRegistryEventHandler;
import com.asheriit.asheriitsfarmerslife.objects.models.PaneModel;
import com.asheriit.asheriitsfarmerslife.tileentity.WinePressHandleTileEntity;
import com.asheriit.asheriitsfarmerslife.tileentity.WinePressTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;

public class WinePressRenderer extends TileEntityRenderer<WinePressTileEntity> {
    public WinePressRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    /**
     * Renders three parts depending on the state and content of the master and slave tile entities:
     * - If the inventory contains output fluid render it considering the amount of fluid
     * - If the inventory contains input items they are rendered on the bottom of the press
     * - Depending on the progress of the recipe the press handle is rendered and animated
     *
     * @param tileEntityIn:      The tile entity to get data required for the render
     * @param partialTicks:      Amount of partial tick passed
     * @param matrixStackIn:     The matrix stack (rotation and translation for the model is done here)
     * @param bufferIn:          The render buffer
     * @param combinedLightIn:   Light
     * @param combinedOverlayIn: Overlay
     */
    @Override
    public void render(@Nonnull WinePressTileEntity tileEntityIn, float partialTicks, @Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        // Render slave data for wine press handle
        World world = tileEntityIn.getWorld();
        BlockPos slavePos = tileEntityIn.getSlave();
        if (world != null && slavePos != null) {
            TileEntity te = world.getTileEntity(slavePos);
            if (te instanceof WinePressHandleTileEntity) {
                WinePressHandleTileEntity winePressHandleTileEntity = (WinePressHandleTileEntity) te;
                Vector3f slaveOffset = new Vector3f(0, 1.0F, 0);
                this.renderWinePressHandleSlave(winePressHandleTileEntity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, slaveOffset);
            }
        }

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

    /**
     * Render the press handle like described in {@link #render(WinePressTileEntity, float, MatrixStack, IRenderTypeBuffer, int, int) render}
     *
     * @param tileEntityIn:      The tile entity to get data required for the render
     * @param partialTicks:      Amount of partial tick passed
     * @param matrixStackIn:     The matrix stack (rotation and translation for the model is done here)
     * @param bufferIn:          The render buffer
     * @param combinedLightIn:   Light
     * @param combinedOverlayIn: Overlay
     */
    private void renderWinePressHandleSlave(WinePressHandleTileEntity tileEntityIn, float partialTicks, @Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, Vector3f renderOffset) {
        int maxExtension = 12;
        int minExtension = 0;

        int currentSteps = tileEntityIn.getProgressStepsCurrent();
        int totalSteps = tileEntityIn.getProgressStepsTotal();
        float currentAnimationTime = AnimationTimingHelper.getElapsedTime() + partialTicks;

        float singleExtensionWidth;
        float currentExtensionWidth;

        // Set parameters for animation triggered -> will be reset if an invalid value comes in next
        if (tileEntityIn.isAnimationActivated() && currentSteps >= 0) {
            tileEntityIn.setAnimationEnd(currentAnimationTime + tileEntityIn.getAnimationLength());
            tileEntityIn.setAnimationActivated(false);
        }

        // If the recipe is invalid set default values for rendering
        if (currentSteps < 0 || totalSteps < 0) {
            currentSteps = 0;
            totalSteps = 0;
            tileEntityIn.setAnimationEnd(currentAnimationTime);
        }

        singleExtensionWidth = (maxExtension - minExtension) * (1.0F / totalSteps);
        currentExtensionWidth = singleExtensionWidth * currentSteps;

        // calculate animation step size considering the animation length
        float animationStepWeight = 1.0F / WinePressTileEntity.ANIMATION_LENGTH;
        float remainingAnimationTime = tileEntityIn.getAnimationEnd() - currentAnimationTime;
        if (remainingAnimationTime < 0) remainingAnimationTime = 0;

        // Render model differently if it is in animation state
        if (remainingAnimationTime > 0) {
            // Render at currentExtensionWidth + (singleExtensionWidth * animationStepWeight) * (WinePressTileEntity.ANIMATION_LENGTH - remainingAnimationTime)
            // If the animation is triggered the current extension width is dependent on the start step saved at animation start
            currentExtensionWidth = singleExtensionWidth * tileEntityIn.getAnimationStartStep();
            // Calculate the extension width which should be added to the current extension width
            // Cannot get larger than the single extension width
            float extension = (singleExtensionWidth * animationStepWeight) * (WinePressTileEntity.ANIMATION_LENGTH - remainingAnimationTime);
            float renderHeight = currentExtensionWidth + extension;

            matrixStackIn.push();
            // Get the baked model for the registered model
            IBakedModel bakedModel = Minecraft.getInstance().getModelManager().getModel(ModelRegistryEventHandler.WINE_PRESS_HANDLE);
            // Get buffer for render type solid since the press handle contains only solid faces
            IVertexBuilder vertexBuffer = bufferIn.getBuffer(RenderType.getSolid());
            MatrixStack.Entry currentMatrix = matrixStackIn.getLast();
            // Translate the model to the calculated position (depending on the current progress of the recipe and the animation state)
            matrixStackIn.translate(0 + renderOffset.getX(), -(renderHeight / 16.0F) + renderOffset.getY(), 0 + renderOffset.getZ());
            // Render the model with all parameters
            // Note: since no coloring takes place all color values can be set to 1.0F
            Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(currentMatrix, vertexBuffer, null, bakedModel, 1, 1, 1, combinedLightIn, combinedOverlayIn, EmptyModelData.INSTANCE);
            matrixStackIn.pop();
        } else {
            // Render at currentExtensionWidth
            // At any time when this is reached the animation is nearly finished
            tileEntityIn.setAnimationStartStep(0);
            if (totalSteps <= 0) currentExtensionWidth = 0;
            // TODO: move the model up to step 0

            matrixStackIn.push();
            // Get the baked model for the registered model
            IBakedModel bakedModel = Minecraft.getInstance().getModelManager().getModel(ModelRegistryEventHandler.WINE_PRESS_HANDLE);
            // Get buffer for render type solid since the press handle contains only solid faces
            IVertexBuilder vertexBuffer = bufferIn.getBuffer(RenderType.getSolid());
            MatrixStack.Entry currentMatrix = matrixStackIn.getLast();
            // Translate the model to the calculated position (depending on the current progress of the recipe)
            matrixStackIn.translate(0 + renderOffset.getX(), -(currentExtensionWidth / 16.0F) + renderOffset.getY(), 0 + renderOffset.getZ());
            // Render the model with all parameters
            // Note: since no coloring takes place all color values can be set to 1.0F
            Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(currentMatrix, vertexBuffer, null, bakedModel, 1.0F, 1.0F, 1.0F, combinedLightIn, combinedOverlayIn, EmptyModelData.INSTANCE);
            matrixStackIn.pop();
        }
    }
}
