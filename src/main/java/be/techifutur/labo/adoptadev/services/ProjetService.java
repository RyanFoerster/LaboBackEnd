package be.techifutur.labo.adoptadev.services;

import be.techifutur.labo.adoptadev.models.entities.Projet;

public interface ProjetService {

    Long add(Projet projet);

    void register(Long devId, Long projetId);

    void unregister(Long devId, Long porjetId);

}
