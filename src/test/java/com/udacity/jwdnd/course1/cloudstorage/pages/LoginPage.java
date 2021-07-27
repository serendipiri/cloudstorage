package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id ="inputUsername")
    private WebElement inputUsername;

    @FindBy(id ="inputPassword")
    private WebElement inputPassword;

    @FindBy(id ="submit-button")
    private WebElement submitButton;


    public void login(String username, String password) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].value='" + username + "';", inputUsername);
        jse.executeScript("arguments[0].value='" + password + "';", inputPassword);
        jse.executeScript("arguments[0].click();", submitButton);
    }

}
