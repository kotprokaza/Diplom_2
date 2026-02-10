package com.stellarburgers.api;

import com.google.gson.Gson;
import com.stellarburgers.models.*;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiClient {
    private static final Gson gson = new Gson();

    @Step("Регистрация пользователя")
    public static Response registerUser(UserRequest user) {
        return given()
                .header("Content-type", "application/json")
                .body(gson.toJson(user))
                .post("/api/auth/register");
    }

    @Step("Логин пользователя")
    public static Response loginUser(LoginRequest loginData) {
        return given()
                .header("Content-type", "application/json")
                .body(gson.toJson(loginData))
                .post("/api/auth/login");
    }

    @Step("Создание заказа")
    public static Response createOrder(OrderRequest order, String token) {
        RequestSpecification request = given()
                .header("Content-type", "application/json")
                .body(gson.toJson(order));

        if (token != null && !token.isEmpty()) {
            request.header("Authorization", token);
        }

        return request.post("/api/orders");
    }

    @Step("Получение заказов пользователя")
    public static Response getUserOrders(String token) {
        if (token != null && !token.isEmpty()) {
            return given()
                    .header("Authorization", token)
                    .get("/api/orders");
        } else {
            return given()
                    .get("/api/orders");
        }
    }

    @Step("Удаление пользователя")
    public static Response deleteUser(String token) {
        return given()
                .header("Authorization", token)
                .delete("/api/auth/user");
    }

    @Step("Получение списка ингредиентов")
    public static Response getIngredients() {
        return given()
                .get("/api/ingredients");
    }
}