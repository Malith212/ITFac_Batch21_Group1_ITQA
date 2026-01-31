package com.itqa.assignment.pages;

import com.itqa.assignment.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CategoriesPage {

    // ------------------------
    // --- WEBDRIVER WAIT ---
    // ------------------------
    private final WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));

    // ------------------------
    // --- MENU LOCATORS ---
    // ------------------------
    private final By categoriesMenuItem = By.xpath("/html/body/div[1]/div/div[1]/a[2]"); // Admin left menu "Categories"

    // ------------------------
    // --- PAGE ELEMENTS LOCATORS ---
    // ------------------------
    private final By header = By.xpath("//h3[@class='mb-4']");
    private final By searchInput = By.xpath("//input[@type='text']");
    private final By categoryDropdown = By.xpath("//select[@class='form-select']");
    private final By searchBtn = By.xpath("/html/body/div[1]/div/div[2]/div[2]/form/div[3]/button");
    private final By resetBtn = By.xpath("/html/body/div[1]/div/div[2]/div[2]/form/div[3]/a[1]");
    private final By addCategoryBtn = By.xpath("//a[@href='/ui/categories/add']");
    private final By categoryNameInput = By.xpath("//input");
    private final By saveCategoryBtn = By.xpath("//button[@type='submit']");

    // ------------------------
    // --- TABLE LOCATORS ---
    // ------------------------
    private final By idColumn = By.xpath("(//a[@class='text-white text-decoration-none'])[1]");
    private final By nameColumn = By.xpath("(//a[@class='text-white text-decoration-none'])[2]");
    private final By parentColumn = By.xpath("(//a[@class='text-white text-decoration-none'])[3]");
    private final By actionsColumn = By.xpath("/html/body/div[1]/div/div[2]/div[2]/table/thead/tr/th[4]");

    // --- SORT INDICATORS ---
    private final By idSortIndicator = By.xpath("/html/body/div[1]/div/div[2]/div[2]/table/thead/tr/th[1]/a/span");
    private final By nameSortIndicator = By.xpath("/html/body/div[1]/div/div[2]/div[2]/table/thead/tr/th[2]/a/span");

    // --- TABLE DATA ---
    private final By allNames = By.xpath("/html/body/div[1]/div/div[2]/div[2]/table/tbody/tr/td[2]");
    private final By firstRowId = By.xpath("/html/body/div[1]/div/div[2]/div[2]/table/tbody/tr[1]/td[1]");
    private final By searchResultRecord = By.xpath("/html/body/div[1]/div/div[2]/div[2]/table/tbody/tr/td[2]");
    private final By deleteBtn = By.xpath("//button[@title='Delete']");

    // ------------------------
    // --- NAVIGATION METHODS ---
    // ------------------------
    /** Navigate to Categories page via menu */
    public void visit() {
        wait.until(ExpectedConditions.elementToBeClickable(categoriesMenuItem)).click();
    }

    // ------------------------
    // --- VISIBILITY CHECKS ---
    // ------------------------
    public boolean isCategoriesPageDisplayed() {
        return Driver.getDriver().getCurrentUrl().contains("categories");
    }

    public boolean isHeaderVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(header)).isDisplayed();
    }

    public boolean isSearchInputVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput)).isDisplayed();
    }

    public boolean isCategoryDropdownVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(categoryDropdown)).isDisplayed();
    }

    public boolean isSearchButtonVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(searchBtn)).isDisplayed();
    }

    public boolean isResetButtonVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(resetBtn)).isDisplayed();
    }

    public boolean isAddCategoryButtonVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(addCategoryBtn)).isDisplayed();
    }

    public boolean isIdColumnVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(idColumn)).isDisplayed();
    }

    public boolean isNameColumnVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(nameColumn)).isDisplayed();
    }

    public boolean isParentColumnVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(parentColumn)).isDisplayed();
    }

    public boolean isActionsColumnVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(actionsColumn)).isDisplayed();
    }

    // ------------------------
    // --- SORTING METHODS ---
    // ------------------------
    /** Click on ID column to sort */
    public void clickIdColumn() {
        wait.until(ExpectedConditions.elementToBeClickable(idColumn)).click();
    }

    /** Check if ID sort indicator is visible */
    public boolean isIdSortIndicatorVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(idSortIndicator)).isDisplayed();
    }

    /** Click on Name column to sort */
    public void clickNameColumn() {
        wait.until(ExpectedConditions.elementToBeClickable(nameColumn)).click();
    }

    /** Check if Name sort indicator is visible */
    public boolean isNameSortIndicatorVisible() {
        try {
            Thread.sleep(2000); // Wait for sorting animation
            return Driver.getDriver().findElement(nameSortIndicator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ------------------------
    // --- TABLE DATA METHODS ---
    // ------------------------
    /** Get all category names from the table */
    public List<String> getAllCategoryNames() {
        List<String> names = new ArrayList<>();
        for (WebElement element : Driver.getDriver().findElements(allNames)) {
            names.add(element.getText());
        }
        System.out.println("All Names: " + names);
        return names;
    }

    /** Get ID of the first row */
    public String getFirstRowId() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(firstRowId)).getText();
    }

    // ------------------------
    // --- CATEGORY ACTIONS ---
    // ------------------------
    /** Add a category */
    public void addCategory(String categoryName) {
        wait.until(ExpectedConditions.elementToBeClickable(addCategoryBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(categoryNameInput)).sendKeys(categoryName);
        wait.until(ExpectedConditions.elementToBeClickable(saveCategoryBtn)).click();
    }

    /** Search for a category */
    public void searchCategory(String categoryName) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(categoryName);
    }

    /** Click Search button */
    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
    }

    // --- Get search result text safely ---
    public String getSearchResultText() {
        // Explicit wait for the search result element to be visible
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultRecord));
        return result.getText();
    }


    /** Delete a category */
    public void clickDeleteButton() {
        Driver.getDriver().navigate().refresh(); // Refresh before deleting
        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
    }
}
