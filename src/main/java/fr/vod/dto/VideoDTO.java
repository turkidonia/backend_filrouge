package fr.vod.dto;

import lombok.Data;

import java.util.List;

@Data
public class VideoDTO {

	private String title;
	private String url;
	private String description;
	private int duration;
	private String author;
	private Boolean isActive = true;
	private Long serviceId;
}
