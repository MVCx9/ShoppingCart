package com.OneBox.ShoppingCart.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductNotFoundInCartException extends RuntimeException {

    public ProductNotFoundInCartException(long cartId, long productId) {
        super("No Products could be found with ID: " + productId + " from Cart with ID: " + cartId);
    }

}
