package com.asheriit.asheriitsfarmerslife.client.tileentity.renderer;

import com.asheriit.asheriitsfarmerslife.events.ModelRegistryEventHandler;
import com.asheriit.asheriitsfarmerslife.tileentity.AgingWineRackTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.items.IItemHandlerModifiable;

public class AgingWineRackRenderer extends TileEntityRenderer<AgingWineRackTileEntity> {
    private static final float DISTANCE_MODIFIER = 5.0F / 16.0F;

    public AgingWineRackRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(AgingWineRackTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        IItemHandlerModifiable inventory = tileEntityIn.getInventory();
        BlockState blockState = tileEntityIn.getBlockState();
        Direction facing = blockState.get(BlockStateProperties.HORIZONTAL_FACING);

        IBakedModel bakedModel = Minecraft.getInstance().getModelManager().getModel(ModelRegistryEventHandler.BOTTLE_HEAD);
        IVertexBuilder vertexBuffer = bufferIn.getBuffer(RenderType.getTranslucent());
        for (int rows = 0; rows < tileEntityIn.getRows(); rows++) {
            for (int cols = 0; cols < tileEntityIn.getCols(); cols++) {
                ItemStack stackInSlot = inventory.getStackInSlot(cols + (rows * tileEntityIn.getCols()));
                if (!stackInSlot.isEmpty()) {
                    matrixStackIn.push();

                    float renderHeight = -(rows * DISTANCE_MODIFIER);
                    if (facing == Direction.NORTH) {
                        matrixStackIn.translate(-(cols * DISTANCE_MODIFIER), renderHeight, 0);
                    } else if (facing == Direction.EAST) {
                        matrixStackIn.rotate(new Quaternion(0, 270.0F, 0, true));
                        matrixStackIn.translate(-(cols * DISTANCE_MODIFIER), renderHeight, -1.0F);
                    } else if (facing == Direction.SOUTH) {
                        matrixStackIn.rotate(new Quaternion(0, 180.0F, 0, true));
                        matrixStackIn.translate(-(cols * DISTANCE_MODIFIER) - 1.0F, renderHeight, -1.0F);
                    } else if (facing == Direction.WEST) {
                        matrixStackIn.rotate(new Quaternion(0, 90.0F, 0, true));
                        matrixStackIn.translate(-(cols * DISTANCE_MODIFIER) - 1.0F, renderHeight, 0);
                    }

                    MatrixStack.Entry currentMatrix = matrixStackIn.getLast();
                    Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(currentMatrix, vertexBuffer, null, bakedModel, 1.0F, 1.0F, 1.0F, combinedLightIn, combinedOverlayIn, EmptyModelData.INSTANCE);
                    matrixStackIn.pop();
                }
            }
        }
    }
}
