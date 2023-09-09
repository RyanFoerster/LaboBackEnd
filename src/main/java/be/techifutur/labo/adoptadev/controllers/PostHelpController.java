package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.models.dtos.PostHelpDTO;
import be.techifutur.labo.adoptadev.models.entities.Comment;
import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.PostHelp;
import be.techifutur.labo.adoptadev.models.enums.VoteType;
import be.techifutur.labo.adoptadev.models.forms.CommentForm;
import be.techifutur.labo.adoptadev.models.forms.PostHelpForm;
import be.techifutur.labo.adoptadev.repositories.PostHelpRepository;
import be.techifutur.labo.adoptadev.services.CommentService;
import be.techifutur.labo.adoptadev.services.PostHelpService;
import be.techifutur.labo.adoptadev.services.VoteService;
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
    private final CommentService commentService;
    private final VoteService voteService;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final PostHelpRepository postHelpRepository;


    public PostHelpController(PostHelpService postHelpService,
                              CommentService commentService,
                              VoteService voteService,
                              JwtUtil jwtUtil,
                              UserDetailsService userDetailsService,
                              PostHelpRepository postHelpRepository) {
        this.postHelpService = postHelpService;
        this.commentService = commentService;
        this.voteService = voteService;

        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.postHelpRepository = postHelpRepository;
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

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        postHelpService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comment/{id:[0-9]+}")
    public ResponseEntity<?> addComment(@PathVariable Long id, @RequestBody @Valid CommentForm form, Authentication authentication){

        String devName = authentication.getName();
        Comment comment = form.toEntity();
        PostHelp postHelp = postHelpService.getOne(id);
        comment.setPost(postHelp);
        Long commentId = commentService.add(comment, devName);
        postHelp.getComments().add(commentService.findOne(commentId));
        postHelpService.update(id, postHelp);


        return ResponseEntity.noContent().build();

    }

    @PostMapping("/comment/{commentId}/vote")
    public ResponseEntity<?> addVote(@PathVariable Long commentId, @RequestParam VoteType voteType, Authentication authentication) {

        String devName = authentication.getPrincipal().toString();
        Dev dev = (Dev)userDetailsService.loadUserByUsername(devName); // ! pour pouvoir get l'id

        voteService.addVote(commentId, dev.getId(), voteType);
        return ResponseEntity.noContent().build();
    }



}
