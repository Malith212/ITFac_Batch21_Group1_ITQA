package com.itqa.assignment.pages;

import com.itqa.assignment.utilities.Driver;
import com.itqa.assignment.utilities.NavigationHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SalesPage {

    // --- LOCATORS ---
    private final By sellPlantBtn   = By.cssSelector(".btn-primary");
    private final By sellPlantForm  = By.cssSelector("form.card.shadow-sm.p-4");
    private final By salesTableTbody = By.cssSelector("table.table tbody");
    private final By tagNameOption = By.tagName("option");
    private final By deleteBtnIcon = By.cssSelector("i.bi.bi-trash");
    private final By errorMessage = By.cssSelector(".text-danger");

    // Dropdown
    private final By plantDropdown = By.id("plantId");

    private final By quantityInput = By.id("quantity");


    // --- ACTION METHODS ---

    public void clickSellPlantBtn() {
        Driver.getDriver().findElement(sellPlantBtn).click();
    }

    public void openPlantDropdown() {
        WebDriverWait wait = NavigationHelper.getWait();
        wait.until(ExpectedConditions.visibilityOfElementLocated(plantDropdown));
        Driver.getDriver().findElement(plantDropdown).click();
    }

    public void enterQuantity(String qty) {
        WebElement input = Driver.getDriver().findElement(quantityInput);
        input.clear();
        input.sendKeys(qty);
    }

    public String getQuantityInputValidationMessage() {
        WebElement input = Driver.getDriver().findElement(quantityInput);

        // Get validation message via JS
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        return (String) js.executeScript("return arguments[0].validationMessage;", input);
    }

    public String getPlantDropdownValidationMessage() {
        WebDriverWait wait = NavigationHelper.getWait();
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(plantDropdown));
        WebElement parent = dropdown.findElement(By.xpath("./..")); // get parent element
        List<WebElement> errorMessages = parent.findElements(errorMessage);
        if (!errorMessages.isEmpty()) {
            return errorMessages.getFirst().getText();
        }
        return "";
    }

    public void selectPlantByIndex(int index) {
        WebDriverWait wait = NavigationHelper.getWait();
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(plantDropdown));
        dropdown.click();

        List<WebElement> options = dropdown.findElements(tagNameOption);
        // Skip first option (placeholder)
        if (index + 1 < options.size()) {
            options.get(index + 1).click();
        }
    }

    public int getSelectedPlantStock() {
        WebElement dropdown = Driver.getDriver().findElement(plantDropdown);
        WebElement selectedOption = dropdown.findElement(By.cssSelector("option:checked"));
        String text = selectedOption.getText();

        // Extract stock from text like "Plant 01 (Stock: 5)"
        if (text.contains("Stock:")) {
            String stockStr = text.replaceAll(".*Stock:\\s*(\\d+).*", "$1");
            return Integer.parseInt(stockStr);
        }
        return 0;
    }

    public boolean isPlantInDropdown(String plantName) {
        WebDriverWait wait = NavigationHelper.getWait();
        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(plantDropdown));
        List<WebElement> options = dropdown.findElements(tagNameOption);

        for (WebElement option : options) {
            String text = option.getText();
            // Extract plant name (before the stock info)
            String optionPlantName = text.replaceAll("\\s*\\(.*\\)$", "").trim();
            if (optionPlantName.equals(plantName)) {
                return true;
            }
        }
        return false;
    }

    public int getPlantStockByName(String plantName) {
        WebDriverWait wait = NavigationHelper.getWait();
        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(plantDropdown));
        List<WebElement> options = dropdown.findElements(tagNameOption);

        for (WebElement option : options) {
            String text = option.getText();
            // Extract plant name (before the stock info)
            String optionPlantName = text.replaceAll("\\s*\\(.*\\)$", "").trim();

            if (optionPlantName.equals(plantName)) {
                // Extract stock from text like "Plant 01 (Stock: 5)"
                if (text.contains("Stock:")) {
                    String stockStr = text.replaceAll(".*Stock:\\s*(\\d+).*", "$1");
                    return Integer.parseInt(stockStr);
                }
            }
        }
        throw new AssertionError("Plant '" + plantName + "' not found in dropdown");
    }

    // --- VERIFICATION METHODS ---

    public void verifySellPlantFormIsDisplayed() {
        WebDriverWait wait = NavigationHelper.getWait();
        wait.until(ExpectedConditions.visibilityOfElementLocated(sellPlantForm));

        if (!Driver.getDriver().findElement(sellPlantForm).isDisplayed()) {
            throw new AssertionError("Sell Plant form is not displayed.");
        }
    }

    public boolean areAllPlantsHavingStock() {
        WebDriverWait wait = NavigationHelper.getWait();
        WebElement select = wait.until(ExpectedConditions.visibilityOfElementLocated(plantDropdown));
        List<WebElement> options = select.findElements(tagNameOption);

        for (WebElement option : options) {
            String text = option.getText();

            if (text.contains("Select Plant")) {
                continue;
            }

            int stock = Integer.parseInt(text.replaceAll("[^0-9]", ""));

            if (stock <= 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isSellBlockedDueToQuantity() {
        WebElement input = Driver.getDriver().findElement(quantityInput);
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        Boolean isValid = (Boolean) js.executeScript("return arguments[0].checkValidity();", input);

        return Boolean.FALSE.equals(isValid);
    }

    public boolean isSellBlockedDueToPlant() {
        WebDriverWait wait = NavigationHelper.getWait();
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(plantDropdown));
        String value = dropdown.getAttribute("value");
        return value == null || value.trim().isEmpty();
    }

    public void verifyLatestSaleEntry(String expectedPlantName, int expectedQuantity) {
        WebDriverWait wait = NavigationHelper.getWait();
        WebElement tbody = wait.until(ExpectedConditions.visibilityOfElementLocated(salesTableTbody));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            throw new AssertionError("No sales entries found in sales table.");
        }

        WebElement latest = rows.getFirst();
        List<WebElement> cells = latest.findElements(By.tagName("td"));

        String soldPlant = cells.get(0).getText().trim();
        String soldQtyText = cells.get(1).getText().trim();
        int soldQty;
        try {
            soldQty = Integer.parseInt(soldQtyText.replaceAll("[^0-9-]", ""));
        } catch (NumberFormatException e) {
            throw new AssertionError("Unable to parse sold quantity from table: " + soldQtyText);
        }

        if (!soldPlant.equals(expectedPlantName) || soldQty != expectedQuantity) {
            throw new AssertionError(String.format("Latest sale mismatch. Table: [plant=%s, qty=%d], Expected: [plant=%s, qty=%d]",
                    soldPlant, soldQty, expectedPlantName, expectedQuantity));
        }
    }

    public String getSelectedPlantName() {
        WebElement dropdown = Driver.getDriver().findElement(plantDropdown);
        WebElement selectedOption = dropdown.findElement(By.cssSelector("option:checked"));
        return selectedOption.getText().replaceAll("\\s*\\(.*\\)$", "").trim();
    }

    public void deleteLatestSalesRecord() {
        WebDriverWait wait = NavigationHelper.getWait();

        // Wait for table and get first row
        WebElement tbody = wait.until(ExpectedConditions.visibilityOfElementLocated(salesTableTbody));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));

        if (rows.isEmpty()) {
            throw new AssertionError("No sales entries found to delete.");
        }

        // Find and click delete button in first row
        WebElement deleteBtn = rows.getFirst().findElement(deleteBtnIcon);
        deleteBtn.click();

        // Handle the confirmation alert
        wait.until(ExpectedConditions.alertIsPresent());
        Driver.getDriver().switchTo().alert().accept();

        // Wait for row to be removed
        wait.until(ExpectedConditions.stalenessOf(rows.getFirst()));
    }

}
