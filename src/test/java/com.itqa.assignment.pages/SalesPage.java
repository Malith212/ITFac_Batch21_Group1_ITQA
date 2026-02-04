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
    private final By sellPlantBtn = By.cssSelector(".btn-primary");
    private final By sellPlantForm = By.cssSelector("form.card.shadow-sm.p-4");
    private final By salesTableTbody = By.cssSelector("table.table tbody");
    private final By tagNameOption = By.tagName("option");
    private final By deleteBtnIcon = By.cssSelector("button.btn-outline-danger");
    private final By errorMessage = By.cssSelector(".text-danger");

    // Sales Page User Features
    private final By salesPageHeader = By.cssSelector("h3.mb-4");
    private final By salesTable = By.cssSelector("table.table");
    private final By salesTableHeaders = By.cssSelector("table.table thead th");
    private final By noSalesMessage = By.cssSelector(".text-muted");
    private final By paginationElement = By.cssSelector("nav ul.pagination");

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
        try {
            WebElement input = Driver.getDriver().findElement(quantityInput);
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            Boolean isValid = (Boolean) js.executeScript("return arguments[0].checkValidity();", input);
            return Boolean.FALSE.equals(isValid);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSellBlockedDueToPlant() {
        try {
            WebDriverWait wait = NavigationHelper.getWait();
            WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(plantDropdown));
            String value = dropdown.getAttribute("value");
            return value == null || value.trim().isEmpty();
        } catch (Exception e) {
            return true; // If can't find element, assume it's blocked
        }
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

    public void deleteLatestSaleEntry() {
        WebDriverWait wait = NavigationHelper.getWait();
        WebElement tbody = wait.until(ExpectedConditions.visibilityOfElementLocated(salesTableTbody));
        List<WebElement> rows = tbody.findElements(By.tagName("tr"));
        if (rows.isEmpty()) {
            throw new AssertionError("No sales entries found in sales table to delete.");
        }

        WebElement latest = rows.getFirst();
        WebElement deleteBtn = latest.findElement(deleteBtnIcon);
        deleteBtn.click();

        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Driver.getDriver().switchTo().alert().accept();
        } catch (Exception ignored) {
            // ignore if alert not present or already handled
        }
        wait.until(ExpectedConditions.stalenessOf(latest));
    }

    public int getPlantStockFromPlantsTableByName(String plantName) {

        WebDriverWait wait = NavigationHelper.getWait();
        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.table")));

        // ===== FIND HEADER INDEXES =====
        List<WebElement> headers = table.findElements(By.cssSelector("thead tr th"));

        int nameColIndex = -1;
        int stockColIndex = -1;

        for (int i = 0; i < headers.size(); i++) {
            String headerText = headers.get(i).getText().trim();
            headerText = headerText.toLowerCase();
            if (headerText.contains("name")) {
                nameColIndex = i;
            }
            if (headerText.contains("stock")) {
                stockColIndex = i;
            }
        }

        if (nameColIndex == -1 || stockColIndex == -1) {
            throw new AssertionError("Name or Stock column not found in Plants table.");
        }

        // ===== FIND ROW BY PLANT NAME =====
        List<WebElement> rows = table.findElements(By.cssSelector("tbody tr"));

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));

            String currentName = cells.get(nameColIndex).getText().trim();

            if (currentName.equalsIgnoreCase(plantName.trim())) {
                String stockText = cells.get(stockColIndex).getText().trim();
                return Integer.parseInt(stockText.replaceAll("[^0-9]", ""));
            }
        }
        throw new AssertionError(
                "Plant with name '" + plantName + "' not found in Plants table."
        );
    }

    // --- SALES USER FEATURES ---

    public boolean isSalesPageHeaderVisible() {
        try {
            WebDriverWait wait = NavigationHelper.getWait();
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(salesPageHeader));
            return header.isDisplayed() && header.getText().trim().equalsIgnoreCase("Sales");
        } catch (Exception e) {
            return false;
        }
    }

    public String getSalesPageHeaderText() {
        WebDriverWait wait = NavigationHelper.getWait();
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(salesPageHeader));
        return header.getText().trim();
    }

    public boolean isSalesTableDisplayedWithColumns(String... expectedColumns) {
        WebDriverWait wait = NavigationHelper.getWait();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(salesTable));
            List<WebElement> headers = Driver.getDriver().findElements(salesTableHeaders);

            for (String expectedColumn : expectedColumns) {
                boolean found = false;
                for (WebElement header : headers) {
                    if (header.getText().trim().equalsIgnoreCase(expectedColumn.trim())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPaginationControlsVisible() {
        try {
            List<WebElement> pagination = Driver.getDriver().findElements(paginationElement);
            return !pagination.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickColumnHeader(String columnName) {
        WebDriverWait wait = NavigationHelper.getWait();
        List<WebElement> headers = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(salesTableHeaders));

        for (WebElement header : headers) {
            if (header.getText().trim().equalsIgnoreCase(columnName.trim())) {
                header.click();
                // Wait for sorting to take effect
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return;
            }
        }
        throw new AssertionError("Column header '" + columnName + "' not found");
    }

    public boolean isColumnSortedAlphabetically(String columnName) {
        List<String> values = getColumnValues(columnName);
        if (values.size() < 2) return true;

        // Check if sorted ascending (A-Z)
        boolean ascending = true;
        for (int i = 0; i < values.size() - 1; i++) {
            if (values.get(i).compareToIgnoreCase(values.get(i + 1)) > 0) {
                ascending = false;
                break;
            }
        }
        // Check if sorted descending (Z-A)
        boolean descending = true;
        for (int i = 0; i < values.size() - 1; i++) {
            if (values.get(i).compareToIgnoreCase(values.get(i + 1)) < 0) {
                descending = false;
                break;
            }
        }

        return ascending || descending;
    }

    public boolean isColumnSortedNumerically(String columnName) {
        List<String> values = getColumnValues(columnName);
        if (values.size() < 2) return true;

        // Parse values to numbers
        List<Double> numbers = new java.util.ArrayList<>();
        for (String value : values) {
            String numStr = value.replaceAll("[^0-9.-]", "");
            if (!numStr.isEmpty()) {
                numbers.add(Double.parseDouble(numStr));
            }
        }
        if (numbers.size() < 2) return true;

        // Check if sorted ascending
        boolean ascending = true;
        for (int i = 0; i < numbers.size() - 1; i++) {
            if (numbers.get(i) > numbers.get(i + 1)) {
                ascending = false;
                break;
            }
        }

        // Check if sorted descending
        boolean descending = true;
        for (int i = 0; i < numbers.size() - 1; i++) {
            if (numbers.get(i) < numbers.get(i + 1)) {
                descending = false;
                break;
            }
        }
        return ascending || descending;
    }

    private List<String> getColumnValues(String columnName) {
        List<String> values = new java.util.ArrayList<>();
        WebDriverWait wait = NavigationHelper.getWait();

        // Find column index
        List<WebElement> headers = Driver.getDriver().findElements(salesTableHeaders);
        int columnIndex = -1;
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getText().trim().equalsIgnoreCase(columnName.trim())) {
                columnIndex = i;
                break;
            }
        }

        if (columnIndex == -1) return values;
        // Get values from that column
        try {
            WebElement tbody = wait.until(ExpectedConditions.visibilityOfElementLocated(salesTableTbody));
            List<WebElement> rows = tbody.findElements(By.tagName("tr"));

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (columnIndex < cells.size()) {
                    values.add(cells.get(columnIndex).getText().trim());
                }
            }
        } catch (Exception e) {
            // Return empty list if table is empty
        }

        return values;
    }

    public boolean isNoSalesMessageDisplayed() {
        try {
            List<WebElement> messages = Driver.getDriver().findElements(noSalesMessage);
            return !messages.isEmpty() && messages.getFirst().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteAllSalesRecordsIfAny() {
        WebDriverWait wait = NavigationHelper.getWait();
        try {
            WebElement tbody = wait.until(ExpectedConditions.visibilityOfElementLocated(salesTableTbody));
            List<WebElement> rows = tbody.findElements(By.tagName("tr"));

            for (WebElement row : rows) {
                WebElement deleteBtn = row.findElement(deleteBtnIcon);
                deleteBtn.click();
                try {
                    wait.until(ExpectedConditions.alertIsPresent());
                    Driver.getDriver().switchTo().alert().accept();
                } catch (Exception ignored) {
                    // ignore if alert not present or already handled
                }
                wait.until(ExpectedConditions.stalenessOf(row));
            }
        } catch (Exception e) {
            // No sales records found, nothing to delete
        }
    }
}

