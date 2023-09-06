package be.techifutur.labo.adoptadev.models.entities;

import be.techifutur.labo.adoptadev.models.enums.ProjetRole;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Participant {

    @EmbeddedId
    private ParticipantId id;

    @ManyToOne
    @MapsId("devId")
    @JoinColumn(name = "part_dev_id")
    private Dev dev;

    @ManyToOne
    @MapsId("projetId")
    @JoinColumn(name = "part_projet_id")
    private Projet projet;

    @Column(name = "part_role")
    private ProjetRole role;


    @Embeddable
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class ParticipantId implements Serializable {

        private Long devId;
        private Long projetId;

    }

}
