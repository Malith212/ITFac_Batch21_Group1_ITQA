package com.itqa.assignment.stepdefinitions;

import com.itqa.assignment.pages.CategoriesPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.itqa.assignment.utilities.Driver;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class CategoriesSteps {

    CategoriesPage categoriesPage = new CategoriesPage();

    // ------------------------
    // --- NAVIGATION / VISIBILITY CHECKS ---
    // ------------------------
    @Given("the admin is on the Categories page")
    public void the_admin_is_on_the_categories_page() {
        categoriesPage.visit();
    }

    @Then("the categories page should be displayed")
    public void the_categories_page_should_be_displayed() {
        Assert.assertTrue(categoriesPage.isCategoriesPageDisplayed());
    }

    @And("the Categories header should be visible")
    public void the_categories_header_should_be_visible() {
        Assert.assertTrue(categoriesPage.isHeaderVisible());
    }

    @And("search sub category input field should be visible")
    public void search_sub_category_input_field_should_be_visible() {
        Assert.assertTrue(categoriesPage.isSearchInputVisible());
    }

    @And("category dropdown should be visible")
    public void category_dropdown_should_be_visible() {
        Assert.assertTrue(categoriesPage.isCategoryDropdownVisible());
    }

    @And("search and reset buttons should be visible")
    public void search_and_reset_buttons_should_be_visible() {
        Assert.assertTrue(categoriesPage.isSearchButtonVisible());
        Assert.assertTrue(categoriesPage.isResetButtonVisible());
    }

    @And("add category button should be visible")
    public void add_category_button_should_be_visible() {
        Assert.assertTrue(categoriesPage.isAddCategoryButtonVisible());
    }

    @And("category table should display ID, Name, Parent, Actions columns")
    public void category_table_should_display_columns() {
        Assert.assertTrue(categoriesPage.isIdColumnVisible());
        Assert.assertTrue(categoriesPage.isNameColumnVisible());
        Assert.assertTrue(categoriesPage.isParentColumnVisible());
        Assert.assertTrue(categoriesPage.isActionsColumnVisible());
    }

    // ------------------------
    // --- SORTING BY ID ---
    // ------------------------
    @When("the admin clicks on the ID column header")
    public void the_admin_clicks_on_the_id_column_header() {
        categoriesPage.clickIdColumn();
    }

    @Then("the sorting indicator should appear on the ID column")
    public void the_sorting_indicator_should_appear_on_the_id_column() {
        Assert.assertTrue(categoriesPage.isIdSortIndicatorVisible());
    }

    @And("categories should be sorted by ID")
    public void categories_should_be_sorted_by_id() {
        List<String> ids = categoriesPage.getAllCategoryIds();
        Assert.assertFalse("Category list should not be empty", ids.isEmpty());

        // Verify IDs are sorted in ascending order
        for (int i = 0; i < ids.size() - 1; i++) {
            int currentId = Integer.parseInt(ids.get(i));
            int nextId = Integer.parseInt(ids.get(i + 1));
            Assert.assertTrue("Categories should be sorted by ID in ascending order. Found " + currentId + " before " + nextId,
                    currentId <= nextId);
        }
    }

    // ------------------------
    // --- SORTING BY NAME ---
    // ------------------------
    @When("the admin clicks on the Name column header")
    public void the_admin_clicks_on_the_name_column_header() {
        categoriesPage.clickNameColumn(); // Click once (ascending)
        categoriesPage.clickNameColumn(); // Click twice (descending if needed)
    }

    @Then("the sorting indicator should appear on the Name column")
    public void the_sorting_indicator_should_appear_on_the_name_column() {
        Assert.assertTrue(categoriesPage.isNameSortIndicatorVisible());
    }

    @And("categories should be sorted alphabetically by name")
    public void categories_should_be_sorted_alphabetically_by_name() {
        List<String> names = categoriesPage.getAllCategoryNames();

        List<String> sortedNames = new ArrayList<>(names);
        sortedNames.sort(String.CASE_INSENSITIVE_ORDER);

        System.out.println("Current Names: " + names);
        System.out.println("Expected Sorted Names: " + sortedNames);

        Assert.assertEquals(sortedNames, names);
    }

    // --- SEARCH SCENARIO ---
    @When("the admin enters a valid category name in search field")
    public void the_admin_enters_a_valid_category_name_in_search_field() throws InterruptedException {
        // Add category first (only in search scenario)
        categoriesPage.addCategory("abc");
        categoriesPage.searchCategory("abc");
    }

    @And("the admin clicks the Search button")
    public void the_admin_clicks_the_search_button() throws InterruptedException {
        categoriesPage.clickSearch();
    }

    @Then("matching category records should be displayed")
    public void matching_category_records_should_be_displayed() {
        String result = categoriesPage.getSearchResultText();
        Assert.assertEquals("abc", result);
    }

    // --- DELETE SCENARIO ---
    @When("the admin searches for a category record")
    public void the_admin_searches_for_a_category_record() throws InterruptedException {
        categoriesPage.searchCategory("abc");
        categoriesPage.clickSearch();
    }

    @And("the admin clicks the delete button")
    public void the_admin_clicks_the_delete_button() throws InterruptedException {
        categoriesPage.clickDeleteButton();
    }

    @Then("the category should be deleted successfully")
    public void the_category_should_be_deleted_successfully() {

        String actualMessage = categoriesPage.getSuccessMessageText();
        Assert.assertEquals("Category deleted successfully", actualMessage);
    }


    @And("success message should be displayed")
    public void success_message_should_be_displayed() {

        Assert.assertTrue(categoriesPage.getSuccessMessageElement().isDisplayed());
    }

    //---User Test cases---

    @Given("the user is on the Categories page")
    public void the_user_is_on_the_categories_page() {
        categoriesPage.visit();
    }

    @When("the user selects {string} category from dropdown")
    public void the_user_selects_a_parent_category_from_dropdown(String categoryName) {
        // Print all options first
        List<String> options = categoriesPage.getParentDropdownOptions();

        // Select the specified category
        categoriesPage.selectParentCategory(categoryName);
    }

    @When("the user selects the created parent category from dropdown")
    public void the_user_selects_the_created_parent_category_from_dropdown() {
        String parentCategoryName = CategoryHooks.getSortTestParentCategoryName();
        categoriesPage.selectParentCategory(parentCategoryName);
    }

    @And("the user clicks the search button on category page")
    public void the_user_clicks_the_search_button_on_category_page() {
        categoriesPage.clickSearchWithWaits();
    }

    @Then("filtered categories by main {string} category should be displayed")
    public void filtered_categories_should_be_displayed(String categoryName) {

        boolean isDisplayed = categoriesPage.isFilteredCategoryDisplayed(categoryName);

        Assert.assertTrue(isDisplayed);
    }

    @Then("filtered categories by the created parent category should be displayed")
    public void filtered_categories_by_the_created_parent_category_should_be_displayed() {
        String parentCategoryName = CategoryHooks.getSortTestParentCategoryName();
        boolean isDisplayed = categoriesPage.isFilteredCategoryDisplayed(parentCategoryName);
        Assert.assertTrue("Categories filtered by '" + parentCategoryName + "' should be displayed", isDisplayed);
    }

    @Then("the Add Category button should not be visible")
    public void the_add_category_button_should_not_be_visible() {
        Assert.assertTrue(categoriesPage.isAddCategoryButtonNotVisible());
    }

    @Then("Edit and Delete actions should be hidden or disabled")
    public void edit_and_delete_actions_should_be_hidden_or_disabled() {

        Assert.assertTrue(categoriesPage.isEditActionHiddenOrDisabled());
        Assert.assertTrue(categoriesPage.isDeleteActionHiddenOrDisabled());
    }

    @When("the user navigates to {string}")
    public void the_user_navigates_to(String path) {

        String baseUrl = "http://localhost:8080"; // change if needed

        Driver.getDriver().navigate().to(baseUrl + path);
    }


    @Then("the user should be redirected to Access Denied page")
    public void the_user_should_be_redirected_to_access_denied_page() {
        Assert.assertTrue(categoriesPage.isAccessDeniedPageDisplayed());
    }

    @When("the user enters an invalid category name in search field")
    public void the_user_enters_an_invalid_category_name_in_search_field() {

        categoriesPage.enterInvalidCategoryName("testnoki");
    }

    @Then("{string} message should be displayed")
    public void message_should_be_displayed(String message) {

        Assert.assertTrue(categoriesPage.isNoCategoryMessageDisplayed(message));
    }

    //pramesh - user

    @Then("the Categories page should be displayed")
    public void the_categories_page_should_be_displayed_user() {
        Assert.assertTrue(categoriesPage.isCategoriesPageDisplayed());
    }

    @And("the category list should be displayed in read-only mode")
    public void the_category_list_should_be_displayed_in_read_only_mode() {

        Assert.assertTrue(categoriesPage.isCategoryListVisible());
        Assert.assertTrue(categoriesPage.isCategoryListReadOnly());
    }

    @When("the user enters a category name in the search field")
    public void the_user_enters_a_category_name_in_the_search_field() {
        categoriesPage.enterCategoryName("category 1");
    }

    @When("the user enters the created sub-category name in the search field")
    public void the_user_enters_the_created_sub_category_name_in_the_search_field() {
        String subCategoryName = CategoryHooks.getCreatedSubCategoryName();
        categoriesPage.enterCategoryName(subCategoryName);
    }

    @Then("matching category records be displayed")
    public void matching_category_records_be_displayed() {
        Assert.assertTrue(
                "Category 'category 1' was NOT found in table",
                categoriesPage.isCategoryPresentInTable("category 1")
        );
    }

    @Then("the created sub-category should be displayed in the results")
    public void the_created_sub_category_should_be_displayed_in_the_results() {
        String subCategoryName = CategoryHooks.getCreatedSubCategoryName();
        Assert.assertTrue(
                "Category '" + subCategoryName + "' was NOT found in table",
                categoriesPage.isCategoryPresentInTable(subCategoryName)
        );
    }

    @When("the user clicks on the Parent column header")
    public void the_user_clicks_on_the_parent_column_header() {
        categoriesPage.clickParentColumn();
    }

    @Then("the sorting indicator should appear on the Parent column")
    public void the_sorting_indicator_should_appear_on_the_parent_column() {
        Assert.assertTrue(categoriesPage.isParentSortIndicatorVisible());
    }

    @And("categories should be sorted based on parent category")
    public void categories_should_be_sorted_based_on_parent_category() {
        Assert.assertTrue(
                "Categories are NOT sorted by parent category",
                categoriesPage.areCategoriesSortedByParent()
        );
    }

    @When("the admin clicks the Add Category button")
    public void the_admin_clicks_the_add_category_button() {
        categoriesPage.clickAddCategoryButton();
    }

    @Then("the Add Category page should be displayed")
    public void the_add_category_page_should_be_displayed() {
        Assert.assertTrue(categoriesPage.isAddCategoryPageDisplayed());
    }

    @And("the admin clicks the Save button on category page")
    public void the_admin_clicks_the_save_button_on_category_page() {
        categoriesPage.clickSaveButton();
    }

    @Then("an error message {string} should be displayed")
    public void an_error_message_should_be_displayed(String message) {
        Assert.assertTrue(
                "Expected error message NOT found: " + message,
                categoriesPage.isValidationMessageDisplayed(message)
        );
    }

    @And("the admin clicks the Cancel button")
    public void the_admin_clicks_the_cancel_button() {
        categoriesPage.clickCancelButton();
    }

    @Then("the admin should be redirected to the Categories page")
    public void the_admin_should_be_redirected_to_the_categories_page() {
        Assert.assertTrue(
                "Admin was NOT redirected to Categories page",
                categoriesPage.isCategoriesPageElementsVisible()
        );
    }

    // ==================== Add Main Category Step Definitions ====================

    // Store the generated category names for this test
    private static String generatedCategoryName;
    private static String generatedMainCategoryName;
    private static String generatedSubCategoryName;

    @And("the admin enters a valid category name")
    public void the_admin_enters_a_valid_category_name() {
        // Generate unique category name (3-10 characters)
        String suffix = String.format("%03d", System.currentTimeMillis() % 1000);
        generatedCategoryName = "AddM" + suffix;
        categoriesPage.enterCategoryNameInAddForm(generatedCategoryName);
    }

    @And("the admin enters a valid main category name")
    public void the_admin_enters_a_valid_main_category_name() {
        // Generate unique main category name (3-10 characters)
        String suffix = String.format("%03d", System.currentTimeMillis() % 1000);
        generatedMainCategoryName = "Main" + suffix;
        categoriesPage.enterCategoryNameInAddForm(generatedMainCategoryName);
    }

    @And("the admin enters a valid sub-category name")
    public void the_admin_enters_a_valid_sub_category_name() {
        // Generate unique sub-category name (3-10 characters)
        String suffix = String.format("%03d", System.currentTimeMillis() % 1000);
        generatedSubCategoryName = "Sub" + suffix;
        categoriesPage.enterCategoryNameInAddForm(generatedSubCategoryName);
    }

    @And("the admin leaves the parent category empty")
    public void the_admin_leaves_the_parent_category_empty() {
        categoriesPage.leaveParentCategoryEmpty();
    }

    @And("the admin selects the created main category as parent")
    public void the_admin_selects_the_created_main_category_as_parent() {
        categoriesPage.selectParentCategoryByName(generatedMainCategoryName);
    }

    @Then("the category should be created successfully")
    public void the_category_should_be_created_successfully() {
        Assert.assertTrue(
                "Category was NOT created successfully",
                categoriesPage.isCategoryCreatedSuccessfully()
        );
    }

    @Then("the sub-category should be created successfully")
    public void the_sub_category_should_be_created_successfully() {
        Assert.assertTrue(
                "Sub-category was NOT created successfully",
                categoriesPage.isCategoryCreatedSuccessfully()
        );
    }

    @And("the new category should appear in the category list")
    public void the_new_category_should_appear_in_the_category_list() {
        Assert.assertTrue(
                "Category '" + generatedCategoryName + "' was NOT found in the category list",
                categoriesPage.isCategoryInList(generatedCategoryName)
        );
    }

    @And("the main category should appear in the category list")
    public void the_main_category_should_appear_in_the_category_list() {
        Assert.assertTrue(
                "Main Category '" + generatedMainCategoryName + "' was NOT found in the category list",
                categoriesPage.isCategoryInList(generatedMainCategoryName)
        );
    }

    @And("the sub-category should appear in the category list with correct parent")
    public void the_sub_category_should_appear_in_the_category_list_with_correct_parent() {
        Assert.assertTrue(
                "Sub-Category '" + generatedSubCategoryName + "' was NOT found in the category list",
                categoriesPage.isCategoryInList(generatedSubCategoryName)
        );
        Assert.assertTrue(
                "Sub-Category '" + generatedSubCategoryName + "' does not have correct parent '" + generatedMainCategoryName + "'",
                categoriesPage.isCategoryWithParent(generatedSubCategoryName, generatedMainCategoryName)
        );
    }

    @When("the admin deletes the created category from UI")
    public void the_admin_deletes_the_created_category_from_ui() {
        categoriesPage.deleteCategoryByName(generatedCategoryName);
    }

    @When("the admin deletes the created sub-category from UI")
    public void the_admin_deletes_the_created_sub_category_from_ui() {
        categoriesPage.deleteCategoryByName(generatedSubCategoryName);
    }

    @When("the admin deletes the created main category from UI")
    public void the_admin_deletes_the_created_main_category_from_ui() {
        categoriesPage.deleteCategoryByName(generatedMainCategoryName);
    }

    @Then("the created category should be removed from the list")
    public void the_created_category_should_be_removed_from_the_list() {
        Assert.assertFalse(
                "Category '" + generatedCategoryName + "' should NOT be in the list after deletion",
                categoriesPage.isCategoryInList(generatedCategoryName)
        );
    }

    @Then("the sub-category should be removed from the list")
    public void the_sub_category_should_be_removed_from_the_list() {
        Assert.assertFalse(
                "Sub-Category '" + generatedSubCategoryName + "' should NOT be in the list after deletion",
                categoriesPage.isCategoryInList(generatedSubCategoryName)
        );
    }

    @Then("the main category should be removed from the list")
    public void the_main_category_should_be_removed_from_the_list() {
        Assert.assertFalse(
                "Main Category '" + generatedMainCategoryName + "' should NOT be in the list after deletion",
                categoriesPage.isCategoryInList(generatedMainCategoryName)
        );
    }
}