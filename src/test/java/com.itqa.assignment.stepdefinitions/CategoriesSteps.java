package com.itqa.assignment.stepdefinitions;

import com.itqa.assignment.pages.CategoriesPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class CategoriesSteps {

    CategoriesPage categoriesPage = new CategoriesPage();

    // --- BASIC NAVIGATION ---
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

    //2nd test case - malith

    @When("the admin clicks on the ID column header")
    public void the_admin_clicks_on_the_id_column_header() {
        categoriesPage.clickIdColumn();
    }

    @Then("the sorting indicator should appear on the ID column")
    public void the_sorting_indicator_should_appear_on_the_id_column() {
        Assert.assertTrue(categoriesPage.isIdSortingIndicatorVisible());
    }

    @And("categories should be sorted by ID")
    public void categories_should_be_sorted_by_id() {
        String firstId = categoriesPage.getFirstRowId();
        Assert.assertEquals("1", firstId); // Verify it equals 1
    }

    //3rd test case - malith

    @When("the admin clicks on the Name column header")
    public void the_admin_clicks_on_the_name_column_header() {
        categoriesPage.clickNameColumn();
        categoriesPage.clickNameColumn();
    }

    @Then("the sorting indicator should appear on the Name column")
    public void the_sorting_indicator_should_appear_on_the_name_column() {
        Assert.assertTrue(categoriesPage.isNameSortingIndicatorVisible());
    }

    @And("categories should be sorted alphabetically by name")
    public void categories_should_be_sorted_alphabetically_by_name() {
        java.util.List<String> names = categoriesPage.getAllCategoryNames();

        // Create a copy and sort it ignoring case
        java.util.List<String> sortedNames = new java.util.ArrayList<>(names);
        sortedNames.sort(String.CASE_INSENSITIVE_ORDER);

        System.out.println("All Names: " + names);
        System.out.println("Sorted Names (expected): " + sortedNames);

        Assert.assertEquals(sortedNames, names); // Compare ignoring case
    }


//4th test case - malith





}
