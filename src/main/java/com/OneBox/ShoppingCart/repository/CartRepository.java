package com.OneBox.ShoppingCart.repository;

import com.OneBox.ShoppingCart.entities.Cart;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
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

    public Cart findCartById(long cartId) {
        return cartsMap.get(cartId);
    }

    public void createCart(Cart cart) {
        cartsMap.put(cart.getId(), cart);
    }

    public void deleteCartById(long cartId) {
        cartsMap.remove(cartId);
    }

    public void addProductToCart(long id, long productId, int quantity) {
        cartsMap.get(id).getProducts().put(productId, quantity);
    }

    public void updateProductInCart(long cartId, long productId, int quantity) {
        cartsMap.get(cartId).getProducts().replace(productId, quantity);
    }

    public void removeProductFromCart(long cartId, long productId) {
        cartsMap.get(cartId).getProducts().remove(productId);
    }

    public void removeAllProductsFromCart(long cartId) {
        cartsMap.get(cartId).getProducts().clear();
    }
}
