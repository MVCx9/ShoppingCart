package com.OneBox.ShoppingCart.integration;


import com.OneBox.ShoppingCart.integration.clients.CartClient;
import com.OneBox.ShoppingCart.integration.clients.ProductClient;
import com.OneBox.ShoppingCart.utils.Constants;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.*;

class CartControllerIT extends IntegrationTestConfiguration {

    private final CartClient cartClient = new CartClient();
    private final ProductClient productClient = new ProductClient();

    @Test
    void shouldCreateACart() {
        // when: I create a cart
        var response = cartClient.createCart();

        // then: the response is successful
        response.then().statusCode(201);

        // and: the response contains the cart id
        response.then()
                .body("id", is(notNullValue()))
                .body("totalAmount", is(0.0f))
                .body("lastActivity", notNullValue())
                .body("products", anEmptyMap());
    }

    @Test
    void shouldGetACart() {
        // given: a cart is created
        int cartId = cartClient.createCart().jsonPath().getInt("id");

        // when: I request a cart
        var response = cartClient.getCart(cartId);

        // then: the response is successful
        response.then().statusCode(200);

        // and: the response contains the cart id
        response.then()
                .body("totalAmount", is(0.0f))
                .body("lastActivity", notNullValue())
                .body("products", anEmptyMap());
    }

    @Test
    void shouldReturnAnErrorIfCartIsNotFound() {
        // when: I request a cart
        var nonExistingCartId = 18;
        var response = cartClient.getCart(nonExistingCartId);

        // then: the response returns an error
        response.then().statusCode(404);

        // and: the response contains the cart id
        response.then().body("detail", is("No Carts could be found with Cart ID: " + nonExistingCartId));
    }

    @Test
    void shouldDeleteACart() {
        // given: a cart is created
        int cartId = cartClient.createCart().jsonPath().getInt("id");

        // the cart exists
        var findResponseBefore = cartClient.getCart(cartId);
        findResponseBefore.then().statusCode(200);
        findResponseBefore.then()
                .body("totalAmount", is(0.0f))
                .body("lastActivity", notNullValue())
                .body("products", anEmptyMap());

        // when: I request to delete a cart
        var response = cartClient.deleteCart(cartId);

        // then: the response is successful
        response.then().statusCode(200);
        response.then().body("message", is(Constants.REMOVE_MESSAGE));

        // and: the cart does not exist and returns an error
        var findResponseAfter = cartClient.getCart(cartId);
        findResponseAfter.then().statusCode(404);
        findResponseAfter.then().body("detail", is("No Carts could be found with Cart ID: " + cartId));
    }

    @Test
    void shouldAddAProductToCart() {
        // given: a cart is created
        int cartId = cartClient.createCart().jsonPath().getInt("id");

        // the cart exists
        var findCartResponseBefore = cartClient.getCart(cartId);
        findCartResponseBefore.then().statusCode(200);
        findCartResponseBefore.then()
                .body("totalAmount", is(0.0f))
                .body("lastActivity", notNullValue())
                .body("products", anEmptyMap());

        // a product is created
        int productId = productClient.createProduct().jsonPath().getInt("id");

        // the product exists
        var findProductResponseBefore = productClient.getProduct(productId);
        findProductResponseBefore.then().statusCode(200);
        findProductResponseBefore.then()
                .body("id", is(productId))
                .body("description", is("TQ123"))
                .body("amount", is(25.50F));

        // when: a product is added to a cart
        var response = cartClient.addProduct(cartId, productId);
        response.then().statusCode(200);

        // and: product is in the cart
        response.then()
                .body("totalAmount", is(greaterThan(0.0f)))
                .body("lastActivity", notNullValue())
                .body("products", aMapWithSize(greaterThanOrEqualTo(1)));
    }


    @Test
    void testRemoveInactiveCarts() {
        // given: a cart is created
        int cartId = cartClient.createCart().jsonPath().getInt("id");

        // the cart exists
        var findCartResponseBefore = cartClient.getCart(cartId);
        findCartResponseBefore.then().statusCode(200);
        findCartResponseBefore.then()
                .body("id", is(cartId))
                .body("totalAmount", is(0.0f))
                .body("lastActivity", notNullValue())
                .body("products", anEmptyMap());

        // when: after 10min cart is deleted
        try {
            Thread.sleep(TimeUnit.MINUTES.toMillis(11));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // and: the cart does not exist and returns an error
        var findResponseAfter = cartClient.getCart(cartId);
        findResponseAfter.then().statusCode(404);
        ValidatableResponse validatableResponse = findResponseAfter.then()
                .body("detail", is("No Carts could be found with Cart ID: " + cartId));
    }

}
