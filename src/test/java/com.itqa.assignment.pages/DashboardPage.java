package com.itqa.assignment.pages;

import com.itqa.assignment.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {
    private static final By sideNavbar = By.cssSelector(".sidebar");
    private final By dashboardMenuItem = By.cssSelector("a[href='/ui/dashboard']");
    private final By categoriesMenuItem = By.cssSelector("a[href='/ui/categories']");
    private final By plantsMenuItem = By.cssSelector("a[href='/ui/plants']");
    private final By salesMenuItem = By.cssSelector("a[href='/ui/sales']");

    private static final By manageCategoriesBtn = By.cssSelector(".btn[href$='/ui/categories']");
    private static final By managePlantsBtn = By.cssSelector(".btn[href$='/ui/plants']");
    private static final By viewSalesBtn = By.cssSelector(".btn[href$='/ui/sales']");

    private static final String categoryCard = "//div[contains(@class,'dashboard-card')][.//i[contains(@class,'bi-diagram-3')]]";
    private static final String plantCard = "//div[contains(@class,'dashboard-card')][.//i[contains(@class,'bi-flower1')]]";
    private static final String salesCard = "//div[contains(@class,'dashboard-card')][.//i[contains(@class,'bi-cash-stack')]]";

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

    public void clickBtnByName(String btnName) {
        String nameLower = btnName.toLowerCase();
        By locatorName = switch (nameLower) {
            case "manage categories" -> manageCategoriesBtn;
            case "manage plants" -> managePlantsBtn;
            case "view sales" -> viewSalesBtn;
            default ->
                    throw new NoSuchElementException("Button with name '" + btnName + "' not found on Dashboard page.");
        };
        Driver.getDriver().findElement(locatorName).click();
    }

    public void cardVisibilityByName(String cardName) {
        String nameLower = cardName.toLowerCase();
        By locatorName = switch (nameLower) {
            case "categories" -> By.xpath(categoryCard);
            case "plants" -> By.xpath(plantCard);
            case "sales" -> By.xpath(salesCard);
            default ->
                    throw new NoSuchElementException("Card with name '" + cardName + "' not found on Dashboard page.");
        };
        Driver.getDriver().findElement(locatorName).isDisplayed();
    }

    // Helper to find the card container by its title (h6)
    private String getCardXpath(String cardTitle) {
        return "//div[contains(@class, 'dashboard-card')][.//h6[normalize-space()='" + cardTitle + "']]";
    }

    public String getMetricValue(String cardTitle, String labelText) {
        // This XPath finds the label text (e.g., "Main") and looks for the
        // preceding sibling div which contains the actual number.
        String xpath = getCardXpath(cardTitle) + "//div[normalize-space()='" + labelText + "']/preceding-sibling::div";
        return Driver.getDriver().findElement(By.xpath(xpath)).getText().trim();
    }
}
