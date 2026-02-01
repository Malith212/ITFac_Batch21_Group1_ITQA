package com.itqa.assignment.pages;

import com.itqa.assignment.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PlantsPage {

    // Locators
    private final By addPlantBtn = By.cssSelector("a.btn-primary");
    private final By plantNameField = By.id("name");
    private final By priceField = By.id("price");
    private final By quantityField = By.id("quantity");
    private final By saveBtn = By.cssSelector("button.btn-primary");
    private final By plantNameErrorMessage = By.cssSelector("#name + .text-danger");
    private final By priceErrorMessage = By.cssSelector("#price + .text-danger");
    private final By quantityErrorMessage = By.cssSelector("#quantity + .text-danger");

    // Locators for plant list page (user view)
    private final By plantTable = By.cssSelector("table.table");
    private final By plantTableRows = By.cssSelector("table.table tbody tr");
    private final By paginationElement = By.cssSelector(".pagination, nav[aria-label='Page navigation']");
    private final By editActionBtn = By.cssSelector("table.table tbody tr td a.btn-warning, table.table tbody tr td a[href*='edit']");
    private final By deleteActionBtn = By.cssSelector("table.table tbody tr td a.btn-danger, table.table tbody tr td form button.btn-danger");
    private final By actionsColumn = By.cssSelector("table.table thead th:last-child");
    private final By pageHeader = By.cssSelector("h3.mb-4, .main-content h3");
    private final By actionsColumnContent = By.cssSelector("table.table tbody tr td:last-child a, table.table tbody tr td:last-child button");

    private WebDriverWait getWait() {
        return new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
    }

    public void clickAddPlantBtn() {
        Driver.getDriver().findElement(addPlantBtn).click();
    }

    public void leavePlantNameEmpty() {
        // Ensure the plant name field is empty
        WebElement nameField = getWait().until(ExpectedConditions.visibilityOfElementLocated(plantNameField));
        nameField.clear();
    }

    public void clickSaveButton() {
        WebElement save = getWait().until(ExpectedConditions.elementToBeClickable(saveBtn));
        save.click();
    }

    public boolean isPlantNameErrorDisplayed() {
        try {
            WebElement errorElement = getWait().until(ExpectedConditions.visibilityOfElementLocated(plantNameErrorMessage));
            return errorElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPlantNameErrorMessage() {
        try {
            WebElement errorElement = getWait().until(ExpectedConditions.visibilityOfElementLocated(plantNameErrorMessage));
            return errorElement.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isPlantNameErrorInRed() {
        try {
            WebElement errorElement = getWait().until(ExpectedConditions.visibilityOfElementLocated(plantNameErrorMessage));
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
        WebElement priceInput = getWait().until(ExpectedConditions.visibilityOfElementLocated(priceField));
        priceInput.clear();
    }

    public boolean isPriceErrorDisplayed() {
        try {
            WebElement errorElement = getWait().until(ExpectedConditions.visibilityOfElementLocated(priceErrorMessage));
            return errorElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPriceErrorMessage() {
        try {
            WebElement errorElement = getWait().until(ExpectedConditions.visibilityOfElementLocated(priceErrorMessage));
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
        WebElement quantityInput = getWait().until(ExpectedConditions.visibilityOfElementLocated(quantityField));
        quantityInput.clear();
        quantityInput.sendKeys("-1");
    }

    public void enterQuantity(String quantity) {
        WebElement quantityInput = getWait().until(ExpectedConditions.visibilityOfElementLocated(quantityField));
        quantityInput.clear();
        quantityInput.sendKeys(quantity);
    }

    public boolean isQuantityErrorDisplayed() {
        try {
            WebElement errorElement = getWait().until(ExpectedConditions.visibilityOfElementLocated(quantityErrorMessage));
            return errorElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getQuantityErrorMessage() {
        try {
            WebElement errorElement = getWait().until(ExpectedConditions.visibilityOfElementLocated(quantityErrorMessage));
            return errorElement.getText();
        } catch (Exception e) {
            return "";
        }
    }

    // ==================== Plant List Page Methods (User View) ====================

    public boolean isPlantListDisplayed() {
        try {
            WebElement table = getWait().until(ExpectedConditions.visibilityOfElementLocated(plantTable));
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
            WebElement header = getWait().until(ExpectedConditions.visibilityOfElementLocated(pageHeader));
            return header.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAtLeastOnePlantRowVisible() {
        try {
            List<WebElement> rows = getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(plantTableRows));
            return !rows.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isActionsColumnVisible() {
        try {
            WebElement actionsHeader = getWait().until(ExpectedConditions.visibilityOfElementLocated(actionsColumn));
            return actionsHeader.isDisplayed() && actionsHeader.getText().contains("Actions");
        } catch (Exception e) {
            return false;
        }
    }
}
