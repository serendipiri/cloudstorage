package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.CloudStorageException;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            Integer existingCredId = credentialService.getCredentialIdCountByUsername(credential);

            if (existingCredId != null
                    && (credential.getCredentialId() == null || !existingCredId.equals(credential.getCredentialId()) )) {
                throw new CloudStorageException("The credential with this username (" + credential.getUsername() + ") is already exists.");
            }

            //Edit credential..
            if (credential != null && credential.getCredentialId() != null) {
                return edit(credential, redirectAttributes);
            }

            // Add a new credential..
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

    private String edit(Credential credential, RedirectAttributes redirectAttributes) {

        try {

            credentialService.editCredential(credential);
            handleMessage(false, "Credential updated successfully.", redirectAttributes);

        } catch (CloudStorageException e) {
            handleMessage(true, e.getMessage(), redirectAttributes);
        } catch (Exception e) {
            e.printStackTrace();
            handleMessage(true, "Credential could not be edited. Something went wrong.", redirectAttributes);
        }
        return "redirect:/home";
    }


    @GetMapping("select/{credentialId}")
    public ResponseEntity<Credential> getEditView (@PathVariable Integer credentialId, Authentication authentication) {

        ResponseEntity<Credential> resp = null;
        try {

            Integer userId = userService.getUserId(authentication.getName());

            Credential credential = credentialService.getCredential(userId, credentialId);
            if (credential == null) {
                throw new CloudStorageException("Credential could not be accessed.");
            }

//            model.addAttribute("error" , "error message???");
            resp = new ResponseEntity<>(credential, HttpStatus.OK);

        }
//        catch (CloudStorageException e) {
//            handleMessage(true, e.getMessage(), redirectAttributes);
//        }
        catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            handleMessage(true, "Credential could not be retrieved. Something went wrong.", redirectAttributes);
        }

        return resp;

    }


    @GetMapping("/delete/{id}")
    public String deleteCredential(@PathVariable Integer id, Authentication authentication, RedirectAttributes redirectAttributes) {

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
