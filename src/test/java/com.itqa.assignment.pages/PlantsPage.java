package com.itqa.assignment.pages;

import com.itqa.assignment.utilities.Driver;
import com.itqa.assignment.utilities.NavigationHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class PlantsPage {

    // Locators for Add Plant page
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

    // Locators for plant list page
    private final By plantTable = By.cssSelector("table.table");
    private final By plantTableRows = By.cssSelector("table.table tbody tr");
    private final By paginationElement = By.cssSelector("nav ul.pagination");
    private final By editActionBtn = By.cssSelector("table.table tbody tr td a.btn-warning, table.table tbody tr td a[href*='edit']");
    private final By deleteActionBtn = By.cssSelector("table.table tbody tr td a.btn-danger, table.table tbody tr td form button.btn-danger");
    private final By actionsColumn = By.cssSelector("table.table thead th:last-child");
    private final By pageHeader = By.cssSelector("h3.mb-4, .main-content h3");

    // Locators for search functionality
    private final By searchField = By.cssSelector("input[name='name']");
    private final By searchButton = By.cssSelector("form button.btn-primary");
    private final By plantRowNames = By.cssSelector("table.table tbody tr td:first-child");
    // Locators for sorting functionality
    private final By nameColumnHeader = By.cssSelector("table.table thead th a[href*='sortField=name']");
    private final By nameColumnHeaderCell = By.xpath("//table[contains(@class,'table')]//thead//th[contains(.,'Name')]");

    // ==================== Add Plant Page Methods ====================

    public void clickAddPlantBtn() {
        Driver.getDriver().findElement(addPlantBtn).click();
    }

    public void leavePlantNameEmpty() {
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
            return color.contains("255, 0, 0") || color.contains("220, 53, 69") ||
                   color.contains("red") || color.contains("239, 68, 68") ||
                   color.contains("185, 28, 28") || color.contains("153, 27, 27");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFormSubmissionBlocked() {
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
    // ==================== Plant List Page Methods ====================

    public boolean isPlantListDisplayed() {
        try {
            WebElement table = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(plantTable));
            return table.isDisplayed();
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

    // ==================== Plant List Page Methods ====================

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
            WebElement pagination = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(paginationElement));
            return pagination.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAddPlantButtonVisible() {
        try {
            List<WebElement> addBtns = Driver.getDriver().findElements(addPlantBtn);
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

    // ==================== Category Dropdown Verification Methods (TC_PLT_ADM_02) ====================

    public boolean isCategoryDropdownVisibleInAddPlant() {
        try {
            WebElement dropdown = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(categoryField));
            return dropdown.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean categoryDropdownContainsAsSelectable(String categoryName) {
        try {
            WebElement dropdown = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(categoryField));
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
            WebElement dropdown = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(categoryField));
            Select select = new Select(dropdown);
            List<WebElement> options = select.getOptions();
            for (WebElement option : options) {
                if (option.getText().contains(categoryName) && option.isEnabled()) {
                    return false;
                }
            }
            return true;
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
        NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(plantTable));
    }

    }

    public boolean categoryDropdownDoesNotContainAsSelectable(String categoryName) {
        try {
            WebElement dropdown = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(categoryDropdownInAddPlant));
            Select select = new Select(dropdown);
            List<WebElement> options = select.getOptions();
            for (WebElement option : options) {
                if (option.getText().contains(categoryName) && option.isEnabled()) {
                    return false;
                }
            }
            return true;
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
                    return false;
                }
            }
            return true;
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

    // ==================== Sorting Functionality Methods ====================

    public boolean isNameColumnHeaderClickable() {
        try {
            WebElement header = NavigationHelper.getWait().until(ExpectedConditions.elementToBeClickable(nameColumnHeader));
            return header.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickNameColumnHeader() {
        WebElement header = NavigationHelper.getWait().until(ExpectedConditions.elementToBeClickable(nameColumnHeader));
        header.click();
        NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(plantTable));
    }

    public boolean isSortIndicatorDisplayed() {
        try {
            WebElement headerCell = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(nameColumnHeaderCell));
            String headerText = headerCell.getText();
            return headerText.contains("↑") || headerText.contains("↓");
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentSortDirection() {
        try {
            WebElement headerCell = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfElementLocated(nameColumnHeaderCell));
            String headerText = headerCell.getText();
            if (headerText.contains("↑")) {
                return "ascending";
            } else if (headerText.contains("↓")) {
                return "descending";
            }
            return "none";
        } catch (Exception e) {
            return "none";
        }
    }

    public List<String> getPlantNamesInOrder() {
        try {
            List<WebElement> rows = NavigationHelper.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(plantRowNames));
            return rows.stream()
                    .map(WebElement::getText)
                    .toList();
        } catch (Exception e) {
            return List.of();
        }
    }

    public boolean isPlantListSortedAlphabetically(boolean ascending) {
        List<String> plantNames = getPlantNamesInOrder();
        if (plantNames.isEmpty() || plantNames.size() == 1) {
            return true;
        }

        for (int i = 0; i < plantNames.size() - 1; i++) {
            int comparison = plantNames.get(i).compareToIgnoreCase(plantNames.get(i + 1));
            if (ascending && comparison > 0) {
                return false;
            }
            if (!ascending && comparison < 0) {
                return false;
            }
        }
        return true;
    }
}
