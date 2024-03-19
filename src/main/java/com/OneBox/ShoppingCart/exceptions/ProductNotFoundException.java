package com.OneBox.ShoppingCart.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("No Products could be found");
    }


    public ProductNotFoundException(Long productId) {
        super("No Product could be found with Product ID: " + productId);
    }
}
