package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.models.WebSocketJson;
import be.techifutur.labo.adoptadev.models.entities.Message;
import be.techifutur.labo.adoptadev.models.entities.User;
import be.techifutur.labo.adoptadev.models.forms.MessageForm;
import be.techifutur.labo.adoptadev.repositories.MessageRepository;
import be.techifutur.labo.adoptadev.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.io.DataInput;
import java.io.IOException;

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
    public void handleSessionConnectEvent(SessionConnectEvent session) {
        System.out.println("event = " + session.toString());
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public void send(MessageForm messageForm) throws IOException {
        User emitter = userRepository.findById(messageForm.getEmitterId()).orElseThrow();
        User receptor = userRepository.findById(messageForm.getReceptorId()).orElseThrow();
        Message message = new Message();
        message.setMessage(messageForm.getMessage());
        message.setEmitter(emitter);
        message.setReceptor(receptor);
        message = messageRepository.save(message);
        emitter.getMessagesEmitter().add(message);
        receptor.getMessagesReceptor().add(message);
        userRepository.save(emitter);
        userRepository.save(receptor);

        ObjectMapper jsonMapper = new ObjectMapper();
        WebSocketJson webSocketJson = jsonMapper.readValue(message.toString(), WebSocketJson.class );

        simpMessagingTemplate.convertAndSend("/topic/messages", webSocketJson);
    }
}
