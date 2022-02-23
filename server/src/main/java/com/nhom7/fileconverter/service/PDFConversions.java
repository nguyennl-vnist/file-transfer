package com.nhom7.fileconverter.service;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import org.springframework.stereotype.Service;

@Service
public class PDFConversions {

	public ByteArrayOutputStream parseDocx(byte[] bytes) throws IOException, DocumentException {
		XWPFDocument doc = new XWPFDocument();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PdfReader reader = new PdfReader(bytes);
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			TextExtractionStrategy strategy =
					parser.processContent(i, new SimpleTextExtractionStrategy());
			String text = strategy.getResultantText();

			XWPFParagraph p = doc.createParagraph();
			XWPFRun run = p.createRun();
			run.setText(text);
			run.addBreak(BreakType.PAGE);
		}
//		FileOutputStream fileOutputStream = new FileOutputStream("test.docx");
//		doc.write(fileOutputStream);
		doc.write(outputStream);
		return outputStream;


	}

	public ByteArrayOutputStream parseTxt(byte[] bytes) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream("test.pdf");
		fileOutputStream.write(bytes);
		File f = new File("test.pdf");
		String parsedText;
		PDFParser parser;
		ByteArrayOutputStream outputStream = null;
//		InputStream inputStream = null;
		try {
//			inputStream = new ByteArrayInputStream(bytes);
			outputStream = new ByteArrayOutputStream();
			parser = new PDFParser(new RandomAccessFile(f, "r"));
			parser.parse();
			COSDocument cosDoc = parser.getDocument();
			PDFTextStripper pdfStripper = new PDFTextStripper();
			PDDocument pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
			PrintWriter pw = new PrintWriter(outputStream);
			pw.print(parsedText);
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outputStream;

	}

	public List<ByteArrayOutputStream> parseImage(byte[] bytes) throws IOException {
		// khai báo document 
		PDDocument document;
		// khai báo outputStream
		ByteArrayOutputStream outputStream = null;

		FileOutputStream fileOutputStream = new FileOutputStream("test.pdf");
		fileOutputStream.write(bytes);
		File f = new File("test.pdf");

		List<ByteArrayOutputStream> byteArrayOutputStreams = new ArrayList<>();
		try {
			document = PDDocument.load(f);
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			for (int page = 0; page < document.getNumberOfPages(); ++page) {
				outputStream = new ByteArrayOutputStream();
				BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

				ImageIOUtil.writeImage(bim,"png",outputStream);
//				ImageIOUtil.writeImage(bim,"1",outputStream,300);
				byteArrayOutputStreams.add(outputStream);
			}
			document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return byteArrayOutputStreams;
	}
}
