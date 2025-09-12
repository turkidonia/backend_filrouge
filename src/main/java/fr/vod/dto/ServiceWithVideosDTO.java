package fr.vod.dto;


import lombok.Data;

import java.util.List;

@Data
public class ServiceWithVideosDTO {
    private String name;
    private List<VideoDTO> videos;

    public ServiceWithVideosDTO(String name, List<VideoDTO> videos) {
        this.name = name;
        this.videos = videos;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public List<VideoDTO> getVideos() {
        return videos;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVideos(List<VideoDTO> videos) {
        this.videos = videos;
    }
}

