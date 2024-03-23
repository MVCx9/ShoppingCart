package com.OneBox.ShoppingCart.entities;


import lombok.Data;

import java.util.Date;
import java.util.Map;

// Without DB engine it's not necessary to Serialize this class
@Data
public class CartMapping {

    private Map<Product, Integer> products;
    private double totalAmount;
    private Date lastActivity;

    public CartMapping(Cart cart, Map<Product, Integer> products) {
        this.products = products;
        this.lastActivity = new Date(cart.getLastActivity());
        this.totalAmount = cart.getTotalAmount();
    }

}