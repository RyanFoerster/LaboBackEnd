package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.models.dtos.PostHelpDTO;
import be.techifutur.labo.adoptadev.models.dtos.VoteInfoDTO;
import be.techifutur.labo.adoptadev.models.entities.*;
import be.techifutur.labo.adoptadev.models.enums.VoteType;
import be.techifutur.labo.adoptadev.models.forms.CommentForm;
import be.techifutur.labo.adoptadev.models.forms.PostHelpForm;
import be.techifutur.labo.adoptadev.repositories.PostHelpRepository;
import be.techifutur.labo.adoptadev.services.CommentService;
import be.techifutur.labo.adoptadev.services.PostHelpService;
import be.techifutur.labo.adoptadev.services.VoteCommentService;
import be.techifutur.labo.adoptadev.services.VoteSujetService;
import be.techifutur.labo.adoptadev.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posthelp")
public class PostHelpController {

    private final PostHelpService postHelpService;
    private final CommentService commentService;
    private final VoteCommentService voteCommentService;
    private final VoteSujetService voteSujetService;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final PostHelpRepository postHelpRepository;


    public PostHelpController(PostHelpService postHelpService,
                              CommentService commentService,
                              VoteCommentService voteCommentService,
                              VoteSujetService voteSujetService, JwtUtil jwtUtil,
                              UserDetailsService userDetailsService,
                              PostHelpRepository postHelpRepository) {
        this.postHelpService = postHelpService;
        this.commentService = commentService;
        this.voteCommentService = voteCommentService;
        this.voteSujetService = voteSujetService;

        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.postHelpRepository = postHelpRepository;
    }


    @PostMapping
    public ResponseEntity<Long> addPost(@RequestBody @Valid PostHelpForm form, Authentication authentication) {

        String devName = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postHelpService.add(form.toEntity(), devName));

    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<PostHelpDTO>> findAll() {
        return ResponseEntity.ok(
                postHelpService.getAll().stream().map(PostHelpDTO::toDTO).toList()
        );
    }


    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<PostHelpDTO> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(PostHelpDTO.toDTO(postHelpService.getOne(id)));
    }

    @PutMapping("/{id:[0-9]+}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid PostHelpForm form) {
        postHelpService.update(id, form.toEntity());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id:[0-9]+}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        postHelpService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id:[0-9]+}/vote")
    public ResponseEntity<VoteInfoDTO> addPostVote(@PathVariable Long id, @RequestParam VoteType voteType, Authentication authentication) {
        String devName = authentication.getPrincipal().toString();
        Dev dev = (Dev) userDetailsService.loadUserByUsername(devName);

        VoteSujet voteSujet = voteSujetService.addVote(id, dev.getId(), voteType);

        return ResponseEntity.accepted().body(
                voteSujet != null ?
                        VoteInfoDTO.toDTO(voteSujet)
                        : VoteInfoDTO.toEmptyDTO(postHelpService.getOne(id).getScore())
        );
    }

    @GetMapping("/{id:[0-9]+}/vote")
    public ResponseEntity<VoteInfoDTO> getVotePost(@PathVariable Long id, Authentication authentication) {
        String devName = authentication.getPrincipal().toString();
        Dev dev = (Dev) userDetailsService.loadUserByUsername(devName);
        Optional<VoteSujet> existingVote = voteSujetService.getVote(id, dev.getId());

        VoteInfoDTO voteInfo = VoteInfoDTO.builder()
                .hasVoted(existingVote.isPresent())
                .voteType(existingVote.map(VoteSujet::getVoteType).orElse(null))
                .score(existingVote.map(vote -> vote.getPostHelp().getScore()).orElseGet(() -> postHelpService.getOne(id).getScore()))
                .build();

        return ResponseEntity.ok(voteInfo);
    }

    @GetMapping("/comment/{commentId:[0-9]+}/vote")
    public ResponseEntity<VoteInfoDTO> getCommentVote(@PathVariable Long commentId, Authentication authentication) {
        String devName = authentication.getPrincipal().toString();
        Dev dev = (Dev) userDetailsService.loadUserByUsername(devName);
        Optional<VoteComment> existingVote = voteCommentService.getVote(commentId, dev.getId());

        VoteInfoDTO voteInfo = VoteInfoDTO.builder()
                .hasVoted(existingVote.isPresent())
                .voteType(existingVote.map(VoteComment::getVoteType).orElse(null))
                .score(existingVote.map(vote -> vote.getComment().getScore()).orElseGet(() -> commentService.findOne(commentId).getScore()))
                .build();

        return  ResponseEntity.ok(voteInfo);
    }

    @PostMapping("/comment/{id:[0-9]+}")
    public ResponseEntity<?> addComment(@PathVariable Long id, @RequestBody @Valid CommentForm form, Authentication authentication) {

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
    public ResponseEntity<VoteInfoDTO> addCommentVote(@PathVariable Long commentId, @RequestParam VoteType voteType, Authentication authentication) {

        String devName = authentication.getPrincipal().toString();
        Dev dev = (Dev) userDetailsService.loadUserByUsername(devName); // ! pour pouvoir get l'id

        VoteComment voteComment = voteCommentService.addVote(commentId, dev.getId(), voteType);

        return ResponseEntity.accepted().body(
                voteComment != null ?
                    VoteInfoDTO.toDto( voteComment )
                    : VoteInfoDTO.toEmptyDTO( commentService.findOne(commentId).getScore() )
        );
    }





}
