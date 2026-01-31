package com.itqa.assignment.stepdefinitions;

import com.itqa.assignment.pages.CategoriesPage;
import com.itqa.assignment.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openqa.selenium.WebElement;

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

    //Sorting Scenario

    // --- SORTING SCENARIO ---
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
        // Verify the first row has ID 1
        Assert.assertEquals("1", categoriesPage.getFirstRowId());
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
}
