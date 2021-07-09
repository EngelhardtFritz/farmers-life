package com.asheriit.asheriitsfarmerslife.client.tileentity.renderer;

import com.asheriit.asheriitsfarmerslife.tileentity.WineRackTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraftforge.items.IItemHandlerModifiable;

public class WineRackRenderer extends TileEntityRenderer<WineRackTileEntity> {
    private static final float Y_DISTANCE = 5.33F;
        private static final float Y_DISTANCE_START = 12.16F;

    public WineRackRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(WineRackTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        IItemHandlerModifiable inventory = tileEntityIn.getInventory();
        BlockState blockState = tileEntityIn.getBlockState();
        Direction facing = blockState.get(BlockStateProperties.HORIZONTAL_FACING);
        for (int rows = 0; rows < tileEntityIn.getRows(); rows++) {
            for (int cols = 0; cols < tileEntityIn.getCols(); cols++) {
                ItemStack stackInSlot = inventory.getStackInSlot(cols + (rows * tileEntityIn.getCols()));

                matrixStackIn.push();
                float blockXScaleNS = 16.0F * 0.5F;
                float blockYScaleNS = 16.0F * 0.75F;
                float blockZScaleNS = 16.0F * 0.5806F;

                if (facing == Direction.NORTH) {
                    matrixStackIn.scale(0.5F, 0.75F, 0.5806F);
                    float renderHeight = (Y_DISTANCE_START - (rows * Y_DISTANCE)) / blockYScaleNS;
                    matrixStackIn.rotate(new Quaternion(-95.0F, 0, 0, true));
                    matrixStackIn.translate((11.75F - (cols * 4.0F)) / blockXScaleNS, -((12.5F - (0.4F * rows)) / blockZScaleNS), renderHeight);
                } else if (facing == Direction.EAST) {
                    matrixStackIn.scale(0.5806F, 0.75F, 0.50F);
                    float renderHeight = (Y_DISTANCE_START + 1.75F - (rows * Y_DISTANCE)) / blockYScaleNS;
                    matrixStackIn.rotate(new Quaternion(90.0F, -5.0F, -90.0F, true));
                    matrixStackIn.translate(-(4.25F + (cols * 4.0F)) / blockXScaleNS, ((3.5F + (0.4F * rows)) / blockZScaleNS), -renderHeight);
                } else if (facing == Direction.SOUTH) {
                    matrixStackIn.scale(0.5F, 0.75F, 0.5806F);
                    float renderHeight = (Y_DISTANCE_START + 1.75F - (rows * Y_DISTANCE)) / blockYScaleNS;
                    matrixStackIn.rotate(new Quaternion(95.0F, 0, 0, true));
                    matrixStackIn.translate((3.75F + (cols * 4.0F)) / blockXScaleNS, ((3.5F + (0.4F * rows)) / blockZScaleNS), -renderHeight);
                } else if (facing == Direction.WEST) {
                    matrixStackIn.scale(0.5806F, 0.75F, 0.50F);
                    float renderHeight = (Y_DISTANCE_START - (rows * Y_DISTANCE)) / blockYScaleNS;
                    matrixStackIn.rotate(new Quaternion(90.0F, 5.0F, 90.0F, true));
                    matrixStackIn.translate((3.75F + (cols * 4.0F)) / blockXScaleNS, -((12.5F - (0.4F * rows)) / blockZScaleNS), -renderHeight);
                }
                Minecraft.getInstance().getItemRenderer().renderItem(stackInSlot, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
                matrixStackIn.pop();
            }
        }
    }
}
