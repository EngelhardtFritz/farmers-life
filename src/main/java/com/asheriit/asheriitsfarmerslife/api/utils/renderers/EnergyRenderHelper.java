package com.asheriit.asheriitsfarmerslife.api.utils.renderers;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class EnergyRenderHelper {

    /**
     * Draw the amount of energy for the given coordinates and fullness
     *
     * @param energyFullness:     Fullness of the energy tank (relative to the full pixel height {@param drawTankHeight})
     * @param currentAnimateTick: The current tick for the animation (Defined in the corresponding tile entity)
     * @param textureWidth:       Width of the space used on the texture
     * @param drawTankX:          X-coordinate of the energy tank (left edge of the tank)
     * @param drawTankY:          Y-coordinate of the energy tank (top edge of the tank)
     * @param drawTankWidth:      Width of the energy tank
     * @param drawTankHeight:     Height of the energy tank
     */
    public static void drawAnimatedEnergyTank(int energyFullness, int currentAnimateTick, int textureWidth, int drawTankX, int drawTankY, int drawTankWidth, int drawTankHeight) {
        if (energyFullness <= 0) {
            return;
        }

        // Get energy texture
        ResourceLocation energyTextureLocation = new ResourceLocation(FarmersLife.MOD_ID, "gui/container/energy_tank_1");
        TextureAtlasSprite energySprite = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(energyTextureLocation);
        if (energySprite == null) {
            FarmersLife.LOGGER.debug("[RenderUtil] Could not find fluid texture in atlas for given stack!");
            return;
        }

        // Set the RenderSystem color
        animateLinearRenderColor(0.7F, 1.0F, 2, 15, currentAnimateTick);

        RenderSystem.enableBlend();
        int quadWidthHeight = 16;

        float spriteMinU = energySprite.getMinU();
        float spriteMinV = energySprite.getMinV();
        float spriteMaxU = energySprite.getMaxU();
        float spriteMaxV = energySprite.getMaxV();

        for (int widthIndex = 0; widthIndex < drawTankWidth; widthIndex = widthIndex + quadWidthHeight) {
            for (int heightIndex = 0; heightIndex < energyFullness; heightIndex = heightIndex + quadWidthHeight) {
                int drawWidth = Math.min(drawTankWidth - widthIndex, textureWidth);
                int drawHeight = Math.min(energyFullness - heightIndex, 16);

                float spriteU = spriteMinU + (spriteMaxU - spriteMinU) * drawWidth / 16F;
                float spriteV = spriteMaxV - (spriteMaxV - spriteMinV) * drawHeight / 16F;

                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder tessellationBuffer = tessellator.getBuffer();
                tessellationBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
                // 1. vertex point from quad (down left)
                tessellationBuffer.pos(drawTankX + widthIndex, drawTankY + drawTankHeight - heightIndex, 0).tex(spriteMinU, spriteMaxV).endVertex();
                // 2. vertex point from quad (down right)
                tessellationBuffer.pos(drawTankX + widthIndex + drawWidth, drawTankY + drawTankHeight - heightIndex, 0).tex(spriteU, spriteMaxV).endVertex();
                // 3. vertex point from quad (up right)
                tessellationBuffer.pos(drawTankX + widthIndex + drawWidth, (drawTankY + drawTankHeight) - (heightIndex + drawHeight), 0).tex(spriteU, spriteV).endVertex();
                // 4. vertex point from quad (up left)
                tessellationBuffer.pos(drawTankX + widthIndex, (drawTankY + drawTankHeight) - (heightIndex + drawHeight), 0).tex(spriteMinU, spriteV).endVertex();

                tessellator.draw();
            }
        }
        RenderSystem.disableBlend();

        RenderSystem.color3f(1.0F, 1.0F, 1.0F);
    }

    /**
     * Animate the following texture binding depending on the given parameters
     *
     * @param minColorValue:  Min color value the texture should blend to (0.0F - 1.0F)
     * @param maxColorValue:  Max color value the texture should blend to (0.0F - 1.0F)
     * @param minMaxBuffer:   Buffer (amount of ticks) value to stay at min and max color value
     * @param animationTicks: Ticks to animate (*2 for fade in and fade out)
     * @param currentTick:    Current tick
     */
    public static void animateLinearRenderColor(float minColorValue, float maxColorValue, int minMaxBuffer, int animationTicks, int currentTick) {
        float diff = maxColorValue - minColorValue;
        float addReduce = diff / (animationTicks - minMaxBuffer);

        if (currentTick >= 0 && currentTick < minMaxBuffer) {
            // Stay at max color value
            RenderSystem.color3f(maxColorValue, maxColorValue, maxColorValue);
        } else if (currentTick >= minMaxBuffer && currentTick < animationTicks) {
            // Reduce light
            int correctTicks = currentTick - minMaxBuffer + 1;
            float animateWithAddReduce = correctTicks * addReduce;
            if (animateWithAddReduce <= diff) {
                float colorToSet = maxColorValue - animateWithAddReduce;
                RenderSystem.color3f(colorToSet, colorToSet, colorToSet);
            }
        } else if (currentTick >= animationTicks && (currentTick < animationTicks + minMaxBuffer)) {
            // Stay at min color value
            RenderSystem.color3f(minColorValue, minColorValue, minColorValue);
        } else {
            // Increase light
            int correctTicks = currentTick - (animationTicks + minMaxBuffer - 1);
            float animateWithAddReduce = correctTicks * addReduce;
            if (animateWithAddReduce <= diff) {
                float colorToSet = minColorValue + animateWithAddReduce;
                RenderSystem.color3f(colorToSet, colorToSet, colorToSet);
            }
        }
    }
}
