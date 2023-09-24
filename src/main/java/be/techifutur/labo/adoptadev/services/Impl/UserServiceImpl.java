package be.techifutur.labo.adoptadev.services.Impl;

import be.techifutur.labo.adoptadev.exceptions.ResourceNotFoundException;
import be.techifutur.labo.adoptadev.exceptions.UniqueViolationException;
import be.techifutur.labo.adoptadev.models.entities.*;
import be.techifutur.labo.adoptadev.repositories.*;
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
    private final AddressRepository addressRepository;
    private final CompanyRepository companyRepository;

    public UserServiceImpl(
            UserRepository userRepository,
            RecruiterRepository recruiterRepository,
            DevRepository devRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            AddressRepository addressRepository,
            CompanyRepository companyRepository
    ) {
        this.userRepository = userRepository;
        this.recruiterRepository = recruiterRepository;
        this.devRepository = devRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.addressRepository = addressRepository;
        this.companyRepository = companyRepository;
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

        userRepository.save(entity);
    }

    @Override
    public void updateRecruiterCompany(Long id, Company company) {
        Recruiter recruiter = getOneRecruiter(id);
        Company entity = recruiter.getCompany();

        if (!company.getName().isBlank()&&!company.getName().isEmpty())
            entity.setName(company.getName());
        if (!company.getDescription().isEmpty()&&!company.getDescription().isBlank())
            entity.setDescription(company.getDescription());

        companyRepository.save(entity);
    }

    @Override
    public void updateCompanyAddress(Long id, Address address) {
        Recruiter recruiter = getOneRecruiter(id);
        Address entity = recruiter.getCompany().getAddress();

        if(!address.getStreet().isEmpty()&&!address.getStreet().isBlank())
            entity.setStreet(address.getStreet());
        if (!address.getNumber().isBlank()&&!address.getNumber().isEmpty())
            entity.setNumber(address.getNumber());
        if (!address.getCity().isEmpty()&&!address.getCity().isBlank())
            entity.setCity(address.getCity());
        if (!address.getZipcode().isBlank()&&!address.getZipcode().isEmpty())
            entity.setZipcode(address.getZipcode());
        if (!address.getCountry().isEmpty()&&!address.getCountry().isBlank())
            entity.setCountry(address.getCountry());

        addressRepository.save(entity);
    }


    @Override
    public void updateDev(Long id, Dev dev) {
        Dev entity = getOneDev(id);

        System.out.println(dev);

        if (!dev.getFirstName().isEmpty() && !dev.getFirstName().isBlank())
            entity.setFirstName(dev.getFirstName());
        if (!dev.getLastName().isEmpty() && !dev.getLastName().isBlank())
            entity.setLastName(dev.getLastName());
        if (!dev.getDescription().isEmpty() && !dev.getDescription().isBlank())
            entity.setDescription(dev.getDescription());
        if (!dev.getTechnologyBackEnds().isEmpty() )
            entity.setTechnologyBackEnds(dev.getTechnologyBackEnds());
        if (!dev.getTechnologyFrontEnds().isEmpty())
            entity.setTechnologyFrontEnds(dev.getTechnologyFrontEnds());
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

        entity.setPassword(passwordEncoder.encode(recruiter.getPassword()));

        userRepository.save(entity);
    }

    @Override
    public void updateDevPassword(Long id, Dev dev) {
        Dev entity = getOneDev(id);

        entity.setPassword(passwordEncoder.encode(dev.getPassword()));

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

    @Override
    public void updateDevAddress(Long id, Address address) {
        Dev dev = getOneDev(id);

        Address entity = dev.getAddress();
        if(!address.getStreet().isEmpty()&&!address.getStreet().isBlank())
            entity.setStreet(address.getStreet());
        if (!address.getNumber().isBlank()&&!address.getNumber().isEmpty())
            entity.setNumber(address.getNumber());
        if (!address.getCity().isEmpty()&&!address.getCity().isBlank())
            entity.setCity(address.getCity());
        if (!address.getZipcode().isBlank()&&!address.getZipcode().isEmpty())
            entity.setZipcode(address.getZipcode());
        if (!address.getCountry().isEmpty()&&!address.getCountry().isBlank())
            entity.setCountry(address.getCountry());

        addressRepository.save(entity);
    }


}
