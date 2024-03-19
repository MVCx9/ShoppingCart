package com.OneBox.ShoppingCart.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProductAlreadyAddedToCartException extends RuntimeException {

    public ProductAlreadyAddedToCartException(long cartId, long productId) {
        super("Product with ID: " + productId + " is already added to Cart with ID: " + cartId);
    }
}
