package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    FileService fileService;
    UserService userService;

    public HomeController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping
    public String getFileList(Authentication authentication, Model model) {
        Integer userId = userService.getUserId(authentication.getName());
        List<File> list = fileService.getFiles(userId);
        if (list.isEmpty()) {
            //TODO: "No files have been uploaded yet."
        }
        model.addAttribute("fileList", list);
        return "home";
    }

    @PostMapping
    public String post() {
        return "home";
    }
}
