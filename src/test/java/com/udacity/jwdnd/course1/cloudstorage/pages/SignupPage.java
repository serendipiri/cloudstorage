package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    private final WebDriver driver;

    public SignupPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id ="inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id ="inputLastName")
    private WebElement inputLastName;

    @FindBy(id ="inputUsername")
    private WebElement inputUsername;

    @FindBy(id ="inputPassword")
    private WebElement inputPassword;

    @FindBy(id ="submit-button")
    private WebElement submitButton;


    public void signup(String firstName, String lastName, String username, String password) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].value='" + firstName + "';", inputFirstName);
        jse.executeScript("arguments[0].value='" + lastName + "';", inputLastName);
        jse.executeScript("arguments[0].value='" + username + "';", inputUsername);
        jse.executeScript("arguments[0].value='" + password + "';", inputPassword);
        jse.executeScript("arguments[0].click();", submitButton);
    }

}
