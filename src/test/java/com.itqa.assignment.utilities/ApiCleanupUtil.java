package com.itqa.assignment.utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static com.itqa.assignment.utilities.ApiHelper.getJwtToken;

public class ApiCleanupUtil {
    private static final String PREFIX = "API_";

    public void cleanUpCategory() {
        // 2. Get the list of plants
        Response listRes = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + getJwtToken(ApiHelper.UserRole.ADMIN))
                .get("/categories/page?page=0&size=100&sortField=id&sortDir=asc");

        // 3. Filter and Delete
        List<Map<String, Object>> categories = listRes.jsonPath().getList("content");

        if (categories != null) {
            categories.forEach(this::deleteCategory);
        }
    }

    private void deleteCategory(Map<String, Object> category) {
        String id = category.get("id").toString();
        RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + getJwtToken(ApiHelper.UserRole.ADMIN))
                .delete("/categories/" + id);
    }

    public void cleanUpPlants() {
        // 2. Get the list of plants
        Response listRes = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + getJwtToken(ApiHelper.UserRole.ADMIN))
                .get("plants/paged?page=0&size=1000&sort=desc");

        // 3. Filter and Delete
        List<Map<String, Object>> plants = listRes.jsonPath().getList("content");

        if (plants != null) {
            plants.forEach(this::deletePlant);
        }
    }

    private void deletePlant(Map<String, Object> plant) {
        String id = plant.get("id").toString();
        RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + getJwtToken(ApiHelper.UserRole.ADMIN))
                .delete("/plants/" + id);
    }

    public void cleanUpSales() {
        // 2. Get the list of plants
        Response listRes = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + getJwtToken(ApiHelper.UserRole.ADMIN))
                .get("sales/page?page=0&size=2000&sort=desc");

        // 3. Filter and Delete
        List<Map<String, Object>> sales = listRes.jsonPath().getList("content");

        if (sales != null) {
            sales.forEach(this::deleteSales);
        }
    }

    private void deleteSales(Map<String, Object> sale) {
        String id = sale.get("id").toString();
        RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + getJwtToken(ApiHelper.UserRole.ADMIN))
                .delete("/sales/" + id);
    }

    public void cleanUpInventory() {
        DatabaseUtil.executeUpdate("delete from inventory");
    }
}
