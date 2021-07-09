package com.asheriit.asheriitsfarmerslife.client.gui;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.ContainerHelper;
import com.asheriit.asheriitsfarmerslife.api.utils.renderers.FluidRenderHelper;
import com.asheriit.asheriitsfarmerslife.container.StompingBarrelContainer;
import com.asheriit.asheriitsfarmerslife.tileentity.StompingBarrelTileEntity;
import com.asheriit.asheriitsfarmerslife.utils.ToolTipHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class StompingBarrelScreen extends ContainerScreen<StompingBarrelContainer> {
    public static final ResourceLocation WINE_PRESS_GUI_TEXTURE = new ResourceLocation(FarmersLife.MOD_ID, "textures/gui/container/stomping_barrel.png");
    public StompingBarrelTileEntity stompingBarrelTileEntity;

    private static final Rectangle2d GUI_PROGRESS = new Rectangle2d(0, 166, 14, 19);
    private static final Rectangle2d GUI_OUTPUT_TANK = new Rectangle2d(118, 18, 13, 49);

    public StompingBarrelScreen(StompingBarrelContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.stompingBarrelTileEntity = screenContainer.stompingBarrelTileEntity;

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
        this.minecraft.getTextureManager().bindTexture(WINE_PRESS_GUI_TEXTURE);
        // Use this blit if the full texture is 256x256px else the texture width and height have to be attached as two more variables
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);


        int currentSteps = this.container.currentProgressSteps.get();
        int totalSteps = this.stompingBarrelTileEntity.getProcessStepTotal();
        int progressionIndex = this.getPressProgressIndex(currentSteps, totalSteps);
        // Parameters: X-Coodinate of default press, Y-Coodinate of default press, X-Coodinate of filled press, Y-Coodinate of filled press, Width of the press, Height of the press
        this.blit(this.guiLeft + 79, this.guiTop + 31, GUI_PROGRESS.getX() + progressionIndex * GUI_PROGRESS.getWidth(), GUI_PROGRESS.getY(), GUI_PROGRESS.getWidth(), GUI_PROGRESS.getHeight());

        FluidRenderHelper.drawFluidTank(this.container.getFluidStackForTank(StompingBarrelTileEntity.OUTPUT_TANK_SLOT),
                ContainerHelper.getScaledInputTankHeight(this.stompingBarrelTileEntity.getTank(StompingBarrelTileEntity.OUTPUT_TANK_SLOT), GUI_OUTPUT_TANK.getHeight()),
                this.guiLeft + GUI_OUTPUT_TANK.getX(),
                this.guiTop + GUI_OUTPUT_TANK.getY(),
                GUI_OUTPUT_TANK.getWidth(),
                GUI_OUTPUT_TANK.getHeight());
    }

    private int getPressProgressIndex(int currentSteps, int totalSteps) {
        float stepWidth = totalSteps / 12.0F;
        for (int i = 0; i < 12; i++) {
            if (currentSteps <= (i + 1) * stepWidth && currentSteps >= i * stepWidth) {
                return i;
            }
        }
        return 0;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        // The color value is formatted as hex number
        this.font.drawString(this.title.getFormattedText(), 8.0F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2), 4210752);
    }

    @Override
    protected void renderHoveredToolTip(int mouseX, int mouseY) {
        if (isPointInRegion(GUI_OUTPUT_TANK.getX(), GUI_OUTPUT_TANK.getY(), GUI_OUTPUT_TANK.getWidth(), GUI_OUTPUT_TANK.getHeight(), mouseX, mouseY)) {
            FluidTank tank = this.container.getFluidTankForSlot(StompingBarrelTileEntity.OUTPUT_TANK_SLOT);
            renderTooltip(ToolTipHelper.createFluidTankToolTip(tank), mouseX, mouseY);
        }
        super.renderHoveredToolTip(mouseX, mouseY);
    }
}
