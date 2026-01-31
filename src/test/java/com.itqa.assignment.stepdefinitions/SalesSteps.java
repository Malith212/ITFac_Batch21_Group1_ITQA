package com.itqa.assignment.stepdefinitions;

import com.itqa.assignment.pages.SalesPage;
import com.itqa.assignment.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SalesSteps {
    SalesPage salesPage = new SalesPage();

    // --- 1. BASIC NAVIGATION ---
    @Given("the admin is on the Sales page")
    public void the_admin_is_on_the_sales_page() {
        salesPage.visit();
    }

    // --- 2. BUTTON INTERACTIONS ---
    @When("the admin clicks on the {string} button in the Sales page")
    public void the_admin_clicks_on_the_button(String buttonName) {
        if (buttonName.equals("Sell Plant")) {
            salesPage.clickSellPlantBtn();
        }
    }

    // --- 3. ASSERTIONS ---
}