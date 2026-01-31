package com.itqa.assignment.pages;

import com.itqa.assignment.utilities.ConfigReader;
import com.itqa.assignment.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SalesPage {
    private final By sellPlantBtn = By.cssSelector(".btn-primary");
    private final By salesMenuItem = By.cssSelector("a[href='/ui/sales']");

    // 2. Navigation Method
    public void visit() {
        Driver.getDriver().findElement(salesMenuItem).click();
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("sales"));
    }

    // 3. Action Method
    public void clickSellPlantBtn() {
        Driver.getDriver().findElement(sellPlantBtn).click();
    }
}