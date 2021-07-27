package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NoteTests extends AuthenticationTests {

    private HomePage loginAndNavigateNotes() {
        signup();
        login();
        HomePage homePage = new HomePage(driver);
        homePage.navNotes();
        return homePage;
    }


    @Test
    @Order(4)
    public void addNote() throws InterruptedException {

        String title = "Test First Note";
        String descp = "Test First Note Description";
        HomePage homePage = loginAndNavigateNotes();

        //There is no notes at first
        Assertions.assertEquals(0, homePage.getNoteListSize());

        // Add a new Note
        new WebDriverWait(driver, 20);
        homePage.createNote(title, descp);

        //List is not empty anymore
        Assertions.assertEquals(1, homePage.getNoteListSize());
        Assertions.assertTrue(homePage.noteExists(title, descp));
    }


    @Test
    @Order(5)
    public void editNote() throws InterruptedException {

        String title = "Test Second Note";
        String descp = "Test Second Note Description";
        String editedTitle = "Test Second Note - Edited";
        String editedDescp = "Test Second Note Description - Edited";

        HomePage homePage = loginAndNavigateNotes();

        // Add a new Note
        new WebDriverWait(driver, 20);
        homePage.createNote(title, descp);

        // Then edit it..
        new WebDriverWait(driver, 20);
        homePage.editNote(title, descp, editedTitle, editedDescp);
        Assertions.assertTrue(homePage.noteExists(editedTitle, editedDescp));

    }

    @Test
    @Order(6)
    public void deleteNote() {

        String title = "Test Note";
        String descp = "Test Note";

        HomePage homePage = loginAndNavigateNotes();

        // Add a new Note
        new WebDriverWait(driver, 20);
        homePage.createNote(title, descp);

        // Then delete it..
        new WebDriverWait(driver, 20);
        Assertions.assertTrue(homePage.noteExists(title, descp));
        homePage.deleteNote(title, descp);
        Assertions.assertFalse(homePage.noteExists(title, descp));

    }

}
