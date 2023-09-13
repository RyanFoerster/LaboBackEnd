package be.techifutur.labo.adoptadev.services.Impl;

import be.techifutur.labo.adoptadev.exceptions.ResourceNotFoundException;
import be.techifutur.labo.adoptadev.exceptions.UniqueViolationException;
import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.Recruiter;
import be.techifutur.labo.adoptadev.models.entities.User;
import be.techifutur.labo.adoptadev.repositories.DevRepository;
import be.techifutur.labo.adoptadev.repositories.RecruiterRepository;
import be.techifutur.labo.adoptadev.repositories.UserRepository;
import be.techifutur.labo.adoptadev.services.UserService;
import be.techifutur.labo.adoptadev.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RecruiterRepository recruiterRepository;
    private final DevRepository devRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(
            UserRepository userRepository,
            RecruiterRepository recruiterRepository,
            DevRepository devRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.recruiterRepository = recruiterRepository;
        this.devRepository = devRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void devRegister(Dev dev) {
        Assert.notNull(dev, "user should not be null");

        if (userRepository.existsByUsername(dev.getUsername())) {
            throw new UniqueViolationException("username");
        }

        dev.setPassword(passwordEncoder.encode(dev.getPassword()));

        userRepository.save(dev);
    }

    @Override
    public void recruiterRegister(Recruiter recruiter) {
        Assert.notNull(recruiter, "user should not be null");

        if (userRepository.existsByUsername(recruiter.getUsername())) {
            throw new UniqueViolationException("username");
        }

        recruiter.setPassword(passwordEncoder.encode(recruiter.getPassword()));

        userRepository.save(recruiter);
    }

    @Override
    public Recruiter getOneRecruiter(Long id) {
        return recruiterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Recruiter.class));
    }

    @Override
    public Dev getOneDev(Long id) {
        return devRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id, Dev.class));
    }

    @Override
    public Recruiter getRecByConfirmationToken(String confirmationToken) {
        return recruiterRepository.findByConfirmationToken(confirmationToken)
                .orElseThrow(() -> new RuntimeException("invalid token"));
    }

    @Override
    public Dev getDevByConfirmationToken(String confirmationToken) {
        return devRepository.findByConfirmationToken(confirmationToken)
                .orElseThrow(() -> new RuntimeException("invalid token"));
    }

    @Override
    public void updateRecruiter(Long id, Recruiter recruiter) {
        Recruiter entity = getOneRecruiter(id);

        if (!recruiter.getFirstName().isEmpty() && !recruiter.getFirstName().isBlank())
            entity.setFirstName(recruiter.getFirstName());
        if (!recruiter.getLastName().isEmpty() && !recruiter.getLastName().isBlank())
            entity.setLastName(recruiter.getLastName());
        if (!recruiter.getEmail().isEmpty() && !recruiter.getEmail().isBlank())
            entity.setEmail(recruiter.getEmail());
        if (!recruiter.getDescription().isEmpty() && !recruiter.getDescription().isBlank())
            entity.setDescription(recruiter.getDescription());

        userRepository.save(entity);
    }


    @Override
    public void updateDev(Long id, Dev dev) {
        Dev entity = getOneDev(id);

        if (!dev.getFirstName().isEmpty() && !dev.getFirstName().isBlank())
            entity.setFirstName(dev.getFirstName());
        if (!dev.getLastName().isEmpty() && !dev.getLastName().isBlank())
            entity.setLastName(dev.getLastName());
        if (!dev.getEmail().isEmpty() && !dev.getEmail().isBlank())
            entity.setEmail(dev.getEmail());
        if (!dev.getDescription().isEmpty() && !dev.getDescription().isBlank())
            entity.setDescription(dev.getDescription());
        if (dev.getBirthDate() != null)
            entity.setBirthDate(dev.getBirthDate());
        if (!dev.getTechnologiesBackEnd().isEmpty())
            entity.setTechnologiesBackEnd(dev.getTechnologiesBackEnd());
        if (!dev.getTechnologiesFrontEnd().isEmpty())
            entity.setTechnologiesFrontEnd(dev.getTechnologiesFrontEnd());
        if (!dev.getGitHub().isEmpty() && !dev.getGitHub().isBlank())
            entity.setGitHub(dev.getGitHub());
        if (!dev.getLinkedIn().isEmpty() && !dev.getLinkedIn().isBlank())
            entity.setLinkedIn(dev.getLinkedIn());
        if (!dev.getCv().isEmpty() && !dev.getCv().isBlank())
            entity.setCv(dev.getCv());
        if (!dev.getPseudo().isEmpty() && !dev.getPseudo().isBlank())
            entity.setPseudo(dev.getPseudo());

        userRepository.save(entity);
    }

    @Override
    public void updateRecruiterPassword(Long id, Recruiter recruiter) {
        Recruiter entity = getOneRecruiter(id);

        entity.setPassword(recruiter.getPassword());

        userRepository.save(entity);
    }

    @Override
    public void updateDevPassword(Long id, Dev dev) {
        Dev entity = getOneDev(id);

        entity.setPassword(dev.getPassword());

        userRepository.save(entity);
    }

    @Override
    public void deleteRecruiter(Long id) {
        Recruiter recruiter = getOneRecruiter(id);
        recruiter.setEnabled(false);
        recruiterRepository.save(recruiter);
    }

    @Override
    public void deleteDev(Long id) {
        Dev dev = getOneDev(id);
        dev.setEnabled(false);
        devRepository.save(dev);
    }

    @Override
    public String login(String username, String password) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        return JwtUtil.generateToken(authentication);
    }
}
