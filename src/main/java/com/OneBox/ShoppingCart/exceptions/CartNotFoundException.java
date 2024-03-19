package com.OneBox.ShoppingCart.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException() {
        super("No Carts could be found");
    }

    public CartNotFoundException(Long cartId) {
        super("No Carts could be found with Cart ID: " + cartId);
    }
}
