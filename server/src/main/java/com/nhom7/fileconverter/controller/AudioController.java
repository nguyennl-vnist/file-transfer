package com.nhom7.fileconverter.controller;

import com.itextpdf.text.DocumentException;
import com.nhom7.fileconverter.response.FileResponse;
import com.nhom7.fileconverter.service.ImageConversions;
import com.nhom7.fileconverter.service.TrimmingAudio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/trimming-audio")
public class AudioController {
    @PostMapping("")
    public ResponseEntity<FileResponse> trimmingAudio(@RequestParam("file") MultipartFile file,@RequestParam("start") long start,@RequestParam("end") long end) throws IOException, DocumentException, UnsupportedAudioFileException {
//        ByteArrayOutputStream outputStream = imageConversions.parsePDF(file.getBytes());
//        return new ResponseEntity<FileResponse>(new FileResponse(outputStream.toByteArray()), HttpStatus.OK);
        InputStream audioSrc = new ByteArrayInputStream(file.getBytes());
        InputStream bufferedIn = new BufferedInputStream(audioSrc);
        AudioInputStream music = AudioSystem.getAudioInputStream(bufferedIn);
//		music = AudioSystem.getAudioInputStream();
		music = new TrimmingAudio(music.getFormat(), music, start, end);
        File output = new File("out.wav");
		AudioSystem.write(music, AudioFileFormat.Type.WAVE, output);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream oos = new ByteArrayOutputStream();

        byte[] buf = new byte[8192];

        InputStream is = new FileInputStream(output);

        int c = 0;

        while ((c = is.read(buf, 0, buf.length)) > 0) {
            oos.write(buf, 0, c);
            oos.flush();
        }
        FileOutputStream nhap = new FileOutputStream("nhap.wav");
        nhap.write(oos.toByteArray());
        oos.close();
        System.out.println("stop");
        is.close();

        return new ResponseEntity<FileResponse>(new FileResponse(oos.toByteArray()), HttpStatus.OK);


    }
}
