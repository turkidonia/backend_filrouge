package fr.vod.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.vod.dto.CategoryDTO;
import fr.vod.dto.HomeDTO;
import fr.vod.model.Category;
import fr.vod.service.CategoryService;
import fr.vod.service.VideoService;

@RestController
public class HomeRestController {
	
	@Autowired
	CategoryService categoryService;
	@Autowired
	VideoService videoService;
	
	
	@GetMapping("/public/home")
	public HomeDTO getHome()
	{
		HomeDTO homeDTO = new HomeDTO();
		
		List<Category> categoryList = categoryService.list();
		List<CategoryDTO> listCategoryDTO = new ArrayList<CategoryDTO>();
		for (Category category : categoryList)
			listCategoryDTO.add(new CategoryDTO(category));
		
		homeDTO.setCategoryListDTO(listCategoryDTO);
		homeDTO.setVideoListDTO(null);
	
		 
		return homeDTO;
	}
	

}
