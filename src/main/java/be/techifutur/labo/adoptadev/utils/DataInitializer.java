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

@Component
public class DataInitializer implements CommandLineRunner {


    private final SecurityConfig securityConfig;
    private final DevRepository devRepository;
    private final RecruiterRepository recruiterRepository;
    private final PostHelpRepository postHelpRepository;
    private final CommentRepository commentRepository;
    private final VoteSujetService voteSujetService;
    private final VoteCommentService voteCommentService;

    public DataInitializer(SecurityConfig securityConfig,
                           DevRepository devRepository,
                           RecruiterRepository recruiterRepository,
                           PostHelpRepository postHelpRepository,
                           CommentRepository commentRepository,
                           VoteSujetService voteSujetService,
                           VoteCommentService voteCommentService)
    {

        this.securityConfig = securityConfig;
        this.devRepository = devRepository;
        this.recruiterRepository = recruiterRepository;
        this.postHelpRepository = postHelpRepository;
        this.commentRepository = commentRepository;
        this.voteSujetService = voteSujetService;
        this.voteCommentService = voteCommentService;
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
        dev.getRoles().add(Role.DEVELOPER);
        devRepository.save(dev);

        Dev dev2 = new Dev();
        dev2.setUsername("string2");
        dev2.setPassword(passwordEncoder.encode("Test1234="));
        dev2.setFirstName("string");
        dev2.setLastName("string");
        dev2.setEmail("string2@string");
        dev2.getRoles().add(Role.DEVELOPER);
        devRepository.save(dev2);

        Recruiter recruiter = new Recruiter();
        recruiter.setUsername("stringRec");
        recruiter.setPassword(passwordEncoder.encode("Test1234="));
        recruiter.setFirstName("stringP");
        recruiter.setLastName("stringN");
        recruiter.setEmail("string@recruiter");
        recruiter.setDescription("stringRecDesc");
        recruiter.getRoles().add(Role.RECRUITER);
        recruiterRepository.save(recruiter);

        PostHelp postHelp = new PostHelp();
        postHelp.setTitle("Cli Angular");
        postHelp.setDev(dev);
        postHelp.setTechnologyFrontEnd(TechnologyFrontEnd.ANGULAR);
        postHelp.setDescription("J'ai besoin d'aide pour créer un component en utilisant le cli Angular");
        postHelp.setGithub("https://github.com/LoVanors/finalLab");
        postHelpRepository.save(postHelp);

        Comment comment = new Comment();
        comment.setPost(postHelp);
        comment.setDev(dev2);
        comment.setMessage("Tu devrais essayer en faisant 'ng g c [Nom de ton component]'");
        commentRepository.save(comment);

        voteCommentService.addVote(comment.getId(), dev.getId(), VoteType.UPVOTE);

        voteSujetService.addVote(postHelp.getId(), dev2.getId(), VoteType.UPVOTE);
    }
}
