package com.asheriit.asheriitsfarmerslife.client.gui;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.ContainerHelper;
import com.asheriit.asheriitsfarmerslife.api.utils.renderers.FluidRenderHelper;
import com.asheriit.asheriitsfarmerslife.container.FermentingBarrelContainer;
import com.asheriit.asheriitsfarmerslife.network.to_server.ClearTankMessageToServer;
import com.asheriit.asheriitsfarmerslife.tileentity.FermentingBarrelTileEntity;
import com.asheriit.asheriitsfarmerslife.tileentity.FiltrationMachineTileEntity;
import com.asheriit.asheriitsfarmerslife.utils.ToolTipHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class FermentingBarrelScreen extends ContainerScreen<FermentingBarrelContainer> {
    public static final ResourceLocation FERMENTING_BARREL_GUI_TEXTURE = new ResourceLocation(FarmersLife.MOD_ID, "textures/gui/container/fermenting_barrel.png");
    public FermentingBarrelTileEntity fermentingBarrelTileEntity;

    private static final Rectangle2d GUI_PROGRESS_BAR = new Rectangle2d(176, 49, 3, 61);
    private static final Rectangle2d GUI_INPUT_OUTPUT_TANK = new Rectangle2d(62, 21, 52, 59);
    private static final Rectangle2d GUI_CROSS = new Rectangle2d(128, 66, 15, 15);
    private static final Rectangle2d GUI_CROSS_ACTIVE = new Rectangle2d(190, 0, 15, 15);

    public FermentingBarrelScreen(FermentingBarrelContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.fermentingBarrelTileEntity = screenContainer.fermentingBarrelTileEntity;

        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 176;
        this.ySize = 182;
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
        this.minecraft.getTextureManager().bindTexture(FERMENTING_BARREL_GUI_TEXTURE);
        // Use this blit if the full texture is 256x256px else the texture width and height have to be attached as two more variables
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        // Draw the GUI arrow
        int scaledProgressHeight = -ContainerHelper.calculateArrowWidth(GUI_PROGRESS_BAR.getHeight(), this.fermentingBarrelTileEntity.getProcessTimeTotal(), this.container.currentProcessTime.get());
        // Parameters: X-Coodinate of default arrow, Y-Coodinate of default arrow, X-Coodinate of filled arrow, Y-Coodinate of filled arrow, Scaled width of the arrow, Scaled Height of the arrow
        this.blit(this.guiLeft + 53, this.guiTop + 81, GUI_PROGRESS_BAR.getX(), GUI_PROGRESS_BAR.getY(), GUI_PROGRESS_BAR.getWidth(), scaledProgressHeight);
        this.blit(this.guiLeft + 120, this.guiTop + 81, GUI_PROGRESS_BAR.getX(), GUI_PROGRESS_BAR.getY(), GUI_PROGRESS_BAR.getWidth(), scaledProgressHeight);

        if (isPointInRegion(GUI_CROSS.getX(), GUI_CROSS.getY(), GUI_CROSS.getWidth(), GUI_CROSS.getHeight(), mouseX, mouseY)) {
            this.blit(this.guiLeft + GUI_CROSS.getX(), this.guiTop + GUI_CROSS.getY(),
                    GUI_CROSS_ACTIVE.getX(), GUI_CROSS_ACTIVE.getY(), GUI_CROSS_ACTIVE.getWidth(), GUI_CROSS_ACTIVE.getHeight());
        }

        // Draw the input fluid tank
        FluidRenderHelper.drawFluidTank(this.container.fermentingBarrelTileEntity.getFluidInTank(FermentingBarrelTileEntity.INPUT_OUTPUT_FLUID_TANK),
                ContainerHelper.getScaledInputTankHeight(this.container.fermentingBarrelTileEntity.getTank(FermentingBarrelTileEntity.INPUT_OUTPUT_FLUID_TANK), GUI_INPUT_OUTPUT_TANK.getHeight()),
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
    public boolean mouseClicked(double mouseX, double mouseY, int keycode) {
        // For keycodes look at net.minecraft.client.util.InputMappings
        // Send message to server to clear tank content
        Vec3d targetBlock = new Vec3d(this.container.fermentingBarrelTileEntity.getPos());
        if (isPointInRegion(GUI_CROSS.getX(), GUI_CROSS.getY(), GUI_CROSS.getWidth(), GUI_CROSS.getHeight(), mouseX, mouseY) && keycode == 0) {
            byte tankSlot = FiltrationMachineTileEntity.INPUT_TANK;
            ClearTankMessageToServer clearTankMessageToServer = new ClearTankMessageToServer(targetBlock, tankSlot);
            FarmersLife.simpleChannel.sendToServer(clearTankMessageToServer);
        }
        return super.mouseClicked(mouseX, mouseY, keycode);
    }

    @Override
    protected void renderHoveredToolTip(int mouseX, int mouseY) {
        if (isPointInRegion(GUI_INPUT_OUTPUT_TANK.getX(), GUI_INPUT_OUTPUT_TANK.getY(), GUI_INPUT_OUTPUT_TANK.getWidth(), GUI_INPUT_OUTPUT_TANK.getHeight(), mouseX, mouseY)) {
            FluidTank tank = this.fermentingBarrelTileEntity.getTank(FermentingBarrelTileEntity.INPUT_OUTPUT_FLUID_TANK);
            renderTooltip(ToolTipHelper.createFluidTankToolTip(tank), mouseX, mouseY);
        }
        super.renderHoveredToolTip(mouseX, mouseY);
    }
}
