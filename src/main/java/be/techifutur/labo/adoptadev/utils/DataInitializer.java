package be.techifutur.labo.adoptadev.utils;

import be.techifutur.labo.adoptadev.configs.SecurityConfig;
import be.techifutur.labo.adoptadev.models.entities.*;
import be.techifutur.labo.adoptadev.models.enums.Role;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import be.techifutur.labo.adoptadev.models.enums.VoteType;
import be.techifutur.labo.adoptadev.repositories.*;
import be.techifutur.labo.adoptadev.services.VoteCommentService;
import be.techifutur.labo.adoptadev.services.VoteSujetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {


    private final SecurityConfig securityConfig;
    private final DevRepository devRepository;
    private final RecruiterRepository recruiterRepository;
    private final PostHelpRepository postHelpRepository;
    private final CommentRepository commentRepository;
    private final VoteSujetService voteSujetService;
    private final VoteCommentService voteCommentService;
    private final MatchRepository matchRepository;
    private final MessageRepository messageRepository;

    public DataInitializer(SecurityConfig securityConfig,
                           DevRepository devRepository,
                           RecruiterRepository recruiterRepository,
                           PostHelpRepository postHelpRepository,
                           CommentRepository commentRepository,
                           VoteSujetService voteSujetService,
                           VoteCommentService voteCommentService, MatchRepository matchRepository, MessageRepository messageRepository) {

        this.securityConfig = securityConfig;
        this.devRepository = devRepository;
        this.recruiterRepository = recruiterRepository;
        this.postHelpRepository = postHelpRepository;
        this.commentRepository = commentRepository;
        this.voteSujetService = voteSujetService;
        this.voteCommentService = voteCommentService;
        this.matchRepository = matchRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
      
        Dev dev = new Dev();
        dev.setUsername("string");
        dev.setPassword(passwordEncoder.encode("Test1234="));
        dev.setFirstName("string");
        dev.setLastName("string");
        dev.setEmail("string@string");
        dev.setRole(Role.DEVELOPER);
        dev.setEnabled(true);
        devRepository.save(dev);

        Dev dev2 = new Dev();
        dev2.setUsername("string2");
        dev2.setPassword(passwordEncoder.encode("Test1234="));
        dev2.setFirstName("string");
        dev2.setLastName("string");
        dev2.setEmail("string2@string");
        dev2.setRole(Role.DEVELOPER);
        dev2.setEnabled(true);
        devRepository.save(dev2);

        Recruiter recruiter = new Recruiter();
        recruiter.setUsername("stringRec");
        recruiter.setPassword(passwordEncoder.encode("Test1234="));
        recruiter.setFirstName("stringP");
        recruiter.setLastName("stringN");
        recruiter.setEmail("string@recruiter");
        recruiter.setRole(Role.RECRUITER);
        recruiter.setDescription("stringRecDesc");
        recruiter.setEnabled(true);
        recruiterRepository.save(recruiter);

        PostHelp postHelp = new PostHelp();
        postHelp.setTitle("Cli Angular");
        postHelp.setDev(dev);
        postHelp.setTechnologyFrontEnd(TechnologyFrontEnd.ANGULAR);
        postHelp.setDescription("J'ai besoin d'aide pour créer un component en utilisant le cli Angular");
        postHelp.setGitHub("https://github.com/LoVanors/finalLab");
        postHelpRepository.save(postHelp);

        Comment comment = new Comment();
        comment.setPost(postHelp);
        comment.setDev(dev2);
        comment.setMessage("Tu devrais essayer en faisant 'ng g c [Nom de ton component]'");
        commentRepository.save(comment);

        voteCommentService.addVote(comment.getId(), dev.getId(), VoteType.UPVOTE);

        voteSujetService.addVote(postHelp.getId(), dev2.getId(), VoteType.UPVOTE);

        Match match = new Match();
        match.setRecruiter(recruiter);
        match.setDev(dev);

        match = matchRepository.save(match);

        Message message = new Message();
        message.setMatch(match);
        message.setEmitterId(recruiter.getId());
        message.setReceptor(dev.getUsername());
        message.setMessage("Salut");
        message.setCreatedAt(LocalDateTime.now());

        Thread.sleep(1000);
        Message message2 = new Message();
        message2.setMatch(match);
        message2.setEmitterId(dev.getId());
        message2.setReceptor(recruiter.getUsername());
        message2.setMessage("Salut ça va ?");
        message2.setCreatedAt(LocalDateTime.now());

        messageRepository.saveAll(List.of(message, message2));
    }
}
