package com.itqa.assignment.stepdefinitions.api;

import com.itqa.assignment.context.ScenarioContext;
import com.itqa.assignment.stepdefinitions.CategoryHooks;
import com.itqa.assignment.utilities.ApiHelper;
import com.itqa.assignment.utilities.ConfigReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class CategorieSteps {

    private static String categoryId;

    @Then("the response should contain a category list")
    public void the_response_should_contain_a_category_list() {
        Response response = ScenarioContext.getApiResponse();
        List<Object> content = response.jsonPath().getList("content");

        Assert.assertNotNull("Expected 'content' to be present in response", content);
        Assert.assertTrue("Expected category list to have items", content.size() > 0);
    }

    @When("a valid category ID exists")
    public void a_valid_category_id_exists() {
        List<Integer> categoryIds = CategoryHooks.getCategorySearchTestCategoryIds();

        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new RuntimeException("No categories found. Ensure @SeedCategorySearchTest hook ran.");
        }

        categoryId = String.valueOf(categoryIds.get(0));
    }

    @When("the user sends a GET request to get categories from {string}")
    public void the_user_sends_a_get_request_to_get_categories_from(String endpoint) {
        String actualEndpoint = endpoint;
        if (categoryId != null) {
            actualEndpoint = actualEndpoint.replace("{categoryId}", categoryId);
        }

        Response apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.USER))
                .contentType("application/json")
                .when()
                .get(actualEndpoint);

        System.out.println("Response Status: " + apiResponse.getStatusCode());
        System.out.println("Response Body: " + apiResponse.getBody().asString());

        ScenarioContext.setApiResponse(apiResponse);
    }

    @Then("the response should contain the category details")
    public void the_response_should_contain_the_category_details() {
        Response response = ScenarioContext.getApiResponse();

        int id = response.jsonPath().getInt("id");
        String name = response.jsonPath().getString("name");

        Assert.assertTrue("Expected category ID to be valid", id > 0);
        Assert.assertNotNull("Expected 'name' to be present in response", name);
    }

    @When("the user sends a POST request to {string} with category details:")
    public void the_user_sends_a_post_request_to_with_category_details(String endpoint, DataTable dataTable) {
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);

        Map<String, Object> categoryBody = Map.of(
                "name", dataMap.get("name")
        );
        System.out.println("Request Body: " + categoryBody);

        Response apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.USER))
                .contentType("application/json")
                .body(categoryBody)
                .when()
                .post(endpoint);

        System.out.println("Response Status: " + apiResponse.getStatusCode());
        System.out.println("Response Body: " + apiResponse.getBody().asString());

        ScenarioContext.setApiResponse(apiResponse);
    }

    @When("the user sends a PUT request to {string} with category details:")
    public void the_user_sends_a_put_request_to_with_category_details(String endpoint, DataTable dataTable) {
        String actualEndpoint = endpoint.replace("{categoryId}", categoryId);
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);

        Map<String, Object> categoryBody = Map.of(
                "id", Integer.parseInt(categoryId),
                "name", dataMap.get("name")
        );
        System.out.println("Request Body: " + categoryBody);

        Response apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.USER))
                .contentType("application/json")
                .body(categoryBody)
                .when()
                .put(actualEndpoint);

        System.out.println("Response Status: " + apiResponse.getStatusCode());
        System.out.println("Response Body: " + apiResponse.getBody().asString());

        ScenarioContext.setApiResponse(apiResponse);
    }

    @When("the user sends a DELETE request to delete a category from {string}")
    public void the_user_sends_a_delete_request_to_delete_a_category_from(String endpoint) {
        String actualEndpoint = endpoint.replace("{categoryId}", categoryId);

        Response apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.USER))
                .contentType("application/json")
                .when()
                .delete(actualEndpoint);

        System.out.println("Response Status: " + apiResponse.getStatusCode());
        System.out.println("Response Body: " + apiResponse.getBody().asString());

        ScenarioContext.setApiResponse(apiResponse);
    }

    @When("the admin sends a POST request to {string} with a valid name and no parent")
    public void the_admin_sends_a_post_request_to_with_a_valid_name_and_no_parent(String endpoint) {
        String suffix = String.format("%03d", System.currentTimeMillis() % 1000);
        String categoryName = "API_Ct" + suffix;

        Map<String, Object> categoryBody = Map.of(
                "name", categoryName
        );
        System.out.println("Request Body: " + categoryBody);

        Response apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .body(categoryBody)
                .when()
                .post(endpoint);

        System.out.println("Response Status: " + apiResponse.getStatusCode());
        System.out.println("Response Body: " + apiResponse.getBody().asString());

        ScenarioContext.setApiResponse(apiResponse);
    }

    @Then("the response should contain the created category")
    public void the_response_should_contain_the_created_category() {
        Response response = ScenarioContext.getApiResponse();

        int id = response.jsonPath().getInt("id");
        String name = response.jsonPath().getString("name");

        Assert.assertTrue("Expected category ID to be valid", id > 0);
        Assert.assertNotNull("Expected 'name' to be present in response", name);
    }

    @When("the admin sends a PUT request to {string} with category details:")
    public void the_admin_sends_a_put_request_to_with_category_details(String endpoint, DataTable dataTable) {
        String actualEndpoint = endpoint.replace("{categoryId}", categoryId);
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);

        Map<String, Object> categoryBody = Map.of(
                "id", Integer.parseInt(categoryId),
                "name", dataMap.get("name")
        );
        System.out.println("Request Body: " + categoryBody);

        Response apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .body(categoryBody)
                .when()
                .put(actualEndpoint);

        System.out.println("Response Status: " + apiResponse.getStatusCode());
        System.out.println("Response Body: " + apiResponse.getBody().asString());

        ScenarioContext.setApiResponse(apiResponse);
    }

    @When("the admin sends a DELETE request to delete a category from {string}")
    public void the_admin_sends_a_delete_request_to_delete_a_category_from(String endpoint) {
        String actualEndpoint = endpoint.replace("{categoryId}", categoryId);

        Response apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .when()
                .delete(actualEndpoint);

        System.out.println("Response Status: " + apiResponse.getStatusCode());
        System.out.println("Response Body: " + apiResponse.getBody().asString());

        ScenarioContext.setApiResponse(apiResponse);
    }

    @When("a valid sub category ID exists")
    public void a_valid_sub_category_id_exists() {
        List<Integer> categoryIds = CategoryHooks.getCategorySearchTestCategoryIds();

        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new RuntimeException("No categories found. Ensure @SeedCategorySearchTest hook ran.");
        }

        categoryId = String.valueOf(categoryIds.get(1));
    }

    @When("a valid parent category ID exists")
    public void a_valid_parent_category_id_exists() {
        List<Integer> categoryIds = CategoryHooks.getCategorySearchTestCategoryIds();

        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new RuntimeException("No categories found. Ensure @SeedCategorySearchTest hook ran.");
        }

        categoryId = String.valueOf(categoryIds.get(0)); // Parent category is index 0
    }

    @When("the admin sends a POST request to {string} with a valid name and parent ID")
    public void the_admin_sends_a_post_request_to_with_a_valid_name_and_parent_id(String endpoint) {
        String suffix = String.format("%03d", System.currentTimeMillis() % 1000);
        String subCategoryName = "API_Sb" + suffix;

        Map<String, Object> categoryBody = Map.of(
                "name", subCategoryName,
                "parent", Map.of(
                        "id", Integer.parseInt(categoryId),
                        "name", CategoryHooks.getCreatedMainCategoryName()
                )
        );
        System.out.println("Request Body: " + categoryBody);

        Response apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .body(categoryBody)
                .when()
                .post(endpoint);

        System.out.println("Response Status: " + apiResponse.getStatusCode());
        System.out.println("Response Body: " + apiResponse.getBody().asString());

        ScenarioContext.setApiResponse(apiResponse);
    }

    @Then("the response should contain the created sub-category linked to the parent")
    public void the_response_should_contain_the_created_sub_category_linked_to_the_parent() {
        Response response = ScenarioContext.getApiResponse();

        int id = response.jsonPath().getInt("id");
        String name = response.jsonPath().getString("name");

        Assert.assertTrue("Expected category ID to be valid", id > 0);
        Assert.assertNotNull("Expected 'name' to be present in response", name);
    }

    @When("the admin sends a GET request to get paginated categories from {string}")
    public void the_admin_sends_a_get_request_to_get_paginated_categories_from(String endpoint) {
        Response apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .when()
                .get(endpoint);

        System.out.println("Response Status: " + apiResponse.getStatusCode());
        System.out.println("Response Body: " + apiResponse.getBody().asString());

        ScenarioContext.setApiResponse(apiResponse);
    }

    @Then("the response should contain a paginated and sorted category list")
    public void the_response_should_contain_a_paginated_and_sorted_category_list() {
        Response response = ScenarioContext.getApiResponse();

        // Check for pagination fields
        List<Object> content = response.jsonPath().getList("content");
        Integer totalElements = response.jsonPath().getInt("totalElements");
        Integer size = response.jsonPath().getInt("size");
        Integer number = response.jsonPath().getInt("number");

        Assert.assertNotNull("Expected 'content' to be present in response", content);
        Assert.assertNotNull("Expected 'totalElements' to be present in response", totalElements);
        Assert.assertNotNull("Expected 'size' to be present in response", size);
        Assert.assertNotNull("Expected 'number' to be present in response", number);

        // Check that content has items
        Assert.assertTrue("Expected content to have items", !content.isEmpty());

        // Check sorting by id ascending (assuming categories have ids)
        if (content.size() > 1) {
            int firstId = response.jsonPath().getInt("content[0].id");
            int secondId = response.jsonPath().getInt("content[1].id");
            Assert.assertTrue("Expected categories to be sorted by id ascending", firstId <= secondId);
        }
    }
}