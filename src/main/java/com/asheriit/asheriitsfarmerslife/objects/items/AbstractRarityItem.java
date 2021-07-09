package com.asheriit.asheriitsfarmerslife.objects.items;

import com.asheriit.asheriitsfarmerslife.properties.ItemTierType;
import net.minecraft.item.Item;

public abstract class AbstractRarityItem extends Item {
    private ItemTierType itemTierType;

    public AbstractRarityItem(Properties properties, ItemTierType itemTierType) {
        super(properties);
        this.itemTierType = itemTierType;
    }

    public ItemTierType getItemTierType() {
        return itemTierType;
    }
}
