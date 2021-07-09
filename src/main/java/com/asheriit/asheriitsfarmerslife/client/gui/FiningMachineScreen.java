package com.asheriit.asheriitsfarmerslife.client.gui;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.ContainerHelper;
import com.asheriit.asheriitsfarmerslife.api.utils.renderers.FluidRenderHelper;
import com.asheriit.asheriitsfarmerslife.container.FiningMachineContainer;
import com.asheriit.asheriitsfarmerslife.network.to_server.ClearTankMessageToServer;
import com.asheriit.asheriitsfarmerslife.tileentity.FiningMachineTileEntity;
import com.asheriit.asheriitsfarmerslife.utils.ToolTipHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class FiningMachineScreen extends AbstractClarificationScreen<FiningMachineContainer> {
    public FiningMachineTileEntity finingMachineTileEntity;

    public FiningMachineScreen(FiningMachineContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.finingMachineTileEntity = screenContainer.finingMachineTileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        // Draw the GUI arrow
        int scaledArrowHeight = ContainerHelper.calculateArrowWidth(GUI_ARROW.getHeight(), this.finingMachineTileEntity.getProcessTimeTotal(), this.container.currentProcessTime.get());
        // Parameters: X-Coordinate of default arrow, Y-Coordinate of default arrow, X-Coordinate of filled arrow, Y-Coordinate of filled arrow, Scaled width of the arrow, Height of the arrow
        this.blit(this.guiLeft + 98, this.guiTop + 35, GUI_ARROW.getX(), GUI_ARROW.getY(), GUI_ARROW.getWidth(), scaledArrowHeight);

        // Draw the input fluid tank
        FluidRenderHelper.drawFluidTank(this.finingMachineTileEntity.getFluidInTank(FiningMachineTileEntity.INPUT_TANK),
                ContainerHelper.getScaledInputTankHeight(this.finingMachineTileEntity.getTank(FiningMachineTileEntity.INPUT_TANK), GUI_INPUT_TANK.getHeight()),
                this.guiLeft + GUI_INPUT_TANK.getX(),
                this.guiTop + GUI_INPUT_TANK.getY(),
                GUI_INPUT_TANK.getWidth(),
                GUI_INPUT_TANK.getHeight());

        // Draw the output fluid tank
        FluidRenderHelper.drawFluidTank(this.finingMachineTileEntity.getFluidInTank(FiningMachineTileEntity.OUTPUT_TANK),
                ContainerHelper.getScaledInputTankHeight(this.finingMachineTileEntity.getTank(FiningMachineTileEntity.OUTPUT_TANK), GUI_OUTPUT_TANK.getHeight()),
                this.guiLeft + GUI_OUTPUT_TANK.getX(),
                this.guiTop + GUI_OUTPUT_TANK.getY(),
                GUI_OUTPUT_TANK.getWidth(),
                GUI_OUTPUT_TANK.getHeight());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int keycode) {
        // For keycodes look at net.minecraft.client.util.InputMappings
        // Change look of buttons if hovered
        Vec3d targetBlock = new Vec3d(this.finingMachineTileEntity.getPos());
        if (isPointInRegion(GUI_DELETE_INPUT_TANK_CONTENT.getX(), GUI_DELETE_INPUT_TANK_CONTENT.getY(), GUI_DELETE_INPUT_TANK_CONTENT.getWidth(), GUI_DELETE_INPUT_TANK_CONTENT.getHeight(), mouseX, mouseY) && keycode == 0) {
            byte tankSlot = FiningMachineTileEntity.INPUT_TANK;
            ClearTankMessageToServer clearTankMessageToServer = new ClearTankMessageToServer(targetBlock, tankSlot);
            FarmersLife.simpleChannel.sendToServer(clearTankMessageToServer);
        } else if (isPointInRegion(GUI_DELETE_OUTPUT_TANK_CONTENT.getX(), GUI_DELETE_OUTPUT_TANK_CONTENT.getY(), GUI_DELETE_OUTPUT_TANK_CONTENT.getWidth(), GUI_DELETE_OUTPUT_TANK_CONTENT.getHeight(), mouseX, mouseY) && keycode == 0) {
            byte tankSlot = FiningMachineTileEntity.OUTPUT_TANK;
            ClearTankMessageToServer clearTankMessageToServer = new ClearTankMessageToServer(targetBlock, tankSlot);
            FarmersLife.simpleChannel.sendToServer(clearTankMessageToServer);
        }
        return super.mouseClicked(mouseX, mouseY, keycode);
    }

    @Override
    protected void renderHoveredToolTip(int mouseX, int mouseY) {
        if (isPointInRegion(GUI_INPUT_TANK.getX(), GUI_INPUT_TANK.getY(), GUI_INPUT_TANK.getWidth(), GUI_INPUT_TANK.getHeight(), mouseX, mouseY)) {
            FluidTank tank = this.finingMachineTileEntity.getTank(FiningMachineTileEntity.INPUT_TANK);
            renderTooltip(ToolTipHelper.createFluidTankToolTip(tank), mouseX, mouseY);
        }
        if (isPointInRegion(GUI_OUTPUT_TANK.getX(), GUI_OUTPUT_TANK.getY(), GUI_OUTPUT_TANK.getWidth(), GUI_OUTPUT_TANK.getHeight(), mouseX, mouseY)) {
            FluidTank tank = this.finingMachineTileEntity.getTank(FiningMachineTileEntity.OUTPUT_TANK);
            renderTooltip(ToolTipHelper.createFluidTankToolTip(tank), mouseX, mouseY);
        }
        super.renderHoveredToolTip(mouseX, mouseY);
    }
}
