package com.nhom7.fileconverter.controller;

import com.itextpdf.text.DocumentException;
import com.nhom7.fileconverter.response.FileResponse;
import com.nhom7.fileconverter.service.PDFConversions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/pdf-converter")
public class PDFController {
    @Autowired
    private PDFConversions pdfConversions;
    @PostMapping("/toDocx")
    public ResponseEntity<FileResponse> toDocxFile(@RequestParam("file") MultipartFile file) throws IOException, DocumentException {
        ByteArrayOutputStream outputStream = pdfConversions.parseDocx(file.getBytes());
        return new ResponseEntity<FileResponse>(new FileResponse(outputStream.toByteArray()), HttpStatus.OK);
    }

    @PostMapping("/toTxt")
    public ResponseEntity<FileResponse> toTxtFile(@RequestParam("file") MultipartFile file) throws IOException, DocumentException {
        ByteArrayOutputStream outputStream = pdfConversions.parseTxt(file.getBytes());
        return new ResponseEntity<FileResponse>(new FileResponse(outputStream.toByteArray()), HttpStatus.OK);
    }

    @PostMapping("/toImages")
    public ResponseEntity<List<FileResponse>> toImageFiles(@RequestParam("file") MultipartFile file) throws IOException, DocumentException {
        List<ByteArrayOutputStream> outputStreams = pdfConversions.parseImage(file.getBytes());
        List<FileResponse> fileResponses = new ArrayList<>();
        for (ByteArrayOutputStream b:outputStreams) {
            fileResponses.add(new FileResponse(b.toByteArray()));
        }
        return new ResponseEntity<List<FileResponse>>(fileResponses, HttpStatus.OK);
    }
}
