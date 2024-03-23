package com.OneBox.ShoppingCart.entities;

import com.OneBox.ShoppingCart.utils.IdGenerator;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

// Without DB engine it's not necessary to Serialize this class
@Data
public class Cart {

    private long id;
    private Map<Long, Integer> products;
    private double totalAmount;
    private long lastActivity;

    public Cart() {
        this.id = IdGenerator.nextCartId();
        this.products = new HashMap<>();
        this.lastActivity = System.currentTimeMillis();
        this.totalAmount = 0;
    }
}
