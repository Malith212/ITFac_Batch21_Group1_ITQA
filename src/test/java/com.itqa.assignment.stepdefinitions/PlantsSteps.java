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

    // ==================== Plant List User View Step Definitions ====================

    @Then("the plant list should be displayed")
    public void the_plant_list_should_be_displayed() {
        Assert.assertTrue("Plant list should be displayed",
                plantPage.isPlantListDisplayed());
    }

    @Then("the plant list should have pagination")
    public void the_plant_list_should_have_pagination() {
        Assert.assertTrue("Plant list should have pagination",
                plantPage.isPaginationDisplayed());
    }

    @Then("the {string} button should not be visible")
    public void the_button_should_not_be_visible(String buttonName) {
        if (buttonName.equals("Add Plant")) {
            Assert.assertFalse("Add Plant button should not be visible for user",
                    plantPage.isAddPlantButtonVisible());
        }
    }

    @Then("the Edit and Delete actions should not be visible")
    public void the_edit_and_delete_actions_should_not_be_visible() {
        Assert.assertFalse("Edit and Delete actions should not be visible for user",
                plantPage.areEditAndDeleteActionsVisible());
    }

    @Then("the page header area should be visible")
    public void the_page_header_area_should_be_visible() {
        Assert.assertTrue("Page header area should be visible",
                plantPage.isPageHeaderVisible());
    }

    @Then("at least one plant row should be visible")
    public void at_least_one_plant_row_should_be_visible() {
        Assert.assertTrue("At least one plant row should be visible",
                plantPage.isAtLeastOnePlantRowVisible());
    }

    @Then("the Actions column should be visible")
    public void the_actions_column_should_be_visible() {
        Assert.assertTrue("Actions column should be visible",
                plantPage.isActionsColumnVisible());
    }

    // ==================== Category Dropdown Verification Step Definitions (TC_PLT_ADM_02) ====================

    @Then("the Category dropdown should be visible in Add Plant page")
    public void the_category_dropdown_should_be_visible_in_add_plant_page() {
        Assert.assertTrue("Category dropdown should be visible in Add Plant page",
                plantPage.isCategoryDropdownVisibleInAddPlant());
    }

    @Then("the Category dropdown should contain the created sub-category as selectable option")
    public void the_category_dropdown_should_contain_the_created_sub_category_as_selectable_option() {
        String subCatName = PlantsHooks.getCategoryDropdownSubCatName();
        Assert.assertTrue("Category dropdown should contain '" + subCatName + "' as selectable option",
                plantPage.categoryDropdownContainsAsSelectable(subCatName));
    }

    @Then("the Category dropdown should not contain the created main category as selectable option")
    public void the_category_dropdown_should_not_contain_the_created_main_category_as_selectable_option() {
        String mainCatName = PlantsHooks.getCategoryDropdownMainCatName();
        Assert.assertTrue("Category dropdown should not contain '" + mainCatName + "' as selectable option",
                plantPage.categoryDropdownDoesNotContainAsSelectable(mainCatName));
    }

    // ==================== Search Functionality Step Definitions (TC_PLT_USR_02) ====================

    @When("the user enters the created plant name in the search field")
    public void the_user_enters_the_created_plant_name_in_the_search_field() {
        String plantName = PlantsHooks.getCreatedPlantName();
        plantPage.enterSearchKeyword(plantName);
    }

    @And("the user clicks the Search button")
    public void the_user_clicks_the_search_button() {
        plantPage.clickSearchButton();
    }

    @Then("the search keyword should be entered")
    public void the_search_keyword_should_be_entered() {
        String plantName = PlantsHooks.getCreatedPlantName();
        Assert.assertTrue("Search keyword should be entered in the search field",
                plantPage.isSearchKeywordEntered(plantName));
    }

    @Then("the page should refresh or grid should update")
    public void the_page_should_refresh_or_grid_should_update() {
        Assert.assertTrue("Should be on the plants page after search",
                plantPage.isPlantListDisplayed());
    }

    @Then("only plants matching the name should be displayed")
    public void only_plants_matching_the_name_should_be_displayed() {
        String plantName = PlantsHooks.getCreatedPlantName();
        Assert.assertTrue("Only plants matching '" + plantName + "' should be displayed",
                plantPage.areOnlyMatchingPlantsDisplayed(plantName));
    }

    @Then("the created plant should be displayed in the results")
    public void the_created_plant_should_be_displayed_in_the_results() {
        String plantName = PlantsHooks.getCreatedPlantName();
        Assert.assertTrue("Plant '" + plantName + "' should be displayed in the results",
                plantPage.isPlantDisplayedInResults(plantName));
    }

    // ==================== Sorting Functionality Step Definitions (TC_PLT_USR_05) ====================

    @Then("the Name column header should be clickable")
    public void the_name_column_header_should_be_clickable() {
        Assert.assertTrue("Name column header should be clickable",
                plantPage.isNameColumnHeaderClickable());
    }

    @When("the user clicks on the Name column header")
    public void the_user_clicks_on_the_name_column_header() {
        plantPage.clickNameColumnHeader();
    }

    @Then("the sort indicator should appear or toggle")
    public void the_sort_indicator_should_appear_or_toggle() {
        Assert.assertTrue("Sort indicator (arrow) should be displayed",
                plantPage.isSortIndicatorDisplayed());
    }

    @Then("the plant list should be sorted alphabetically")
    public void the_plant_list_should_be_sorted_alphabetically() {
        String sortDirection = plantPage.getCurrentSortDirection();
        boolean ascending = sortDirection.equals("ascending");
        Assert.assertTrue("Plant list should be sorted alphabetically (" + sortDirection + ")",
                plantPage.isPlantListSortedAlphabetically(ascending));
    }

    @Then("the plant list should be sorted in ascending order")
    public void the_plant_list_should_be_sorted_in_ascending_order() {
        Assert.assertTrue("Plant list should be sorted in ascending order (A-Z)",
                plantPage.isPlantListSortedAlphabetically(true));
    }

    @Then("the plant list should be sorted in descending order")
    public void the_plant_list_should_be_sorted_in_descending_order() {
        Assert.assertTrue("Plant list should be sorted in descending order (Z-A)",
                plantPage.isPlantListSortedAlphabetically(false));
    }
}
