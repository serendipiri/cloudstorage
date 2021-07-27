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

    /* CREDENTIAL */


    public void logout() {
        jse.executeScript("arguments[0].click()", logoutButton);
    }

    public void navNotes() {
        jse.executeScript("arguments[0].click()", notesTab);
    }


    public void createNote(String title, String description) {

        jse.executeScript("arguments[0].click()", addNewNoteBtn);

        new WebDriverWait(driver, 6).until(ExpectedConditions.visibilityOf(noteTitleText));
        new WebDriverWait(driver, 6).until(ExpectedConditions.visibilityOf(noteDescriptionText));
        jse.executeScript("arguments[0].value='" + title + "';", noteTitleText);
        jse.executeScript("arguments[0].value='" + description + "';", noteDescriptionText);
        jse.executeScript("arguments[0].click()", noteSubmitBtn);

    }

    public int getListSize() {

        for (WebElement noteElement : noteList) {
            String title = noteElement.findElement(By.id("row-noteTitle")).getText();
            String description = noteElement.findElement(By.id("row-noteDesc")).getText();
        }
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
                         String editedTitle, String editedDescp) throws InterruptedException {

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
}
