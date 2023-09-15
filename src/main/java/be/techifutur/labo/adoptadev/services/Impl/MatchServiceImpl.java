package be.techifutur.labo.adoptadev.services.Impl;

import be.techifutur.labo.adoptadev.models.entities.*;
import be.techifutur.labo.adoptadev.models.enums.Role;
import be.techifutur.labo.adoptadev.repositories.MatchRepository;
import be.techifutur.labo.adoptadev.repositories.UserRepository;
import be.techifutur.labo.adoptadev.services.MatchService;
import be.techifutur.labo.adoptadev.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchServiceImpl implements MatchService {

    private final UserService userService;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    public MatchServiceImpl(UserService userService, MatchRepository matchRepository,
                            UserRepository userRepository) {
        this.userService = userService;
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Long create(Long devId, Long recruiterId) {

        Dev dev = userService.getOneDev(devId);
        Recruiter recruiter = userService.getOneRecruiter(recruiterId);

        if(matchRepository.existsByDevAndRecruiter(dev, recruiter)){
            throw new RuntimeException("Match already exist");
        }


        Match match = new Match();
        match.setDev(dev);
        match.setRecruiter(recruiter);

        return matchRepository.save(match).getId();
    }

    @Override
    public Optional<Match> getOne(Long devId, Long recruiterId) {

        Dev dev = userService.getOneDev(devId);
        Recruiter recruiter = userService.getOneRecruiter(recruiterId);

        return matchRepository.findByDevAndRecruiter(dev, recruiter);
    }

    @Override
    public List<Match> getAll() {
        return matchRepository.findAll();
    }

    @Override
    public Match update(Long id, Match match) {
        return null;
    }

    @Override
    public void delete(Long devId, Long recruiterId) {

        Dev dev = userService.getOneDev(devId);
        Recruiter recruiter = userService.getOneRecruiter(recruiterId);

        matchRepository.deleteByDevAndRecruiter(dev, recruiter);
    }

    @Override
    public List<Match> getMatchByUser(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        boolean isRecruiter = user.getRole() == Role.RECRUITER;

        return isRecruiter ? matchRepository.findByRecruiterId(user.getId()) : matchRepository.findByDevId(user.getId());
    }
}
