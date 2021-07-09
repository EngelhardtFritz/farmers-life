package com.asheriit.asheriitsfarmerslife.client.gui;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.ContainerHelper;
import com.asheriit.asheriitsfarmerslife.api.utils.TemperatureUnit;
import com.asheriit.asheriitsfarmerslife.api.utils.renderers.FluidRenderHelper;
import com.asheriit.asheriitsfarmerslife.container.TemperatureChamberContainer;
import com.asheriit.asheriitsfarmerslife.network.to_server.ClearTankMessageToServer;
import com.asheriit.asheriitsfarmerslife.network.to_server.SetBooleanMessageToServer;
import com.asheriit.asheriitsfarmerslife.network.to_server.TemperatureChangeMessageToServer;
import com.asheriit.asheriitsfarmerslife.tileentity.TemperatureChamberTileEntity;
import com.asheriit.asheriitsfarmerslife.utils.ToolTipHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.ArrayList;
import java.util.List;

public class TemperatureChamberScreen extends ContainerScreen<TemperatureChamberContainer> {
    public static final ResourceLocation TEMPERATURE_CHAMBER_GUI_TEXTURE = new ResourceLocation(FarmersLife.MOD_ID, "textures/gui/container/temperature_chamber.png");
    public TemperatureChamberTileEntity temperatureChamberTileEntity;

    private static final float SCALE_Y = 8.5F / 9.0F;
    private static final float RESET_SCALE_Y = 1.0F / SCALE_Y;

    protected static final Rectangle2d GUI_BUFFER_PROGRESS = new Rectangle2d(211, 17, 2, 17);
    protected static final Rectangle2d GUI_HOT_BUFFER_PROGRESS = new Rectangle2d(207, 29, 2, 29);
    protected static final Rectangle2d GUI_HOT_BUFFER = new Rectangle2d(22, 19, 2, 29);
    protected static final Rectangle2d GUI_COLD_BUFFER_PROGRESS = new Rectangle2d(209, 29, 2, 29);
    protected static final Rectangle2d GUI_COLD_BUFFER = new Rectangle2d(46, 19, 2, 29);
    protected static final Rectangle2d GUI_ARROW = new Rectangle2d(192, 30, 16, 12);
    protected static final Rectangle2d GUI_DELETE_INPUT_TANK_CONTENT = new Rectangle2d(62, 71, 15, 15);
    protected static final Rectangle2d GUI_DELETE_ACTIVATED = new Rectangle2d(192, 42, 15, 15);
    protected static final Rectangle2d GUI_TEMP_UP_ARROW = new Rectangle2d(93, 21, 15, 15);
    protected static final Rectangle2d GUI_TEMP_UP_ARROW_ACTIVATED = new Rectangle2d(192, 0, 15, 15);
    protected static final Rectangle2d GUI_TEMP_DOWN_ARROW = new Rectangle2d(93, 51, 15, 15);
    protected static final Rectangle2d GUI_TEMP_DOWN_ARROW_ACTIVATED = new Rectangle2d(192, 15, 15, 15);
    protected static final Rectangle2d GUI_INPUT_TANK = new Rectangle2d(63, 19, 13, 49);
    protected static final Rectangle2d GUI_TEMPERATURE_UNIT = new Rectangle2d(82, 39, 37, 9);

