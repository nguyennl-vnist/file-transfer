package com.nhom7.fileconverter.controller;

import com.nhom7.fileconverter.response.FileResponse;
import com.nhom7.fileconverter.service.TxtConversions;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/txt-converter")
public class TxtController {
	@Autowired
	private TxtConversions txtConversions;

	@PostMapping
	public ResponseEntity<FileResponse> toPDFFile(@RequestParam("file") MultipartFile file) throws IOException {
		ByteArrayOutputStream outputStream = txtConversions.parsePDF(file);
		return new ResponseEntity<FileResponse>(new FileResponse(outputStream.toByteArray()), HttpStatus.OK);
	}

}
