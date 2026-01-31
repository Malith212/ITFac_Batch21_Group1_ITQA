package com.itqa.assignment.pages;

import com.itqa.assignment.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CategoriesPage {

    private final WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));

    // --- Menu ---
    private final By categoriesMenuItem = By.xpath("/html/body/div[1]/div/div[1]/a[2]");

    // --- Page Elements ---
    private final By header = By.xpath("//h3[@class='mb-4']");
    private final By searchInput = By.xpath("//input[@type='text']");
    private final By categoryDropdown = By.xpath("//select[@class='form-select']");
    private final By searchBtn = By.xpath("/html/body/div[1]/div/div[2]/div[2]/form/div[3]/button");
    private final By resetBtn = By.xpath("/html/body/div[1]/div/div[2]/div[2]/form/div[3]/a[1]");
    private final By addCategoryBtn = By.xpath("//a[@href='/ui/categories/add']");
    private final By categoryNameInput = By.xpath("//input");
    private final By saveCategoryBtn = By.xpath("//button[@type='submit']");
    private final By searchResultRecord = By.xpath("/html/body/div[1]/div/div[2]/div[2]/table/tbody/tr/td[2]");
    private final By deleteBtn = By.xpath("//button[@title='Delete']");

    // --- Table Columns ---
    private final By idColumn = By.xpath("(//a[@class='text-white text-decoration-none'])[1]");
    private final By nameColumn = By.xpath("(//a[@class='text-white text-decoration-none'])[2]");
    private final By parentColumn = By.xpath("(//a[@class='text-white text-decoration-none'])[3]");
    private final By actionsColumn = By.xpath("/html/body/div[1]/div/div[2]/div[2]/table/thead/tr/th[4]");

    // --- Navigation ---
    public void visit() {
        wait.until(ExpectedConditions.elementToBeClickable(categoriesMenuItem)).click();
    }

    // --- Validations ---
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

    // --- Sorting Columns ---

    // --- Sorting Columns ---
    public void clickIdColumn() {
        wait.until(ExpectedConditions.elementToBeClickable(idColumn)).click();
    }

    // Locator for the sorting indicator on ID column
    private final By idSortIndicator = By.xpath("/html/body/div[1]/div/div[2]/div[2]/table/thead/tr/th[1]/a/span");

    // Check if the sorting indicator is visible
    public boolean isIdSortIndicatorVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(idSortIndicator)).isDisplayed();
    }

    // Locator for the first row ID
    private final By firstRowId = By.xpath("/html/body/div[1]/div/div[2]/div[2]/table/tbody/tr[1]/td[1]");

    // Get the ID of the first row
    public String getFirstRowId() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(firstRowId)).getText();
    }


    // --- Add Category (used only in search scenario) ---
    public void addCategory(String categoryName) {
        wait.until(ExpectedConditions.elementToBeClickable(addCategoryBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(categoryNameInput)).sendKeys(categoryName);
        wait.until(ExpectedConditions.elementToBeClickable(saveCategoryBtn)).click();
    }

    // --- Search ---
    public void searchCategory(String categoryName) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(categoryName);
    }

    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
    }

    public String getSearchResultText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultRecord)).getText();
    }

    // --- Delete Category ---
    public void clickDeleteButton() {
        Driver.getDriver().navigate().refresh();
        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
    }
}
