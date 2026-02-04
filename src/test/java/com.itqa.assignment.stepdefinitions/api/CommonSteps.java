package com.itqa.assignment.stepdefinitions.api;

import com.itqa.assignment.context.ScenarioContext;
import com.itqa.assignment.utilities.ApiHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class CommonSteps {
    @Given("admin auth token is set")
    public void admin_auth_token_is_set() {
        String token = ApiHelper.getJwtToken(ApiHelper.UserRole.ADMIN);
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Failed to retrieve admin JWT token");
        }
    }

    @Given("user auth token is set")
    public void user_auth_token_is_set() {
        String token = ApiHelper.getJwtToken(ApiHelper.UserRole.USER);
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Failed to retrieve user JWT token");
        }
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatusCode) {
        Assert.assertEquals("Status code mismatch!", expectedStatusCode, ScenarioContext.getApiResponse().getStatusCode());
    }

    @Then("the response should contain {string} with value {int}")
    public void the_response_should_contain_with_value(String key, int expectedValue) {
        int actualValue = ScenarioContext.getApiResponse().jsonPath().getInt(key);
        Assert.assertEquals("Value mismatch for key: " + key, expectedValue, actualValue);
    }
}
