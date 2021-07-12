package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ResultController {

    @GetMapping("/result")
    public String resultView() {
        return "result";
    }

    @PostMapping("/result")
    public String post() {
        return "result";
    }
}
