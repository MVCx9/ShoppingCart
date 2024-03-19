package com.OneBox.ShoppingCart.repository;

import com.OneBox.ShoppingCart.entities.Cart;
import com.OneBox.ShoppingCart.entities.Product;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CartRepository {

    private final Map<Long, Cart> cartsMap = new HashMap<>();

    public Map<Long, Cart> getCartsMap() {
        return cartsMap;
    }

    public void registerCartActivity(long cartId, double totalAmount, long currentTime){
        cartsMap.get(cartId).setTotalAmount(totalAmount);
        cartsMap.get(cartId).setLastActivity(currentTime);
    }

    public List<Cart> getAllCarts() {
        return cartsMap.values().stream().toList();
    }

    public Cart findCartById(long cartId) {
        return cartsMap.get(cartId);
    }

    public void createCart(Cart cart) {
        cartsMap.put(cart.getId(), cart);
    }

    public void deleteCartById(long cartId) {
        cartsMap.remove(cartId);
    }

    public void addProductToCart(long id, Product product, int quantity) {
        cartsMap.get(id).getProducts().put(product, quantity);
    }

    public void updateProductInCart(long cartId, Product product, int quantity) {
        cartsMap.get(cartId).getProducts().put(product, quantity);
    }

    public void removeProductFromCart(long cartId, Product product) {
        cartsMap.get(cartId).getProducts().remove(product);
    }

    public void removeAllProductsFromCart(long cartId) {
        cartsMap.get(cartId).getProducts().clear();
    }
}
