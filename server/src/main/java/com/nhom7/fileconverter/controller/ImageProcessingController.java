package com.nhom7.fileconverter.controller;

import com.itextpdf.text.DocumentException;
import com.nhom7.fileconverter.response.FileResponse;
import com.nhom7.fileconverter.service.ImageConversions;
import com.nhom7.fileconverter.service.imageprocessing.ImageProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/image-processing")
public class ImageProcessingController {

    @Autowired
    private ImageProcessingService imageProcessingService;
    @PostMapping("/write-text")
    public ResponseEntity<FileResponse> writeTextOnImage(@RequestParam("file") MultipartFile file) throws IOException, DocumentException {
        ByteArrayOutputStream outputStream = imageProcessingService.writeTextOn(file.getBytes());
        return new ResponseEntity<FileResponse>(new FileResponse(outputStream.toByteArray()), HttpStatus.OK);
    }
    @PostMapping("/negative-color")
    public ResponseEntity<FileResponse> toNegativeColor(@RequestParam("file") MultipartFile file) throws IOException, DocumentException {
        ByteArrayOutputStream outputStream = imageProcessingService.toNegativeColor(file.getBytes());
        return new ResponseEntity<FileResponse>(new FileResponse(outputStream.toByteArray()), HttpStatus.OK);
    }
    @PostMapping("/mirror-image")
    public ResponseEntity<FileResponse> mirrorImage(@RequestParam("file") MultipartFile file) throws IOException, DocumentException {
        ByteArrayOutputStream outputStream = imageProcessingService.mirrorImage(file.getBytes());
        return new ResponseEntity<FileResponse>(new FileResponse(outputStream.toByteArray()), HttpStatus.OK);
    }
}
