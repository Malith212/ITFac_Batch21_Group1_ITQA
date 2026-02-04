package com.itqa.assignment.stepdefinitions.api;

import com.itqa.assignment.context.ScenarioContext;
import com.itqa.assignment.stepdefinitions.PlantsHooks;
import com.itqa.assignment.utilities.ApiHelper;
import com.itqa.assignment.utilities.ConfigReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class PlantsSteps {

    private static String subCategoryId;
    private static String plantId;
    private static String plantCategoryId;

    @And("a valid sub-category exists for plant creation")
    public void a_valid_sub_category_exists_from_hook() {
        List<Integer> categoryIds = PlantsHooks.getCategoryIds();

        if (categoryIds == null || categoryIds.size() < 2) {
            throw new RuntimeException("No sub-category found. Ensure @SeedPlantSearchTest hook ran.");
        }

        subCategoryId = String.valueOf(categoryIds.get(1));
    }

    @When("the admin sends a POST request to {string} with plant details:")
    public void the_admin_sends_a_post_request_to_with_plant_details(String endpoint, DataTable dataTable) {
        String actualEndpoint = endpoint.replace("{categoryId}", subCategoryId);
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);

        Map<String, Object> plantBody = Map.of(
                "name", dataMap.get("name"),
                "price", Integer.parseInt(dataMap.get("price")),
                "quantity", Integer.parseInt(dataMap.get("quantity")),
                "categoryId", Integer.parseInt(subCategoryId)
        );
        System.out.println("Request Body: " + plantBody);

        Response apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .body(plantBody)
                .when()
                .post(actualEndpoint);

        System.out.println("Response Status: " + apiResponse.getStatusCode());
        System.out.println("Response Body: " + apiResponse.getBody().asString());

        ScenarioContext.setApiResponse(apiResponse);
    }

    @And("the response body should contain a valid {string}")
    public void the_response_body_should_contain_a_valid(String key) {
        Response response = ScenarioContext.getApiResponse();
        Object value = response.jsonPath().get(key);
        Assert.assertNotNull("Expected '" + key + "' to be present in response", value);
        if (key.equals("id")) {
            int id = response.jsonPath().getInt(key);
            Assert.assertTrue("Expected '" + key + "' to be a valid positive integer", id > 0);
        }
    }

    @And("the response body should contain {string} with value {string}")
    public void the_response_body_should_contain_with_value(String key, String expectedValue) {
        Response response = ScenarioContext.getApiResponse();
        String actualValue = response.jsonPath().getString(key);
        Assert.assertEquals("Value mismatch for key: " + key, expectedValue, actualValue);
    }

    @And("a plant exists for update")
    public void a_plant_exists_for_update() {
        List<Integer> plantIds = PlantsHooks.getPlantIds();
        List<Integer> categoryIds = PlantsHooks.getCategoryIds();

        if (plantIds == null || plantIds.isEmpty()) {
            throw new RuntimeException("No plants found for update test. Ensure @SeedPlantSearchTest hook ran.");
        }

        plantId = String.valueOf(plantIds.get(0));
        plantCategoryId = String.valueOf(categoryIds.get(1));
    }

    @When("the admin sends a PUT request to {string} with updated details:")
    public void the_admin_sends_a_put_request_to_with_updated_details(String endpoint, DataTable dataTable) {
        String actualEndpoint = endpoint.replace("{plantId}", plantId);
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);

        Map<String, Object> plantBody = Map.of(
                "id", Integer.parseInt(plantId),
                "name", dataMap.get("name"),
                "price", Integer.parseInt(dataMap.get("price")),
                "quantity", Integer.parseInt(dataMap.get("quantity")),
                "categoryId", Integer.parseInt(plantCategoryId)
        );
        System.out.println("Request Body: " + plantBody);

        Response apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .body(plantBody)
                .when()
                .put(actualEndpoint);

        System.out.println("Response Status: " + apiResponse.getStatusCode());
        System.out.println("Response Body: " + apiResponse.getBody().asString());

        ScenarioContext.setApiResponse(apiResponse);
    }

    @And("a plant exists for deletion")
    public void a_plant_exists_for_deletion() {
        List<Integer> plantIds = PlantsHooks.getPlantIds();

        if (plantIds == null || plantIds.isEmpty()) {
            throw new RuntimeException("No plants found for deletion test. Ensure @SeedPlantSearchTest hook ran.");
        }

        plantId = String.valueOf(plantIds.get(0));
    }

    @When("the admin sends a DELETE request to {string}")
    public void the_admin_sends_a_delete_request_to(String endpoint) {
        String actualEndpoint = endpoint.replace("{plantId}", plantId);

        Response apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .when()
                .delete(actualEndpoint);

        System.out.println("Response Status: " + apiResponse.getStatusCode());
        if (apiResponse.getStatusCode() == 204) {
            System.out.println("Plant deleted successfully");
        } else {
            System.out.println("Response Body: " + apiResponse.getBody().asString());
        }

        ScenarioContext.setApiResponse(apiResponse);
    }

    @And("a plant exists for retrieval")
    public void a_plant_exists_for_retrieval() {
        List<Integer> plantIds = PlantsHooks.getPlantIds();

        if (plantIds == null || plantIds.isEmpty()) {
            throw new RuntimeException("No plants found for retrieval test. Ensure @SeedPlantSearchTest hook ran.");
        }

        plantId = String.valueOf(plantIds.get(0));
    }

    @When("the admin sends a GET request to {string}")
    public void the_admin_sends_a_get_request_to(String endpoint) {
        String actualEndpoint = endpoint.replace("{plantId}", plantId);

        Response apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .when()
                .get(actualEndpoint);

        System.out.println("Response Status: " + apiResponse.getStatusCode());
        System.out.println("Response Body: " + apiResponse.getBody().asString());

        ScenarioContext.setApiResponse(apiResponse);
    }

    @And("the response body should contain the created plant name")
    public void the_response_body_should_contain_the_created_plant_name() {
        Response response = ScenarioContext.getApiResponse();
        String actualName = response.jsonPath().getString("name");
        String expectedName = PlantsHooks.getCreatedPlantName();

        Assert.assertNotNull("Expected plant name to be present in response", actualName);
        Assert.assertEquals("Plant name mismatch", expectedName, actualName);
    }

    @And("the response body should contain validation error for price")
    public void the_response_body_should_contain_validation_error_for_price() {
        Response response = ScenarioContext.getApiResponse();
        String responseBody = response.getBody().asString();

        Assert.assertTrue("Expected validation error for price in response",
                responseBody.toLowerCase().contains("price") ||
                responseBody.contains("must be greater than") ||
                responseBody.contains("validation"));
    }

    @And("the created plant is deleted")
    public void the_created_plant_is_deleted() {
        Response response = ScenarioContext.getApiResponse();
        int createdPlantId = response.jsonPath().getInt("id");

        Response deleteResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN))
                .contentType("application/json")
                .when()
                .delete("/plants/" + createdPlantId);

        System.out.println("Cleanup - Deleted Plant ID: " + createdPlantId + ", Status: " + deleteResponse.getStatusCode());
    }
}
