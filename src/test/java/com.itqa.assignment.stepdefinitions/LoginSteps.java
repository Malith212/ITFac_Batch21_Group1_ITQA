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
    @When("the user enters valid admin username")
    public void the_user_enters_valid_admin_username() {
        loginPage.enterUsername("admin");
    }

    @When("the user enters valid admin password")
    public void the_user_enters_valid_admin_password() {
        loginPage.enterPassword("admin123");
    }

    @When("the user enters invalid admin username")
    public void the_user_enters_invalid_admin_username() {
        loginPage.enterUsername("invalidAdmin");
    }

    @When("the user enters invalid admin password")
    public void the_user_enters_invalid_admin_password() {
        loginPage.enterPassword("wrongPassword");
    }

    @When("the user clicks the {string} button")
    public void the_user_clicks_the_button(String buttonName) {
        if (buttonName.equals("Login")) {
            loginPage.clickLoginBtn();
        }
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

    @Given("the user is logged into the system")
    public void logged_in_as_a_user() {
        NavigationHelper.navigateTo("login");
        loginPage.submitLogin("testuser", "test123");
        NavigationHelper.waitForUrlContains("dashboard");
    }
}
