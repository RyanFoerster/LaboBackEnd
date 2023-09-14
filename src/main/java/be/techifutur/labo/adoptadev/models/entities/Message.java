package be.techifutur.labo.adoptadev.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @Column(name = "message_content")
    private String message;

    @ManyToOne
    @JoinColumn(name = "emitter_id")
    private User emitter;

    @ManyToOne
    @JoinColumn(name = "receptor_id")
    private User receptor;

    @ManyToOne
    private Match match;

    public Message(String message, User emitter) {
        this.message = message;
        this.emitter = emitter;
    }
}
