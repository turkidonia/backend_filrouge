package fr.vod.service;

import fr.vod.dto.VideoDTO;
import fr.vod.model.Utilisateur;
import fr.vod.model.Video;
import fr.vod.repository.ServiceRepository;
import fr.vod.repository.UtilisateurRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.vod.repository.VideoRepository;

import java.util.List;


@Service
public class VideoService {
	@Autowired
	private VideoRepository videoRepo;
	@Autowired
	private ServiceRepository serviceRepo;

	@Autowired
    private UtilisateurRepository utilisateurRepository;
	
	public List<Video> getAllVideos() {
		return videoRepo.findAll();
	}
	public List<Video> getAllVideosByService(String service) {
		return videoRepo.findByService_NameIgnoreCaseAndIsActiveTrue(service);
	}

	public Video createVideo(VideoDTO dto) {
		Utilisateur mentor = utilisateurRepository.findByEmail(dto.getAuthor()).orElse(null);
		Video video = new Video();
		video.setTitle(dto.getTitle());
		video.setUrl(dto.getUrl());
		video.setDescription(dto.getDescription());
		video.setDuration(dto.getDuration());
		video.setAuthor(dto.getAuthor());
		video.setService(serviceRepo.findById(dto.getServiceId()).orElseThrow());
		video.setUtilisateur(mentor);
		return videoRepo.save(video);
	}
	
	public boolean updateIsActive(Long id) {
		if(videoRepo.existsById(id)) {
			Video video = videoRepo.findById(id).orElse(null);
			video.setIsActive(!video.getIsActive());
			videoRepo.save(video);
			return true;
			
		}
		
		return false;
		
	}
}
