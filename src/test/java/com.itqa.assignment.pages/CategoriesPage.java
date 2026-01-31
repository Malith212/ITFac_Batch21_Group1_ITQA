package com.itqa.assignment.pages;

import com.itqa.assignment.utilities.Driver;
import org.openqa.selenium.By;

public class CategoriesPage {

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
        Driver.getDriver().findElement(categoriesMenuItem).click();
    }

    // --- Validations ---
    public boolean isCategoriesPageDisplayed() {
        return Driver.getDriver().getCurrentUrl().contains("categories");
    }

    public boolean isHeaderVisible() {
        return Driver.getDriver().findElement(header).isDisplayed();
    }

    public boolean isSearchInputVisible() {
        return Driver.getDriver().findElement(searchInput).isDisplayed();
    }

    public boolean isCategoryDropdownVisible() {
        return Driver.getDriver().findElement(categoryDropdown).isDisplayed();
    }

    public boolean isSearchButtonVisible() {
        return Driver.getDriver().findElement(searchBtn).isDisplayed();
    }

    public boolean isResetButtonVisible() {
        return Driver.getDriver().findElement(resetBtn).isDisplayed();
    }

    public boolean isAddCategoryButtonVisible() {
        return Driver.getDriver().findElement(addCategoryBtn).isDisplayed();
    }

    public boolean isIdColumnVisible() {
        return Driver.getDriver().findElement(idColumn).isDisplayed();
    }

    public boolean isNameColumnVisible() {
        return Driver.getDriver().findElement(nameColumn).isDisplayed();
    }

    public boolean isParentColumnVisible() {
        return Driver.getDriver().findElement(parentColumn).isDisplayed();
    }

    public boolean isActionsColumnVisible() {
        return Driver.getDriver().findElement(actionsColumn).isDisplayed();
    }

    // --- Sorting Columns ---
    public void clickIdColumn() {
        Driver.getDriver().findElement(idColumn).click();
    }

    public void clickNameColumn() {
        Driver.getDriver().findElement(nameColumn).click();
    }

    // --- Add Category (used only in search scenario) ---
    public void addCategory(String categoryName) throws InterruptedException {
        Thread.sleep(2000);
        Driver.getDriver().findElement(addCategoryBtn).click();
        Thread.sleep(2000);
        Driver.getDriver().findElement(categoryNameInput).sendKeys(categoryName);
        Thread.sleep(2000);
        Driver.getDriver().findElement(saveCategoryBtn).click();
        Thread.sleep(2000);
    }

    // --- Search ---
    public void searchCategory(String categoryName) throws InterruptedException {
        Thread.sleep(2000);
        Driver.getDriver().findElement(searchInput).clear();
        Driver.getDriver().findElement(searchInput).sendKeys(categoryName);
    }

    public void clickSearch() throws InterruptedException {
        Thread.sleep(1000);
        Driver.getDriver().findElement(searchBtn).click();
        Thread.sleep(2000);
    }

    public String getSearchResultText() {
        return Driver.getDriver().findElement(searchResultRecord).getText();
    }

    // --- Delete Category ---
    public void clickDeleteButton() throws InterruptedException {
        Driver.getDriver().navigate().refresh();
        Thread.sleep(5000);
        Driver.getDriver().findElement(deleteBtn).click();
    }
}
