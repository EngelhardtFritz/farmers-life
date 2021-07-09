package com.asheriit.asheriitsfarmerslife.client.gui;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.ContainerHelper;
import com.asheriit.asheriitsfarmerslife.container.FertilizerComposterContainer;
import com.asheriit.asheriitsfarmerslife.tileentity.FertilizerComposterTileEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class FertilizerComposterScreen extends ContainerScreen<FertilizerComposterContainer> {
    public static final ResourceLocation FERTILIZER_COMPOSTER_GUI_TEXTURE = new ResourceLocation(FarmersLife.MOD_ID, "textures/gui/container/fertilizer_composter.png");
    public FertilizerComposterTileEntity fertilizerComposterTileEntity;

    private static final Rectangle2d GUI_ARROW = new Rectangle2d(0, 166, 22, 16);

    public FertilizerComposterScreen(FertilizerComposterContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.fertilizerComposterTileEntity = screenContainer.fertilizerComposterTileEntity;

        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    public void render(int mouseX, int mouseY, float p_render_3_) {
        this.renderBackground();
        super.render(mouseX, mouseY, p_render_3_);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        // Set Background texture
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(FERTILIZER_COMPOSTER_GUI_TEXTURE);
        // Use this blit if the full texture is 256x256px else the texture width and height have to be attached as two more variables
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        // Draw the GUI arrow
        int scaledArrowWidth = ContainerHelper.calculateArrowWidth(GUI_ARROW.getWidth(), this.fertilizerComposterTileEntity.getProcessTimeTotal(), this.container.currentProcessTime.get());
        // Parameters: X-Coodinate of default arrow, Y-Coodinate of default arrow, X-Coodinate of filled arrow, Y-Coodinate of filled arrow, Scaled width of the arrow, Height of the arrow
        this.blit(this.guiLeft + 76, this.guiTop + 35, GUI_ARROW.getX(), GUI_ARROW.getY(), scaledArrowWidth, GUI_ARROW.getHeight());
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        // The color value is formatted as hex number
        this.font.drawString(this.title.getFormattedText(), 8.0F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2), 4210752);
    }
}
