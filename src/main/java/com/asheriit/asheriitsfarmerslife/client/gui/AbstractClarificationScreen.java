package com.asheriit.asheriitsfarmerslife.client.gui;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.utils.ToolTipHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractClarificationScreen<T extends Container> extends ContainerScreen<T> {
    public static final ResourceLocation CLARIFICATION_MACHINE_GUI_TEXTURE = new ResourceLocation(FarmersLife.MOD_ID, "textures/gui/container/clarification_machine.png");

    protected static final Rectangle2d GUI_ARROW = new Rectangle2d(20, 188, 8, 19);
    protected static final Rectangle2d GUI_INPUT_TANK = new Rectangle2d(45, 19, 14, 49);
    protected static final Rectangle2d GUI_OUTPUT_TANK = new Rectangle2d(117, 19, 14, 49);
    protected static final Rectangle2d GUI_DELETE_INPUT_TANK_CONTENT = new Rectangle2d(42, 72, 18, 18);
    protected static final Rectangle2d GUI_DELETE_OUTPUT_TANK_CONTENT = new Rectangle2d(114, 72, 18, 18);
    protected static final Rectangle2d GUI_DELETE_ACTIVATED = new Rectangle2d(0, 188, 20, 20);

    public AbstractClarificationScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 176;
        this.ySize = 188;
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
        this.minecraft.getTextureManager().bindTexture(CLARIFICATION_MACHINE_GUI_TEXTURE);
        // Use this blit if the full texture is 256x256px else the texture width and height have to be attached as two more variables
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

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
        super.renderHoveredToolTip(mouseX, mouseY);
    }
}
