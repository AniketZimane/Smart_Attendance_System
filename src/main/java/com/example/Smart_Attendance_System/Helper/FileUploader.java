package com.example.Smart_Attendance_System.Helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploader {

    final String DIR = new ClassPathResource("static/assets/img/").getFile().getAbsolutePath();

    public FileUploader() throws IOException {
    }


    public boolean uploadFile(MultipartFile file, String fileNameNew)
    {
        boolean result = false;

        try {
            Files.copy(file.getInputStream(), Paths.get(DIR + File.separator + fileNameNew), StandardCopyOption.REPLACE_EXISTING);
            result = true;
        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        }

        return result;
    }
}
