package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.models.entities.Message;
import be.techifutur.labo.adoptadev.repositories.MessageRepository;
import be.techifutur.labo.adoptadev.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Controller
@Slf4j
public class ChatController {

    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageRepository messageRepository;

    public ChatController(UserRepository userRepository, SimpMessagingTemplate simpMessagingTemplate, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageRepository = messageRepository;
    }

    @EventListener
    public void handleSessionConnectEvent(SessionConnectEvent event){
        System.out.println("event = " + event.getUser().getName());
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public String send(String message, SessionConnectEvent sessionConnectEvent){
        log.info("Ici");
        System.out.println("ici");
        return message;
//        User emitter = (User) sessionConnectEvent.getUser();
//        User receptor = userRepository.findById(2L).orElseThrow();
//        log.info("receptor {}", receptor.getUsername());
//        log.info("emitter {}", emitter.getUsername());
//        message.setEmitter(emitter);
//        message.setReceptor(receptor);
//        emitter.getMessagesEmitter().add(message);
//        receptor.getMessagesReceptor().add(message);
//        log.info("message {}", message.getMessage());
//        simpMessagingTemplate.convertAndSendToUser(receptor.getUsername(), "/topic/messages", message);
    }
}
