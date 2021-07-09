package com.asheriit.asheriitsfarmerslife.client.gui;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.ContainerHelper;
import com.asheriit.asheriitsfarmerslife.api.utils.renderers.FluidRenderHelper;
import com.asheriit.asheriitsfarmerslife.container.FluidStorageContainer;
import com.asheriit.asheriitsfarmerslife.tileentity.FluidStorageTileEntity;
import com.asheriit.asheriitsfarmerslife.utils.ToolTipHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class FluidStorageScreen extends ContainerScreen<FluidStorageContainer> {
    public static final ResourceLocation FLUID_STORAGE_BARREL_GUI_TEXTURE = new ResourceLocation(FarmersLife.MOD_ID, "textures/gui/container/fluid_storage_barrel.png");
    public FluidStorageTileEntity fluidStorageTileEntity;

    private static final Rectangle2d GUI_INPUT_OUTPUT_TANK = new Rectangle2d(62, 19, 52, 47);

    public FluidStorageScreen(FluidStorageContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.fluidStorageTileEntity = screenContainer.fluidStorageTileEntity;

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
        this.minecraft.getTextureManager().bindTexture(FLUID_STORAGE_BARREL_GUI_TEXTURE);
        // Use this blit if the full texture is 256x256px else the texture width and height have to be attached as two more variables
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        // Draw the input fluid tank
        FluidRenderHelper.drawFluidTank(this.fluidStorageTileEntity.getFluidInTank(FluidStorageTileEntity.INPUT_OUTPUT_FLUID_TANK),
                ContainerHelper.getScaledInputTankHeight(this.fluidStorageTileEntity.getTank(FluidStorageTileEntity.INPUT_OUTPUT_FLUID_TANK), GUI_INPUT_OUTPUT_TANK.getHeight()),
                this.guiLeft + GUI_INPUT_OUTPUT_TANK.getX(),
                this.guiTop + GUI_INPUT_OUTPUT_TANK.getY(),
                GUI_INPUT_OUTPUT_TANK.getWidth(),
                GUI_INPUT_OUTPUT_TANK.getHeight());
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
        if (isPointInRegion(GUI_INPUT_OUTPUT_TANK.getX(), GUI_INPUT_OUTPUT_TANK.getY(), GUI_INPUT_OUTPUT_TANK.getWidth(), GUI_INPUT_OUTPUT_TANK.getHeight(), mouseX, mouseY)) {
            FluidTank tank = this.fluidStorageTileEntity.getTank(FluidStorageTileEntity.INPUT_OUTPUT_FLUID_TANK);
            renderTooltip(ToolTipHelper.createFluidTankToolTip(tank), mouseX, mouseY);
        }
        super.renderHoveredToolTip(mouseX, mouseY);
    }
}
