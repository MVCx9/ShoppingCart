package com.OneBox.ShoppingCart.repository;

import com.OneBox.ShoppingCart.entities.Cart;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CartRepositoryTest {

    @Test
    void testCartRepository() {
        // Crear un mock para Cart
        Cart mockCart = Mockito.mock(Cart.class);
        Mockito.when(mockCart.getId()).thenReturn(1L);
        Mockito.when(mockCart.getTotalAmount()).thenReturn(200.0);
        Mockito.when(mockCart.getLastActivity()).thenReturn(System.currentTimeMillis());

        CartRepository cartRepository = new CartRepository();

        // Test createCart
        cartRepository.createCart(new Cart());
        Cart createdCart = cartRepository.getCartsMap().get(mockCart.getId());
        assertEquals(mockCart.getId(), createdCart.getId(), "Created cart should be the same as the input cart");

        // Test findCartById
        Cart foundCart = cartRepository.findCartById(mockCart.getId());
        assertEquals(mockCart.getId(), foundCart.getId(), "Found cart should be the same as the created cart");

        // Test deleteCartById
        cartRepository.deleteCartById(mockCart.getId());
        Cart deletedCart = cartRepository.findCartById(mockCart.getId());
        assertNull(deletedCart, "Deleted cart should be null");
    }
}

