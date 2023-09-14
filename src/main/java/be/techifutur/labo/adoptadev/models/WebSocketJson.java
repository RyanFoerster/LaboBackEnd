package be.techifutur.labo.adoptadev.models;

import be.techifutur.labo.adoptadev.models.entities.User;
import lombok.Data;

@Data
public class WebSocketJson {
    private String message;
    private User emitter;
    private User receptor;
}
