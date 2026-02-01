package com.itqa.assignment.pages;

import com.itqa.assignment.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;


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

        // Wait until search input is visible
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));

        // Wait until search input is clickable
        wait.until(ExpectedConditions.elementToBeClickable(searchInput));

        // Clear existing text safely
        input.clear();

        // Enter category name
        input.sendKeys(categoryName);

        // Wait until text is actually entered into input field
        wait.until(ExpectedConditions.attributeToBe(searchInput, "value", categoryName));
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

        Driver.getDriver().navigate().refresh();

        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();

        // Wait for alert
        wait.until(ExpectedConditions.alertIsPresent());

        // Accept alert
        Driver.getDriver().switchTo().alert().accept();

    }

    By successMessage = By.xpath("//span[contains(text(),'Category deleted successfully')]");

    public WebElement getSuccessMessageElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
    }

    public String getSuccessMessageText() {
        return getSuccessMessageElement().getText();
    }

    //---User Test cases---


    // --- PARENT DROPDOWN LOCATOR ---
    private final By parentDropdown = By.cssSelector("select"); // your CSS locator
    private final By parentDropdownOptions = By.cssSelector("select option"); // all options

// --- METHODS ---

    /** Click parent dropdown, get all options, and print them */
    public List<String> getParentDropdownOptions() {
        // Wait until dropdown is clickable
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(parentDropdown));
        dropdown.click(); // optional, mostly for UI visibility

        // Get all option elements
        List<WebElement> options = Driver.getDriver().findElements(parentDropdownOptions);

        // Extract text and print
        List<String> optionTexts = new ArrayList<>();
        System.out.println("Parent Dropdown Options:");
        for (WebElement option : options) {
            String text = option.getText();
            optionTexts.add(text);
            System.out.println(text);
        }

        return optionTexts;
    }

    /** Select a parent category by visible text */
    public void selectParentCategory(String parentName) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(parentDropdown));
        Select select = new Select(dropdown);
        select.selectByVisibleText(parentName);
        System.out.println("Selected parent category: " + parentName);
    }

    /** Click the Search button with explicit waits instead of Thread.sleep */
    public void clickSearchWithWaits() {
        // Wait until the Search button is clickable
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(searchBtn));

        // Click the Search button
        searchButton.click();
        System.out.println("Search button clicked");

        // Wait until the table is updated with results
        // Replace the fixed 10s sleep with a wait for the first row of results to be visible
        By firstRowResult = By.xpath("/html/body/div[1]/div/div[2]/div[2]/table/tbody/tr[1]"); // first row of table
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstRowResult));
        System.out.println("Search results loaded");
    }

    // --- TABLE TD LOCATOR ---
    private final By tableCells = By.xpath("//table/tbody/tr/td");

    /** Verify filtered category is displayed in table */
    public boolean isFilteredCategoryDisplayed(String categoryName) {

        // Wait until table rows are visible
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableCells));

        List<WebElement> cells = Driver.getDriver().findElements(tableCells);

        for (WebElement cell : cells) {
            String text = cell.getText();

            if (text.equalsIgnoreCase(categoryName)) {
                System.out.println("Filtered category found: " + text);
                return true; // break automatically
            }
        }

        System.out.println("Filtered category NOT found");
        return false;
    }

    /** Check Add Category button is NOT visible */
    public boolean isAddCategoryButtonNotVisible() {
        try {
            return !Driver.getDriver().findElement(addCategoryBtn).isDisplayed();
        } catch (Exception e) {
            // If element is not found in DOM → it is not visible (expected)
            return true;
        }
    }

    private final By editAction = By.xpath("//a[@title='Edit']");

    public boolean isEditActionHiddenOrDisabled() {
        List<WebElement> elements = Driver.getDriver().findElements(editAction);

        // If element is not present
        if (elements.isEmpty()) {
            return true;
        }

        WebElement edit = elements.get(0);

        // If element exists but hidden or disabled
        return (!edit.isDisplayed() || !edit.isEnabled());
    }

    private final By deleteAction = By.xpath("//form[contains(@action,'/ui/categories/delete')]");

    public boolean isDeleteActionHiddenOrDisabled() {
        List<WebElement> elements = Driver.getDriver().findElements(deleteAction);

        // If element is not present
        if (elements.isEmpty()) {
            return true;
        }

        WebElement delete = elements.get(0);

        return (!delete.isDisplayed() || !delete.isEnabled());
    }

    public void navigateToAddCategoryPage() {
        Driver.getDriver().navigate().to(
                Driver.getDriver().getCurrentUrl().split("/ui")[0] + "/ui/categories/add"
        );
    }

    // Access Denied header
    private final By accessDeniedHeader = By.cssSelector("h2");


    public boolean isAccessDeniedPageDisplayed() {

        try {
            WebElement header = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(accessDeniedHeader)
            );

            String actualText = header.getText().trim();

            System.out.println("Access Denied Page Text: " + actualText);

            return actualText.equals("403 - Access Denied");

        } catch (Exception e) {
            return false;
        }
    }




}
