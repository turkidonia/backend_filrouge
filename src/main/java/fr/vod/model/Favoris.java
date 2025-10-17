package fr.vod.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "favoris")
public class Favoris {

    @EmbeddedId
    private FavorisId id;

    private LocalDateTime publishDate;

    @MapsId("utilisateurId")
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @MapsId("videoId")
    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    // constructeur pratique
    public Favoris(Utilisateur utilisateur, Video video, LocalDateTime publishDate) {
        this.utilisateur = utilisateur;
        this.video = video;
        this.publishDate = publishDate;
        this.id = new FavorisId(utilisateur.getId(), video.getId());
    }
}
