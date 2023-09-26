package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.services.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/{subDirectory}/upload")
    public ResponseEntity<String> upload(@PathVariable String subDirectory, @RequestParam MultipartFile file) {
        try {
            String filePath = fileService.save(file, subDirectory);
            return ResponseEntity.ok(filePath);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading the file: " + e.getMessage());
        }
    }

    @GetMapping("/{subDirectory}/get/{file}")
    public ResponseEntity<?> getFile(@PathVariable String subDirectory, @PathVariable String file) {
        try {
            Resource resource = fileService.getFile(file, subDirectory);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: " + e.getMessage());
        }
    }
}