package com.asheriit.asheriitsfarmerslife.properties;

public enum ItemTierType {
    COMMON(1),
    UNCOMMON(2),
    RARE(3),
    LEGENDARY(4);

    private final int itemTier;

    private ItemTierType(int itemTier) {
        this.itemTier = itemTier;
    }

    public int getItemTier() {
        return itemTier;
    }
}
