package com.itqa.assignment.pages;

import com.itqa.assignment.utilities.Driver;
import com.itqa.assignment.utilities.NavigationHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.itqa.assignment.utilities.NavigationHelper;

import java.time.Duration;
import java.util.List;

public class PlantsPage {

    // Locators
    private final By addPlantBtn = By.cssSelector("a.btn-primary");
    private final By plantNameField = By.id("name");
    private final By priceField = By.id("price");
    private final By quantityField = By.id("quantity");
    private final By categoryField = By.id("categoryId");
    private final By saveBtn = By.cssSelector("button.btn-primary");
    private final By plantNameErrorMessage = By.cssSelector("#name + .text-danger");
    private final By priceErrorMessage = By.cssSelector("#price + .text-danger");
    private final By quantityErrorMessage = By.cssSelector("#quantity + .text-danger");
    private final By categoryErrorMessage = By.cssSelector("#categoryId + .text-danger");

    // Locators for plant list page (user view)
    private final By plantTable = By.cssSelector("table.table");
    private final By plantTableRows = By.cssSelector("table.table tbody tr");
    private final By paginationElement = By.cssSelector(".pagination, nav[aria-label='Page navigation']");
    private final By editActionBtn = By.cssSelector("table.table tbody tr td a.btn-warning, table.table tbody tr td a[href*='edit']");
    private final By deleteActionBtn = By.cssSelector("table.table tbody tr td a.btn-danger, table.table tbody tr td form button.btn-danger");
    private final By actionsColumn = By.cssSelector("table.table thead th:last-child");
    private final By pageHeader = By.cssSelector("h3.mb-4, .main-content h3");
    private final By actionsColumnContent = By.cssSelector("table.table tbody tr td:last-child a, table.table tbody tr td:last-child button");

    // Locators for Category management (used in TC_PLT_ADM_02)
    private final By addCategoryBtn = By.cssSelector("a.btn-primary");
    private final By categoryNameField = By.id("name");
    private final By parentCategoryDropdown = By.id("parentId");
    private final By saveCategoryBtn = By.cssSelector("button.btn-primary");
    private final By categoryTableRows = By.cssSelector("table.table tbody tr");
    private final By categoryDropdownInAddPlant = By.id("categoryId");

    // Locators for search functionality
    private final By searchField = By.cssSelector("input[name='name']");
    private final By searchButton = By.cssSelector("form button.btn-primary");
    private final By noPlantsFoundMessage = By.xpath("//*[contains(text(),'No plants found')]");
    private final By plantRowNames = By.cssSelector("table.table tbody tr td:first-child");


    public void clickAddPlantBtn() {
        Driver.getDriver().findElement(addPlantBtn).click();
    }

