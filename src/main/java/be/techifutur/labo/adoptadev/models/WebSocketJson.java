package be.techifutur.labo.adoptadev.models;

import lombok.Data;

@Data
public class WebSocketJson {
    private String payload;
    private Long recipientId;
}
