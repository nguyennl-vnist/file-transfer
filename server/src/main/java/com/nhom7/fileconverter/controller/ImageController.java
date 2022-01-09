package com.nhom7.fileconverter.controller;

import com.itextpdf.text.DocumentException;
import com.nhom7.fileconverter.response.FileResponse;
import com.nhom7.fileconverter.service.ImageConversions;
import com.nhom7.fileconverter.service.PDFConversions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/image-converter")
public class ImageController {
    @Autowired
    private ImageConversions imageConversions;
    @PostMapping("/toPDF")
    public ResponseEntity<FileResponse> toPDFFile(@RequestParam("file") MultipartFile file) throws IOException, DocumentException {
        ByteArrayOutputStream outputStream = imageConversions.parsePDF(file.getBytes());
        return new ResponseEntity<FileResponse>(new FileResponse(outputStream.toByteArray()), HttpStatus.OK);
    }
}
