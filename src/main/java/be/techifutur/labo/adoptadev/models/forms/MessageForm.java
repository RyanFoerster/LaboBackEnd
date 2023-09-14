package be.techifutur.labo.adoptadev.models.forms;

import lombok.Data;

@Data
public class MessageForm {

    private String message;

    private Long receptorId;

    private Long emitterId;
}
