package com.itqa.assignment.pages;

import com.itqa.assignment.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {
    private final By dashboardMenuItem = By.cssSelector("a[href='/ui/dashboard']");
    private final By categoriesMenuItem = By.cssSelector("a[href='/ui/categories']");
    private final By plantsMenuItem = By.cssSelector("a[href='/ui/plants']");
    private final By salesMenuItem = By.cssSelector("a[href='/ui/sales']");

    public void visitPage(String pageName) {
        switch (pageName) {
            case "Dashboard":
                Driver.getDriver().findElement(dashboardMenuItem).click();
                break;
            case "Categories":
                Driver.getDriver().findElement(categoriesMenuItem).click();
                break;
            case "Plants":
                Driver.getDriver().findElement(plantsMenuItem).click();
                break;
            case "Sales":
                Driver.getDriver().findElement(salesMenuItem).click();
                break;
            default:
                throw new IllegalArgumentException("Invalid page name: " + pageName);
        }
    }

}
