package com.asheriit.asheriitsfarmerslife.objects.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ChaffRodItem extends Item {
    public ChaffRodItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        // For vanilla burn times look at AbstractFurnaceTileEntity#getBurnTimes()
        return 200;
    }
}
