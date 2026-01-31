package com.itqa.assignment.pages;

import com.itqa.assignment.utilities.ConfigReader;
import com.itqa.assignment.utilities.Driver;
import org.openqa.selenium.By;

public class SalesPage {
    private final By sellPlantBtn = By.cssSelector(".btn-primary");

    // 2. Navigation Method
    public void visit() {
        Driver.getDriver().get(ConfigReader.getProperty("ui.url") + "/sales");
    }

    // 3. Action Method
    public void clickSellPlantBtn() {
        Driver.getDriver().findElement(sellPlantBtn).click();
    }
}