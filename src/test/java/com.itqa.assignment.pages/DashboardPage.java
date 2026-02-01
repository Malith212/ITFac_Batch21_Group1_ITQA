package com.itqa.assignment.pages;

import com.itqa.assignment.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import com.itqa.assignment.utilities.Driver;

public class DashboardPage {
    private static final By sideNavbar = By.cssSelector(".sidebar");
    private final By dashboardMenuItem = By.cssSelector("a[href='/ui/dashboard']");
    private final By categoriesMenuItem = By.cssSelector("a[href='/ui/categories']");
    private final By plantsMenuItem = By.cssSelector("a[href='/ui/plants']");
    private final By salesMenuItem = By.cssSelector("a[href='/ui/sales']");

    private final By manageCategoriesBtn = By.cssSelector(".btn[href$='/ui/categories']");
    private final By managePlantsBtn = By.cssSelector(".btn[href$='/ui/plants']");
    private final By viewSalesBtn = By.cssSelector(".btn[href$='/ui/sales']");

    public static boolean isSidebarVisible() {
        return Driver.getDriver().findElement(sideNavbar).isDisplayed();
    }

    public boolean isMenuItemHighlighted(String itemName) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));
        String activeMenuXpath = "//a[normalize-space()='%s' and contains(@class, 'active')]";

        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(String.format(activeMenuXpath, itemName))
            )).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickButtonByName(String buttonName) {
        By buttonLocator = switch (buttonName.toLowerCase()) {
            case "manage categories" -> manageCategoriesBtn;
            case "manage plants" -> managePlantsBtn;
            case "view sales" -> viewSalesBtn;
            default ->
                    throw new NoSuchElementException("Button with name '" + buttonName + "' not found on Dashboard page.");
        };
        Driver.getDriver().findElement(buttonLocator).click();
    }
}
