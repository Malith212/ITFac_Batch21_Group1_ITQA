package com.itqa.assignment.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import com.itqa.assignment.pages.LoginPage;
import com.itqa.assignment.utilities.NavigationHelper;

public class LoginSteps {
    LoginPage loginPage = new LoginPage();

    // --- 1. LOGIN ACTIONS ---
    @When("the user enters valid admin credentials")
    public void the_user_enters_valid_admin_credentials() {
        loginPage.submitLogin("admin", "admin123");
    }

    @When("the user enters {string} and {string}")
    public void the_user_enters_and(String username, String password) {
        loginPage.submitLogin(username, password);
    }

    // --- 2. ASSERTIONS ---
    @Then("the user should be redirected to the Dashboard")
    public void the_user_should_be_redirected_to_the_dashboard() {
        NavigationHelper.waitForUrlContains("dashboard");
        Assert.assertTrue(NavigationHelper.isOnPage("dashboard"));
    }

    @Then("the user should see an error message {string}")
    public void the_user_should_see_an_error_message(String expectedMessage) {
        String actualMessage = loginPage.getGlobalErrorMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }

    // --- 3. REUSABLE BACKGROUND/PRECONDITION STEPS ---
    @Given("the admin is logged into the system")
    public void logged_in_as_an_admin() {
        NavigationHelper.navigateTo("login");
        loginPage.submitLogin("admin", "admin123");
        NavigationHelper.waitForUrlContains("dashboard");
    }

    @Given("logged in as a Standard User")
    public void logged_in_as_a_user() {
        NavigationHelper.navigateTo("login");
        loginPage.submitLogin("testuser", "test123");
        NavigationHelper.waitForUrlContains("dashboard");
    }
}
