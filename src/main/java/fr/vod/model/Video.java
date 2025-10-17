package fr.vod.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String url;
    private String description;
    private int duration;
    private Boolean isActive = true;

    // Relation vers Service
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    // Relation vers Commentaire
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Commentaire> commentaires;

    // Relation vers Like
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Like> likesList;

    // Relation vers Favoris
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Favoris> favoris;

    // Mentor (l'utilisateur qui a posté la vidéo)
    @ManyToOne
    @JoinColumn(name = "mentor_id")
    @JsonIgnore // <-- empêche la boucle infinie lors de la sérialisation JSON
    private Utilisateur utilisateur;
}
