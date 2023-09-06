package be.techifutur.labo.adoptadev.services;

import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.Recruiter;

public interface UserService {

    void devRegister(Dev dev);

    void recruiterRegister(Recruiter recruiter);

    String login(String username, String password);

}
