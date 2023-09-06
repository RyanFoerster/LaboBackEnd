package be.techifutur.labo.adoptadev.services.Impl;

import be.techifutur.labo.adoptadev.exceptions.ResourceNotFoundException;
import be.techifutur.labo.adoptadev.exceptions.UniqueViolationException;
import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.Recruiter;
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
    public void updateRecruiter(Long id, Recruiter recruiter) {

    }

    @Override
    public void updateDev(Long id, Dev dev) {

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
