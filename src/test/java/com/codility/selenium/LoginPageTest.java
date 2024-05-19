package com.codility.selenium;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginPageTest extends BaseTest {

    @Test
    public void emailInputShouldBeVisible() {
        Boolean displayed = webDriver.findElement(By.id("email-input")).isDisplayed();
        assertTrue("Email input should be displayed", displayed);
    }

    @Test
    public void passwordInputShouldBeVisible() {
        Boolean displayed = webDriver.findElement(By.id("password-input")).isDisplayed();
        assertTrue("Password input should be displayed", displayed);
    }

    @Test
    public void loginButtonShouldBeVisible() {
        Boolean displayed = webDriver.findElement(By.id("login-button")).isDisplayed();
        assertTrue("Login button should be displayed", displayed);
    }

    @Test
    public void validCredentialsScenarioWelcomeMessageShouldBeVisible() {
        webDriver.findElement(By.id("email-input")).sendKeys("login@codility.com");
        webDriver.findElement(By.id("password-input")).sendKeys("password");
        webDriver.findElement(By.id("login-button")).click();
        Boolean displayed = webDriver.findElement(By.cssSelector("#container > div")).isDisplayed();
        assertTrue("Welcome message should be visible", displayed);
    }

    @Test
    public void validCredentialsScenarioWelcomeMessageShouldContainExpectedMessage() {
        webDriver.findElement(By.id("email-input")).sendKeys("login@codility.com");
        webDriver.findElement(By.id("password-input")).sendKeys("password");
        webDriver.findElement(By.id("login-button")).click();
        String actualMessage = webDriver.findElement(By.cssSelector("#container > div")).getText();
        String expectedMessage = "Welcome to Codility";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void invalidCredentialsScenarioErrorMessageShouldBeVisible() {
        webDriver.findElement(By.id("email-input")).sendKeys("unknown@codility.com");
        webDriver.findElement(By.id("password-input")).sendKeys("password");
        webDriver.findElement(By.id("login-button")).click();
        Boolean displayed = webDriver.findElement(By.cssSelector("#messages > div")).isDisplayed();
        assertTrue("Invalid credential error message should be visible", displayed);
    }

    @Test
    public void invalidCredentialsScenarioErrorMessageShouldContainExpectedText() {
        webDriver.findElement(By.id("email-input")).sendKeys("unknown@codility.com");
        webDriver.findElement(By.id("password-input")).sendKeys("password");
        webDriver.findElement(By.id("login-button")).click();
        String actualText = webDriver.findElement(By.cssSelector("#messages > div")).getText();
        String expectedText = "You shall not pass! Arr!";
        assertEquals(expectedText, actualText);
    }

    @Test
    public void invalidEmailFormScenarioErrorMessageShouldBeVisible() {
        webDriver.findElement(By.id("email-input")).sendKeys("invalid");
        webDriver.findElement(By.id("password-input")).sendKeys("password");
        webDriver.findElement(By.id("login-button")).click();
        Boolean displayed = webDriver.findElement(By.cssSelector("#messages > div")).isDisplayed();
        assertTrue("Invalid email error message should be visible", displayed);
    }

    @Test
    public void invalidEmailFormScenarioErrorMessageShouldContainExpectedText() {
        webDriver.findElement(By.id("email-input")).sendKeys("invalid");
        webDriver.findElement(By.id("password-input")).sendKeys("password");
        webDriver.findElement(By.id("login-button")).click();
        String actualText = webDriver.findElement(By.cssSelector("#messages > div")).getText();
        String expectedText = "Enter a valid email";
        assertEquals(expectedText, actualText);
    }

    @Test
    public void emptyEmailAndEmptyPasswordScenarioMessagesShouldBeVisible() {
        webDriver.findElement(By.id("login-button")).click();
        Boolean emailErrorMessageDisplayed = webDriver.findElement(By.cssSelector("#messages > div:nth-child(1)")).isDisplayed();
        Boolean passwordErrorMessageDisplayed = webDriver.findElement(By.cssSelector("#messages > div:nth-child(2)")).isDisplayed();
        assertAll(
                "Both messages should be visible",
                () -> assertTrue(emailErrorMessageDisplayed),
                () -> assertTrue(passwordErrorMessageDisplayed)
        );
    }

    @Test
    public void emptyEmailAndEmptyPasswordScenarioMessagesShouldContainExpectedText() {
        webDriver.findElement(By.id("login-button")).click();
        String emailErrorMessageActualText = webDriver.findElement(By.cssSelector("#messages > div:nth-child(1)")).getText();
        String emailErrorMessageExpectedText = "Email is required";

        String passwordErrorMessageActualText = webDriver.findElement(By.cssSelector("#messages > div:nth-child(2)")).getText();
        String passwordErrorMessageExpectedText = "Password is required";

        assertAll(
                "Both messages should contain expected text",
                () -> assertEquals(emailErrorMessageExpectedText, emailErrorMessageActualText),
                () -> assertEquals(passwordErrorMessageExpectedText, passwordErrorMessageActualText)
        );
    }

    @Test
    public void tabKeyWorksAsExpected() {
        WebElement emailInput = webDriver.findElement(By.id("email-input"));
        WebElement password = webDriver.findElement(By.id("password-input"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        emailInput.click();
        emailInput.sendKeys("login@codility.com");

        Actions actions = new Actions(webDriver);
        actions.sendKeys("\t").perform();

        password.sendKeys("password");

        actions.sendKeys("\t").perform();

        assertTrue("Tab key should reach all elements", loginButton.equals(webDriver.switchTo().activeElement()));
    }

    @Test
    public void enterKeyWorksAsExpected() {
        WebElement emailInput = webDriver.findElement(By.id("email-input"));
        WebElement password = webDriver.findElement(By.id("password-input"));

        emailInput.click();
        emailInput.sendKeys("login@codility.com");

        Actions actions = new Actions(webDriver);
        actions.sendKeys("\t").perform();

        password.sendKeys("password");

        actions.sendKeys("\t").perform();

        actions.sendKeys("\n").perform();

        Boolean welcomeMessageDisplayed = webDriver.findElement(By.cssSelector("#container > div")).isDisplayed();

        assertTrue("Enter key should perform click on login button", welcomeMessageDisplayed);
    }
}
