package com.nhom7.fileconverter.service;

import java.io.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

@Service
public class ImageConversions {
	public ByteArrayOutputStream parsePDF(byte[] bytes) {
		Document document = new Document();
		ByteArrayOutputStream output = null;
		try {
//			input = new ByteArrayInputStream(bytes);
			output = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, output);
			writer.open();
			document.open();
			document.add(Image.getInstance(bytes));
			document.close();
			writer.close();
		} catch (IOException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
}
