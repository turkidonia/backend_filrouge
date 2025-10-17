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
@Table(name = "video_likes")
public class Like {

    @EmbeddedId
    private LikeId id;

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
    public Like(Utilisateur utilisateur, Video video, LocalDateTime publishDate) {
        this.utilisateur = utilisateur;
        this.video = video;
        this.publishDate = publishDate;
        this.id = new LikeId(utilisateur.getId(), video.getId());
    }
}
