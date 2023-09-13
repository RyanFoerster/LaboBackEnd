package be.techifutur.labo.adoptadev.configs;

import be.techifutur.labo.adoptadev.handler.ChatHandler;
import be.techifutur.labo.adoptadev.repositories.MessageRepository;
import be.techifutur.labo.adoptadev.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public WebSocketConfig(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler(), "/chat").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler chatHandler(){
        return new ChatHandler(this.messageRepository, this.userRepository);
    }
}
