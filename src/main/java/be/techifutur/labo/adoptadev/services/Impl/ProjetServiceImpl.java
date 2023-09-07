package be.techifutur.labo.adoptadev.services.Impl;


import be.techifutur.labo.adoptadev.exceptions.ResourceAlreadyLinkedException;
import be.techifutur.labo.adoptadev.exceptions.ResourceNotFoundException;
import be.techifutur.labo.adoptadev.exceptions.UniqueViolationException;
import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.Participant;
import be.techifutur.labo.adoptadev.models.entities.Participant.ParticipantId;
import be.techifutur.labo.adoptadev.models.entities.Projet;
import be.techifutur.labo.adoptadev.models.enums.ProjetRole;
import be.techifutur.labo.adoptadev.repositories.DevRepository;
import be.techifutur.labo.adoptadev.repositories.ParticipantRepository;
import be.techifutur.labo.adoptadev.repositories.ProjetRepository;
import be.techifutur.labo.adoptadev.services.ProjetService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjetServiceImpl implements ProjetService {

    private final ProjetRepository projetRepository;
    private final DevRepository devRepository;
    private final ParticipantRepository participantRepository;

    public ProjetServiceImpl(ProjetRepository projetRepository,
                             DevRepository devRepository,
                             ParticipantRepository participantRepository) {
        this.projetRepository = projetRepository;
        this.devRepository = devRepository;
        this.participantRepository = participantRepository;
    }


    @Override
    public Long add(Projet projet, Long devId) {

        projet.setId(null);

        List<String> fieldUniqueErrors = new LinkedList<>();
        if (projetRepository.existsByName(projet.getName())) {
            fieldUniqueErrors.add("name");
            throw new UniqueViolationException(fieldUniqueErrors);
        }

        Dev dev = devRepository.findById(devId).orElseThrow();

        Long projetId = projetRepository.save(projet).getId();


        Participant participant = new Participant();
        ParticipantId participantId = new ParticipantId(devId, projetId);
        participant.setId(participantId);
        participant.setDev(dev);
        participant.setProjet(projet);
        participant.setRole(ProjetRole.PROJET_ADMIN);

        participantRepository.save(participant);

        return projetId;
    }

    @Override
    public void register(Long devId, Long projetId) {

        Dev dev = devRepository.findById(devId).orElseThrow(
                () -> new ResourceNotFoundException(devId, Dev.class)
        );
        Projet projet = projetRepository.findById(projetId).orElseThrow(
                () -> new ResourceNotFoundException(projetId, Projet.class)
        );

        if (projet.getParticipants().stream().anyMatch(
                p -> p.getDev().getId().equals(devId)
        )) {
            throw new ResourceAlreadyLinkedException(Projet.class, projetId, Dev.class, devId);
        }

        Participant participant = new Participant();
        ParticipantId participantId = new ParticipantId(devId, projet.getId());
        participant.setId(participantId);
        participant.setDev(dev);
        participant.setProjet(projet);
        participant.setRole(ProjetRole.PROJET_MEMBER);

        participantRepository.save(participant);

    }

    @Override
    public void unregister(Long devId, Long projetId) {

        Projet projet = projetRepository.findById(projetId).orElseThrow(
                () -> new ResourceNotFoundException(projetId, Projet.class)
        );
        if (projet.getParticipants().stream().noneMatch(
                p -> p.getId().getDevId().equals(devId)
        )) {
            throw new ResourceNotFoundException(devId, Projet.class);
        }

        ParticipantId participantId = new ParticipantId(devId, projetId);
        Participant participant = participantRepository.findById(participantId).orElseThrow(
                () -> new ResourceNotFoundException(participantId, Participant.class)
        );
        participantRepository.delete(participant);
    }

    @Override
    public List<Projet> getAllProjects(Long devId) {
        return projetRepository.findAll().stream()
                .filter(projet -> projet.getParticipants().stream()
                        .anyMatch(participant -> participant.getDev().getId().equals(devId))
                )
                .collect(Collectors.toList());
    }


}
