//package com.nhom7.fileconverter.service;
//
//import java.io.BufferedInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.URL;
//
//public class DownloadFile {
//	public static void main(String[] args) {
//		try (BufferedInputStream inputStream = new BufferedInputStream(new URL(
//				"https://4.bp.blogspot.com/-7yl0bnFz0i0/UkQCBriBnJI/AAAAAAAAAiA/tceMEfJHskk/s1600/anh-dep-hinh-nen-thien-nhien-7.jpg")
//						.openStream());
//				FileOutputStream fileOS = new FileOutputStream("test.jpg")) {
//			byte data[] = new byte[1024];
//			int byteContent;
//			while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
//				fileOS.write(data, 0, byteContent);
//			}
//		} catch (IOException e) {
//			// handles IO exceptions
//		}
//	}
//}
