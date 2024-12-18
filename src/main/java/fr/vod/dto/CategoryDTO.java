package fr.vod.dto;

import java.util.List;

import fr.vod.model.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
	
	Integer id;
	String nameDTO;
	List<VideoDTO> videoListDTO;
	
	public CategoryDTO(){
		
	}
	
	
	 public CategoryDTO(Category category) {
		 this.nameDTO = category.getDesignation();
	 }

}
