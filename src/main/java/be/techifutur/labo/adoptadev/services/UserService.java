package be.techifutur.labo.adoptadev.services;

import be.techifutur.labo.adoptadev.models.entities.*;

import java.util.List;

public interface UserService {

    void devRegister(Dev dev);

    void recruiterRegister(Recruiter recruiter);

    Recruiter getOneRecruiter(Long id);

    List<User> getAllDev();
    Dev getOneDev(Long id);

    Recruiter getRecByConfirmationToken(String confirmationToken);

    Dev getDevByConfirmationToken(String confirmationToken);

    void updateRecruiter(Long id, Recruiter recruiter);
    void updateRecruiterCompany(Long id, Company company);
    void updateCompanyAddress(Long id, Address address);

    void updateDev(Long id, Dev dev);

    Dev saveDev(Dev dev);
    void updateDevCvPath(Long devId, String cvPath);


    void updateDevAddress(Long id, Address address);


    void updateRecruiterPassword(Long id, Recruiter recruiter);

    void updateDevPassword(Long id, Dev dev);

    void deleteRecruiter(Long id);

    void deleteDev(Long id);

    String login(String username, String password);

}
