package be.techifutur.labo.adoptadev.services.Impl;


import be.techifutur.labo.adoptadev.exceptions.ResourceNotFoundException;
import be.techifutur.labo.adoptadev.models.entities.*;
import be.techifutur.labo.adoptadev.models.enums.VoteType;
import be.techifutur.labo.adoptadev.repositories.DevRepository;
import be.techifutur.labo.adoptadev.repositories.PostHelpRepository;
import be.techifutur.labo.adoptadev.repositories.VoteSujetRepository;
import be.techifutur.labo.adoptadev.services.VoteSujetService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteSujetServiceImpl implements VoteSujetService {

    private final VoteSujetRepository voteSujetRepository;
    private final PostHelpRepository postHelpRepository;
    private final DevRepository devRepository;

    public VoteSujetServiceImpl(VoteSujetRepository voteSujetRepository, PostHelpRepository postHelpRepository, DevRepository devRepository) {
        this.voteSujetRepository = voteSujetRepository;
        this.postHelpRepository = postHelpRepository;
        this.devRepository = devRepository;
    }


    @Override
    public VoteSujet addVote(Long postHelpId, Long devId, VoteType voteType) {
        PostHelp postHelp = postHelpRepository.findById(postHelpId)
                .orElseThrow(() -> new ResourceNotFoundException(postHelpId, PostHelp.class));

        Dev dev = devRepository.findById(devId)
                .orElseThrow(() -> new ResourceNotFoundException(devId, Dev.class));


        Optional<VoteSujet> existingVote = voteSujetRepository.findByPostHelpAndDev(postHelp, dev);
        // Va servir pour savoir si un vote existe déjà ou non

        if (existingVote.isPresent()) {
            if (existingVote.get().getVoteType() == voteType) {
                // annuler le vote, donc soustraire le score actuel
                adjustScore(postHelp, existingVote.get().getVoteType() == VoteType.UPVOTE ? -1 : 1);
                voteSujetRepository.delete(existingVote.get());
                return null;
            } else {
                // changer le type de vote, donc ajuster le score en conséquence
                adjustScore(postHelp, existingVote.get().getVoteType() == VoteType.UPVOTE ? -2 : 2);
                existingVote.get().setVoteType(voteType);
                return voteSujetRepository.save(existingVote.get());
            }
        } else {
            // nouveau vote, donc ajouter au score
            adjustScore(postHelp, voteType == VoteType.UPVOTE ? 1 : -1);

            VoteSujet voteSujet = new VoteSujet();
            voteSujet.setDev(dev);
            voteSujet.setPostHelp(postHelp);
            voteSujet.setVoteType(voteType);

            return voteSujetRepository.save(voteSujet);
        }
    }

    private void adjustScore(PostHelp postHelp, int adjustment) {
        postHelp.setScore(postHelp.getScore() + adjustment);
        postHelpRepository.save(postHelp);
    }
}
