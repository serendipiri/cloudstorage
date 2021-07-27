package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.CloudStorageException;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;

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
    public void downloadFile(@RequestParam("fileDownloadId") Integer id, HttpServletResponse response, Authentication authentication,
                             RedirectAttributes redirectAttributes) {

        try {

            Integer userId = userService.getUserId(authentication.getName());

            File file = fileService.getFileById(userId, id);
            if (file == null) {
                throw new CloudStorageException("File not found!");
            }

            response.setContentType(file.getContentType());
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFileName() + "\"");
            response.getOutputStream().write(file.getFileData());


        } catch (CloudStorageException e) {
            handleMessage(true, e.getMessage(), redirectAttributes);
        } catch (Exception e) {
            e.printStackTrace();
            handleMessage(true, "File could not be downloaded. Something went wrong.", redirectAttributes);
        }

    }

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
        redirectAttributes.addFlashAttribute("activeTab", "files");
        redirectAttributes.addFlashAttribute(error ? "errorMsgFile" : "successMsgFile", message);
    }

}