    public void leavePlantNameEmpty() {
        // Ensure the plant name field is empty
        WebElement nameField = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(plantNameField));
        nameField.clear();
    }

    public void clickSaveButton() {
        WebElement save = NavigationHelper.getWait().until(ExpectedConditions.elementToBeClickable(saveBtn));
        save.click();
    }

    public boolean isPlantNameErrorDisplayed() {
        try {
            WebElement errorElement = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(plantNameErrorMessage));
            return errorElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPlantNameErrorMessage() {
        try {
            WebElement errorElement = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(plantNameErrorMessage));
            return errorElement.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isPlantNameErrorInRed() {
        try {
            WebElement errorElement = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(plantNameErrorMessage));
            String color = errorElement.getCssValue("color");
            // Check if the color contains red (common red values: rgb(255, 0, 0), rgb(220, 53, 69), etc.)
            return color.contains("255, 0, 0") || color.contains("220, 53, 69") ||
                   color.contains("red") || color.contains("239, 68, 68") ||
                   color.contains("185, 28, 28") || color.contains("153, 27, 27");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFormSubmissionBlocked() {
        // Check if still on the add plant page (form was not submitted)
        String currentUrl = Driver.getDriver().getCurrentUrl();
        if (currentUrl == null) {
            return false;
        }
        return currentUrl.contains("/plants/add");
    }

    public boolean isPlantNameFieldEmpty() {
        WebElement nameField = Driver.getDriver().findElement(plantNameField);
        String value = nameField.getAttribute("value");
        return value == null || value.trim().isEmpty();
    }

    // Price field methods
    public void leavePriceFieldEmpty() {
        // Ensure the price field is empty
        WebElement priceInput = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(priceField));
        priceInput.clear();
    }

    public boolean isPriceErrorDisplayed() {
        try {
            WebElement errorElement = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(priceErrorMessage));
            return errorElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPriceErrorMessage() {
        try {
            WebElement errorElement = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(priceErrorMessage));
            return errorElement.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isPriceFieldEmpty() {
        WebElement priceInput = Driver.getDriver().findElement(priceField);
        String value = priceInput.getAttribute("value");
        return value == null || value.trim().isEmpty();
    }

    // Quantity field methods
    public void enterNegativeQuantity() {
        WebElement quantityInput = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(quantityField));
        quantityInput.clear();
        quantityInput.sendKeys("-1");
    }

    public void leaveQuantityFieldEmpty() {
        NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(quantityField));
        Driver.getDriver().findElement(quantityField).clear();
    }

    public void enterQuantity(String quantity) {
        WebElement quantityInput = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(quantityField));
        quantityInput.clear();
        quantityInput.sendKeys(quantity);
    }

    public void leaveCategoryFieldEmpty() {
        NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(categoryField));
        Select categorySelect = new Select(Driver.getDriver().findElement(categoryField));
        categorySelect.selectByIndex(0);
    }

    public boolean isQuantityErrorDisplayed() {
        try {
            WebElement errorElement = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(quantityErrorMessage));
            return errorElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getQuantityErrorMessage() {
        try {
            WebElement errorElement = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(quantityErrorMessage));
            return errorElement.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isCategoryErrorDisplayed() {
        try {
            NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(categoryErrorMessage));
            return Driver.getDriver().findElement(categoryErrorMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCategoryErrorMessage() {
        try {
            NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(categoryErrorMessage));
            return Driver.getDriver().findElement(categoryErrorMessage).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isPlantListDisplayed() {
        try {
            WebElement table = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(plantTable));
            return table.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPaginationDisplayed() {
        try {
            // Check if pagination exists - it may not be present if there are few records
            List<WebElement> paginationElements = Driver.getDriver().findElements(paginationElement);
            return !paginationElements.isEmpty() && paginationElements.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasPlantRecords() {
        try {
            List<WebElement> rows = Driver.getDriver().findElements(plantTableRows);
            return !rows.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAddPlantButtonVisible() {
        try {
            List<WebElement> addBtns = Driver.getDriver().findElements(addPlantBtn);
            // Check if any Add Plant button exists and is displayed
            for (WebElement btn : addBtns) {
                if (btn.getText().contains("Add") && btn.isDisplayed()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEditActionVisible() {
        try {
            List<WebElement> editBtns = Driver.getDriver().findElements(editActionBtn);
            return !editBtns.isEmpty() && editBtns.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDeleteActionVisible() {
        try {
            List<WebElement> deleteBtns = Driver.getDriver().findElements(deleteActionBtn);
            return !deleteBtns.isEmpty() && deleteBtns.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areEditAndDeleteActionsVisible() {
        return isEditActionVisible() || isDeleteActionVisible();
    }

    public boolean hasActionsColumnContent() {
        try {
            List<WebElement> actionButtons = Driver.getDriver().findElements(actionsColumnContent);
            return !actionButtons.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPageHeaderVisible() {
        try {
            WebElement header = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(pageHeader));
            return header.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAtLeastOnePlantRowVisible() {
        try {
            List<WebElement> rows = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(plantTableRows));
            return !rows.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isActionsColumnVisible() {
        try {
            WebElement actionsHeader = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(actionsColumn));
            return actionsHeader.isDisplayed() && actionsHeader.getText().contains("Actions");
        } catch (Exception e) {
            return false;
        }
    }


    public void clickAddCategoryButton() {
        WebElement addBtn = NavigationHelper.getWait().until(ExpectedConditions.elementToBeClickable(addCategoryBtn));
        addBtn.click();
        // Wait for the URL to contain "/add" indicating we're on the add category page
        NavigationHelper.getWait().until(ExpectedConditions.urlContains("/add"));
        // Wait for the add category form to load (wait for the parent category dropdown to appear)
        NavigationHelper.getWait().until(ExpectedConditions.presenceOfElementLocated(parentCategoryDropdown));
    }

    public void enterCategoryName(String name) {
        WebElement nameField = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(categoryNameField));
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void leaveParentCategoryEmpty() {
        WebElement dropdown = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(parentCategoryDropdown));
        Select select = new Select(dropdown);
        // Select the "Main Category" option (no parent = main category)
        select.selectByVisibleText("Main Category");
    }

    public void selectParentCategory(String parentName) {
        WebElement dropdown = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(parentCategoryDropdown));
        Select select = new Select(dropdown);
        select.selectByVisibleText(parentName);
    }

    public void clickSaveCategoryButton() {
        WebElement saveBtn = NavigationHelper.getWait().until(ExpectedConditions.elementToBeClickable(saveCategoryBtn));
        saveBtn.click();
        // Wait for redirect back to categories list (not the add page)
        NavigationHelper.getWait().until(ExpectedConditions.not(ExpectedConditions.urlContains("/add")));
        // Wait for the table to be visible
        NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(categoryTableRows));
    }

    public boolean isCategoryVisibleInList(String categoryName) {
        try {
            NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(categoryTableRows));
            List<WebElement> rows = Driver.getDriver().findElements(categoryTableRows);
            for (WebElement row : rows) {
                if (row.getText().contains(categoryName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteCategory(String categoryName) {
        List<WebElement> rows = Driver.getDriver().findElements(categoryTableRows);
        for (WebElement row : rows) {
            if (row.getText().contains(categoryName)) {
                // Find and click the delete button in this row (inside a form with btn-outline-danger)
                WebElement deleteBtn = row.findElement(By.cssSelector("form button.btn-outline-danger, form .btn-outline-danger, button.btn-danger, .btn-danger"));
                deleteBtn.click();

                // Browser will show a confirm dialog - use explicit wait for alert
                try {
                    WebDriverWait alertWait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));
                    alertWait.until(ExpectedConditions.alertIsPresent());
                    Driver.getDriver().switchTo().alert().accept();
                } catch (Exception e) {
                    // No alert present or already handled
                }

                // Wait for the page to refresh
                try {
                    NavigationHelper.getWait().until(ExpectedConditions.stalenessOf(row));
                } catch (Exception e) {
                    // Row already removed, wait for table to reload
                    NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(categoryTableRows));
                }
                break;
            }
        }
    }

    // Category dropdown verification in Add Plant page
    public boolean isCategoryDropdownVisibleInAddPlant() {
        try {
            WebElement dropdown = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(categoryDropdownInAddPlant));
            return dropdown.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean categoryDropdownContainsAsSelectable(String categoryName) {
        try {
            WebElement dropdown = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(categoryDropdownInAddPlant));
            Select select = new Select(dropdown);
            List<WebElement> options = select.getOptions();
            for (WebElement option : options) {
                if (option.getText().contains(categoryName) && option.isEnabled()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean categoryDropdownDoesNotContainAsSelectable(String categoryName) {
        try {
            WebElement dropdown = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(categoryDropdownInAddPlant));
            Select select = new Select(dropdown);
            List<WebElement> options = select.getOptions();
            for (WebElement option : options) {
                // Check if the category is in the dropdown and enabled (selectable)
                if (option.getText().contains(categoryName) && option.isEnabled()) {
                    return false; // Found as selectable, so return false
                }
            }
            return true; // Not found or not selectable
        } catch (Exception e) {
            return true;
        }
    }

    // ==================== Search Functionality Methods ====================

    public void enterSearchKeyword(String keyword) {
        WebElement search = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(searchField));
        search.clear();
        search.sendKeys(keyword);
    }

    public void clickSearchButton() {
        WebElement search = NavigationHelper.getWait().until(ExpectedConditions.elementToBeClickable(searchButton));
        search.click();
        // Wait for page to refresh/update
        NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(plantTable));
    }

    public boolean isSearchKeywordEntered(String keyword) {
        try {
            WebElement search = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(searchField));
            String value = search.getAttribute("value");
            return value != null && value.contains(keyword);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areOnlyMatchingPlantsDisplayed(String keyword) {
        try {
            List<WebElement> rows = Driver.getDriver().findElements(plantRowNames);
            if (rows.isEmpty()) {
                return false;
            }
            for (WebElement row : rows) {
                String plantName = row.getText().toLowerCase();
                if (!plantName.contains(keyword.toLowerCase())) {
                    return false; // Found a plant that doesn't match
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNoPlantsFoundMessageDisplayed() {
        try {
            WebElement message = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(noPlantsFoundMessage));
            return message.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPlantDisplayedInResults(String plantName) {
        try {
            List<WebElement> rows = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(plantRowNames));
            for (WebElement row : rows) {
                if (row.getText().toLowerCase().contains(plantName.toLowerCase())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
