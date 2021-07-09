package com.asheriit.asheriitsfarmerslife.client.gui;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.ContainerHelper;
import com.asheriit.asheriitsfarmerslife.api.utils.renderers.FluidRenderHelper;
import com.asheriit.asheriitsfarmerslife.container.BoilingCauldronContainer;
import com.asheriit.asheriitsfarmerslife.network.to_server.ClearTankMessageToServer;
import com.asheriit.asheriitsfarmerslife.network.to_server.SetBooleanMessageToServer;
import com.asheriit.asheriitsfarmerslife.tileentity.BoilingCauldronTileEntity;
import com.asheriit.asheriitsfarmerslife.utils.ToolTipHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.ArrayList;
import java.util.List;

public class BoilingCauldronScreen extends ContainerScreen<BoilingCauldronContainer> {
    public static final ResourceLocation BOILING_CAULDRON_GUI_TEXTURE = new ResourceLocation(FarmersLife.MOD_ID, "textures/gui/container/boiling_cauldron.png");
    public BoilingCauldronTileEntity boilingCauldronTileEntity;

    protected static final Rectangle2d GUI_ARROW = new Rectangle2d(176, 14, 22, 16);
    protected static final Rectangle2d GUI_INPUT_TANK = new Rectangle2d(27, 18, 13, 49);
    protected static final Rectangle2d GUI_OUTPUT_TANK = new Rectangle2d(136, 18, 13, 49);
    protected static final Rectangle2d GUI_DELETE_INPUT_TANK_CONTENT = new Rectangle2d(26, 71, 16, 16);
    protected static final Rectangle2d GUI_DELETE_OUTPUT_TANK_CONTENT = new Rectangle2d(134, 71, 16, 16);
    protected static final Rectangle2d GUI_DELETE_ACTIVATED = new Rectangle2d(176, 30, 16, 16);
    protected static final Rectangle2d GUI_FUEL_FIRE_BUTTON = new Rectangle2d(81, 54, 13, 13);
    protected static final Rectangle2d GUI_RED_CROSS = new Rectangle2d(176, 46, 13, 13);
    protected static final Rectangle2d GUI_FUEL_FIRE = new Rectangle2d(176, 14, 14, 14);

