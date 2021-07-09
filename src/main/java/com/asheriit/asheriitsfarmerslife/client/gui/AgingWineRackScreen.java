package com.asheriit.asheriitsfarmerslife.client.gui;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.ContainerHelper;
import com.asheriit.asheriitsfarmerslife.container.AgingWineRackContainer;
import com.asheriit.asheriitsfarmerslife.tileentity.AgingWineRackTileEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class AgingWineRackScreen extends ContainerScreen<AgingWineRackContainer> {
    public static final ResourceLocation WINE_RACK_GUI_TEXTURE = new ResourceLocation(FarmersLife.MOD_ID, "textures/gui/container/wine_rack.png");
    public AgingWineRackTileEntity agingWineRackTileEntity;

    private static final Rectangle2d GUI_PROGRESS_BAR = new Rectangle2d(176, 16, 16, 16);
    private static final int INIT_X = 61;
    private static final int INIT_Y = 17 + GUI_PROGRESS_BAR.getHeight();

    public AgingWineRackScreen(AgingWineRackContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.agingWineRackTileEntity = screenContainer.agingWineRackTileEntity;

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
        this.minecraft.getTextureManager().bindTexture(WINE_RACK_GUI_TEXTURE);
        // Use this blit if the full texture is 256x256px else the texture width and height have to be attached as two more variables
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        int maxCols = this.agingWineRackTileEntity.getCols();
        int maxRows = this.agingWineRackTileEntity.getRows();
        for (int rows = 0; rows < maxRows; rows++) {
            int yCoordinate = INIT_Y + (rows * 18);
            for (int cols = 0; cols < maxCols; cols++) {
                int xCoordinate = INIT_X + (cols * 18);
                int slot = cols + (rows * maxCols);
                int scaledProgressHeight = -ContainerHelper.calculateArrowWidth(GUI_PROGRESS_BAR.getWidth(),
                        this.container.agingWineRackTileEntity.getProcessTimeTotalForSlot(slot),
                        this.container.agingWineRackTileEntity.getProcessTimesPerSlot()[slot]);
                // Parameters: X-Coodinate of default arrow, Y-Coodinate of default arrow, X-Coodinate of filled arrow, Y-Coodinate of filled arrow, Scaled width of the arrow, Scaled Height of the arrow
                this.blit(this.guiLeft + xCoordinate, this.guiTop + yCoordinate, GUI_PROGRESS_BAR.getX(), GUI_PROGRESS_BAR.getY(), GUI_PROGRESS_BAR.getWidth(), scaledProgressHeight);
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        // The color value is formatted as hex number
        this.font.drawString(this.title.getFormattedText(), 8.0F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2), 4210752);
    }
}
