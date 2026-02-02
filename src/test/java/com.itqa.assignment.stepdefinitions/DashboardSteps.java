package com.itqa.assignment.stepdefinitions;

import com.itqa.assignment.pages.DashboardPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.Set;


public class DashboardSteps {
    DashboardPage dashboardPage = new DashboardPage();

    @When("the user clicks on the {string} button in the Dashboard page")
    public void the_user_clicks_on_the_button_in_the_dashboard_page(String btnName) {
        Set<String> validButtons = Set.of("Manage Categories", "Manage Plants", "View Sales");
        if (!validButtons.contains(btnName)) {
            throw new IllegalArgumentException("Unknown button: " + btnName);
        }
        dashboardPage.clickBtnByName(btnName);
    }

    @When("the admin clicks on the {string} button in the Dashboard page")
    public void the_admin_clicks_on_the_button_in_the_dashboard_page(String btnName) {
        Set<String> validButtons = Set.of("Manage Categories", "Manage Plants", "View Sales");
        if (!validButtons.contains(btnName)) {
            throw new IllegalArgumentException("Unknown button: " + btnName);
        }
        dashboardPage.clickBtnByName(btnName);
    }

    @When("the {string} card is visible on the Dashboard page")
    public void the_card_is_visible_on_the_dashboard_page(String cardName) {
        Set<String> validCards = Set.of("Plants", "Categories", "Sales");
        if (!validCards.contains(cardName)) {
            throw new IllegalArgumentException("Unknown card: " + cardName);
        }
        dashboardPage.cardVisibilityByName(cardName);
    }

    @Then("the sidebar navigation should be visible")
    public void the_sidebar_navigation_should_be_visible() {
        Assert.assertTrue("Sidebar navigation is not visible", DashboardPage.isSidebarVisible());
    }

    @Then("the {string} menu item should be highlighted")
    public void the_menu_item_should_be_highlighted(String menuItem) {
        Assert.assertTrue(menuItem + " menu item is not highlighted",
            dashboardPage.isMenuItemHighlighted(menuItem));
    }

    @Then("the {string} card should show {string} for {string} count")
    public void verify_metric(String cardTitle, String expectedValue, String label) {
        String actualValue = dashboardPage.getMetricValue(cardTitle, label);
        Assert.assertEquals("Mismatch in " + label + " for " + cardTitle, expectedValue, actualValue);
    }
}
