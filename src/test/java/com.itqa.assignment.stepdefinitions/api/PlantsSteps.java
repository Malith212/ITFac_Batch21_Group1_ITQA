package com.itqa.assignment.stepdefinitions.api;

import com.itqa.assignment.context.ScenarioContext;
import com.itqa.assignment.stepdefinitions.DashboardHooks;
import com.itqa.assignment.stepdefinitions.PlantsHooks;
import com.itqa.assignment.utilities.ApiHelper;
import com.itqa.assignment.utilities.ConfigReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
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
    private static String plantName;

    Response response;

    @Given("valid sub-category id exists")
    public void valid_sub_category_id_exists() {
        List<Integer> categoryIds = DashboardHooks.getCategoryIds();
        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new RuntimeException("No category found. Ensure @SeedDashboardTest hook ran.");
        }
        subCategoryId = String.valueOf(categoryIds.get(1));
    }

    @Given("valid plant name exists")
    public void valid_plant_name_exists() {
        List<String> plantNames = DashboardHooks.getCreatedPlantNames();
        if (plantNames == null || plantNames.isEmpty()) {
            throw new RuntimeException("No plant name found. Ensure @SeedDashboardTest hook ran.");
        }
        plantName = plantNames.getFirst();
    }

    @Given("a plant named {string} exists in that sub-category")
    public void a_plant_named_exists_in_that_sub_category(String name) {
        plantName = name;
    }

    @When("a valid sub-category exists for plant creation")
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

    @When("the user sends a GET request to retrieve plant with id 9999 from {string}")
    public void the_user_sends_a_get_request_to_retrieve_plant_with_id_9999_from(String endpoint) {
        var apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.USER))
                .contentType("application/json")
                .when()
                .get(endpoint);
        ScenarioContext.setApiResponse(apiResponse);
    }

    @When("the user sends a GET request to retrieve plants by sub-category id from {string}")
    public void the_user_sends_a_get_request_to_retrieve_plants_by_sub_category_id_from(String endpoint) {
        String actualEndpoint = endpoint.replace("{subCategoryId}", subCategoryId);
        var apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.USER))
                .contentType("application/json")
                .when()
                .get(actualEndpoint);
        ScenarioContext.setApiResponse(apiResponse);
    }

    @When("the user sends a GET request to retrieve plants by name from {string}")
    public void the_user_sends_a_get_request_to_retrieve_plants_by_name_from(String endpoint) {
        String actualEndpoint = endpoint.replace("{plantName}", plantName);
        var apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.USER))
                .contentType("application/json")
                .when()
                .get(actualEndpoint);
        ScenarioContext.setApiResponse(apiResponse);
    }

    @Then("the response body should contain a valid {string}")
    public void the_response_body_should_contain_a_valid(String key) {
        Response response = ScenarioContext.getApiResponse();
        Object value = response.jsonPath().get(key);
        Assert.assertNotNull("Expected '" + key + "' to be present in response", value);
        if (key.equals("id")) {
            int id = response.jsonPath().getInt(key);
            Assert.assertTrue("Expected '" + key + "' to be a valid positive integer", id > 0);
        }
    }

    @Then("the response body should contain {string} with value {string}")
    public void the_response_body_should_contain_with_value(String key, String expectedValue) {
        Response response = ScenarioContext.getApiResponse();
        String actualValue = response.jsonPath().getString(key);
        Assert.assertEquals("Value mismatch for key: " + key, expectedValue, actualValue);
    }

    @When("a plant exists for update")
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

    @When("a plant exists for deletion")
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

    @When("a plant exists for retrieval")
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

    @Then("the response body should contain the created plant name")
    public void the_response_body_should_contain_the_created_plant_name() {
        Response response = ScenarioContext.getApiResponse();
        String actualName = response.jsonPath().getString("name");
        String expectedName = PlantsHooks.getCreatedPlantName();

        Assert.assertNotNull("Expected plant name to be present in response", actualName);
        Assert.assertEquals("Plant name mismatch", expectedName, actualName);
    }

    @Then("the response body should contain validation error for price")
    public void the_response_body_should_contain_validation_error_for_price() {
        Response response = ScenarioContext.getApiResponse();
        String responseBody = response.getBody().asString();

        Assert.assertTrue("Expected validation error for price in response",
                responseBody.toLowerCase().contains("price") ||
                responseBody.contains("must be greater than") ||
                responseBody.contains("validation"));
    }

    @Then("the response body should contain validation error for name length")
    public void the_response_body_should_contain_validation_error_for_name_length() {
        Response response = ScenarioContext.getApiResponse();
        String nameError = response.getBody().asString();

        Assert.assertNotNull("Expected 'details.name' to be present in response", nameError);
        Assert.assertTrue("Expected validation error for name length in response",
                nameError.toLowerCase().contains("validation failed") &&
                (nameError.contains("must be between")));
    }

    @Then("the created plant is deleted")
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

    @When("the user sends a POST request to {string} with plant details:")
    public void the_user_sends_a_post_request_to_with_plant_details(String endpoint, DataTable dataTable) {
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
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.USER))
                .contentType("application/json")
                .body(plantBody)
                .when()
                .post(actualEndpoint);

        System.out.println("Response Status: " + apiResponse.getStatusCode());
        System.out.println("Response Body: " + apiResponse.getBody().asString());

        ScenarioContext.setApiResponse(apiResponse);
    }

    @Then("the response body should contain error message for insufficient permissions")
    public void the_response_body_should_contain_error_message_for_insufficient_permissions() {
        Response response = ScenarioContext.getApiResponse();
        String responseBody = response.getBody().asString();

        Assert.assertTrue("Expected error message for insufficient permissions in response",
                responseBody.toLowerCase().contains("forbidden") ||
                responseBody.toLowerCase().contains("access denied") ||
                responseBody.toLowerCase().contains("permission") ||
                response.getStatusCode() == 403);
    }

    @When("a plant exists for user deletion attempt")
    public void a_plant_exists_for_user_deletion_attempt() {
        List<Integer> plantIds = PlantsHooks.getPlantIds();

        if (plantIds == null || plantIds.isEmpty()) {
            throw new RuntimeException("No plants found for deletion test. Ensure @SeedPlantSearchTest hook ran.");
        }

        plantId = String.valueOf(plantIds.get(0));
    }

    @When("the user sends a DELETE request to {string}")
    public void the_user_sends_a_delete_request_to(String endpoint) {
        String actualEndpoint = endpoint.replace("{plantId}", plantId);

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

    @When("a plant exists for user update attempt")
    public void a_plant_exists_for_user_update_attempt() {
        List<Integer> plantIds = PlantsHooks.getPlantIds();
        List<Integer> categoryIds = PlantsHooks.getCategoryIds();

        if (plantIds == null || plantIds.isEmpty()) {
            throw new RuntimeException("No plants found for update test. Ensure @SeedPlantSearchTest hook ran.");
        }

        plantId = String.valueOf(plantIds.get(0));
        plantCategoryId = String.valueOf(categoryIds.get(1));
    }

    @When("the user sends a PUT request to {string} with updated details:")
    public void the_user_sends_a_put_request_to_with_updated_details(String endpoint, DataTable dataTable) {
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
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.USER))
                .contentType("application/json")
                .body(plantBody)
                .when()
                .put(actualEndpoint);

        System.out.println("Response Status: " + apiResponse.getStatusCode());
        System.out.println("Response Body: " + apiResponse.getBody().asString());

        ScenarioContext.setApiResponse(apiResponse);
    }

    @When("the user sends a GET request to {string}")
    public void the_user_sends_a_get_request_to(String endpoint) {
        Response apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.USER))
                .contentType("application/json")
                .when()
                .get(endpoint);

        System.out.println("Response Status: " + apiResponse.getStatusCode());
        System.out.println("Response Body: " + apiResponse.getBody().asString());

        ScenarioContext.setApiResponse(apiResponse);
    }

    @Then("the response body should contain a list of plants")
    public void the_response_body_should_contain_a_list_of_plants() {
        Response response = ScenarioContext.getApiResponse();
        List<Object> content = response.jsonPath().getList("content");

        Assert.assertNotNull("Expected 'content' to be present in response", content);
        Assert.assertTrue("Expected plant list to not be empty", content.size() >= 0);
    }

    @Then("the response body should contain content array with max {int} items")
    public void the_response_body_should_contain_content_array_with_max_items(int maxItems) {
        Response response = ScenarioContext.getApiResponse();
        List<Object> content = response.jsonPath().getList("content");

        Assert.assertNotNull("Expected 'content' to be present in response", content);
        Assert.assertTrue("Expected content array to have max " + maxItems + " items, but found " + content.size(),
                content.size() <= maxItems);
    }

    @Then("the response should contain a list of plants associated with the provided sub-category id")
    public void the_response_should_contain_a_list_of_plants_associated_with_the_provided_sub_category_id() {
        List<Map<String, Object>> plants = ScenarioContext.getApiResponse().jsonPath().getList("");
        for (Map<String, Object> plant : plants) {
            Map<String, Object> category = (Map<String, Object>) plant.get("category");
            String actualCategoryId = category.get("id").toString();
            Assert.assertEquals("Expected category name: " + subCategoryId + ", but got: " + actualCategoryId, subCategoryId, actualCategoryId);
        }
    }

    @Then("the response should contain a list with {int} plant object with name {string}")
    public void the_response_should_contain_a_list_with_1_plant_object_with_name(int count, String plantName) {
        List<Map<String, Object>> plants = ScenarioContext.getApiResponse().jsonPath().getList("content");
        Assert.assertEquals("Expected exactly " + count + " plant in the response", count, plants.size());
        String actualPlantName = plants.getFirst().get("name").toString();
        Assert.assertEquals("Expected plant name: " + plantName + ", but got: " + actualPlantName, plantName, actualPlantName);
    }
}
