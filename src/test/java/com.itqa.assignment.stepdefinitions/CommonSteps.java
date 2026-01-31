package com.itqa.assignment.stepdefinitions;

import com.itqa.assignment.utilities.NavigationHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

/**
 * Common reusable step definitions for navigation and general assertions.
 * These steps can be used across multiple feature files.
 */
public class CommonSteps {

    // --- GENERIC NAVIGATION STEPS ---

    @Given("the user is on the {string} page")
    public void the_user_is_on_the_page(String pageName) {
        NavigationHelper.navigateTo(pageName);
    }

    @When("the user navigates to the {string} page via sidebar")
    public void the_user_navigates_to_page_via_sidebar(String pageName) {
        NavigationHelper.navigateViaSidebar(pageName);
    }

    @When("the user navigates directly to the {string} page")
    public void the_user_navigates_directly_to_page(String pageName) {
        NavigationHelper.navigateTo(pageName);
    }

    // --- GENERIC ASSERTIONS ---

    @Then("the user should be on the {string} page")
    public void the_user_should_be_on_the_page(String pageName) {
        NavigationHelper.waitForUrlContains(pageName);
        Assert.assertTrue("Expected to be on " + pageName + " page", 
            NavigationHelper.isOnPage(pageName));
    }

    @Then("the URL should contain {string}")
    public void the_url_should_contain(String urlSegment) {
        NavigationHelper.waitForUrlContains(urlSegment);
        Assert.assertTrue("URL should contain: " + urlSegment,
            NavigationHelper.isOnPage(urlSegment) || 
            com.itqa.assignment.utilities.Driver.getDriver().getCurrentUrl().contains(urlSegment));
    }
}
