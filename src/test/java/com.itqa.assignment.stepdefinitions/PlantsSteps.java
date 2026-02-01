package com.itqa.assignment.stepdefinitions;

import com.itqa.assignment.pages.PlantsPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class PlantsSteps {
    PlantsPage plantPage = new PlantsPage();

    @When("the admin clicks on the {string} button in the Plant page")
    public void the_admin_clicks_on_the_button(String buttonName) {
        if (buttonName.equals("Add Plant")) {
            this.plantPage.clickAddPlantBtn();
        }
    }

    @When("the admin leaves the Plant Name field empty")
    public void the_admin_leaves_the_plant_name_field_empty() {
        plantPage.leavePlantNameEmpty();
    }

    @And("the admin clicks the Save button")
    public void the_admin_clicks_the_save_button() {
        plantPage.clickSaveButton();
    }

    @Then("the form submission should be blocked")
    public void the_form_submission_should_be_blocked() {
        Assert.assertTrue("Form submission should be blocked - still on add plant page",
                plantPage.isFormSubmissionBlocked());
    }

    @Then("the error message {string} should be displayed below the Plant Name field in red")
    public void the_error_message_should_be_displayed_below_the_plant_name_field_in_red(String expectedMessage) {
        // Verify error message is displayed
        Assert.assertTrue("Error message should be displayed",
                plantPage.isPlantNameErrorDisplayed());

        // Verify error message text
        String actualMessage = plantPage.getPlantNameErrorMessage();
        Assert.assertTrue("Error message should contain: " + expectedMessage + ", but was: " + actualMessage,
                actualMessage.toLowerCase().contains(expectedMessage.toLowerCase()));

        // Verify error message is in red color
        Assert.assertTrue("Error message should be displayed in red color",
                plantPage.isPlantNameErrorInRed());
    }

    @Then("the Plant Name field should remain blank")
    public void the_plant_name_field_should_remain_blank() {
        Assert.assertTrue("Plant Name field should be empty",
                plantPage.isPlantNameFieldEmpty());
    }

    // Price field step definitions
    @When("the admin leaves the Price field empty")
    public void the_admin_leaves_the_price_field_empty() {
        plantPage.leavePriceFieldEmpty();
    }

    @Then("the error message {string} should be displayed below the Price field")
    public void the_error_message_should_be_displayed_below_the_price_field(String expectedMessage) {
        // Verify error message is displayed
        Assert.assertTrue("Price error message should be displayed",
                plantPage.isPriceErrorDisplayed());

        // Verify error message text
        String actualMessage = plantPage.getPriceErrorMessage();
        Assert.assertTrue("Error message should contain: " + expectedMessage + ", but was: " + actualMessage,
                actualMessage.toLowerCase().contains(expectedMessage.toLowerCase()));
    }

    @Then("the Price field should remain blank")
    public void the_price_field_should_remain_blank() {
        Assert.assertTrue("Price field should be empty",
                plantPage.isPriceFieldEmpty());
    }

    // Quantity field step definitions
    @When("the admin enters a negative number in the Quantity field")
    public void the_admin_enters_a_negative_number_in_the_quantity_field() {
        plantPage.enterNegativeQuantity();
    }

    @Then("the error message {string} should be displayed below the Quantity field")
    public void the_error_message_should_be_displayed_below_the_quantity_field(String expectedMessage) {
        // Verify error message is displayed
        Assert.assertTrue("Quantity error message should be displayed",
                plantPage.isQuantityErrorDisplayed());

        // Verify error message text
        String actualMessage = plantPage.getQuantityErrorMessage();
        Assert.assertTrue("Error message should contain: " + expectedMessage + ", but was: " + actualMessage,
                actualMessage.toLowerCase().contains(expectedMessage.toLowerCase()));
    }
}
