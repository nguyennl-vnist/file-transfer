package com.nhom7.fileconverter.service;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZippingFile {
    public static void main(String[] args) throws IOException {
        //Zipping Single File
//        FileOutputStream fos = new FileOutputStream("/Users/Public/compressed.zip");
//        zipFile("/Users/Public/Test.txt",fos);
        //Zipping Multiple Files
//        List<String> srcFiles = Arrays.asList("/Users/Public/Test.txt", "/Users/Public/Test1.txt");
//        FileOutputStream fos1 = new FileOutputStream("/Users/Public/multiCompressed.zip");
//        zipMultipleFiles(srcFiles,fos1);
        //Zipping A Directory
        String sourceFile = "/Users/Public/cao";
        FileOutputStream fos = new FileOutputStream("/Users/Public/dirCompressed.zip");
        zippingDirectory(sourceFile,fos);
    }
    private static void zipFile(String sourceFile,FileOutputStream fos) throws IOException {
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFile);
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        zipOut.close();
        fis.close();
        fos.close();
    }
    private static void zipMultipleFiles(List<String> srcFiles,FileOutputStream fos) throws IOException {
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (String srcFile : srcFiles) {
            File fileToZip = new File(srcFile);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        }
        zipOut.close();
        fos.close();
    }
    private static void zippingDirectory(String sourceFile,FileOutputStream fos) throws IOException {
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFile);
        zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();
    }
    //Zipping A Directory
    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
            }
            zipOut.closeEntry();
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    // Unzipping
}
