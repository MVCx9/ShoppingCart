package com.OneBox.ShoppingCart.entities;

import com.OneBox.ShoppingCart.utils.IdGenerator;
import lombok.Data;

//  Without DB engine it's not necessary to Serialize this class
@Data
public class Product {

    private long id;
    private String description;
    double amount;

    public Product() {
        this.id = IdGenerator.nextProductId();
    }
}
