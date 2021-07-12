package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView() {
        return "signup";
    }

    @PostMapping
    public String signup(@ModelAttribute User user, Model model) {

        String error = null;

        if (!userService.isUsernameAvailable(user.getUsername())) {
            error = "The user with this username is already exists.";
        } else {
            int added = userService.addUser(user);
            if (added < 1) {
                error = "There was an error signing you up. Please try again.";
            }
        }

        handleErrors(model, error);

        return "signup";
    }

    private void handleErrors(Model model, String error) {
        if (error == null) {
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", error);
        }
    }

}
