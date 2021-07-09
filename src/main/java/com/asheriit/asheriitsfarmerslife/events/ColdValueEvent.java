package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.init.FarmersLifeEventFactory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;

/**
 * {@link ColdValueEvent} is fired when determining the cold value for an ItemStack. <br>
 * <br>
 * To set the cold value of your own item, implement a handler for this event.<br>
 * <br>
 * This event is fired from {@link FarmersLifeEventFactory#getItemColdValue(ItemStack)}.<br>
 * <br>
 * This event is {@link Cancelable} to prevent later handlers from changing the value.<br>
 * <br>
 * This event does not have a result. {@link HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 **/
@Cancelable
public class ColdValueEvent extends Event {
    @Nonnull
    private final ItemStack itemStack;
    private int coldValue;

    public ColdValueEvent(@Nonnull ItemStack itemStack, int coldValue) {
        this.itemStack = itemStack;
        this.coldValue = coldValue;
    }

    /**
     * Get the ItemStack with cold value
     */
    @Nonnull
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Set the cold value for the given ItemStack.
     * Setting it to 0 will prevent the item from being used as a cold item.
     */
    public void setColdValue(int coldValue) {
        if (coldValue >= 0) {
            this.coldValue = coldValue;
            setCanceled(true);
        }
    }

    /**
     * The resulting value of this event, the cold value for the ItemStack.
     * A value of 0 will prevent the item from being used as a cold item.
     */
    public int getColdValue() {
        return coldValue;
    }
}
