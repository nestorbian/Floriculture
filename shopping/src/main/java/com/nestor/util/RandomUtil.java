package com.nestor.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    public static String generateOrderNumber() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 18; i++) {
            builder.append(ThreadLocalRandom.current().nextLong(10));
        }
        return builder.toString();
    }

}
