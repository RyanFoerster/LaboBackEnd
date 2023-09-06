package be.techifutur.labo.adoptadev.services;

import be.techifutur.labo.adoptadev.models.entities.Projet;

import java.util.List;

public interface ProjetService {

    Long add(Projet projet);

    void register(Long devId, Long projetId);

    void unregister(Long devId, Long projetId);

    List<Projet> getAllProjects();

}
