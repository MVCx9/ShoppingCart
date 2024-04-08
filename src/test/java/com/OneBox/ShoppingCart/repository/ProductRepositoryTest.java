package com.OneBox.ShoppingCart.repository;

import com.OneBox.ShoppingCart.entities.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    @Test
    void testProductRepository() {
        // Crear un mock para Product
        Product mockProduct = Mockito.mock(Product.class);
        Mockito.when(mockProduct.getId()).thenReturn(1L);
        Mockito.when(mockProduct.getDescription()).thenReturn("Test Product");
        Mockito.when(mockProduct.getAmount()).thenReturn(100.0);

        ProductRepository productRepository = new ProductRepository();

        // Test createProduct
        productRepository.createProduct(mockProduct);
        Product createdProduct = productRepository.findProductById(mockProduct.getId());
        assertEquals(mockProduct, createdProduct, "Created product should be the same as the input product");

        // Test getAllProducts
        List<Product> products = productRepository.getAllProducts();
        assertTrue(products.contains(mockProduct), "Products should contain the created product");

        // Test deleteProductById
        productRepository.deleteProductById(mockProduct.getId());
        Product deletedProduct = productRepository.findProductById(mockProduct.getId());
        assertNull(deletedProduct, "Deleted product should be null");
    }
}


