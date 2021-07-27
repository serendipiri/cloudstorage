package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialTests extends AuthenticationTests {

    private HomePage loginAndNavigateCredentials() {
        signup();
        login();
        HomePage homePage = new HomePage(driver);
        homePage.navCredentials();
        return homePage;
    }

    @Test
    @Order(4)
    public void addCredential() {

        String url = "http://localhost:8080/home";
        String username = "serendipity";
        String password = "121212";
        HomePage homePage = loginAndNavigateCredentials();

        //There is no Credential at first
        Assertions.assertEquals(0, homePage.getCredentialListSize());

        // Add a new Credential
        new WebDriverWait(driver, 20);
        homePage.createCredential(url, username, password);

        //List is not empty anymore
        Assertions.assertEquals(1, homePage.getCredentialListSize());
        Assertions.assertTrue(homePage.credentialExists(url, username));
    }


    @Test
    @Order(5)
    public void editCredential() {

        String url = "http://localhost:8080/dome";
        String username = "serenity";
        String password = "121212";
        String editedUrl = "http://localhost:8080/some";
        String editedUsername = "cerence";
        String editedPassword = "121212";

        HomePage homePage = loginAndNavigateCredentials();

        // Add a new Credential
        new WebDriverWait(driver, 20);
        homePage.createCredential(url, username, password);

        // Then edit it..
        new WebDriverWait(driver, 20);
        homePage.editCredential(url, username, password, editedUrl, editedUsername, editedPassword);
        Assertions.assertTrue(homePage.credentialExists(editedUrl, editedUsername));

    }

    @Test
    @Order(6)
    public void deleteCredential() {

        String url = "http://localhost:8080/come";
        String username = "cerenity";
        String password = "121212";

        HomePage homePage = loginAndNavigateCredentials();

        // Add a new Credential
        new WebDriverWait(driver, 20);
        homePage.createCredential(url, username, password);

        // Then delete it..
        new WebDriverWait(driver, 20);
        Assertions.assertTrue(homePage.credentialExists(url, username));
        homePage.deleteCredential(url, username);
        Assertions.assertFalse(homePage.credentialExists(url, username));

    }

}
