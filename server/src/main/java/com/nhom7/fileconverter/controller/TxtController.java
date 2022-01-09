package com.nhom7.fileconverter.controller;

import com.nhom7.fileconverter.response.FileResponse;
import com.nhom7.fileconverter.service.TxtConversions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/txt-converter")
public class TxtController {
	@Autowired
	private TxtConversions txtConversions;

	@PostMapping("/toPDF")
	public ResponseEntity<FileResponse> toPDFFile(@RequestParam("file") MultipartFile file) throws IOException {
		ByteArrayOutputStream outputStream = txtConversions.parsePDF(file.getBytes());
		return new ResponseEntity<FileResponse>(new FileResponse(outputStream.toByteArray()), HttpStatus.OK);
	}

}
