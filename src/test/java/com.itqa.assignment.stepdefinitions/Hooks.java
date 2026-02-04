package com.itqa.assignment.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import com.itqa.assignment.utilities.Driver;

public class Hooks {

    // --- BROWSER SETUP (runs for all @ui scenarios) ---
    @Before(value = "@ui", order = 4)
    public void setUpUI() {
        Driver.getDriver();
    }


    // --- BROWSER TEARDOWN ---
    @After("@ui")
    public void tearDownUI() {
        Driver.closeDriver();
    }
}
