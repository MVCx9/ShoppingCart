package com.OneBox.ShoppingCart.services;

import com.OneBox.ShoppingCart.entities.Cart;
import com.OneBox.ShoppingCart.entities.Product;
import com.OneBox.ShoppingCart.exceptions.CartAlreadyExistsException;
import com.OneBox.ShoppingCart.exceptions.CartNotFoundException;
import com.OneBox.ShoppingCart.exceptions.ProductAlreadyAddedToCartException;
import com.OneBox.ShoppingCart.exceptions.ProductNotFoundInCartException;
import com.OneBox.ShoppingCart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class CartService {

    // Dependency injections
    private final ProductService productService;
    private final CartRepository cartRepository;

    /**
     * Returns all Carts
     * This action is not an activity
     */
    public List<Cart> getAllCarts() {
        List<Cart> carts = cartRepository.getAllCarts();

        if(carts.isEmpty()){
            throw new CartNotFoundException();
        }

        return carts;
    }

    /**
     * Find Cart by ID
     * This action is not an activity
     */
    public Cart findCartById(Long cartId) {
        Cart cart = cartRepository.findCartById(cartId);

        if(cart == null) {
            throw new CartNotFoundException(cartId);
        }

        return cart;
    }

    /**
     * Create Cart
     */
    public Cart createCart() {
        Cart cart = new Cart();

        // FIXME: functional? repository?
        if(cartRepository.findCartById(cart.getId()) != null){
            throw new CartAlreadyExistsException(cart.getId());
        }

        cartRepository.createCart(cart);

        return findCartById(cart.getId());
    }

    /**
     * Delete Cart by ID
     */
    public void deleteCartById(Long cartId) {
        Cart cart = findCartById(cartId);

        cartRepository.deleteCartById(cart.getId());
    }

    /**
     * Add a quantity of the same Product to Cart
     * This action is an activity
     */
    public Cart addProductToCart(Long cartId, Long productId, Integer productQuantity) {
        Cart cart = findCartById(cartId);
        Product product = productService.findProductById(productId);

        if(isProductInCart(cart.getId(), product.getId())){
            throw new ProductAlreadyAddedToCartException(cart.getId(), product.getId());
        }

        cartRepository.addProductToCart(cart.getId(), product, productQuantity);
        registerCartActivity(cart.getId());

        return findCartById(cart.getId());
    }

    /**
     * Get a list of Products from Cart specified
     * This action is an activity
     */
    public Map<Product, Integer> getProductsFromCart(Long cartId) {
        return findCartById(cartId).getProducts();
    }

    /**
     * Update Product quantity from Cart.
     * This action is an activity
     */
    public void updateProductQuantityFromCart(Long cartId, Long productId, Integer productQuantity) {
        Cart cart = findCartById(cartId);
        Product product = productService.findProductById(productId);

        if(isProductInCart(cart.getId(), product.getId())){
            throw new ProductNotFoundInCartException(cart.getId(), product.getId());
        }

        cartRepository.updateProductInCart(cart.getId(), product, productQuantity);
        registerCartActivity(cartId);
    }

    /**
     * Remove single Product from product
     * This action is an activity
     */
    public void removeProductFromCart(Long cartId, Long productId) {
        Cart cart = findCartById(cartId);
        Product product = productService.findProductById(productId);

        if(!isProductInCart(cart.getId(), product.getId())){
            throw new ProductNotFoundInCartException(cart.getId(), product.getId());
        }

        cartRepository.removeProductFromCart(cart.getId(), product );
        registerCartActivity(cartId);
    }

    /**
     * Remove all Product from Cart
     * This action is an activity
     */
    public void removeAllProductsFromCart(Long cartId) {
        Cart cart = findCartById(cartId);

        cartRepository.removeAllProductsFromCart(cart.getId());
        registerCartActivity(cartId);
    }

    /**
     * Find if a Product is inside the Cart by ID
     */
    private boolean isProductInCart(Long cartId, Long productId) {
        Cart cart = findCartById(cartId);
        Product product = productService.findProductById(productId);

        return cart.getProducts().get(product) != null;
    }

    /**
     * Register Cart activity saving Date info + updating totalAmount
     */
    private void registerCartActivity(Long cartId){
        Map<Long, Cart> carts = cartRepository.getCartsMap();

        // Calculate the total cost of each product
        double totalAmount = carts.get(cartId).getProducts().entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getAmount() * entry.getValue())
                .sum();
        long currentTime = System.currentTimeMillis();

        cartRepository.registerCartActivity(cartId, totalAmount, currentTime);
    }

    /**
     * Scheduled function to remove inactive carts (10 min)
     */
    @Scheduled(fixedRate = 60000) // executed each min
    private void removeInactiveCarts() {
        long currentTime = System.currentTimeMillis();
        Map<Long, Cart> carts = cartRepository.getCartsMap();

        if(carts != null){
            // Cart filter to select which carts lasActivity >= 10 min
            List<Long> cartIdsToDelete =
                    carts.entrySet().stream()
                            .filter(cart -> currentTime - cart.getValue().getLastActivity() >= TimeUnit.MINUTES.toMillis(10))
                            .map(Map.Entry::getKey)
                            .toList();

            // Do not filter and delete at the same time with Stream to avoid ConcurrentModificationException
            cartIdsToDelete.forEach(cartRepository::deleteCartById);
        }
    }

}
