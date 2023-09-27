package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.exceptions.ResourceNotFoundException;
import be.techifutur.labo.adoptadev.models.dtos.AddressDTO;

import be.techifutur.labo.adoptadev.models.dtos.DevDTO;
import be.techifutur.labo.adoptadev.models.dtos.UserDTO;
import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.User;
import be.techifutur.labo.adoptadev.models.forms.AddressUpdateForm;

import be.techifutur.labo.adoptadev.models.forms.DevPasswordUpdateForm;
import be.techifutur.labo.adoptadev.models.forms.DevProfileUpdateForm;
import be.techifutur.labo.adoptadev.services.FileService;
import be.techifutur.labo.adoptadev.services.UserService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/dev")
public class DevController {

    private final UserService userService;
    private final FileService fileService;
    private final UserDetailsService userDetailsService;

    public DevController(UserService userService,
                         FileService fileService,
                         UserDetailsService userDetailsService) {
        this.userService = userService;
        this.fileService = fileService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll(){
        return ResponseEntity.ok(userService.getAllDev().stream().map(UserDTO::toDTO).toList());
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<DevDTO> getOne(@PathVariable Long id) {
        Dev dev = userService.getOneDev(id);
        DevDTO body = DevDTO.toDTO(dev);
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{confirmationToken}")
    public ResponseEntity<?> confirmRegister(@PathVariable String confirmationToken) {
        Dev dev = userService.getDevByConfirmationToken(confirmationToken);
        dev.setEnabled(true);
        dev.setConfirmationToken(null);
        userService.updateDev(dev.getId(), dev);
        return ResponseEntity.noContent()
                .build();
    }

    @PutMapping
    public ResponseEntity<DevDTO> update(Authentication authentication, @RequestBody @Valid DevProfileUpdateForm form) {
        System.out.println(form);
        Dev dev = (Dev) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        userService.updateDev(dev.getId(), form.toEntity());
        return ResponseEntity.noContent()
                .build();
    }

    @PatchMapping
    public ResponseEntity<DevDTO> updatePassword(Authentication authentication, @RequestBody @Valid DevPasswordUpdateForm form) {
        Dev dev = (Dev) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        userService.updateDevPassword(dev.getId(), form.toEntity());
        return ResponseEntity.noContent()
                .build();
    }

    @PutMapping("/address")
    public ResponseEntity<AddressDTO> updateDevAddress(Authentication authentication, @RequestBody  AddressUpdateForm form){
        Dev dev = (Dev) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        userService.updateDevAddress(dev.getId(), form.toEntity());

        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping
    public ResponseEntity<?> delete(Authentication authentication) {
        Dev dev = (Dev) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        userService.deleteDev(dev.getId());
        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/{devId}/upload-cv")
    public ResponseEntity<?> uploadCV(@PathVariable Long devId, @RequestParam MultipartFile file) {
        try {
            Dev dev = userService.getOneDev(devId);
            String cvPath = fileService.save(file, "cvs");
            dev.setCv(cvPath);
            userService.saveDev(dev);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{devId}/download-cv")
    public ResponseEntity<?> downloadCV(@PathVariable Long devId) {
        try {
            Dev dev = userService.getOneDev(devId);
            String fullCvPath = dev.getCv();
            Path path = Paths.get(fullCvPath);
            String cvFileName = path.getFileName().toString();
            // Assuming the subdirectory can be derived from the full path,
            // but you might need to adjust this based on your actual file structure
            String subDirectory = path.getParent().getFileName().toString();

            Resource resource = fileService.getFile(cvFileName, subDirectory);

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("File not found.");
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
