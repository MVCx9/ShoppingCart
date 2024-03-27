package com.OneBox.ShoppingCart.integration;

import com.OneBox.ShoppingCart.integration.clients.ProductClient;
import com.OneBox.ShoppingCart.utils.Constants;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class ProductControllerIT extends IntegrationTestConfiguration {

    private final ProductClient productClient = new ProductClient();

    @Test
    void shouldCreateAProduct() {
        // when: I create a product
        var response = productClient.createProduct();

        // then: the response is successful
        response.then().statusCode(201);

        // and: the response contains the product id
        response.then()
                .body("id", is(notNullValue()))
                .body("description", is("TQ123"))
                .body("amount", is(25.50F));
    }

    @Test
    void shouldFindAProduct() {
        // given: a cart is created
        int cartId = productClient.createProduct().jsonPath().getInt("id");

        // when: I request a product
        var response = productClient.getProduct(cartId);

        // then: the response is successful
        response.then().statusCode(200);

        // and: the response contains the product id
        response.then()
                .body("id", is(cartId))
                .body("description", is("TQ123"))
                .body("amount", is(25.50F));
    }

    @Test
    void shouldReturnAnErrorIfCartIsNotFound() {
        // when: I request a cart
        var nonExistingProductId = 18;
        var response = productClient.getProduct(nonExistingProductId);

        // then: the response returns an error
        response.then().statusCode(404);

        // and: the response contains the cart id
        ValidatableResponse validatableResponse = response.then()
                .body("detail", is("No Product could be found with Product ID: " + nonExistingProductId));
    }

    @Test
    void shouldDeleteAProduct() {
        // given: a product is created
        int productId = productClient.createProduct().jsonPath().getInt("id");

        // the product exists
        var findResponseBefore = productClient.getProduct(productId);
        findResponseBefore.then().statusCode(200);
        findResponseBefore.then()
                .body("id", is(productId))
                .body("description", is("TQ123"))
                .body("amount", is(25.50F));

        // when: I request to delete a product
        var response = productClient.deleteProduct(productId);

        // then: the response is successful
        response.then().statusCode(200);

        response.then()
                .body("message", is(Constants.REMOVE_MESSAGE));

        // and: the cart does not exist and returns an error
        var findResponseAfter = productClient.getProduct(productId);
        findResponseAfter.then().statusCode(404);
        ValidatableResponse validatableResponse = findResponseAfter.then()
                .body("detail", is("No Product could be found with Product ID: " + productId));
    }


}