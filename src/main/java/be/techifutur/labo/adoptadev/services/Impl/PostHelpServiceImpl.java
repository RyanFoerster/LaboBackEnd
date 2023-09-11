package be.techifutur.labo.adoptadev.services.Impl;

import be.techifutur.labo.adoptadev.exceptions.NameNotFoundException;
import be.techifutur.labo.adoptadev.exceptions.ResourceNotFoundException;
import be.techifutur.labo.adoptadev.models.entities.Comment;
import be.techifutur.labo.adoptadev.models.entities.Dev;
import be.techifutur.labo.adoptadev.models.entities.PostHelp;
import be.techifutur.labo.adoptadev.repositories.CommentRepository;
import be.techifutur.labo.adoptadev.repositories.DevRepository;
import be.techifutur.labo.adoptadev.repositories.PostHelpRepository;
import be.techifutur.labo.adoptadev.repositories.VoteSujetRepository;
import be.techifutur.labo.adoptadev.services.PostHelpService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostHelpServiceImpl implements PostHelpService {

    private final PostHelpRepository postHelpRepository;
    private final CommentRepository commentRepository;
    private final DevRepository devRepository;
    private final VoteSujetRepository voteSujetRepository;


    public PostHelpServiceImpl(PostHelpRepository postHelpRepository,
                               CommentRepository commentRepository,
                               DevRepository devRepository,
                               VoteSujetRepository voteSujetRepository) {
        this.postHelpRepository = postHelpRepository;
        this.commentRepository = commentRepository;
        this.devRepository = devRepository;
        this.voteSujetRepository = voteSujetRepository;
    }


    @Override
    public Long add(PostHelp post, String devName) {

        Dev dev = devRepository.findByUsername(devName).orElseThrow(
                () -> new NameNotFoundException(devName, Dev.class)
        );
        post.setDev(dev);

        return postHelpRepository.save(post).getId();

    }

    @Override
    public List<PostHelp> getAll() {
        return postHelpRepository.findAll().stream().toList();
    }

    @Override
    public PostHelp getOne(Long aLong) {
        return postHelpRepository.findById(aLong).orElseThrow(
                () -> new ResourceNotFoundException(aLong, PostHelp.class)
        );
    }

    @Override
    public void update(Long aLong, PostHelp postHelp) {

        PostHelp entity = getOne(aLong);

        entity.setTitle(postHelp.getTitle());
        entity.setTechnologyFrontEnd(postHelp.getTechnologyFrontEnd());
        entity.setTechnologyBackEnd(postHelp.getTechnologyBackEnd());
        entity.setDescription(postHelp.getDescription());
        entity.setGithub(postHelp.getGithub());
        entity.setOuvert(postHelp.getOuvert());
        postHelpRepository.save(entity);

    }

    @Override
    public void delete(Long aLong) {

        PostHelp postHelp = getOne(aLong);
        postHelp.setOuvert(false);
        postHelpRepository.save(postHelp);

    }


    public void isActive(Long aLong, PostHelp postHelp){

        PostHelp entity = getOne(aLong);

        entity.setOuvert(!postHelp.getOuvert());

        postHelpRepository.save(entity);
    }

    public List<Comment> getComments(){
        return commentRepository.findAll().stream().toList();
    }



}
