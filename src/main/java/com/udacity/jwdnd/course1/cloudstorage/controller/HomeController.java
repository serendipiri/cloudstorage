package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    FileService fileService;
    UserService userService;
    NoteService noteService;
    CredentialService credentialService;

    public HomeController(FileService fileService, UserService userService,
                          NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String getLists(Authentication authentication, Model model)
    {
        Integer userId = userService.getUserId(authentication.getName());

        model.addAttribute("fileList", fileService.getFiles(userId));
        model.addAttribute("noteList", noteService.getNotes(userId));
        model.addAttribute("credentialList", credentialService.getCredentials(userId));

        //After Login..
        if (model.getAttribute("activeTab") == null) {
            model.addAttribute("activeTab", "files");
        }
        return "home";
    }

    @PostMapping
    public String post() {
        return "home";
    }

}
