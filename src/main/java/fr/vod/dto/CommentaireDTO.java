package fr.vod.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentaireDTO {
    private Long idComment;
    private String content;
    private LocalDateTime publishDate;
    private Long idVideo;
    private Long idUser;
}
