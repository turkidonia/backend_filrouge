package fr.vod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.vod.model.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long>{
	
	
	public Service findCategoryById(Long id);
}
