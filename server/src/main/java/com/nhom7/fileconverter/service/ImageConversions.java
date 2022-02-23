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
		// khai báo document để copy từ document sang output
		Document document = new Document();
		// khai báo output
		ByteArrayOutputStream output = new ByteArrayOutputStream();;
		try {
			// PdfWriter để hỗ trợ việc viết pdf ra ouput (công cụ để chuyển đổi có đầu vào là 1 document trắng và output)
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
