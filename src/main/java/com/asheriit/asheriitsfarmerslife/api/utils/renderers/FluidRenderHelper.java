package com.asheriit.asheriitsfarmerslife.api.utils.renderers;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class FluidRenderHelper {

    /**
     * Draws the amount of fluid in a tank for the given fluid
     * To get the RGB colors the color attribute can be shifted. For details look at Forge {@link net.minecraftforge.fluids.FluidAttributes FluidAttributes}
     *
     * @param fluidStack:     The fluid to get the color from
     * @param tankFullness:   The fullness of the tank relative to the tank height
     * @param drawTankX:      The x-coordinate to start drawing the fluid
     * @param drawTankY:      The y-coordinate to start drawing the fluid
     * @param drawTankWidth:  The width of the fluid tank
     * @param drawTankHeight: The height of the fluid tank
     */
    public static void drawFluidTank(FluidStack fluidStack, int tankFullness, int drawTankX, int drawTankY, int drawTankWidth, int drawTankHeight) {
//        FarmersLife.LOGGER.info("fluidStack: " + fluidStack.getFluid().getRegistryName() + ", tankFullness: " + tankFullness + ", drawTankWidth: " + drawTankWidth +
//                ", drawTankHeight: " + drawTankHeight);
        if (tankFullness <= 0) {
            return;
        }
        // Get fluid texture
        ResourceLocation fluidLocation = fluidStack.getFluid().getAttributes().getStillTexture();
        TextureAtlasSprite fluidSprite = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(fluidLocation);
        if (fluidSprite == null) {
            FarmersLife.LOGGER.debug("[RenderUtil] Could not find fluid texture in atlas for given stack!");
            return;
        }

        Minecraft.getInstance().getTextureManager().bindTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE);
        int fluidColor = fluidStack.getFluid().getAttributes().getColor();
        Vector3f colorRGB = new Vector3f(((fluidColor >> 16) & 0xFF) / 255f, ((fluidColor >> 8) & 0xFF) / 255f, ((fluidColor >> 0) & 0xFF) / 255f);
        RenderSystem.color3f(colorRGB.getX(), colorRGB.getY(), colorRGB.getZ());

        RenderSystem.enableBlend();
        int quadWidthHeight = 16;

        float spriteMinU = fluidSprite.getMinU();
        float spriteMinV = fluidSprite.getMinV();
        float spriteMaxU = fluidSprite.getMaxU();
        float spriteMaxV = fluidSprite.getMaxV();

        for (int widthIndex = 0; widthIndex < drawTankWidth; widthIndex = widthIndex + quadWidthHeight) {
            for (int heightIndex = 0; heightIndex < tankFullness; heightIndex = heightIndex + quadWidthHeight) {
                int drawWidth = Math.min(drawTankWidth - widthIndex, 16);
                int drawHeight = Math.min(tankFullness - heightIndex, 16);

                float spriteU = spriteMinU + (spriteMaxU - spriteMinU) * drawWidth / 16F;
                float spriteV = spriteMinV + (spriteMaxV - spriteMinV) * drawHeight / 16F;

                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder tessellationBuffer = tessellator.getBuffer();
                tessellationBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
                // 1. vertex point from quad (down left)
                tessellationBuffer.pos(drawTankX + widthIndex, drawTankY + drawTankHeight - heightIndex, 0).tex(spriteMinU, spriteV).endVertex();
                // 2. vertex point from quad (down right)
                tessellationBuffer.pos(drawTankX + widthIndex + drawWidth, drawTankY + drawTankHeight - heightIndex, 0).tex(spriteU, spriteV).endVertex();
                // 3. vertex point from quad (up right)
                tessellationBuffer.pos(drawTankX + widthIndex + drawWidth, (drawTankY + drawTankHeight) - (heightIndex + drawHeight), 0).tex(spriteU, spriteMinV).endVertex();
                // 4. vertex point from quad (up left)
                tessellationBuffer.pos(drawTankX + widthIndex, (drawTankY + drawTankHeight) - (heightIndex + drawHeight), 0).tex(spriteMinU, spriteMinV).endVertex();

                tessellator.draw();
            }
        }
        RenderSystem.disableBlend();

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
