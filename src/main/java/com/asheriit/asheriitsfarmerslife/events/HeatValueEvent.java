package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.init.FarmersLifeEventFactory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;

/**
 * {@link HeatValueEvent} is fired when determining the heat value for an ItemStack. <br>
 * <br>
 * To set the heat value of your own item, implement a handler for this event.<br>
 * <br>
 * This event is fired from {@link FarmersLifeEventFactory#getItemHeatValue(ItemStack)}.<br>
 * <br>
 * This event is {@link Cancelable} to prevent later handlers from changing the value.<br>
 * <br>
 * This event does not have a result. {@link HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.
 **/
@Cancelable
public class HeatValueEvent extends Event {
    @Nonnull
    private final ItemStack itemStack;
    private int heatValue;

    public HeatValueEvent(@Nonnull ItemStack itemStack, int heatValue) {
        this.itemStack = itemStack;
        this.heatValue = heatValue;
    }

    /**
     * Get the ItemStack with cold value
     */
    @Nonnull
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Set the heat value for the given ItemStack.
     * Setting it to 0 will prevent the item from being used as a heat item.
     */
    public void setHeatValue(int coldValue) {
        if (coldValue >= 0) {
            this.heatValue = coldValue;
            setCanceled(true);
        }
    }

    /**
     * The resulting value of this event, the heat value for the ItemStack.
     * A value of 0 will prevent the item from being used as a heat item.
     */
    public int getHeatValue() {
        return heatValue;
    }
}
