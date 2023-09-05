package be.techifutur.labo.adoptadev.services;

import be.techifutur.labo.adoptadev.models.entities.User;

public interface UserService {

    void register(User user);

    String login(String username, String password);
}
