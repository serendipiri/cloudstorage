package com.udacity.jwdnd.course1.cloudstorage.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ErrorHandler {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileSizeLimitExceeded(MaxUploadSizeExceededException exc, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeTab", "files");
        redirectAttributes.addFlashAttribute("errorMsgFile",
                "File size limit exceeded. You can upload max " + maxFileSize + " size of files.");
        return "redirect:/home";
    }

}
