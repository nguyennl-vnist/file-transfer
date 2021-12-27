package com.nhom7.fileconverter.service;

import java.io.*;

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
	public ByteArrayOutputStream parsePDF(MultipartFile multipartFile) throws IOException {
		String fileName = multipartFile.getOriginalFilename();
		Document pdfDoc = new Document(PageSize.A4);
		System.out.println("FileName = "+fileName);
		ByteArrayOutputStream outputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			PdfWriter.getInstance(pdfDoc, outputStream)
			  .setPdfVersion(PdfWriter.PDF_VERSION_1_7);
			pdfDoc.open();
			Font myfont = new Font();
			myfont.setStyle(Font.NORMAL);
			myfont.setSize(11);
			pdfDoc.add(new Paragraph("\n"));
			BufferedReader br = new BufferedReader(new FileReader(multipartFile.getOriginalFilename()));
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
		FileOutputStream fileOutputStream = new FileOutputStream("root/"+fileName.substring(0,fileName.length()-4)+".pdf");
		outputStream.writeTo(fileOutputStream);
		return outputStream;
		
	}
}
