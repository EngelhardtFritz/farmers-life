package com.asheriit.asheriitsfarmerslife.api.utils;

public enum TemperatureUnit {
    KELVIN(0, "Kelvin", "K"),
    CELSIUS(1, "Celsius", "\u00B0C"),
    FAHRENHEIT(2, "Fahrenheit", "\u00B0F");

    private final int id;
    private final String name;
    private final String abbreviation;

    TemperatureUnit(int id, String name, String abbreviation) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TemperatureUnit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                '}';
    }

    /**
     * Get the TemperatureUnit by its Id
     * @param id: ID of the TemperatureUnit
     * @return If Id is found returns TemperatureUnit, KELVIN otherwise
     */
    public static TemperatureUnit getTemperatureUnitById(int id) {
        for (TemperatureUnit tempUnit: TemperatureUnit.values()) {
            if (tempUnit.getId() == id) {
                return tempUnit;
            }
        }
        return KELVIN;
    }

    /**
     * Get the temperature as double converted from one TemperatureUnit to another
     *
     * @param from: TemperatureUnit the current temperature is
     * @param to: TemperatureUnit to convert the temperature to
     * @param temperature: Float value of the temperature
     * @return converted temperature if from != to, else returns given temperature
     */
    public static double getFormattedTemperature(TemperatureUnit from, TemperatureUnit to, float temperature) {
        if (from == to) return temperature;

        switch (from) {
            case KELVIN:
                return to == CELSIUS ? temperature - 273.15F : (temperature - 273.15F) * (9.0F / 5.0F) + 32;
            case CELSIUS:
                return to == KELVIN ? temperature + 273.15F : temperature + 32;
            case FAHRENHEIT:
                return to == KELVIN ? (temperature - 32) * (5.0F / 9.0F) + 273.15F : temperature - 32;
            default:
                return temperature;
        }
    }

    /**
     * Gets converted temperature for a given temperature in Kelvin
     * @param to: TemperatureUnit to convert to
     * @param temperature: Float value of the temperature in Kelvin
     * @return converted temperature
     */
    public static double getTemperatureFromKelvin(TemperatureUnit to, float temperature) {
        if (to == KELVIN) return temperature;
        return to == CELSIUS ? temperature - 273.15F : (temperature - 273.15F) * (9.0F / 5.0F) + 32;
    }
}
