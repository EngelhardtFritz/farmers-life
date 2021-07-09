package com.asheriit.asheriitsfarmerslife.state.properties;

import net.minecraft.util.IStringSerializable;

public enum DiagonalBlockType implements IStringSerializable {
    EMPTY("empty"),
    NORTHEAST("ne"),
    SOUTHEAST("se"),
    SOUTHWEST("sw"),
    NORTHWEST("nw"),
    ALL("all");

    private final String type;

    private DiagonalBlockType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.type;
    }
}
