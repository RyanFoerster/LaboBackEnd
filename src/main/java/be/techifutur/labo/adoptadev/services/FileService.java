package be.techifutur.labo.adoptadev.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void save(MultipartFile file);
    Resource getFile(String fileName);
}
