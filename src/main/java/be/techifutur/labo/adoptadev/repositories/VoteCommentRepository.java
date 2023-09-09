package be.techifutur.labo.adoptadev.repositories;

import be.techifutur.labo.adoptadev.models.entities.Comment;
import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.VoteComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteCommentRepository extends JpaRepository<VoteComment, Long> {
    Optional<VoteComment> findByCommentAndDev(Comment comment, Dev dev);

}
