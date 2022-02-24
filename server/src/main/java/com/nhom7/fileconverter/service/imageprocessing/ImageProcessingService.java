package com.nhom7.fileconverter.service.imageprocessing;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
@Service
public class ImageProcessingService {
    public ByteArrayOutputStream writeTextOn(byte[] bytes) {
        BufferedImage img = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        File f = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("input.png");
            fileOutputStream.write(bytes);
            f = new File("input.png");
            img = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e);
        }
        BufferedImage temp = new BufferedImage(img.getWidth(),
                img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics graphics = temp.getGraphics();
        graphics.drawImage(img, 0, 0, null);
        graphics.setFont(new Font("Arial", Font.PLAIN, 80));
        graphics.setColor(new Color(255, 0, 0, 40));
        String watermark = "WaterMark generated";
        graphics.drawString(watermark, img.getWidth() / 5,
                img.getHeight() / 3);
        graphics.dispose();
        byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(temp, "png", byteArrayOutputStream);

            return byteArrayOutputStream;
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    public ByteArrayOutputStream toNegativeColor(byte[] bytes) {
        BufferedImage image = null;
        File file = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("input.png");
            fileOutputStream.write(bytes);
            file = new File("input.png");
            image = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println(e);
        }
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                p = (a << 24) | (r << 16) | (g << 8) | b;
                image.setRGB(x, y, p);
            }
        }
        try {
            ImageIO.write(image, "png", byteArrayOutputStream);
            System.out.println("Successfully converted a colored image into a negative image");
            return byteArrayOutputStream;
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    public ByteArrayOutputStream mirrorImage(byte[] bytes){
        BufferedImage img1 = null;
        File file = null;

        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream("input.png");
            fileOutputStream.write(bytes);
            file = new File("input.png");
            img1 = ImageIO.read(file);
        }
        catch(IOException e)
        {
            System.out.println("Error: " + e);
        }
        int width = img1.getWidth();
        int height = img1.getHeight();
        BufferedImage img2 = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++)
        {
            for (int lx = 0, rx = width - 1; lx < width; lx++, rx--)
            {
                int p = img1.getRGB(lx, y);
                img2.setRGB(rx, y, p);
            }
        }
        // save mirror image
        try
        {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(img2, "png", byteArrayOutputStream);
            System.out.println("Successfully created a mirror image at the given path");
            return byteArrayOutputStream;
        }
        catch(IOException e)
        {
            System.out.println("Error: " + e);
        }
        return null;
    }

}
