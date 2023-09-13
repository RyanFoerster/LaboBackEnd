package be.techifutur.labo.adoptadev.controllers;

import be.techifutur.labo.adoptadev.models.entities.Message;
import be.techifutur.labo.adoptadev.models.entities.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/<topicName>")
    public Message sendMessage(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        User emitter = (User) headerAccessor.getSessionAttributes().get("emitter");
        User receptor = (User) headerAccessor.getSessionAttributes().get("receptor");
        String topicName = emitter.getUsername() + "/" + receptor.getUsername();
        chatMessage.setEmitter(emitter);
        chatMessage.setReceptor(receptor);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Message addUser(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("emitter", chatMessage.getEmitter());
        headerAccessor.getSessionAttributes().put("receptor", chatMessage.getReceptor());
        return chatMessage;
    }
}
