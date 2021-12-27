package com.nhom7.fileconverter.service;

import java.io.*;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
@Service
public class TxtConversions {
//	public static void main(String[] args) {
//		parsePDF("Test.txt");
//	}
	public ByteArrayOutputStream parsePDF(byte[] bytes) throws IOException {
		Document pdfDoc = new Document(PageSize.A4);
		ByteArrayOutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(bytes);
			outputStream = new ByteArrayOutputStream();
			PdfWriter.getInstance(pdfDoc, outputStream)
			  .setPdfVersion(PdfWriter.PDF_VERSION_1_7);
			pdfDoc.open();
			Font myfont = new Font();
			myfont.setStyle(Font.NORMAL);
			myfont.setSize(11);
			pdfDoc.add(new Paragraph("\n"));
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String strLine;
			while ((strLine = br.readLine()) != null) {
			    Paragraph para = new Paragraph(strLine + "\n", myfont);
			    para.setAlignment(Element.ALIGN_JUSTIFIED);
			    pdfDoc.add(para);
			}	
			pdfDoc.close();
			br.close();
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileOutputStream fileOutputStream = new FileOutputStream("test.pdf");
		outputStream.writeTo(fileOutputStream);
		return outputStream;
		
	}
}