    public TemperatureChamberScreen(TemperatureChamberContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.temperatureChamberTileEntity = screenContainer.temperatureChamberTileEntity;

        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 192;
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
        this.minecraft.getTextureManager().bindTexture(TEMPERATURE_CHAMBER_GUI_TEXTURE);
        // Use this blit if the full texture is 256x256px else the texture width and height have to be attached as two more variables
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        // Draw buffer input progress
        int scaledBufferProgressHeight = -ContainerHelper.calculateArrowWidth(GUI_BUFFER_PROGRESS.getHeight(),
                TemperatureChamberTileEntity.BUFFER_INPUT_COOLDOWN,
                this.container.bufferCooldown.get());
        // Parameters: X-Coordinate of default arrow, Y-Coordinate of default arrow, X-Coordinate of filled arrow, Y-Coordinate of filled arrow, Scaled width of the arrow, Height of the arrow
        this.blit(this.guiLeft + 34, this.guiTop + 52 + GUI_BUFFER_PROGRESS.getHeight(), GUI_BUFFER_PROGRESS.getX(), GUI_BUFFER_PROGRESS.getY(), GUI_BUFFER_PROGRESS.getWidth(), scaledBufferProgressHeight);

        // Draw heat buffer progress
        int scaledHeatBufferProgressHeight = -ContainerHelper.calculateArrowWidth(GUI_HOT_BUFFER_PROGRESS.getHeight(),
                TemperatureChamberTileEntity.MAX_HEAT_BUFFER,
                this.container.heatBuffer.get());
        // Parameters: X-Coordinate of default arrow, Y-Coordinate of default arrow, X-Coordinate of filled arrow, Y-Coordinate of filled arrow, Scaled width of the arrow, Height of the arrow
        this.blit(this.guiLeft + 22, this.guiTop + 19 + GUI_HOT_BUFFER_PROGRESS.getHeight(), GUI_HOT_BUFFER_PROGRESS.getX(), GUI_HOT_BUFFER_PROGRESS.getY(), GUI_HOT_BUFFER_PROGRESS.getWidth(), scaledHeatBufferProgressHeight);

        // Draw cold buffer progress
        int scaledColdBufferProgressHeight = -ContainerHelper.calculateArrowWidth(GUI_COLD_BUFFER_PROGRESS.getHeight(),
                TemperatureChamberTileEntity.MAX_COLD_BUFFER,
                this.container.coldBuffer.get());
        // Parameters: X-Coordinate of default arrow, Y-Coordinate of default arrow, X-Coordinate of filled arrow, Y-Coordinate of filled arrow, Scaled width of the arrow, Height of the arrow
        this.blit(this.guiLeft + 46, this.guiTop + 19 + GUI_COLD_BUFFER_PROGRESS.getHeight(), GUI_COLD_BUFFER_PROGRESS.getX(), GUI_COLD_BUFFER_PROGRESS.getY(), GUI_COLD_BUFFER_PROGRESS.getWidth(), scaledColdBufferProgressHeight);

        // Draw the GUI arrow
        int scaledArrowWidth = ContainerHelper.calculateArrowWidth(GUI_ARROW.getWidth(),
                this.temperatureChamberTileEntity.getProcessTimeTotal(),
                this.container.currentProcessTime.get());
        // Parameters: X-Coordinate of default arrow, Y-Coordinate of default arrow, X-Coordinate of filled arrow, Y-Coordinate of filled arrow, Scaled width of the arrow, Height of the arrow
        this.blit(this.guiLeft + 144, this.guiTop + 38, GUI_ARROW.getX(), GUI_ARROW.getY(), scaledArrowWidth, GUI_ARROW.getHeight());

        if (isPointInRegion(GUI_DELETE_INPUT_TANK_CONTENT.getX(), GUI_DELETE_INPUT_TANK_CONTENT.getY(), GUI_DELETE_INPUT_TANK_CONTENT.getWidth(), GUI_DELETE_INPUT_TANK_CONTENT.getHeight(), mouseX, mouseY)) {
            this.blit(this.guiLeft + GUI_DELETE_INPUT_TANK_CONTENT.getX(), this.guiTop + GUI_DELETE_INPUT_TANK_CONTENT.getY(),
                    GUI_DELETE_ACTIVATED.getX(), GUI_DELETE_ACTIVATED.getY(), GUI_DELETE_ACTIVATED.getWidth(), GUI_DELETE_ACTIVATED.getHeight());
        }

        if (isPointInRegion(GUI_TEMP_UP_ARROW.getX(), GUI_TEMP_UP_ARROW.getY(), GUI_TEMP_UP_ARROW.getWidth(), GUI_TEMP_UP_ARROW.getHeight(), mouseX, mouseY)) {
            this.blit(this.guiLeft + GUI_TEMP_UP_ARROW.getX(), this.guiTop + GUI_TEMP_UP_ARROW.getY(),
                    GUI_TEMP_UP_ARROW_ACTIVATED.getX(), GUI_TEMP_UP_ARROW_ACTIVATED.getY(), GUI_TEMP_UP_ARROW_ACTIVATED.getWidth(), GUI_TEMP_UP_ARROW_ACTIVATED.getHeight());
        }

        if (isPointInRegion(GUI_TEMP_DOWN_ARROW.getX(), GUI_TEMP_DOWN_ARROW.getY(), GUI_TEMP_DOWN_ARROW.getWidth(), GUI_TEMP_DOWN_ARROW.getHeight(), mouseX, mouseY)) {
            this.blit(this.guiLeft + GUI_TEMP_DOWN_ARROW.getX(), this.guiTop + GUI_TEMP_DOWN_ARROW.getY(),
                    GUI_TEMP_DOWN_ARROW_ACTIVATED.getX(), GUI_TEMP_DOWN_ARROW_ACTIVATED.getY(), GUI_TEMP_DOWN_ARROW_ACTIVATED.getWidth(), GUI_TEMP_DOWN_ARROW_ACTIVATED.getHeight());
        }

        // Draw the input fluid tank
        FluidRenderHelper.drawFluidTank(this.temperatureChamberTileEntity.getFluidInTank(TemperatureChamberTileEntity.INPUT_FLUID_TANK),
                ContainerHelper.getScaledInputTankHeight(this.container.temperatureChamberTileEntity.getTank(TemperatureChamberTileEntity.INPUT_FLUID_TANK), GUI_INPUT_TANK.getHeight()),
                this.guiLeft + GUI_INPUT_TANK.getX(),
                this.guiTop + GUI_INPUT_TANK.getY(),
                GUI_INPUT_TANK.getWidth(),
                GUI_INPUT_TANK.getHeight());
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        // The color value is formatted as hex number
        this.font.drawString(this.title.getFormattedText(), 16.0F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 16.0F, (float) (this.ySize - 96 + 2), 4210752);

        // Draw target temperature string (divide temperature by 100 to get correct values)
        TemperatureUnit tempUnit = TemperatureUnit.getTemperatureUnitById(this.container.temperatureUnit.get());
        double temperature = TemperatureUnit.getTemperatureFromKelvin(tempUnit, (this.container.targetTemperature.get() / 100.0F));
        String temperatureString = (new StringTextComponent(ToolTipHelper.TEMPERATURE_FORMAT.format(temperature) + " " + tempUnit.getAbbreviation()).applyTextStyle(TextFormatting.DARK_GRAY)).getFormattedText();

        // Scale the matrix to always match the rectangle of the screen
        float scaleXZ = 35.0F / this.font.getStringWidth(temperatureString);
        if (scaleXZ >= SCALE_Y) {
            scaleXZ = SCALE_Y;
        }
        float lineY = (scaleXZ >= SCALE_Y) ? 40.0F * (1.0F / scaleXZ) : (40.0F * RESET_SCALE_Y);
        float center = 100.5F;      // center of the line (scale with calculated scale)
        float scaleXZReset = 1.0F / scaleXZ;
        float lineX = ((center * scaleXZReset) - (this.font.getStringWidth(temperatureString) / 2.0F));

        RenderSystem.scalef(scaleXZ, SCALE_Y, 1.0F);
        this.font.drawString(temperatureString, lineX, lineY, 4210752);
        RenderSystem.scalef(scaleXZReset, RESET_SCALE_Y, 1.0F);
    }

