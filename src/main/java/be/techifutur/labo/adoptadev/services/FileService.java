package be.techifutur.labo.adoptadev.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String save(MultipartFile file, String subDirectory);
    Resource getFile(String fileName, String subDirectory);
}
