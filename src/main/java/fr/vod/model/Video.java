package fr.vod.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String url;
    private String description;
    private int duration;
    private String author;
    private String publisher;
    private Boolean isActive = true;

    @ManyToOne
    private Service service;

    @OneToMany(mappedBy = "video")
    private List<Commentaire> commentaires;

    @OneToMany(mappedBy = "video")
    private List<Like> likesList;

    @OneToMany(mappedBy = "video")
    private List<Favoris> favoris;
    
    
    @ManyToOne
    @JoinColumn(name="mentor_id")
    private Utilisateur utilisateur;
}
