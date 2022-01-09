package com.nhom7.fileconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.sound.sampled.AudioFormat;

@SpringBootApplication
public class FileconverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileconverterApplication.class, args);
    }
}
