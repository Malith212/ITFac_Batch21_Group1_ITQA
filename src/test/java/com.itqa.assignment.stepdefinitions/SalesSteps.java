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
    // --- ADMIN FEATURES ---
    @When("the admin clicks on the {string} button in the Sales page")
    public void the_admin_clicks_on_the_button_in_Sales_page(String buttonName) {
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
    public void the_admin_navigates_to_the_sell_plant_page() {
        salesPage.clickSellPlantBtn();
    }

    @When("the admin clicks on the Plant selection dropdown")
    public void the_admin_clicks_on_the_plant_selection_dropdown() {
        salesPage.openPlantDropdown();
    }

    @Then("the plant dropdown should display only plants with stock greater than 0")
    public void the_plant_dropdown_should_display_only_plants_with_stock_greater_than_0() {
        Assert.assertTrue(
                "Dropdown contains plants with zero stock",
                salesPage.areAllPlantsHavingStock()
        );
    }

    @When("the admin clicks on the {string} button")
    public void the_admin_clicks_on_the_button(String buttonName) {
        if (buttonName.equals("Sell")) {
            salesPage.clickSellPlantBtn();
        }
    }

    @When("the admin enters quantity {string}")
    public void the_admin_enters_quantity(String qty) {
        salesPage.enterQuantity(qty);
    }

    @Then("the sell plant form submission should be blocked")
    public void the_sell_plant_form_submission_should_be_blocked() {
        // Check if plant validation should be tested
        boolean isPlantInvalid = salesPage.isSellBlockedDueToPlant();
        boolean isQuantityInvalid = salesPage.isSellBlockedDueToQuantity();

        Assert.assertTrue(
                "Form was not blocked for invalid input",
                isPlantInvalid || isQuantityInvalid
        );
    }

    @Then("the browser validation message should be displayed as {string}")
    public void the_browser_validation_message_should_be_displayed_as(String expectedMessage) {
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
    public void the_admin_clicks_on_the_button_without_selecting_a_plant(String buttonName) {
        // Ensure no plant is selected (dropdown stays at default)
        // Then click the button
        if (buttonName.equals("Sell")) {
            salesPage.clickSellPlantBtn();
        }
    }

    @When("the admin selects a plant from the dropdown")
    public void the_admin_selects_a_plant_from_the_dropdown() {
        salesPage.openPlantDropdown();
        salesPage.selectPlantByIndex(0);
        stockBeforeSale = salesPage.getSelectedPlantStock();
        selectedPlantName = salesPage.getSelectedPlantName();
    }

    @When("the admin enters quantity between 0 and selected plant stock")
    public void the_admin_enters_quantity_between_0_and_selected_plant_stock() {
        // Enter a quantity less than or equal to stock
        quantityEntered = Math.min(1, stockBeforeSale);
        salesPage.enterQuantity(String.valueOf(quantityEntered));
    }

    @Then("the sell plant form should be submitted successfully")
    public void the_sell_plant_form_should_be_submitted_successfully() {
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
    public void the_stock_of_the_selected_plant_should_be_reduced_accordingly() {
        // Calculate expected stock
        int expectedStock = stockBeforeSale - quantityEntered;

        // Navigate to Plants page to read the actual stock from the plants table
        NavigationHelper.navigateTo("plants");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Read current stock from plants table
        int stockAfterSale = salesPage.getPlantStockFromPlantsTableByName(selectedPlantName);

        Assert.assertEquals(
                "Stock was not reduced correctly. Expected: " + expectedStock + ", Actual: " + stockAfterSale,
                expectedStock,
                stockAfterSale
        );

        // Navigate back to Sales page before exiting
        // This ensures subsequent steps can work with the sales table
        NavigationHelper.navigateTo("sales");

        // Delete the added sales record to maintain test isolation
        salesPage.deleteLatestSaleEntry();
    }


    // --- SALES USER FEATURES ---

    @Then("the Sales page header should be visible")
    public void the_sales_page_header_should_be_visible() {
        Assert.assertTrue(
                "Sales page header is not visible",
                salesPage.isSalesPageHeaderVisible()
        );
    }

    @Then("the Sales page header should be visible as {string}")
    public void the_sales_page_header_should_be_visible_as(String expectedHeader) {
        String actualHeader = salesPage.getSalesPageHeaderText();
        Assert.assertEquals(
                "Sales page header mismatch",
                expectedHeader,
                actualHeader
        );
    }

    @Then("the Sales table should be displayed with columns: Plant, Quantity, Total Price, Sold At")
    public void the_sales_table_should_be_displayed_with_columns_plant_quantity_total_price_sold_at() {
        Assert.assertTrue(
                "Sales table is not displayed with expected columns",
                salesPage.isSalesTableDisplayedWithColumns("Plant", "Quantity", "Total Price", "Sold At")
        );
    }

    @Then("the pagination controls should be visible")
    public void the_pagination_controls_should_be_visible() {
        Assert.assertTrue(
                "Pagination controls are not visible",
                salesPage.isPaginationControlsVisible()
        );
    }

    @When("the user clicks on the {string} column header")
    public void the_user_clicks_on_the_column_header(String columnName) {
        salesPage.clickColumnHeader(columnName);
    }

    @Then("the sales records should be sorted alphabetically by Plant name")
    public void the_sales_records_should_be_sorted_alphabetically_by_plant_name() {
        Assert.assertTrue(
                "Sales records are not sorted alphabetically by Plant name",
                salesPage.isColumnSortedAlphabetically("Plant")
        );
    }

    @Then("the sales records should be sorted numerically by Quantity")
    public void the_sales_records_should_be_sorted_numerically_by_quantity() {
        Assert.assertTrue(
                "Sales records are not sorted numerically by Quantity",
                salesPage.isColumnSortedNumerically("Quantity")
        );
    }

    @Then("the sales records should be sorted numerically by Total Price")
    public void the_sales_records_should_be_sorted_numerically_by_total_price() {
        Assert.assertTrue(
                "Sales records are not sorted numerically by Total Price",
                salesPage.isColumnSortedNumerically("Total Price")
        );
    }

    @Given("there are no sales records in the system")
    public void there_are_no_sales_records_in_the_system() {
        salesPage.deleteAllSalesRecordsIfAny();
    }

    @Then("the message {string} should be displayed in the table")
    public void the_message_should_be_displayed_in_the_table(String expectedMessage) {
        Assert.assertTrue(
                "Expected message '" + expectedMessage + "' is not displayed",
                salesPage.isNoSalesMessageDisplayed()
        );
    }

}

