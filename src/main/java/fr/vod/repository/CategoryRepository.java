package fr.vod.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.vod.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer>{
	
	//public  List<Category> findAll();
	public Category findByDesignation(String designation);
	
	public Category findCategoryById(Integer id);
}
