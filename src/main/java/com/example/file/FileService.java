package com.example.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileService
{
    void storeFile(MultipartFile file);
}
