package com.itqa.assignment.stepdefinitions;

import com.itqa.assignment.pages.SalesPage;
import io.cucumber.java.en.When;

/**
 * Sales-specific step definitions.
 * Generic navigation steps are handled by CommonSteps.
 */
public class SalesSteps {
    SalesPage salesPage = new SalesPage();

    // --- SALES PAGE SPECIFIC ACTIONS ---
    @When("the admin clicks on the {string} button in the Sales page")
    public void the_admin_clicks_on_the_button(String buttonName) {
        if (buttonName.equals("Sell Plant")) {
            salesPage.clickSellPlantBtn();
        }
    }
}