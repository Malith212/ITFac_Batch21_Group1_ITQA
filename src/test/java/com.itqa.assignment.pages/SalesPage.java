package com.itqa.assignment.pages;

import com.itqa.assignment.utilities.ConfigReader;
import com.itqa.assignment.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SalesPage {
    private final By sellPlantBtn = By.cssSelector(".btn-primary");

    // 2. Navigation Method

    // 3. Action Method
    public void clickSellPlantBtn() {
        Driver.getDriver().findElement(sellPlantBtn).click();
    }
}