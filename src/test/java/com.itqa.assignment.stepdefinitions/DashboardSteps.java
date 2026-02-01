package com.itqa.assignment.stepdefinitions;

import com.itqa.assignment.pages.DashboardPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.Set;


public class DashboardSteps {
    DashboardPage dashboardPage = new DashboardPage();

    @When("the admin clicks on the {string} button in the Dashboard page")
    public void the_user_clicks_on_the_button_in_the_dashboard_page(String buttonName) {
        Set<String> validButtons = Set.of("Manage Categories", "Manage Plants", "View Sales");
        if (!validButtons.contains(buttonName)) {
            throw new IllegalArgumentException("Unknown button: " + buttonName);
        }
        dashboardPage.clickButtonByName(buttonName);
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
}
