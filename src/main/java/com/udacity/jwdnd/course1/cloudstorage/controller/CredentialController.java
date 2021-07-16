package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.CloudStorageException;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    UserService userService;
    CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping
    public String addCredential(@ModelAttribute("credential") Credential credential, Authentication authentication,
                                Model model, RedirectAttributes redirectAttributes) {
        try {

            credential.setUserId(userService.getUserId(authentication.getName()));

//            //Edit credential..
//            if (credential != null && credential.getCredentialId() != null) {
//                return edit(credential, redirectAttributes);
//            }

            credentialService.addCredential(credential);

            handleMessage(false, "Credential added successfully.", redirectAttributes);

        } catch (CloudStorageException e) {
            handleMessage(true, e.getMessage(), redirectAttributes);
        } catch (Exception e) {
            e.printStackTrace();
            handleMessage(true, "Credential could not be added. Something went wrong.", redirectAttributes);
        }

        return "redirect:/home";
    }


    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable Integer id, Authentication authentication, RedirectAttributes redirectAttributes) {

        try {

            Integer userId = userService.getUserId(authentication.getName());

            Integer res = credentialService.deleteCredential(userId, id);
            if (res != null && res < 1) {
                throw new CloudStorageException("Credential could not be deleted!");
            }

            handleMessage(false, "Credential deleted successfully.", redirectAttributes);

        } catch (CloudStorageException e) {
            handleMessage(true, e.getMessage(), redirectAttributes);
        } catch (Exception e) {
            e.printStackTrace();
            handleMessage(true, "Credential could not be deleted. Something went wrong.", redirectAttributes);
        }

        return "redirect:/home";
    }



    public void handleMessage(boolean error, String message, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeTab", "credentials");
        redirectAttributes.addFlashAttribute(error ? "errorMsgCredential" : "successMsgCredential", message);
    }
}
