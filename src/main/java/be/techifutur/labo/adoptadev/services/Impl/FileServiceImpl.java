package be.techifutur.labo.adoptadev.services.Impl;

import be.techifutur.labo.adoptadev.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.path}")
    private String filePath;

    @Override
    public String save(MultipartFile file, String subDirectory) {
        try {
            String dir = System.getProperty("user.dir") + "/" + filePath + "/" + subDirectory;
            File directory = new File(dir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String fullFilePath = dir + "/" + file.getOriginalFilename();
            file.transferTo(new File(fullFilePath));
            return fullFilePath;
        } catch (IOException e) {
            throw new RuntimeException("Could not save the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource getFile(String fileName, String subDirectory) {
        try {
            String dir = System.getProperty("user.dir") + "/" + filePath + "/" + subDirectory;
            Path path = Paths.get(dir + "/" + fileName);
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
