package be.techifutur.labo.adoptadev.models.dtos;

import be.techifutur.labo.adoptadev.models.entities.PostHelp;
import be.techifutur.labo.adoptadev.models.entities.VoteSujet;
import be.techifutur.labo.adoptadev.models.enums.VoteType;
import lombok.Builder;
import lombok.Data;

import java.util.stream.Collectors;

@Data @Builder
public class VoteInfoDTO {

    private VoteType voteType;
    private boolean hasVoted;

}
