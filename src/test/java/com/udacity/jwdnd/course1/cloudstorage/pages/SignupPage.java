package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    public SignupPage(WebDriver driver) {
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
        this.inputFirstName.sendKeys(firstName);
        this.inputLastName.sendKeys(lastName);
        this.inputUsername.sendKeys(username);
        this.inputPassword.sendKeys(password);
        this.submitButton.click();
    }

}
