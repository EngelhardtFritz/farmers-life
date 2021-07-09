package com.asheriit.asheriitsfarmerslife.properties;

import net.minecraftforge.common.IExtensibleEnum;

public enum AlcoholicStrength implements IExtensibleEnum {
    WEAK("weak", 1),
    MEDIUM("medium", 2),
    STRONG("strong", 3),
    GODLY("godly", 5),
    DEADLY("deadly", 8);

    public final int level;
    public final String description;

    private AlcoholicStrength(String description, int level) {
        this.description = description;
        this.level = level;
    }

    public static AlcoholicStrength create(String name, String description, int level) {
        throw new IllegalStateException("Enum not extended");
    }
}
