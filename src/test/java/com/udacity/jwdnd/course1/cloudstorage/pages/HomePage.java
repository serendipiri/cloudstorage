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

    public HomePage(WebDriver driver) {
        this.driver = driver;
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
        this.logoutButton.click();
    }


    public void navNotes() {
        this.notesTab.click();
    }

    public void createNote(String title, String description) throws InterruptedException {

        Thread.sleep(4000);

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", addNewNoteBtn);

        new WebDriverWait(driver, 6).until(ExpectedConditions.visibilityOf(noteTitleText)).sendKeys(title);
        new WebDriverWait(driver, 6).until(ExpectedConditions.visibilityOf(noteDescriptionText)).sendKeys(description);
        jse.executeScript("arguments[0].click()", noteSubmitBtn);
//        noteSubmitBtn.click();

    }

    public int getListSize() throws InterruptedException {
        Thread.sleep(3000);
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

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        Thread.sleep(3000);

        WebElement noteElement = getNoteElement(title, descp);
        noteElement.findElement(By.className("edit-note-btn")).click();


//        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
//                .withTimeout(Duration.ofSeconds(30))
//                .pollingEvery(Duration.ofSeconds(5))
//                .ignoring(NoSuchElementException.class);
//
//        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
//            public WebElement apply(WebDriver driver) {
//                return driver.findElement(By.id("note-title"));
//            }
//        });

        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(noteTitleText)).sendKeys(editedTitle);
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(noteDescriptionText)).sendKeys(editedDescp);

        jse.executeScript("arguments[0].click()", noteSubmitBtn);
//        noteSubmitBtn.click();  NOT WORKING! Why?


    }


}
