package com.itqa.assignment.pages;
import com.itqa.assignment.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

    // --- Table Columns ---
    private final By idColumn = By.xpath("(//a[@class='text-white text-decoration-none'])[1]");
    private final By nameColumn = By.xpath("(//a[@class='text-white text-decoration-none'])[2]");
    private final By parentColumn = By.xpath("(//a[@class='text-white text-decoration-none'])[3]");
    private final By actionsColumn = By.xpath("/html/body/div[1]/div/div[2]/div[2]/table/thead/tr/th[4]");

    // --- Table Rows ---
    private final By firstRecordId = By.xpath("/html/body/div[1]/div/div[2]/div[2]/table/tbody/tr[2]/td[1]");

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

    //2nd test case - malith

    // --- Actions ---
    public void clickIdColumn() {
        Driver.getDriver().findElement(idColumn).click();
    }

    // --- Sorting Indicator ---
    private final By idSortingIndicator = By.xpath("/html/body/div[1]/div/div[2]/div[2]/table/thead/tr/th[1]/a/span");

    public boolean isIdSortingIndicatorVisible() {
        try {
            // Wait 2 seconds to let the indicator appear
            Thread.sleep(2000);
            return Driver.getDriver().findElement(idSortingIndicator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // --- First Record ID ---
    private final By firstRowId = By.xpath("/html/body/div[1]/div/div[2]/div[2]/table/tbody/tr[1]/td[1]");

    public String getFirstRowId() {
        String id = Driver.getDriver().findElement(firstRowId).getText();
        System.out.println("First row ID: " + id); // Print to console
        return id;
    }

    // 3rd test cases - malith

    // --- Actions ---
    public void clickNameColumn() {
        Driver.getDriver().findElement(nameColumn).click();
    }

    // --- Name Column Sorting Indicator ---
    private final By nameSortingIndicator = By.xpath("/html/body/div[1]/div/div[2]/div[2]/table/thead/tr/th[2]/a/span");

    public boolean isNameSortingIndicatorVisible() {
        try {
            // Wait 2 seconds for the indicator to appear
            Thread.sleep(2000);
            return Driver.getDriver().findElement(nameSortingIndicator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private final By allNames = By.xpath("/html/body/div[1]/div/div[2]/div[2]/table/tbody/tr/td[2]");

    public java.util.List<String> getAllCategoryNames() {
        java.util.List<String> names = new java.util.ArrayList<>();
        for (org.openqa.selenium.WebElement element : Driver.getDriver().findElements(allNames)) {
            names.add(element.getText());
        }
        System.out.println("All Names: " + names); // Print all names
        return names;
    }


//4th test case - malith



}
