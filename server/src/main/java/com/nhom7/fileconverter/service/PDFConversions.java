package com.nhom7.fileconverter.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
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

public class PDFConversions {
	public static void main(String[] args) {
		String fileName = "Test.pdf";
//		parseHTML(fileName);
	}

	public static void parseDocx(String fileName) {
		XWPFDocument doc = new XWPFDocument();
		String pdf = fileName;
		PdfReader reader;
		try {
			reader = new PdfReader(pdf);
			PdfReaderContentParser parser = new PdfReaderContentParser(reader);
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				TextExtractionStrategy strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
				String text = strategy.getResultantText();
				XWPFParagraph p = doc.createParagraph();
				XWPFRun run = p.createRun();
				run.setText(text);
				run.addBreak(BreakType.PAGE);
			}
			FileOutputStream out = new FileOutputStream("Test.docx");
			doc.write(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void parseTxt(String fileName) {
		File f = new File(fileName);
		String parsedText;
		PDFParser parser;
		try {
			parser = new PDFParser(new RandomAccessFile(f, "r"));
			parser.parse();
			COSDocument cosDoc = parser.getDocument();
			PDFTextStripper pdfStripper = new PDFTextStripper();
			PDDocument pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
			PrintWriter pw = new PrintWriter("Test.txt");
			pw.print(parsedText);
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void parseImage(String fileName, String extension) {
		PDDocument document;
		try {
			document = PDDocument.load(new File(fileName));
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			for (int page = 0; page < document.getNumberOfPages(); ++page) {
				BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
				ImageIOUtil.writeImage(bim, String.format("Test-%d.%s", page + 1, extension), 300);
			}
			document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
