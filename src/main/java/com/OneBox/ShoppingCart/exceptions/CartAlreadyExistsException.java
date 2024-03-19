package com.OneBox.ShoppingCart.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CartAlreadyExistsException extends RuntimeException {

    public CartAlreadyExistsException(long cartId) {
        super("Cart with ID: " + cartId + " already exists ");
    }
}
