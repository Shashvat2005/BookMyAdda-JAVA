package com.bookmyadda.booking_backend.controller;

import com.bookmyadda.booking_backend.service.FileStorageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@CrossOrigin("*")
public class FileController {

    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        return fileStorageService.uploadFile(file);
    }
}