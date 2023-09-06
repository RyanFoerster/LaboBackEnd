package be.techifutur.labo.adoptadev.repositories;

import be.techifutur.labo.adoptadev.models.entities.Participant;
import be.techifutur.labo.adoptadev.models.entities.Participant.ParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, ParticipantId> {
}
