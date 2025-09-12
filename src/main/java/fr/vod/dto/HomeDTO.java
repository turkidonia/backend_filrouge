package fr.vod.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeDTO {
	
	List<ServiceDTO> categoryListDTO;
	List<VideoDTO> videoListDTO;

}
