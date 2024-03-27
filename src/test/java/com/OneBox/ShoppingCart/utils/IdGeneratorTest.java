package com.OneBox.ShoppingCart.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdGeneratorTest {

    @Test
    public void testNextCartId() {
        long id1 = IdGenerator.nextCartId();
        long id2 = IdGenerator.nextCartId();
        assertEquals(id1 + 1, id2, "IDs should be sequential");
    }

    @Test
    public void testNextProductId() {
        long id1 = IdGenerator.nextProductId();
        long id2 = IdGenerator.nextProductId();
        assertEquals(id1 + 1, id2, "IDs should be sequential");
    }
}

