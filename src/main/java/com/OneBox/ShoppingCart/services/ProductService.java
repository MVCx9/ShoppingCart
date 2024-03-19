package com.OneBox.ShoppingCart.services;

import com.OneBox.ShoppingCart.entities.Product;
import com.OneBox.ShoppingCart.exceptions.ProductAlreadyExistsException;
import com.OneBox.ShoppingCart.exceptions.ProductNotFoundException;
import com.OneBox.ShoppingCart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    /**
     * Returns all Products
     */
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.getAllProducts();

        if(products.isEmpty()){
            throw new ProductNotFoundException();
        }

        return products;
    }

    /**
     * Find Product by ID
     */
    public Product findProductById(Long productId) {
        Product product = productRepository.findProductById(productId);

        if(product == null) {
            throw new ProductNotFoundException(productId);
        }

        return product;
    }

    /**
     * Save Product
     */
    public Product createProduct(String description, Double amount) {
        Product product = new Product();
        product.setDescription(description);
        product.setAmount(amount);

        // FIXME: functional? repository?
        if(productRepository.findProductById(product.getId()) != null) {
            throw new ProductAlreadyExistsException(product.getId());
        }

        productRepository.createProduct(product);

        return findProductById(product.getId());
    }

    /**
     * Update Product
     */
    public void updateProduct(Long productId, String description, Double amount) {
        Product product = findProductById(productId);

        if(!description.isEmpty()){
            product.setDescription(description);
        }

        if(amount != null){
            product.setAmount(amount);
        }

        productRepository.updateProduct(product);
    }

    /**
     * Delete Product by ID
     */
    public void deleteProductById(Long productId) {
        Product product = findProductById(productId);

        productRepository.deleteProductById(product.getId());
    }
}
