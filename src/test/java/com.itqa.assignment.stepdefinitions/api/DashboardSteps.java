package com.itqa.assignment.stepdefinitions.api;

import com.itqa.assignment.context.ScenarioContext;
import com.itqa.assignment.utilities.ApiHelper;
import com.itqa.assignment.utilities.ConfigReader;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;

public class DashboardSteps {

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
}
