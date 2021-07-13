package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {

    FileService fileService;
    UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile, Authentication authentication, Model model) {

        Integer userId = userService.getUserId(authentication.getName());

        if (multipartFile == null || multipartFile.isEmpty()) {
            // TODO : "No file selected to upload!"
        }
        if (!fileService.isFileNameAvailable(multipartFile.getName(), userId)) {
            //TODO: "The file with this file name is already exists.";
        }
        else {
            fileService.createFile(multipartFile, userId);
        }

        return "redirect:/home";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity downloadFile(@PathVariable Integer id, Authentication authentication) {

        Integer userId = userService.getUserId(authentication.getName());

        File file = fileService.getFileById(userId, id);
        if (file == null) {
            //TODO: "File not found!"
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + file.getFileName() + "\"")
                .body(file.getFileData());
    }

    @DeleteMapping("/delete")
    public String deleteFile() {

        return "redirect:/home";
    }

}
