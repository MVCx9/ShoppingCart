package com.OneBox.ShoppingCart.integration.clients;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class CartClient {

    private final String PATH = "/carts";

    public Response createCart() {
        return given().post(PATH);
    }

    public Response getCart(long id) {
        return given().get(PATH + "/" + id);
    }

    public Response deleteCart(long id) {
        return given().delete(PATH + "/" + id);
    }

    public Response addProduct(long cartId, long productId){
        Map<String, Object> map = new HashMap<>();
        map.put("productQuantity", "2");

        return given()
                .accept(JSON)
                .contentType(JSON)
                .queryParams(map)
                .post(PATH + "/" + cartId + "/products/" + productId);
    }

}
