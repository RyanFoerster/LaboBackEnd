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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
public class ChatController {

    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageRepository messageRepository;
    private final MatchRepository matchRepository;

    public ChatController(UserRepository userRepository,
                          SimpMessagingTemplate simpMessagingTemplate,
                          MessageRepository messageRepository,
                          MatchRepository matchRepository)
    {
        this.userRepository = userRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageRepository = messageRepository;
        this.matchRepository = matchRepository;
    }

    @EventListener
    public void handleSessionConnectEvent(SessionConnectEvent event) {
        log.info("Un nouveau client s'est connecté");
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        log.info("Un client s'est déconnecté");
    }

    private final String PRIVATE_REPLY_QUEUE = "/topic/private-reply";

    @Transactional
    @MessageMapping("/private-message")
    public void handlePrivateMessage(MessageForm privateMessage) {
        Long matchId = privateMessage.getMatchId();
        String emitter = privateMessage.getEmitter();
        String messageText = privateMessage.getMessage();

        User emitterUser = userRepository.findByUsername(emitter)
                .orElseThrow(() -> new IllegalArgumentException("Émetteur introuvable"));

        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Match introuvable"));

        User receptorUser = (privateMessage.getEmitter().equals(match.getDev().getUsername()))
                ? match.getRecruiter()
                : match.getDev();

        Message message = new Message();
        message.setReceptor(receptorUser);
        message.setEmitter(emitterUser);
        message.setMessage(messageText);
        message.setMatch(match);
        message.setCreatedAt(LocalDateTime.now());

        message = messageRepository.save(message);

        match.getMessages().add(message);
        receptorUser.getMessagesReceptor().add(message);
        emitterUser.getMessagesEmitter().add(message);

        matchRepository.save(match);
        userRepository.saveAll(List.of(receptorUser, emitterUser));

        simpMessagingTemplate.convertAndSendToUser(
                "string",
                PRIVATE_REPLY_QUEUE,
                MessageDTO.toDTO(message)
        );

        simpMessagingTemplate.convertAndSendToUser(
                "stringRec",
                PRIVATE_REPLY_QUEUE,
                MessageDTO.toDTO(message)
        );
    }


}
