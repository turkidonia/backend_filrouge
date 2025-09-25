package fr.vod.repository;

import fr.vod.model.Video;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
	List<Video> findByService_NameIgnoreCase(String service);
}

