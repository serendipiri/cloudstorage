package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.CloudStorageException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile, Authentication authentication,
                             Model model, RedirectAttributes redirectAttributes) {
        try {

            Integer userId = userService.getUserId(authentication.getName());

            if (multipartFile == null || multipartFile.isEmpty()) {
                throw new CloudStorageException("No file selected to upload!");
            }
            if (!fileService.isFileNameAvailable(multipartFile.getOriginalFilename(), userId)) {
                throw new CloudStorageException("The file with this name is already exists.");
            } else {
                fileService.createFile(multipartFile, userId);
                handleMessage(false, "File uploaded successfully.", redirectAttributes);
            }

        } catch (CloudStorageException e) {
            handleMessage(true, e.getMessage(), redirectAttributes);
        } catch (Exception e) {
            e.printStackTrace();
            handleMessage(true, "File could not be uploaded. Something went wrong.", redirectAttributes);
        }

        return "redirect:/home";
    }

    @PostMapping("/download")
    public ResponseEntity downloadFile(@RequestParam("fileDownloadId") Integer id, Authentication authentication,
                                       RedirectAttributes redirectAttributes) {

        ResponseEntity<byte[]> response = ResponseEntity.notFound().build();

        try {

            Integer userId = userService.getUserId(authentication.getName());

            File file = fileService.getFileById(userId, id);
            if (file == null) {
                throw new CloudStorageException("File not found!");
            }

            response = ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + file.getFileName() + "\"")
                    .body(file.getFileData());

        } catch (CloudStorageException e) {
            handleMessage(true, e.getMessage(), redirectAttributes);
        } catch (Exception e) {
            e.printStackTrace();
            handleMessage(true, "File could not be downloaded. Something went wrong.", redirectAttributes);
        }

        return response; //TODO: how to return error msg to /home page
    }

//    @GetMapping({"/download","/download/{id:.+}"})
//    public String downloadFileGet() {
//        return "redirect:/home?view";
//    }

    @GetMapping("/delete/{id}")
    public String deleteFile(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

        try {

            Integer res = fileService.deleteFile(id);
            if (res != null && res < 1) {
                throw new CloudStorageException("File could not be deleted!");
            }
            handleMessage(false, "File deleted successfully.", redirectAttributes);

        } catch (CloudStorageException e) {
            handleMessage(true, e.getMessage(), redirectAttributes);
        } catch (Exception e) {
            e.printStackTrace();
            handleMessage(true, "File could not be deleted. Something went wrong.", redirectAttributes);
        }

        return "redirect:/home";
    }


    public void handleMessage(boolean error, String message, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(error ? "errorMsg" : "successMsg", message);
    }

}
