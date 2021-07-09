package com.asheriit.asheriitsfarmerslife.api.utils;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.util.ResourceLocation;

public class NetworkMessageConstants {
    public static final ResourceLocation SIMPLE_CHANNEL_RL = new ResourceLocation(FarmersLife.MOD_ID, "simple_channel");

    public static final String MESSAGE_PROTOCOL_VERSION = "1.0";
    public static final byte CLEAR_TANK_MESSAGE_ID = 20;
    public static final byte SYNC_DRUNKEN_STATE_MESSAGE_ID = 21;
    public static final byte SYNC_ORIGINAL_KEYS_MESSAGE_ID = 22;
    public static final byte DRUNKEN_LEVEL_MESSAGE_ID = 23;
    public static final byte RESET_KEYBINDINGS_MESSAGE_ID = 24;
    public static final byte MANIPULATE_KEYBINDINGS_MESSAGE_ID = 25;
    public static final byte SET_BOOLEAN_MESSAGE_ID = 26;
    public static final byte TEMPERATURE_CHANGE_MESSAGE_ID = 27;
}
