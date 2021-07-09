package com.asheriit.asheriitsfarmerslife.api.utils;

import java.util.Random;

public class ModMathHelper {
    public static final Random RANDOM = new Random();

    // Durstenfeld shuffle
    public static void shuffleArray(String[] ar) {
        for (int i = ar.length - 1; i > 0; i--) {
            int index = RANDOM.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
