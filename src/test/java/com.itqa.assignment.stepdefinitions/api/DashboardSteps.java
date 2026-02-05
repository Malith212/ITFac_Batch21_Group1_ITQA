package com.itqa.assignment.stepdefinitions.api;

import com.itqa.assignment.context.ScenarioContext;
import com.itqa.assignment.utilities.ApiHelper;
import com.itqa.assignment.utilities.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class DashboardSteps {
    Response response;

    @When("the user sends a GET request to retrieve the Category Summary from {string}")
    public void the_user_sends_a_get_request_to_retrieve_the_category_summary_from(String endpoint) {
        var apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.USER))
                .contentType("application/json")
                .when()
                .get(endpoint);
        ScenarioContext.setApiResponse(apiResponse);
    }

    @When("the user sends a GET request to retrieve the Plant Summary from {string}")
    public void the_user_sends_a_get_request_to_retrieve_the_plants_summary_from(String endpoint) {
        var apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.USER))
                .contentType("application/json")
                .when()
                .get(endpoint);
        ScenarioContext.setApiResponse(apiResponse);
    }

    //================================Plants Specific==============================

    @Given("valid sub-category id is provided")
    public void valid_sub_category_id_is_provided() {
        response = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.USER))
                .contentType("application/json")
                .when()
                .get("/categories/sub-categories")
                .then()
                .extract().response();
    }

    @Given("valid plant name is provided")
    public void valid_plant_name_is_provided() {
        var apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.USER))
                .contentType("application/json")
                .when()
                .get("/plants");
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
        int subCategoryId = response.jsonPath().getInt("[0].id");
        var apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.USER))
                .contentType("application/json")
                .when()
                .get(endpoint + "/" + subCategoryId);
        ScenarioContext.setApiResponse(apiResponse);
    }

    @When("the user sends a GET request to retrieve plants by name from {string}")
    public void the_user_sends_a_get_request_to_retrieve_plants_by_name_from(String endpoint) {
        String plantName = ScenarioContext.getApiResponse().jsonPath().getString("[0].name");
        var apiResponse = RestAssured.given()
                .baseUri(ConfigReader.getProperty("api.base.uri"))
                .header("Authorization", "Bearer " + ApiHelper.getJwtToken(ApiHelper.UserRole.USER))
                .contentType("application/json")
                .when()
                .get(endpoint + "?page=0&size=10&name=" + plantName);
        ScenarioContext.setApiResponse(apiResponse);
    }

    @Then("the response should contain a list of plants associated with the provided sub-category id")
    public void the_response_should_contain_a_list_of_plants_associated_with_the_provided_sub_category_id() {
        int expectedSubCategoryId = response.jsonPath().getInt("[0].id");
        List<Map<String, Object>> plants = ScenarioContext.getApiResponse().jsonPath().getList("");
        for (Map<String, Object> plant : plants) {
            @SuppressWarnings("unchecked")
            Map<String, Object> category = (Map<String, Object>) plant.get("category");
            int actualCategoryId = (int) category.get("id");
            Assert.assertEquals("Expected category name: " + expectedSubCategoryId + ", but got: " + actualCategoryId, expectedSubCategoryId, actualCategoryId);
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
