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
        Assert.assertEquals("1", categoriesPage.getFirstRowId());
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

    @When("the user selects a parent category from dropdown")
    public void the_user_selects_a_parent_category_from_dropdown() {
        // Print all options first
        List<String> options = categoriesPage.getParentDropdownOptions();

        // Select "category 1"
        categoriesPage.selectParentCategory("category 1");
    }

    @And("the user clicks the Search button")
    public void the_user_clicks_the_search_button() {
        categoriesPage.clickSearchWithWaits();
    }

    @Then("filtered categories should be displayed")
    public void filtered_categories_should_be_displayed() {

        boolean isDisplayed = categoriesPage.isFilteredCategoryDisplayed("category 1");

        Assert.assertTrue(isDisplayed);
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



}
