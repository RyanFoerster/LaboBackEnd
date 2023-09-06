package be.techifutur.labo.adoptadev.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "post_dev")
@Getter
@Setter
public class PostDev {

    @Id
    @Column(name = "post_dev_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "post_dev_titre",nullable = false)
    private String title;
    @Column(name = "post_dev_description",nullable = false)
    private String description;
    @Column(name = "post_dev_image")
    private String image;
    @Column(name = "post_dev_link")
    private String link;

}