    public BoilingCauldronScreen(BoilingCauldronContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.boilingCauldronTileEntity = screenContainer.boilingCauldronTileEntity;

        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 176;
        this.ySize = 184;
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
        this.minecraft.getTextureManager().bindTexture(BOILING_CAULDRON_GUI_TEXTURE);
        // Use this blit if the full texture is 256x256px else the texture width and height have to be attached as two more variables
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        // Draw the GUI arrow
        int scaledArrowWidth = ContainerHelper.calculateArrowWidth(GUI_ARROW.getWidth(),
                this.boilingCauldronTileEntity.getProcessTimeTotal(),
                this.container.currentProcessTime.get());
        // Parameters: X-Coordinate of default arrow, Y-Coordinate of default arrow, X-Coordinate of filled arrow, Y-Coordinate of filled arrow, Scaled width of the arrow, Height of the arrow
        this.blit(this.guiLeft + 77, this.guiTop + 35, GUI_ARROW.getX(), GUI_ARROW.getY(), scaledArrowWidth, GUI_ARROW.getHeight());

        // Draw the fuel state
        int scaledFireHeight = -ContainerHelper.calculateArrowWidth(GUI_FUEL_FIRE.getHeight(),
                this.container.totalBurnTime.get(),
                this.container.burnTimeLeft.get());
        // Parameters: X-Coordinate of default arrow, Y-Coordinate of default arrow, X-Coordinate of filled arrow, Y-Coordinate of filled arrow, Scaled width of the arrow, Height of the arrow
        this.blit(this.guiLeft + 81, this.guiTop + 54 + GUI_FUEL_FIRE.getHeight(), GUI_FUEL_FIRE.getX(), GUI_FUEL_FIRE.getY(), GUI_FUEL_FIRE.getWidth(), scaledFireHeight);

        // Change look of buttons if hovered
        if (isPointInRegion(GUI_DELETE_INPUT_TANK_CONTENT.getX(), GUI_DELETE_INPUT_TANK_CONTENT.getY(), GUI_DELETE_INPUT_TANK_CONTENT.getWidth(), GUI_DELETE_INPUT_TANK_CONTENT.getHeight(), mouseX, mouseY)) {
            // Parameters: X-Coordinate of default delete button, Y-Coordinate of default delete button, X-Coordinate of filled delete button, Y-Coordinate of filled delete button, Scaled width of the delete button, Height of the delete button
            this.blit(this.guiLeft + GUI_DELETE_INPUT_TANK_CONTENT.getX(), this.guiTop + GUI_DELETE_INPUT_TANK_CONTENT.getY(),
                    GUI_DELETE_ACTIVATED.getX(), GUI_DELETE_ACTIVATED.getY(), GUI_DELETE_ACTIVATED.getWidth(), GUI_DELETE_ACTIVATED.getHeight());
        } else if (isPointInRegion(GUI_DELETE_OUTPUT_TANK_CONTENT.getX(), GUI_DELETE_OUTPUT_TANK_CONTENT.getY(), GUI_DELETE_OUTPUT_TANK_CONTENT.getWidth(), GUI_DELETE_OUTPUT_TANK_CONTENT.getHeight(), mouseX, mouseY)) {
            // Parameters: X-Coordinate of default delete button, Y-Coordinate of default delete button, X-Coordinate of filled delete button, Y-Coordinate of filled delete button, Scaled width of the delete button, Height of the delete button
            this.blit(this.guiLeft + GUI_DELETE_OUTPUT_TANK_CONTENT.getX(), this.guiTop + GUI_DELETE_OUTPUT_TANK_CONTENT.getY(),
                    GUI_DELETE_ACTIVATED.getX(), GUI_DELETE_ACTIVATED.getY(), GUI_DELETE_ACTIVATED.getWidth(), GUI_DELETE_ACTIVATED.getHeight());
        }

        if (!this.container.boilingCauldronTileEntity.isFuelEnabled()) {
            // Parameters: X-Coordinate of default delete button, Y-Coordinate of default delete button, X-Coordinate of filled delete button, Y-Coordinate of filled delete button, Scaled width of the delete button, Height of the delete button
            this.blit(this.guiLeft + GUI_FUEL_FIRE_BUTTON.getX(), this.guiTop + GUI_FUEL_FIRE_BUTTON.getY(),
                    GUI_RED_CROSS.getX(), GUI_RED_CROSS.getY(), GUI_RED_CROSS.getWidth(), GUI_RED_CROSS.getHeight());
        }

        // Draw the input fluid tank
        FluidRenderHelper.drawFluidTank(this.boilingCauldronTileEntity.getFluidInTank(BoilingCauldronTileEntity.INPUT_FLUID_TANK),
                ContainerHelper.getScaledInputTankHeight(this.container.boilingCauldronTileEntity.getTank(BoilingCauldronTileEntity.INPUT_FLUID_TANK), GUI_INPUT_TANK.getHeight()),
                this.guiLeft + GUI_INPUT_TANK.getX(),
                this.guiTop + GUI_INPUT_TANK.getY(),
                GUI_INPUT_TANK.getWidth(),
                GUI_INPUT_TANK.getHeight());

        // Draw the output fluid tank
        FluidRenderHelper.drawFluidTank(this.boilingCauldronTileEntity.getFluidInTank(BoilingCauldronTileEntity.OUTPUT_FLUID_TANK),
                ContainerHelper.getScaledInputTankHeight(this.container.boilingCauldronTileEntity.getTank(BoilingCauldronTileEntity.OUTPUT_FLUID_TANK), GUI_OUTPUT_TANK.getHeight()),
                this.guiLeft + GUI_OUTPUT_TANK.getX(),
                this.guiTop + GUI_OUTPUT_TANK.getY(),
                GUI_OUTPUT_TANK.getWidth(),
                GUI_OUTPUT_TANK.getHeight());
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
        if (isPointInRegion(GUI_DELETE_INPUT_TANK_CONTENT.getX(), GUI_DELETE_INPUT_TANK_CONTENT.getY(), GUI_DELETE_INPUT_TANK_CONTENT.getWidth(), GUI_DELETE_INPUT_TANK_CONTENT.getHeight(), mouseX, mouseY) ||
                isPointInRegion(GUI_DELETE_OUTPUT_TANK_CONTENT.getX(), GUI_DELETE_OUTPUT_TANK_CONTENT.getY(), GUI_DELETE_OUTPUT_TANK_CONTENT.getWidth(), GUI_DELETE_OUTPUT_TANK_CONTENT.getHeight(), mouseX, mouseY)) {
            List<String> stringList = new ArrayList<>();
            stringList.add(ToolTipHelper.DELETE_CONTENT_TOOLTIP_LOCATION.getFormattedText());
            renderTooltip(stringList, mouseX, mouseY);
        }
        if (isPointInRegion(GUI_INPUT_TANK.getX(), GUI_INPUT_TANK.getY(), GUI_INPUT_TANK.getWidth(), GUI_INPUT_TANK.getHeight(), mouseX, mouseY)) {
            FluidTank tank = this.boilingCauldronTileEntity.getTank(BoilingCauldronTileEntity.INPUT_FLUID_TANK);
            renderTooltip(ToolTipHelper.createFluidTankToolTip(tank), mouseX, mouseY);
        }
        if (isPointInRegion(GUI_OUTPUT_TANK.getX(), GUI_OUTPUT_TANK.getY(), GUI_OUTPUT_TANK.getWidth(), GUI_OUTPUT_TANK.getHeight(), mouseX, mouseY)) {
            FluidTank tank = this.boilingCauldronTileEntity.getTank(BoilingCauldronTileEntity.OUTPUT_FLUID_TANK);
            renderTooltip(ToolTipHelper.createFluidTankToolTip(tank), mouseX, mouseY);
        }
        if (isPointInRegion(GUI_FUEL_FIRE_BUTTON.getX(), GUI_FUEL_FIRE_BUTTON.getY(), GUI_FUEL_FIRE_BUTTON.getWidth(), GUI_FUEL_FIRE_BUTTON.getHeight(), mouseX, mouseY)) {
            if (this.boilingCauldronTileEntity.isFuelEnabled()) {
                List<String> tooltip = new ArrayList<>();
                tooltip.add(ToolTipHelper.USE_FUEL.getFormattedText() + " " + ToolTipHelper.ENABLED.getFormattedText());
                tooltip.add(ToolTipHelper.FUEL_ENABLED_RESULT.applyTextStyle(TextFormatting.GRAY).getFormattedText());
                renderTooltip(tooltip, mouseX, mouseY);
            } else {
                List<String> tooltip = new ArrayList<>();
                tooltip.add(ToolTipHelper.USE_FUEL.getFormattedText() + " " + ToolTipHelper.DISABLED.getFormattedText());
                tooltip.add(ToolTipHelper.FUEL_DISABLED_RESULT.applyTextStyle(TextFormatting.GRAY).getFormattedText());
                renderTooltip(tooltip, mouseX, mouseY);
            }
        }
        super.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int keycode) {
        // For keycodes look at net.minecraft.client.util.InputMappings
        // Send message to server to clear tank content
        Vec3d targetBlock = new Vec3d(this.boilingCauldronTileEntity.getPos());
        if (isPointInRegion(GUI_DELETE_INPUT_TANK_CONTENT.getX(), GUI_DELETE_INPUT_TANK_CONTENT.getY(), GUI_DELETE_INPUT_TANK_CONTENT.getWidth(), GUI_DELETE_INPUT_TANK_CONTENT.getHeight(), mouseX, mouseY) && keycode == 0) {
            byte tankSlot = BoilingCauldronTileEntity.INPUT_FLUID_TANK;
            ClearTankMessageToServer clearTankMessageToServer = new ClearTankMessageToServer(targetBlock, tankSlot);
            FarmersLife.simpleChannel.sendToServer(clearTankMessageToServer);
        } else if (isPointInRegion(GUI_DELETE_OUTPUT_TANK_CONTENT.getX(), GUI_DELETE_OUTPUT_TANK_CONTENT.getY(), GUI_DELETE_OUTPUT_TANK_CONTENT.getWidth(), GUI_DELETE_OUTPUT_TANK_CONTENT.getHeight(), mouseX, mouseY) && keycode == 0) {
            byte tankSlot = BoilingCauldronTileEntity.OUTPUT_FLUID_TANK;
            ClearTankMessageToServer clearTankMessageToServer = new ClearTankMessageToServer(targetBlock, tankSlot);
            FarmersLife.simpleChannel.sendToServer(clearTankMessageToServer);
        } else if (isPointInRegion(GUI_FUEL_FIRE_BUTTON.getX(), GUI_FUEL_FIRE_BUTTON.getY(), GUI_FUEL_FIRE_BUTTON.getWidth(), GUI_FUEL_FIRE_BUTTON.getHeight(), mouseX, mouseY)) {
            SetBooleanMessageToServer setBooleanMessageToServer = new SetBooleanMessageToServer(targetBlock);
            FarmersLife.simpleChannel.sendToServer(setBooleanMessageToServer);
        }
        return super.mouseClicked(mouseX, mouseY, keycode);
    }
}
