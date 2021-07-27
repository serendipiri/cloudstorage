package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {

    private WebDriver driver;
    private JavascriptExecutor jse;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        jse = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }


    @FindBy(id ="logout-button")
    private WebElement logoutButton;


    /* NOTE */
    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(name = "addNewNoteButton")
    private WebElement addNewNoteBtn;

    @FindBy(id = "noteSubmitButton")
    private WebElement noteSubmitBtn;

    @FindBy(id = "note-title")
    private WebElement noteTitleText;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionText;

    @FindBy(id = "noteList")
    private List<WebElement> noteList;
    /* NOTE */


    /* CREDENTIAL */
    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(name = "credentialNewNoteButton")
    private WebElement addNewCredentialBtn;

    @FindBy(id = "credentialSubmitBtn")
    private WebElement credentialSubmitBtn;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credentialList")
    private List<WebElement> credentialList;
    /* CREDENTIAL */


    public void logout() {
        jse.executeScript("arguments[0].click()", logoutButton);
    }

    public void navNotes() {
        jse.executeScript("arguments[0].click()", notesTab);
    }

    public void navCredentials() {
        jse.executeScript("arguments[0].click()", credentialTab);
    }


    public void createNote(String title, String description) {

        jse.executeScript("arguments[0].click()", addNewNoteBtn);

        new WebDriverWait(driver, 6).until(ExpectedConditions.visibilityOf(noteTitleText));
        new WebDriverWait(driver, 6).until(ExpectedConditions.visibilityOf(noteDescriptionText));
        jse.executeScript("arguments[0].value='" + title + "';", noteTitleText);
        jse.executeScript("arguments[0].value='" + description + "';", noteDescriptionText);
        jse.executeScript("arguments[0].click()", noteSubmitBtn);

    }

    public int getNoteListSize() {
        return noteList.size();
    }

    public boolean noteExists(String title, String descp) {

        WebElement noteElement = getNoteElement(title, descp);
        return noteElement != null;

    }


    private WebElement getNoteElement(String title, String descp) {
        return  noteList.stream()
                .filter(note -> title.equals(note.findElement(By.id("row-noteTitle")).getText())
                        && descp.equals(note.findElement(By.id("row-noteDesc")).getText()))
                .findAny()
                .orElse(null);
    }


    public void editNote(String title, String descp,
                         String editedTitle, String editedDescp) {

        WebElement noteElement = getNoteElement(title, descp);
        jse.executeScript("arguments[0].click()", noteElement.findElement(By.className("edit-note-btn")));

        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteTitleText));
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteDescriptionText));
        jse.executeScript("arguments[0].value='" + editedTitle + "';", noteTitleText);
        jse.executeScript("arguments[0].value='" + editedDescp + "';", noteDescriptionText);
        jse.executeScript("arguments[0].click()", noteSubmitBtn);

    }


    public void deleteNote(String title, String descp) {

        WebElement noteElement = getNoteElement(title, descp);
        jse.executeScript("arguments[0].click()", noteElement.findElement(By.className("delete-note-btn")));

    }


    public void createCredential(String url, String username, String password) {

        jse.executeScript("arguments[0].click()", addNewCredentialBtn);

        new WebDriverWait(driver, 6).until(ExpectedConditions.visibilityOf(credentialUrl));
        new WebDriverWait(driver, 6).until(ExpectedConditions.visibilityOf(credentialUsername));
        new WebDriverWait(driver, 6).until(ExpectedConditions.visibilityOf(credentialPassword));
        jse.executeScript("arguments[0].value='" + url + "';", credentialUrl);
        jse.executeScript("arguments[0].value='" + username + "';", credentialUsername);
        jse.executeScript("arguments[0].value='" + password + "';", credentialPassword);
        jse.executeScript("arguments[0].click()", credentialSubmitBtn);

    }

    public int getCredentialListSize() {
        return credentialList.size();
    }

    public boolean credentialExists(String url, String username) {

        WebElement credentialElement = getCredentialElement(url, username);
        return credentialElement != null;

    }


    private WebElement getCredentialElement(String url, String username) {
        return  credentialList.stream()
                .filter(credential -> url.equals(credential.findElement(By.id("row-credentialUrl")).getText())
                        && username.equals(credential.findElement(By.id("row-credentialUsername")).getText()))
                .findAny()
                .orElse(null);
    }


    public void editCredential(String url, String username, String password,
                         String editedUrl, String editedUsername, String editedPassword) {

        WebElement credentialElement = getCredentialElement(url, username);
        jse.executeScript("arguments[0].click()", credentialElement.findElement(By.className("edit-credential-btn")));

        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(credentialUrl));
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(credentialUsername));
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(credentialPassword));
        jse.executeScript("arguments[0].value='" + editedUrl + "';", credentialUrl);
        jse.executeScript("arguments[0].value='" + editedUsername + "';", credentialUsername);
        jse.executeScript("arguments[0].value='" + editedPassword + "';", credentialPassword);
        jse.executeScript("arguments[0].click()", credentialSubmitBtn);

    }


    public void deleteCredential(String url, String username) {

        WebElement credentialElement = getCredentialElement(url, username);
        jse.executeScript("arguments[0].click()", credentialElement.findElement(By.className("delete-credential-btn")));

    }
}
