package com.OneBox.ShoppingCart.controllers;

import com.OneBox.ShoppingCart.entities.Product;
import com.OneBox.ShoppingCart.services.ProductService;
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
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //FIXME: which is the correct way to return results?

    @GetMapping("/get-all")
    @ResponseStatus(value = OK)
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    @ResponseStatus(value = OK)
    public Product findProductById(@PathVariable @NotNull Long productId) {
        return productService.findProductById(productId);
    }

    @PostMapping()
    @ResponseStatus(value = CREATED)
    public Product createProduct(@RequestParam @NotNull String description, @RequestParam @NotNull Double amount) {
        return productService.createProduct(description, amount);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Map<String, String>> updateProduct(@PathVariable @NotNull Long productId, @RequestParam String description, @RequestParam Double amount) {
        productService.updateProduct(productId, description, amount);
        Map<String, String> response = new HashMap<>();
        response.put("message", Constants.UPDATE_MESSAGE);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, String>> deleteProductById(@PathVariable @NotNull Long productId) {
        productService.deleteProductById(productId);
        Map<String, String> response = new HashMap<>();
        response.put("message", Constants.REMOVE_MESSAGE);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler({RuntimeException.class})
    public ErrorResponse handleException(RuntimeException error) {
        return ErrorResponse.create(error, HttpStatus.NOT_FOUND, error.getMessage());
    }
}