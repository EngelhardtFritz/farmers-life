package com.asheriit.asheriitsfarmerslife.utils;

import com.asheriit.asheriitsfarmerslife.api.utils.FormattingHelper;
import com.asheriit.asheriitsfarmerslife.api.utils.TemperatureUnit;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ToolTipHelper {
    public static final DecimalFormat TEMPERATURE_FORMAT = new DecimalFormat("###.#");

    public static final ITextComponent HAS_SHIFT_DOWN_TOOLTIP_LOCATION = new TranslationTextComponent("tooltip.asheriitsfarmerslife.shift_down");
    public static final ITextComponent HAS_ALT_DOWN_TOOLTIP_LOCATION = new TranslationTextComponent("tooltip.asheriitsfarmerslife.alt_down");
    public static final ITextComponent MACHINE_AUTOMATION_TOOLTIP_LOCATION = new TranslationTextComponent("tooltip.asheriitsfarmerslife.machines.slot_description");
    public static final ITextComponent EMPTY_FLUID_TANK_TOOLTIP_LOCATION = new TranslationTextComponent("tooltip.asheriitsfarmerslife.empty_fluid_tank");
    public static final ITextComponent FLUID_TANK_TOOLTIP_LOCATION = new TranslationTextComponent("tooltip.asheriitsfarmerslife.fluid_tank");
    public static final ITextComponent WINE_GRAPE_PLACEMENT_TOOLTIP_LOCATION = new TranslationTextComponent("tooltip.asheriitsfarmerslife.how_to_place");
    public static final ITextComponent DELETE_CONTENT_TOOLTIP_LOCATION = new TranslationTextComponent("tooltip.asheriitsfarmerslife.delete_content");
    public static final ITextComponent DEFAULT_TEMPERATURE_TOOLTIP_LOCATION = new TranslationTextComponent("tooltip.asheriitsfarmerslife.default_temperature");
    public static final ITextComponent CURRENT_TEMPERATURE_TOOLTIP_LOCATION = new TranslationTextComponent("tooltip.asheriitsfarmerslife.current_temperature");
    public static final ITextComponent TARGET_TEMPERATURE_TOOLTIP_LOCATION = new TranslationTextComponent("tooltip.asheriitsfarmerslife.target_temperature");
    public static final ITextComponent CURRENT_TEMPERATURE_NOTE_TOOLTIP_LOCATION = new TranslationTextComponent("tooltip.asheriitsfarmerslife.current_temperature_note");
    public static final ITextComponent TEMP_UP_TOOLTIP_LOCATION = new TranslationTextComponent("tooltip.asheriitsfarmerslife.temp_up");
    public static final ITextComponent TEMP_DOWN_TOOLTIP_LOCATION = new TranslationTextComponent("tooltip.asheriitsfarmerslife.temp_down");
    public static final ITextComponent ENABLED = new TranslationTextComponent("tooltip.asheriitsfarmerslife.enabled").applyTextStyle(TextFormatting.LIGHT_PURPLE);
    public static final ITextComponent DISABLED = new TranslationTextComponent("tooltip.asheriitsfarmerslife.disabled").applyTextStyle(TextFormatting.LIGHT_PURPLE);
    public static final ITextComponent DECREASE_RATE = new TranslationTextComponent("tooltip.asheriitsfarmerslife.decrease_rate");
    public static final ITextComponent HEAT_BUFFER = new TranslationTextComponent("tooltip.asheriitsfarmerslife.heat_buffer");
    public static final ITextComponent COLD_BUFFER = new TranslationTextComponent("tooltip.asheriitsfarmerslife.cold_buffer");

    // Filtration machine information
    public static final ITextComponent CLARIFICATION_NORTH_LOCATION = new TranslationTextComponent("tooltip.asheriitsfarmerslife.clarification_machine.north");
    public static final ITextComponent CLARIFICATION_WEST_EAST_LOCATION = new TranslationTextComponent("tooltip.asheriitsfarmerslife.clarification_machine.west_east");
    public static final ITextComponent CLARIFICATION_SOUTH_LOCATION = new TranslationTextComponent("tooltip.asheriitsfarmerslife.clarification_machine.south");
    public static final ITextComponent CLARIFICATION_UP_LOCATION = new TranslationTextComponent("tooltip.asheriitsfarmerslife.clarification_machine.up");
    public static final ITextComponent CLARIFICATION_DOWN_LOCATION = new TranslationTextComponent("tooltip.asheriitsfarmerslife.clarification_machine.down");

    // Boiling cauldron information
    public static final ITextComponent USE_FUEL = new TranslationTextComponent("tooltip.asheriitsfarmerslife.boiling_cauldron.use_fuel");
    public static final ITextComponent FUEL_ENABLED_RESULT = new TranslationTextComponent("tooltip.asheriitsfarmerslife.boiling_cauldron.fuel_enabled.result");
    public static final ITextComponent FUEL_DISABLED_RESULT = new TranslationTextComponent("tooltip.asheriitsfarmerslife.boiling_cauldron.fuel_disabled.result");

    // Important: Never add any siblings or other components to the empty line
    private static final StringTextComponent EMPTY_LINE = new StringTextComponent("");
    public static final String STYLE_WHITE_COLON_WITH_PURPLE_TEXT = TextFormatting.WHITE + ": " + TextFormatting.LIGHT_PURPLE;

    /**
     * Gets a copy of an empty line to add for tooltips
     *
     * @return Empty StringTextComponent
     */
    @Nonnull
    public static StringTextComponent getEmptyLine() {
        return EMPTY_LINE.shallowCopy();
    }

    /**
     * Creates a tooltip for a fluid tank with the current fullness / maxCapacity and the contained fluid
     *
     * @param tank: Fluid tank
     * @return list of strings with all information
     */
    public static List<String> createFluidTankToolTip(FluidTank tank) {
        List<String> stringList = new ArrayList<>();
        FluidStack fluidStack = tank.getFluid();
        ITextComponent fluidName = fluidStack.getDisplayName();
        int fluidAmount = fluidStack.getAmount();
        int fluidMaxAmount = tank.getCapacity();

        if (!fluidStack.isEmpty()) {
            stringList.add(ToolTipHelper.FLUID_TANK_TOOLTIP_LOCATION.getFormattedText() + " (" + fluidName.getFormattedText() + ")");
        } else {
            stringList.add(ToolTipHelper.EMPTY_FLUID_TANK_TOOLTIP_LOCATION.getFormattedText());
        }
        stringList.add(TextFormatting.GRAY + FormattingHelper.formatNumber(fluidAmount) + " / " + FormattingHelper.formatNumber(fluidMaxAmount) + "mB");

        return stringList;
    }


    /**
     * Creates a tooltip for a fluid tank with the current fullness / maxCapacity and the contained fluid
     * Shows the temperature of the fluid in the tank
     *
     * @param tank: Fluid tank
     * @param currentTemp: Current temperature
     * @param targetTemp: Target temperature
     * @return list of strings with all information
     */
    public static List<String> createFluidTankWithTemperatureToolTip(FluidTank tank, TemperatureUnit temperatureUnit, float currentTemp, float targetTemp) {
        List<String> stringList = new ArrayList<>();
        FluidStack fluidStack = tank.getFluid();
        ITextComponent fluidName = fluidStack.getDisplayName();
        float temperature = fluidStack.getFluid().getAttributes().getTemperature();
        int fluidAmount = fluidStack.getAmount();
        int fluidMaxAmount = tank.getCapacity();

        if (!fluidStack.isEmpty()) {
            stringList.add(ToolTipHelper.FLUID_TANK_TOOLTIP_LOCATION.getFormattedText() + " (" + fluidName.getFormattedText() + ")");
            if (currentTemp == -1) {
                stringList.add(DEFAULT_TEMPERATURE_TOOLTIP_LOCATION.getFormattedText() + ": " + new StringTextComponent(TEMPERATURE_FORMAT.format(TemperatureUnit.getTemperatureFromKelvin(temperatureUnit, temperature))).applyTextStyle(TextFormatting.LIGHT_PURPLE).getFormattedText() + " " + temperatureUnit.getAbbreviation());
            } else {
                stringList.add(DEFAULT_TEMPERATURE_TOOLTIP_LOCATION.getFormattedText() + ": " + new StringTextComponent(TEMPERATURE_FORMAT.format(TemperatureUnit.getTemperatureFromKelvin(temperatureUnit, temperature))).applyTextStyle(TextFormatting.LIGHT_PURPLE).getFormattedText() + " " + temperatureUnit.getAbbreviation());
                stringList.add(CURRENT_TEMPERATURE_TOOLTIP_LOCATION.getFormattedText() + ": " + new StringTextComponent(TEMPERATURE_FORMAT.format(TemperatureUnit.getTemperatureFromKelvin(temperatureUnit, currentTemp))).applyTextStyle(TextFormatting.LIGHT_PURPLE).getFormattedText() + " " + temperatureUnit.getAbbreviation());
                stringList.add(TARGET_TEMPERATURE_TOOLTIP_LOCATION.getFormattedText() + ": " + new StringTextComponent(TEMPERATURE_FORMAT.format(TemperatureUnit.getTemperatureFromKelvin(temperatureUnit, targetTemp))).applyTextStyle(TextFormatting.LIGHT_PURPLE).getFormattedText() + " " + temperatureUnit.getAbbreviation());
            }
        } else {
            stringList.add(ToolTipHelper.EMPTY_FLUID_TANK_TOOLTIP_LOCATION.getFormattedText());
        }
        stringList.add(TextFormatting.GRAY + FormattingHelper.formatNumber(fluidAmount) + " / " + FormattingHelper.formatNumber(fluidMaxAmount) + "mB");

        return stringList;
    }
}
