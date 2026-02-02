package com.itqa.assignment.pages;

import org.openqa.selenium.By;
import com.itqa.assignment.utilities.Driver;
import com.itqa.assignment.utilities.ConfigReader;

public class LoginPage {
    // 1. Define Locators as 'By' objects
    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginBtn = By.cssSelector("button[type='submit']");
    private final By inputError = By.className("invalid-feedback");
    private final By globalError = By.cssSelector(".alert.alert-danger");

    // 2. Navigation Method
    public void visit() {
        Driver.getDriver().get(ConfigReader.getProperty("ui.url") + "/login");
    }

    // 3. Action Method (Direct interaction)
    public void enterUsername(String username) {
        Driver.getDriver().findElement(usernameInput).clear();
        Driver.getDriver().findElement(usernameInput).sendKeys(username);
    }

    public void enterPassword(String password) {
        Driver.getDriver().findElement(passwordInput).clear();
        Driver.getDriver().findElement(passwordInput).sendKeys(password);
    }

    public void clickLoginBtn() {
        Driver.getDriver().findElement(loginBtn).click();
    }

    public void submitLogin(String username, String password) {
        // Find and interact directly using the Driver utility
        Driver.getDriver().findElement(usernameInput).clear();
        Driver.getDriver().findElement(usernameInput).sendKeys(username);

        Driver.getDriver().findElement(passwordInput).clear();
        Driver.getDriver().findElement(passwordInput).sendKeys(password);

        Driver.getDriver().findElement(loginBtn).click();
    }

    // 4. Verification Methods
    public String getGlobalErrorMessage() {
        return Driver.getDriver().findElement(globalError).getText();
    }

    public boolean isInputErrorDisplayed() {
        return Driver.getDriver().findElement(inputError).isDisplayed();
    }
}
