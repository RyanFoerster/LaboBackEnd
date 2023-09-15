package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.models.dtos.MessageDTO;
import be.techifutur.labo.adoptadev.models.entities.*;
import be.techifutur.labo.adoptadev.models.enums.Role;
import be.techifutur.labo.adoptadev.models.forms.MessageForm;
import be.techifutur.labo.adoptadev.repositories.MatchRepository;
import be.techifutur.labo.adoptadev.repositories.MessageRepository;
import be.techifutur.labo.adoptadev.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
public class ChatController {

    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageRepository messageRepository;
    private final MatchRepository matchRepository;

    public ChatController(UserRepository userRepository, SimpMessagingTemplate simpMessagingTemplate, MessageRepository messageRepository, MatchRepository matchRepository) {
        this.userRepository = userRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageRepository = messageRepository;
        this.matchRepository = matchRepository;
    }

    @EventListener
    public void handleSessionConnectEvent(SessionConnectEvent session) {
        System.out.println("event = " + session.toString());
    }

    @MessageMapping("/private-message")
    public void handlePrivateMessage(MessageForm privateMessage) {
        Long matchId = privateMessage.getReceptorId();
        String emitter = privateMessage.getEmitter();
        String message = privateMessage.getMessage();

        User emitterObject = userRepository.findByUsername(emitter).orElseThrow();

        Match match = matchRepository.findById(matchId).orElseThrow();

        User receptorObject;

        if(privateMessage.getEmitter().equals(match.getDev().getUsername())){
            receptorObject = match.getRecruiter();
        }else{
            receptorObject = match.getDev();
        }

        Message messageObject = new Message();
        messageObject.setReceptor(receptorObject);
        messageObject.setEmitter(emitterObject);
        messageObject.setMessage(message);
        messageObject.setMatch(match);
        messageObject.setCreatedAt(LocalDateTime.now());

        messageObject = messageRepository.save(messageObject);

        match.getMessages().add(messageObject);
        matchRepository.save(match);

        receptorObject.getMessagesReceptor().add(messageObject);
        emitterObject.getMessagesEmitter().add(messageObject);
        userRepository.saveAll(List.of(receptorObject, emitterObject));

        // Diffuse le message uniquement au destinataire spécifié
        simpMessagingTemplate.convertAndSendToUser(
                receptorObject.getUsername(),
                "/queue/private-reply",
                MessageDTO.toDTO(messageObject)
        );

        simpMessagingTemplate.convertAndSendToUser(
                emitterObject.getUsername(),
                "/queue/private-reply",
                MessageDTO.toDTO(messageObject)
        );
    }

}
