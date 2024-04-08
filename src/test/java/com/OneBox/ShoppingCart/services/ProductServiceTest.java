package com.OneBox.ShoppingCart.services;

import com.OneBox.ShoppingCart.entities.Product;
import com.OneBox.ShoppingCart.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductServiceTest {

    @Test
    void testProductService() {
        // Crear un mock para ProductRepository
        ProductRepository mockProductRepository = Mockito.mock(ProductRepository.class);

        // Crear una instancia de ProductService con el mock de ProductRepository
        ProductService productService = new ProductService(mockProductRepository);

        // Crear un mock para Product
        Product mockProduct = Mockito.mock(Product.class);
        Mockito.when(mockProduct.getId()).thenReturn(1L);
        Mockito.when(mockProduct.getDescription()).thenReturn("Test Product");
        Mockito.when(mockProduct.getAmount()).thenReturn(100.0);

        // Configurar el mock de ProductRepository para devolver el mock de Product
        Mockito.when(mockProductRepository.findProductById(1L)).thenReturn(mockProduct);
        Mockito.when(mockProductRepository.getAllProducts()).thenReturn(Collections.singletonList(mockProduct));

        // Test createProduct from Mocked class
        Mockito.doNothing().when(mockProductRepository).createProduct(mockProduct);

        // Test findProductById we should find the Mocked Product
        Product foundProduct = productService.findProductById(1L);
        assertEquals(mockProduct, foundProduct, "Found product should be the same as the mock product");

        // Test getAllProducts
        List<Product> allProducts = productService.getAllProducts();
        assertEquals(1, allProducts.size(), "There should be one product in the list");
        assertEquals(mockProduct, allProducts.get(0), "The product in the list should be the same as the mock product");

        // Test deleteProductById
        Mockito.doNothing().when(mockProductRepository).deleteProductById(1L);
        productService.deleteProductById(1L);
    }
}
