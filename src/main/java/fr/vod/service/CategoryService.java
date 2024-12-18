package fr.vod.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.vod.model.Category;
import fr.vod.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	public List<Category> list()
	{
		return (List<Category>)categoryRepository.findAll();
	}
	
	public boolean exists(String designation) {
		return (categoryRepository.findByDesignation(designation)!=null);
	}
	
	
	
	public Category createCategory(String name) {
        Category category = new Category();
        category.setDesignation(name);
        categoryRepository.save(category);//ca c'est enregistrer les cathegorie dans la base de donnees
        return category;
    }
	
	public boolean deleteCategory(String idCategory) 
    {
        try {
            categoryRepository.delete(categoryRepository.findCategoryById(Integer.valueOf(idCategory)));
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
