package com.OneBox.ShoppingCart.controllers;

import com.OneBox.ShoppingCart.entities.Cart;
import com.OneBox.ShoppingCart.entities.Product;
import com.OneBox.ShoppingCart.services.CartService;
import com.OneBox.ShoppingCart.utils.Constants;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    //FIXME: which is the correct way to return results?

    @PostMapping()
    @ResponseStatus(value = CREATED)
    public Cart createCart() {
        return cartService.createCart();
    }

    @GetMapping("/{cartId}")
    @ResponseStatus(value = OK)
    public Cart findCartById(@PathVariable @NotNull Long cartId) {
        return cartService.findCartById(cartId);
    }

    @GetMapping("/get-all")
    @ResponseStatus(value = OK)
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Map<String, String>> deleteCartById(@PathVariable @NotNull Long cartId) {
        cartService.deleteCartById(cartId);
        Map<String, String> response = new HashMap<>();
        response.put("message", Constants.REMOVE_MESSAGE);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{cartId}/products/{productId}")
    @ResponseStatus(value = OK)
    public Cart addProductToCart(@PathVariable @NotNull Long cartId, @PathVariable @NotNull Long productId, @RequestParam @NotNull Integer productQuantity) {
        if(productQuantity <= 0) {
            throw new IllegalArgumentException("Illegal value for productQuantity: " + productQuantity);
        }
        return cartService.addProductToCart(cartId, productId, productQuantity);
    }

    @GetMapping("/products/{cartId}")
    @ResponseStatus(value = OK)
    public Map<Product, Integer> getProductsFromCart(@PathVariable @NotNull Long cartId) {
        return cartService.getProductsFromCart(cartId);
    }

    @PutMapping("{cartId}/products/{productId}")
    @ResponseStatus(value = OK)
    public ResponseEntity<Map<String, String>> updateProductQuantityFromCart(@PathVariable @NotNull Long cartId, @PathVariable @NotNull Long productId, @RequestParam @NotNull Integer productQuantity) {
        if(productQuantity <= 0) {
            throw new IllegalArgumentException("Illegal value for productQuantity: " + productQuantity);
        }

        cartService.updateProductQuantityFromCart(cartId, productId, productQuantity);

        Map<String, String> response = new HashMap<>();
        response.put("message", Constants.UPDATE_MESSAGE);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    @ResponseStatus(value = OK)
    public ResponseEntity<Map<String, String>> removeProductFromCart(@PathVariable @NotNull Long cartId, @PathVariable @NotNull Long productId) {
        cartService.removeProductFromCart(cartId, productId);

        Map<String, String> response = new HashMap<>();
        response.put("message", Constants.REMOVE_MESSAGE);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove-all-products/{cartId}")
    @ResponseStatus(value = OK)
    public ResponseEntity<Map<String, String>> removeAllProductsFromCart(@PathVariable @NotNull Long cartId) {
        cartService.removeAllProductsFromCart(cartId);

        Map<String, String> response = new HashMap<>();
        response.put("message", Constants.REMOVE_MESSAGE);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler({RuntimeException.class})
    public ErrorResponse handleException(RuntimeException error) {
        return ErrorResponse.create(error, HttpStatus.NOT_FOUND, error.getMessage());
    }
}
