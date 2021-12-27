package com.nhom7.fileconverter.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

public class ImageConversions {
	
	public static void main(String[] args) {
		parsePDF("Test.jpg");
	}
	public static void parsePDF(String fileName) {
		Document document = new Document();
//		String input = fileName + "." + extension;
		String output = "Test" + ".pdf";
		FileInputStream fis = null;
		FileOutputStream fos;
		try {
			fis = new FileInputStream(fileName);
			fos = new FileOutputStream(output);
			PdfWriter writer = PdfWriter.getInstance(document, fos);
			writer.open();
			document.open();
			document.add(Image.getInstance(fileName));
			document.close();
			writer.close();
		} catch (IOException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
