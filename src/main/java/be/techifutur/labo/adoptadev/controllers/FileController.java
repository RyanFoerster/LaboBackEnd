package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.services.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public void upload(@RequestParam MultipartFile file) {
        fileService.save(file);
    }

    @GetMapping("/get/{file}")
    public ResponseEntity<Resource> getFile(@PathVariable String file) {
        Resource resource = fileService.getFile(file);

        if (resource != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        return ResponseEntity.internalServerError().build();
    }
}