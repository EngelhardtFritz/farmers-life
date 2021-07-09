package com.asheriit.asheriitsfarmerslife.api.utils;

public class FormattingHelper {

    public static String formatNumber(int number) {
        return String.format("%,d", number);
    }

    public static String formatInt(int number) {
        return String.format("%+d", number);
    }
}
