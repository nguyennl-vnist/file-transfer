package com.nhom7.fileconverter.service;

import java.io.*;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
//30
@Service
public class DocxConversions {
	public ByteArrayOutputStream parsePDF(byte[] bytes) {
		InputStream is = null;
		ByteArrayOutputStream out = null;
		try {
			is = new ByteArrayInputStream(bytes);
			out = new ByteArrayOutputStream();
			long start = System.currentTimeMillis();
			// 1) Load DOCX into XWPFDocument
			XWPFDocument document = new XWPFDocument(is);
			// 2) Prepare Pdf options
			PdfOptions options = PdfOptions.create();
			// 3) Convert XWPFDocument to Pdf
			PdfConverter.getInstance().convert(document, out, options);
			System.out.println(
					"File converted to a PDF file in :: " + (System.currentTimeMillis() - start) + " milli seconds");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return out;
	}
}
