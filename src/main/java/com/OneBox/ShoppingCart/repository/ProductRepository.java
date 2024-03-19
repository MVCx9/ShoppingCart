package com.OneBox.ShoppingCart.repository;

import com.OneBox.ShoppingCart.entities.Product;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository {

    private final Map<Long, Product> productsMap = new HashMap<>();

    public List<Product> getAllProducts() {
        return productsMap.values().stream().toList();
    }

    public Product findProductById(long id) {
        return productsMap.get(id);
    }

    public void createProduct(Product product) {
        productsMap.put(product.getId(), product);
    }

    public void deleteProductById(long productId) {
        productsMap.remove(productId);
    }

    public void updateProduct(Product product) {
        productsMap.replace(product.getId(), product);
    }
}


