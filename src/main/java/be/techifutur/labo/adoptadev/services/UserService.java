package be.techifutur.labo.adoptadev.services;

import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.Recruiter;
import be.techifutur.labo.adoptadev.models.entities.User;

public interface UserService {

    void devRegister(Dev dev);

    void recruiterRegister(Recruiter recruiter);

    Recruiter getOneRecruiter(Long id);

    Dev getOneDev(Long id);

    void updateRecruiter(Long id, Recruiter recruiter);

    void updateDev(Long id, Dev dev);
    void updateRecruiterPassword(Long id, Recruiter recruiter);

    void updateDevPassword(Long id, Dev dev);
    void deleteRecruiter(Long id);

    void deleteDev(Long id);

    String login(String username, String password);

}
