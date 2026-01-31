package com.itqa.assignment.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import com.itqa.assignment.utilities.Driver;
import com.itqa.assignment.utilities.NavigationHelper;
import com.itqa.assignment.pages.LoginPage;

public class Hooks {

    private final LoginPage loginPage = new LoginPage();

    // --- BROWSER SETUP (runs for all @ui scenarios) ---
    @Before(value = "@ui", order = 1)
    public void setUpUI() {
        Driver.getDriver();
    }

    // --- ADMIN LOGIN PRECONDITION ---
    @Before(value = "@ui and @admin", order = 2)
    public void loginAsAdmin() {
        NavigationHelper.navigateTo("login");
        loginPage.submitLogin("admin", "admin123");
        NavigationHelper.waitForUrlContains("dashboard");
    }

    // --- STANDARD USER LOGIN PRECONDITION ---
    @Before(value = "@ui and @user", order = 2)
    public void loginAsUser() {
        NavigationHelper.navigateTo("login");
        loginPage.submitLogin("testuser", "test123");
        NavigationHelper.waitForUrlContains("dashboard");
    }

    // --- BROWSER TEARDOWN ---
    @After("@ui")
    public void tearDownUI() {
        Driver.closeDriver();
    }
}
