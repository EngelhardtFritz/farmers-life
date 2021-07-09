package com.asheriit.asheriitsfarmerslife.state.properties;

import net.minecraft.util.IStringSerializable;

public enum TrellisMaterialType implements IStringSerializable {
    WOOD("wood"),
    IRON("iron");

    private final String type;

    private TrellisMaterialType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.type;
    }
}
