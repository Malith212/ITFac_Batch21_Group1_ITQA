package com.itqa.assignment.pages;

import com.itqa.assignment.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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
}
