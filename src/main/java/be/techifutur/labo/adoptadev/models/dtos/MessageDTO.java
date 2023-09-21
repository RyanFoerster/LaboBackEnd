package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.Message;
import be.techifutur.labo.adoptadev.models.entities.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageDTO {

    private Long id;
    private String message;
    private LocalDateTime createdAt;
    private Long matchId;
//    private String receptor;
//    private String emitter;

    private String receptor;
    private Long emitterId;

    public static MessageDTO toDTO(Message message){
        if( message == null )
            return null;

        return MessageDTO.builder()
                .id( message.getId() )
                .matchId( message.getMatch().getId() )
                .message( message.getMessage() )
                .emitterId( message.getEmitterId() )
                .receptor(  message.getReceptor() )
                .createdAt(message.getCreatedAt())
                .build();
    }

//    @Data
//    @Builder
//    public static class CorrespondantDTO {
//        private String username;
//        private Long userId;
//
//        public static CorrespondantDTO toDTO(User user){
//            if( user == null )
//                return null;
//
//            return CorrespondantDTO.builder()
//                    .userId(user.getId())
//                    .username(user.getUsername())
//                    .build();
//        }
//
//    }

}
