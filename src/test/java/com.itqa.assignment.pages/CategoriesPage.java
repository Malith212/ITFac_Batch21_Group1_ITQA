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

    // --- WebDriver Wait ---
    private final WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));

    // --- MENU ---
    private final By categoriesMenuItem = By.xpath("/html/body/div[1]/div/div[1]/a[2]"); // Admin left menu "Categories"

    // --- PAGE ELEMENTS ---
    private final By header = By.xpath("//h3[@class='mb-4']");
    private final By searchInput = By.xpath("//input[@type='text']");
    private final By categoryDropdown = By.xpath("//select[@class='form-select']");
    private final By searchBtn = By.xpath("/html/body/div[1]/div/div[2]/div[2]/form/div[3]/button");
    private final By resetBtn = By.xpath("/html/body/div[1]/div/div[2]/div[2]/form/div[3]/a[1]");
    private final By addCategoryBtn = By.xpath("//a[@href='/ui/categories/add']");

    // --- TABLE COLUMNS ---
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

    // ------------------------
    // --- NAVIGATION ---
    // ------------------------
    public void visit() {
        wait.until(ExpectedConditions.elementToBeClickable(categoriesMenuItem)).click();
    }

    // ------------------------
    // --- ELEMENT VISIBILITY ---
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
    // --- SORTING ---
    // ------------------------
    public void clickIdColumn() {
        wait.until(ExpectedConditions.elementToBeClickable(idColumn)).click();
    }

    public boolean isIdSortIndicatorVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(idSortIndicator)).isDisplayed();
    }

    public void clickNameColumn() {
        wait.until(ExpectedConditions.elementToBeClickable(nameColumn)).click();
    }

    public boolean isNameSortIndicatorVisible() {
        try {
            Thread.sleep(2000); // small wait for sorting indicator
            return Driver.getDriver().findElement(nameSortIndicator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ------------------------
    // --- TABLE DATA FETCH ---
    // ------------------------
    public List<String> getAllCategoryNames() {
        List<String> names = new ArrayList<>();
        for (WebElement element : Driver.getDriver().findElements(allNames)) {
            names.add(element.getText());
        }
        System.out.println("All Names: " + names);
        return names;
    }

    public String getFirstRowId() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(firstRowId)).getText();
    }

    // --- Add Category (used only in search scenario) ---
    public void addCategory(String categoryName) {
        wait.until(ExpectedConditions.elementToBeClickable(addCategoryBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(categoryNameInput)).sendKeys(categoryName);
        wait.until(ExpectedConditions.elementToBeClickable(saveCategoryBtn)).click();
    }

    private final By categoryNameInput = By.xpath("//input");
    private final By saveCategoryBtn = By.xpath("//button[@type='submit']");
    private final By searchResultRecord = By.xpath("/html/body/div[1]/div/div[2]/div[2]/table/tbody/tr/td[2]");
    private final By deleteBtn = By.xpath("//button[@title='Delete']");

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
