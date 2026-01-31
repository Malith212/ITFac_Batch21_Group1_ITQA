package com.itqa.assignment.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import com.itqa.assignment.utilities.Driver;

public class Hooks {

    @Before("@ui") // Only runs for scenarios tagged with @ui
    public void setUpUI() {
        Driver.getDriver();
    }

    @After("@ui")
    public void tearDownUI() {
        Driver.closeDriver();
    }
}
