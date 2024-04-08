package com.OneBox.ShoppingCart.utils;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator{

    private static final AtomicLong cartIdGenerated = new AtomicLong();
    private static final AtomicLong productIdGenerated = new AtomicLong();

    public static long nextCartId() {
        return cartIdGenerated.incrementAndGet();
    }

    public static long nextProductId() {
        return productIdGenerated.incrementAndGet();
    }
}
