package com.OneBox.ShoppingCart.integration.clients;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class ProductClient {

    private final String PATH = "/products";

    public Response createProduct() {
        Map<String, Object> map = new HashMap<>();
        map.put("description", "TQ123");
        map.put("amount", "25.50");

        return given()
                .accept(JSON)
                .contentType(JSON)
                .queryParams(map)
                .post(PATH);
    }

    public Response getProduct(long id) {
        return given().get(PATH + "/" + id);
    }

    public Response deleteProduct(long id) {
        return given().delete(PATH + "/" + id);
    }
}