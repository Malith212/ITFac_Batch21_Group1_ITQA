package com.itqa.assignment.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import com.itqa.assignment.pages.LoginPage;
import com.itqa.assignment.utilities.Driver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginSteps {
    // Instantiate the page class to access its methods
    LoginPage loginPage = new LoginPage();

    // --- 1. BASIC NAVIGATION ---
    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        loginPage.visit();
    }

    // --- 2. LOGIN ACTIONS ---
    @When("the user enters valid admin credentials")
    public void the_user_enters_valid_admin_credentials() {
        loginPage.submitLogin("admin", "admin123");
    }

    @When("the user enters {string} and {string}")
    public void the_user_enters_and(String username, String password) { // Snippet used 'string, string2'
        loginPage.submitLogin(username, password);
    }

    // --- 3. ASSERTIONS ---
    @Then("the user should be redirected to the Dashboard")
    public void the_user_should_be_redirected_to_the_dashboard() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        // Wait until the URL actually contains 'dashboard'
        wait.until(ExpectedConditions.urlContains("dashboard"));
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("dashboard"));
    }

    @Then("the user should see an error message {string}")
    public void the_user_should_see_an_error_message(String expectedMessage) {
        String actualMessage = loginPage.getGlobalErrorMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }

    // --- 4. REUSABLE BACKGROUND STEPS ---
    @Given("the admin is logged into the system")
    public void logged_in_as_an_admin() {
        loginPage.visit();
        loginPage.submitLogin("admin", "admin123");
    }

    @Given("logged in as a Standard User")
    public void logged_in_as_a_user() {
        loginPage.visit();
        loginPage.submitLogin("testuser", "test123");
    }
}
