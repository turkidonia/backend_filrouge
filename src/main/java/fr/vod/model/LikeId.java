package fr.vod.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LikeId implements Serializable {

    private Long utilisateurId;
    private Long videoId;

    public LikeId() {}

    public LikeId(Long utilisateurId, Long videoId) {
        this.utilisateurId = utilisateurId;
        this.videoId = videoId;
    }

    // getters et setters
    public Long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }

    public Long getVideoId() { return videoId; }
    public void setVideoId(Long videoId) { this.videoId = videoId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LikeId)) return false;
        LikeId likeId = (LikeId) o;
        return Objects.equals(utilisateurId, likeId.utilisateurId) &&
               Objects.equals(videoId, likeId.videoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utilisateurId, videoId);
    }
}
