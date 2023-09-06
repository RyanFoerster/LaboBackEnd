package be.techifutur.labo.adoptadev.services.Impl;


import be.techifutur.labo.adoptadev.models.entities.Projet;
import be.techifutur.labo.adoptadev.repositories.ProjetRepository;
import be.techifutur.labo.adoptadev.services.ProjetService;
import org.springframework.stereotype.Service;

@Service
public class ProjetServiceImpl implements ProjetService {

    private final ProjetRepository projetRepository;

    public ProjetServiceImpl(ProjetRepository projetRepository) {
        this.projetRepository = projetRepository;
    }


    @Override
    public Long add(Projet projet) {
        return null;
    }

    @Override
    public void register(Long devId, Long projetId) {

    }

    @Override
    public void unregister(Long devId, Long porjetId) {

    }
}
