package com.OneBox.ShoppingCart.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException(long productId) {
        super("Product with ID: " + productId + " already exists ");
    }
}