    @Override
    protected void renderHoveredToolTip(int mouseX, int mouseY) {
        super.renderHoveredToolTip(mouseX, mouseY);
        if (isPointInRegion(GUI_DELETE_INPUT_TANK_CONTENT.getX(), GUI_DELETE_INPUT_TANK_CONTENT.getY(), GUI_DELETE_INPUT_TANK_CONTENT.getWidth(), GUI_DELETE_INPUT_TANK_CONTENT.getHeight(), mouseX, mouseY)) {
            List<String> stringList = new ArrayList<>();
            stringList.add(ToolTipHelper.DELETE_CONTENT_TOOLTIP_LOCATION.getFormattedText());
            renderTooltip(stringList, mouseX, mouseY);
        }

        TemperatureUnit tempUnit = TemperatureUnit.getTemperatureUnitById(this.container.temperatureUnit.get());
        if (isPointInRegion(GUI_INPUT_TANK.getX(), GUI_INPUT_TANK.getY(), GUI_INPUT_TANK.getWidth(), GUI_INPUT_TANK.getHeight(), mouseX, mouseY)) {
            FluidTank tank = this.temperatureChamberTileEntity.getTank(TemperatureChamberTileEntity.INPUT_FLUID_TANK);
            renderTooltip(ToolTipHelper.createFluidTankWithTemperatureToolTip(tank, tempUnit, this.container.currentTemperature.get() / 100.0F, this.container.targetTemperature.get() / 100.0F), mouseX, mouseY);
        }

        if (isPointInRegion(GUI_TEMP_UP_ARROW.getX(), GUI_TEMP_UP_ARROW.getY(), GUI_TEMP_UP_ARROW.getWidth(), GUI_TEMP_UP_ARROW.getHeight(), mouseX, mouseY)) {
            List<String> stringList = new ArrayList<>();
            stringList.add(ToolTipHelper.TEMP_UP_TOOLTIP_LOCATION.getFormattedText());
            stringList.add(ToolTipHelper.CURRENT_TEMPERATURE_NOTE_TOOLTIP_LOCATION.applyTextStyle(TextFormatting.GRAY).getFormattedText());
            renderTooltip(stringList, mouseX, mouseY);
        }

        if (isPointInRegion(GUI_TEMP_DOWN_ARROW.getX(), GUI_TEMP_DOWN_ARROW.getY(), GUI_TEMP_DOWN_ARROW.getWidth(), GUI_TEMP_DOWN_ARROW.getHeight(), mouseX, mouseY)) {
            List<String> stringList = new ArrayList<>();
            stringList.add(ToolTipHelper.TEMP_DOWN_TOOLTIP_LOCATION.getFormattedText());
            stringList.add(ToolTipHelper.CURRENT_TEMPERATURE_NOTE_TOOLTIP_LOCATION.applyTextStyle(TextFormatting.GRAY).getFormattedText());
            renderTooltip(stringList, mouseX, mouseY);
        }

        if (isPointInRegion(GUI_HOT_BUFFER.getX(), GUI_HOT_BUFFER.getY(), GUI_HOT_BUFFER.getWidth(), GUI_HOT_BUFFER.getHeight(), mouseX, mouseY)) {
            List<String> stringList = new ArrayList<>();
            StringTextComponent heatDecreaseRate = new StringTextComponent(ToolTipHelper.TEMPERATURE_FORMAT.format(this.container.heatDecreasePerTick.get() / TemperatureChamberTileEntity.TEMPERATURE_CHANGE_COOLDOWN) + "/t");
            StringTextComponent heatBuffer = new StringTextComponent("(" + (this.container.heatBuffer.get() / 10) + "/" + (TemperatureChamberTileEntity.MAX_HEAT_BUFFER / 10) + ")");
            stringList.add(ToolTipHelper.HEAT_BUFFER.applyTextStyle(TextFormatting.LIGHT_PURPLE).getFormattedText() + " " + heatBuffer.applyTextStyle(TextFormatting.DARK_GRAY).getFormattedText());
            stringList.add("");
            stringList.add(ToolTipHelper.DECREASE_RATE.getFormattedText() + ": " + heatDecreaseRate.applyTextStyle(TextFormatting.LIGHT_PURPLE).getFormattedText());
            renderTooltip(stringList, mouseX, mouseY);
        }

        if (isPointInRegion(GUI_COLD_BUFFER.getX(), GUI_COLD_BUFFER.getY(), GUI_COLD_BUFFER.getWidth(), GUI_COLD_BUFFER.getHeight(), mouseX, mouseY)) {
            List<String> stringList = new ArrayList<>();
            StringTextComponent coldDecreaseRate = new StringTextComponent(ToolTipHelper.TEMPERATURE_FORMAT.format(this.container.coldDecreasePerTick.get() / TemperatureChamberTileEntity.TEMPERATURE_CHANGE_COOLDOWN) + "/t");
            StringTextComponent coldBuffer = new StringTextComponent("(" + (this.container.coldBuffer.get() / 10) + "/" + (TemperatureChamberTileEntity.MAX_COLD_BUFFER / 10) + ")");
            stringList.add(ToolTipHelper.COLD_BUFFER.applyTextStyle(TextFormatting.LIGHT_PURPLE).getFormattedText() + " " + coldBuffer.applyTextStyle(TextFormatting.DARK_GRAY).getFormattedText());
            stringList.add("");
            stringList.add(ToolTipHelper.DECREASE_RATE.getFormattedText() + ": " + coldDecreaseRate.applyTextStyle(TextFormatting.LIGHT_PURPLE).getFormattedText());
            renderTooltip(stringList, mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int keycode) {
        // For keycodes look at net.minecraft.client.util.InputMappings
        // Send message to server to clear tank content
        Vec3d targetBlock = new Vec3d(this.temperatureChamberTileEntity.getPos());
        if (isPointInRegion(GUI_DELETE_INPUT_TANK_CONTENT.getX(), GUI_DELETE_INPUT_TANK_CONTENT.getY(), GUI_DELETE_INPUT_TANK_CONTENT.getWidth(), GUI_DELETE_INPUT_TANK_CONTENT.getHeight(), mouseX, mouseY) && keycode == 0) {
            byte tankSlot = TemperatureChamberTileEntity.INPUT_FLUID_TANK;
            ClearTankMessageToServer message = new ClearTankMessageToServer(targetBlock, tankSlot);
            FarmersLife.simpleChannel.sendToServer(message);
        } else if (isPointInRegion(GUI_TEMP_UP_ARROW.getX(), GUI_TEMP_UP_ARROW.getY(), GUI_TEMP_UP_ARROW.getWidth(), GUI_TEMP_UP_ARROW.getHeight(), mouseX, mouseY) && keycode == 0) {
            this.handleTemperatureChangeClick(targetBlock, true);
        } else if (isPointInRegion(GUI_TEMP_DOWN_ARROW.getX(), GUI_TEMP_DOWN_ARROW.getY(), GUI_TEMP_DOWN_ARROW.getWidth(), GUI_TEMP_DOWN_ARROW.getHeight(), mouseX, mouseY) && keycode == 0) {
            this.handleTemperatureChangeClick(targetBlock, false);
        } else if (isPointInRegion(GUI_TEMPERATURE_UNIT.getX(), GUI_TEMPERATURE_UNIT.getY(), GUI_TEMPERATURE_UNIT.getWidth(), GUI_TEMPERATURE_UNIT.getHeight(), mouseX, mouseY) && keycode == 0) {
            SetBooleanMessageToServer message = new SetBooleanMessageToServer(targetBlock);
            FarmersLife.simpleChannel.sendToServer(message);
        }
        return super.mouseClicked(mouseX, mouseY, keycode);
    }

    /**
     * Handles mouse click which increases or decrease the temperature of the TileEntity.
     * The amount to change the temperature depends on the player holding shift, control, alt or no further keys.
     * Sends packet with update information to the Server.
     *
     * @param targetBlock:    Coordinates of the target TileEntity
     * @param shouldIncrease: True if the temperature should be increased, false otherwise
     */
    private void handleTemperatureChangeClick(Vec3d targetBlock, boolean shouldIncrease) {
        TemperatureChangeMessageToServer message;
        if (hasShiftDown() && !hasControlDown() && !hasAltDown()) {
            message = new TemperatureChangeMessageToServer(targetBlock, shouldIncrease, (short) TemperatureChamberTileEntity.TEMPERATURE_STEP_WEIGHT_SHIFT);
        } else if (hasControlDown() && !hasShiftDown() && !hasAltDown()) {
            message = new TemperatureChangeMessageToServer(targetBlock, shouldIncrease, (short) TemperatureChamberTileEntity.TEMPERATURE_STEP_WEIGHT_CONTROL);
        } else if (hasAltDown() && !hasControlDown() && !hasShiftDown()) {
            message = new TemperatureChangeMessageToServer(targetBlock, shouldIncrease, (short) TemperatureChamberTileEntity.TEMPERATURE_STEP_WEIGHT_ALT);
        } else {
            message = new TemperatureChangeMessageToServer(targetBlock, shouldIncrease, (short) TemperatureChamberTileEntity.TEMPERATURE_STEP_WEIGHT);
        }
        FarmersLife.simpleChannel.sendToServer(message);
    }
}
