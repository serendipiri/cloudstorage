package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getFiles(Integer userId) {
        return fileMapper.fileList(userId);
    }

    public File getFileById(Integer userId, Integer fileId) {
        return fileMapper.getFileById(userId, fileId);
    }

    public boolean isFileNameAvailable(String fileName, Integer userId) {
        return fileMapper.getFileByName(fileName, userId) == null;
    }

    public void createFile(MultipartFile multipartFile, Integer userId) {
        try {
            int added = fileMapper.addFile(new File(null, multipartFile.getOriginalFilename(),
                    multipartFile.getContentType(),multipartFile.getSize()+"", userId,
                    multipartFile.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer deleteFile(Integer fileId) {
        return fileMapper.deleteFile(fileId);
    }
}
