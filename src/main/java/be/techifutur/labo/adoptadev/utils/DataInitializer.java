package be.techifutur.labo.adoptadev.utils;

import be.techifutur.labo.adoptadev.configs.SecurityConfig;
import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.PostHelp;
import be.techifutur.labo.adoptadev.models.entities.Recruiter;
import be.techifutur.labo.adoptadev.models.enums.Role;
import be.techifutur.labo.adoptadev.models.enums.TechnologyFrontEnd;
import be.techifutur.labo.adoptadev.repositories.DevRepository;
import be.techifutur.labo.adoptadev.repositories.PostHelpRepository;
import be.techifutur.labo.adoptadev.repositories.RecruiterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {


    private final SecurityConfig securityConfig;
    private final DevRepository devRepository;
    private final RecruiterRepository recruiterRepository;
    private final PostHelpRepository postHelpRepository;

    public DataInitializer(SecurityConfig securityConfig,
                           DevRepository devRepository,
                           RecruiterRepository recruiterRepository,
                           PostHelpRepository postHelpRepository
    ) {

        this.securityConfig = securityConfig;
        this.devRepository = devRepository;
        this.recruiterRepository = recruiterRepository;
        this.postHelpRepository = postHelpRepository;
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
        recruiterRepository.save(recruiter);

        PostHelp postHelp = new PostHelp();
        postHelp.setDev(dev);
        postHelp.setTechnologyFrontEnd(TechnologyFrontEnd.ANGULAR);
        postHelp.setDescription("J'ai besoin d'aide pour créer un component en utilisant le cli Angular");
        postHelp.setGithub("https://github.com/LoVanors/finalLab");
        postHelpRepository.save(postHelp);
    }
}
