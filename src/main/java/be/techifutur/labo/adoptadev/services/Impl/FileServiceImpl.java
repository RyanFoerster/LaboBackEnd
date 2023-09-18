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
public class FileServiceImpl implements FileService{


    @Value("${file.path}")
    private String filePath;

    @Override
    public void save(MultipartFile file) {
        String dir = System.getProperty("user.dir") + "/" + filePath;
        try {
            file.transferTo(new File(dir + "/" + file.getOriginalFilename()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Resource getFile(String fileName){
        String dir = System.getProperty("user.dir") + "/" + filePath + "/" + fileName;
        Path path = Paths.get(dir);

        try {
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
