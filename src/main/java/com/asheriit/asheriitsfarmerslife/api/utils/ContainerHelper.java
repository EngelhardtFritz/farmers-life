package com.asheriit.asheriitsfarmerslife.api.utils;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ContainerHelper {
    /**
     * Calculates the relative width with considering the current progression
     *
     * @param arrowWidth: Max width of the arrow
     * @param maxProgression: Max Progression
     * @param curProgression: Current Progression
     * @return Width depending on the progression and width of the arrow
     */
    public static int calculateArrowWidth(int arrowWidth, int maxProgression, int curProgression) {
        if (curProgression <= 0 || maxProgression <= 0) {
            return 0;
        }
        return Math.round(((float)curProgression * (float)arrowWidth) / (float)maxProgression);
    }

    /**
     * Calculates the relative width with considering the current progression
     *
     * @param arrowWidth: Max width of the arrow
     * @param maxProgression: Max Progression
     * @param curProgression: Current Progression
     * @param min: Minimum returned if all values are valid
     * @return Width depending on the progression and width of the arrow
     */
    public static int calculateArrowWidthWithMinimum(int arrowWidth, int maxProgression, int curProgression, int min) {
        if (curProgression <= 0 || maxProgression <= 0 || min < 0) {
            return 0;
        }
        return Math.min(min, calculateArrowWidth(arrowWidth, maxProgression, curProgression));
    }

    /**
     * Calculates the scaled progression width for the fluid tank
     *
     * @param tankIn: FluidTank object to calculate the height for
     * @param tankHeight: Height of the tank gui in pixels
     * @return height of the tank to draw
     */
    @OnlyIn(Dist.CLIENT)
    public static int getScaledInputTankHeight(FluidTank tankIn, int tankHeight) {
        int minRenderHeight = 1;
        FluidStack tankFluidStack = tankIn.getFluid();
        int fluidAmountInTank = tankFluidStack.getAmount();
        int fluidAmountMax = tankIn.getCapacity();

        if (fluidAmountInTank == 0 || tankFluidStack.getFluid() == null) {
            return 0;
        }

        return Math.max(minRenderHeight, (fluidAmountInTank * tankHeight) / fluidAmountMax);
    }
}
