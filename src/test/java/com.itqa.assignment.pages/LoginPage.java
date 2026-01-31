package com.itqa.assignment.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.itqa.assignment.utilities.Driver;
import com.itqa.assignment.utilities.ConfigReader;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver = Driver.getDriver();
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Explicit wait

    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginBtn = By.cssSelector("button[type='submit']");
    private final By inputError = By.className("invalid-feedback");
    private final By globalError = By.cssSelector(".alert.alert-danger");

    public void visit() {
        driver.get(ConfigReader.getProperty("ui.url") + "/login");
    }

    public void submitLogin(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).clear();
        driver.findElement(usernameInput).sendKeys(username);

        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).clear();
        driver.findElement(passwordInput).sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
    }

    public String getGlobalErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(globalError)).getText();
    }

    public boolean isInputErrorDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(inputError)).isDisplayed();
    }
}
