package com.nhom7.fileconverter.service;//package com.nhom7.convert_file;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.tomcat.util.http.fileupload.FileItem;
//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
//import org.springframework.web.multipart.MultipartFile;
//
//public class UploadFile {
//	public void uploadFile(MultipartFile multipartFile) {
//		// get path upload folder
//				String root = uploadFolder();
//				File file = new File(root);
//				if (file.exists() == false) {
//					file.mkdirs();
//				}
//				List<FileItem> containFileItems = new ArrayList<FileItem>(); // chua cac file != null,de ghi ra folder
//				ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
//				boolean video = false, image = false;// xac dinh file dang nao.
//				int countImage = 0, countVideo = 0; // get so luong image,video
//				int filesSize = 0; // tong size cua cac file upload
//				List<FileItem> list = new ArrayList<FileItem>();
//
//				try {
//
//					list = servletFileUpload.parseRequest(request);
//					for (FileItem fileItem : list) {
//						
//						
//					}
//					}
//						
//	}
//	public String uploadFolder() {
//		String root = System.getProperty("user.dir") + "\\uploadFiles";
//		System.out.println("root = " + root);
//		return root;
//
//	}
//}
