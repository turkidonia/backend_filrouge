package fr.vod.dto;

import fr.vod.model.Video;
import lombok.Data;

@Data
public class VideoDTO {

	private Long id;
    private String title;
    private String url;
    private String description;
    private int duration;
    private Boolean isActive = true;
    private Long serviceId;
    private String mentorName; // prénom+nom du mentor
    private String mentorEmail;

    // Conversion entité -> DTO
    public static VideoDTO fromEntity(Video video) {
        VideoDTO dto = new VideoDTO();
        dto.setId(video.getId()); // ✅ Très important !
        dto.setTitle(video.getTitle());
        dto.setUrl(video.getUrl());
        dto.setDescription(video.getDescription());
        dto.setDuration(video.getDuration());
        dto.setIsActive(video.getIsActive());
        dto.setServiceId(video.getService() != null ? video.getService().getId() : null);

        if (video.getUtilisateur() != null) {
            dto.setMentorName(video.getUtilisateur().getName() + " " + video.getUtilisateur().getSurname());
            dto.setMentorEmail(video.getUtilisateur().getEmail());
        }

        return dto;
    }
}
