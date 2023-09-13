package be.techifutur.labo.adoptadev.handler;

import be.techifutur.labo.adoptadev.models.WebSocketJson;
import be.techifutur.labo.adoptadev.models.entities.Message;
import be.techifutur.labo.adoptadev.models.entities.User;
import be.techifutur.labo.adoptadev.repositories.MessageRepository;
import be.techifutur.labo.adoptadev.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;

import java.util.List;

@Slf4j
public class ChatHandler implements WebSocketHandler {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public ChatHandler(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = session.getPrincipal().getName();
        log.info("Client {} connected", username);

        // Get the history of messages
        List<Message> messages = messageRepository.findAllByEmitterOrReceptor(userRepository.findByUsername(username).orElseThrow(), null);

        log.info(String.valueOf(messages.size()));

        // Send the history of messages to the client
        for (Message message : messages) {
            session.sendMessage(new TextMessage(message.getMessage()));
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String username = session.getPrincipal().getName();
        String messagePayload = (String) message.getPayload();

        User emitter = userRepository.findByUsername(username).orElseThrow();

        ObjectMapper jsonMapper = new ObjectMapper();
        WebSocketJson webSocketJson = jsonMapper.readValue( messagePayload, WebSocketJson.class );

        String messageText = webSocketJson.getPayload();
        Long recipientId = webSocketJson.getRecipientId();

        if (recipientId != null) {
            User receptor = userRepository.findById(recipientId).orElseThrow();

            String messageToRecipient = username + ": " + messageText;
            // Save the message in the database
            Message messageObject = new Message();
            messageObject.setMessage(messageText);
            messageObject.setEmitter(emitter);
            messageObject.setReceptor(receptor);

            messageObject = messageRepository.save(messageObject);
            emitter.getMessagesEmitter().add(messageObject);
            receptor.getMessagesReceptor().add(messageObject);

            userRepository.save(emitter);
            userRepository.save(receptor);

            // Send the message to the recipient
            session.sendMessage(new TextMessage(messageToRecipient));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String username = session.getPrincipal().getName();
        log.info("Client {} disconnected", username);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
