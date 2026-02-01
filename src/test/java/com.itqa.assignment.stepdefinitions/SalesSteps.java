package com.itqa.assignment.stepdefinitions;

import com.itqa.assignment.pages.SalesPage;
import com.itqa.assignment.utilities.NavigationHelper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import org.junit.Assert;

/**
 * Sales-specific step definitions.
 * Generic navigation steps are handled by CommonSteps.
 */
public class SalesSteps {
    SalesPage salesPage = new SalesPage();

    // State management for stock verification
    private int stockBeforeSale;
    private int quantityEntered;
    private String selectedPlantName;

    // --- SALES PAGE SPECIFIC ACTIONS ---
    @When("the admin clicks on the {string} button in the Sales page")
    public void the_admin_clicks_on_the_button(String buttonName) {
        if (buttonName.equals("Sell Plant")) {
            salesPage.clickSellPlantBtn();
        }
    }

    @Then("the {string} form should be display successfully")
    public void the_form_should_be_display_successfully(String formName) {
        if (formName.equals("Sell Plant")) {
            salesPage.verifySellPlantFormIsDisplayed();
        }
    }

    @Given("the admin navigates to the Sell Plant page")
    public void navigateToSellPlantPage() {
        salesPage.clickSellPlantBtn();
    }

    @When("the admin clicks on the Plant selection dropdown")
    public void clickPlantDropdown() {
        salesPage.openPlantDropdown();
    }

    @Then("the plant dropdown should display only plants with stock greater than 0")
    public void validatePlantDropdownStock() {
        Assert.assertTrue(
                "Dropdown contains plants with zero stock",
                salesPage.areAllPlantsHavingStock()
        );
    }

    @When("the admin clicks on the {string} button")
    public void clickButton(String buttonName) {
        if (buttonName.equals("Sell")) {
            salesPage.clickSellPlantBtn();
        }
    }

    @When("the admin enters quantity {string}")
    public void enterQuantity(String qty) {
        salesPage.enterQuantity(qty);
    }

    @Then("the sell plant form submission should be blocked")
    public void verifyFormBlocked() {
        // Check if plant validation should be tested
        boolean isPlantInvalid = salesPage.isSellBlockedDueToPlant();
        boolean isQuantityInvalid = salesPage.isSellBlockedDueToQuantity();

        Assert.assertTrue(
                "Form was not blocked for invalid input",
                isPlantInvalid || isQuantityInvalid
        );
    }

    @Then("the browser validation message should be displayed as {string}")
    public void verifyBrowserValidationMessage(String expectedMessage) {
        String actualMessage;

        // Determine which field to check based on the expected message
        if (expectedMessage.toLowerCase().contains("plant")) {
            actualMessage = salesPage.getPlantDropdownValidationMessage();
        } else {
            actualMessage = salesPage.getQuantityInputValidationMessage();
        }

        Assert.assertEquals(
                "Validation message mismatch",
                expectedMessage,
                actualMessage
        );
    }

    @When("the admin clicks on the {string} button without selecting a plant")
    public void clickButtonWithoutSelectingPlant(String buttonName) {
        // Ensure no plant is selected (dropdown stays at default)
        // Then click the button
        if (buttonName.equals("Sell")) {
            salesPage.clickSellPlantBtn();
        }
    }

    @When("the admin selects a plant from the dropdown")
    public void selectPlantFromDropdown() {
        salesPage.openPlantDropdown();
        salesPage.selectPlantByIndex(0);
        stockBeforeSale = salesPage.getSelectedPlantStock();
        selectedPlantName = salesPage.getSelectedPlantName();
    }

    @When("the admin enters quantity between 0 and selected plant stock")
    public void enterValidQuantity() {
        // Enter a quantity less than or equal to stock
        quantityEntered = Math.min(1, stockBeforeSale);
        salesPage.enterQuantity(String.valueOf(quantityEntered));
    }

    @Then("the sell plant form should be submitted successfully")
    public void verifyFormSubmissionSuccessful() {
        // After form submission, we should be redirected to sales page
        // Wait for the redirect
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Verify we're on the sales list page and the sale entry exists
        salesPage.verifyLatestSaleEntry(selectedPlantName, quantityEntered);
    }

    @Then("stock of the selected plant should be reduced accordingly")
    public void verifyStockDecrease() {
        // Navigate back to Sell Plant page to verify stock
        salesPage.clickSellPlantBtn();

        // Wait for form to load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Open dropdown to refresh stock values
        salesPage.openPlantDropdown();

        // Calculate expected stock
        int expectedStock = stockBeforeSale - quantityEntered;

        // If expected stock is 0, the plant should not be in dropdown
        if (expectedStock == 0) {
            // Verify plant is not in dropdown (because stock is 0)
            boolean plantExists = salesPage.isPlantInDropdown(selectedPlantName);
            Assert.assertFalse(
                    "Plant with 0 stock should not be in dropdown, but it was found",
                    plantExists
            );
        } else {
            // Get current stock for the same plant by name (not index, since order may change)
            int stockAfterSale = salesPage.getPlantStockByName(selectedPlantName);

            Assert.assertEquals(
                    "Stock was not reduced correctly. Expected: " + expectedStock + ", Actual: " + stockAfterSale,
                    expectedStock,
                    stockAfterSale
            );
        }
    }

    @Then("the admin deletes the created sales record")
    public void deleteCreatedSalesRecord() {
        NavigationHelper.navigateTo("sales");
        salesPage.deleteLatestSalesRecord();
    }

}

