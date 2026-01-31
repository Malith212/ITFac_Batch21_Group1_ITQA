package com.itqa.assignment.stepdefinitions;

import com.itqa.assignment.pages.DashboardPage;
import com.itqa.assignment.utilities.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardSteps {
    DashboardPage dashboardPage = new DashboardPage();

    // --- 1. BASIC NAVIGATION ---


    // --- 2. BUTTON INTERACTIONS ---
    @When("the admin clicks on the {string} button")
    public void the_admin_clicks_on_the_button(String buttonName) {
        dashboardPage.visitPage(buttonName);
    }

    // --- 3. ASSERTIONS ---
    @Then("the admin should be redirected to the page with url {string}")
    public void the_admin_should_be_redirected_to_the_page_with_url(String uriSegment) {
        String lowerCaseSegment = uriSegment.toLowerCase();
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains(lowerCaseSegment));
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains(lowerCaseSegment));
    }
}
