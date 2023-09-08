package be.techifutur.labo.adoptadev.controllers.security;

import be.techifutur.labo.adoptadev.models.dtos.PostHelpDTO;
import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.forms.PostHelpForm;
import be.techifutur.labo.adoptadev.repositories.CommentRepository;
import be.techifutur.labo.adoptadev.repositories.DevRepository;
import be.techifutur.labo.adoptadev.repositories.PostHelpRepository;
import be.techifutur.labo.adoptadev.services.PostHelpService;
import be.techifutur.labo.adoptadev.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posthelp")
public class PostHelpController {

    private final PostHelpService postHelpService;

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;


    public PostHelpController(PostHelpService postHelpService,
                              JwtUtil jwtUtil,
                              UserDetailsService userDetailsService) {
        this.postHelpService = postHelpService;

        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }


    @PostMapping
    public ResponseEntity<Long> addPost(@RequestBody @Valid PostHelpForm form, Authentication authentication){

        String devName = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postHelpService.add(form.toEntity(), devName));

    }


    @GetMapping
    public ResponseEntity<List<PostHelpDTO>> findAll(){
        return ResponseEntity.ok(
                postHelpService.getAll().stream().map(PostHelpDTO::toDTO).toList()
        );
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<PostHelpDTO> findOne(@PathVariable Long id){
        return ResponseEntity.ok(PostHelpDTO.toDTO(postHelpService.getOne(id)));
    }

    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<?> update(@PathVariable Long id,  @RequestBody @Valid PostHelpForm form){
        postHelpService.update(id, form.toEntity());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("\"/{id:[0-9]+}\"")
    public ResponseEntity<?> delete(@PathVariable Long id){
        postHelpService.delete(id);
        return ResponseEntity.noContent().build();
    }





}
