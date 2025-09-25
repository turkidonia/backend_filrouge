package fr.vod.service;

import fr.vod.dto.VideoDTO;
import fr.vod.model.Video;
import fr.vod.repository.ServiceRepository;
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

	public List<Video> getAllVideos() {
		return videoRepo.findAll();
	}
	public List<Video> getAllVideosByService(String service) {
		return videoRepo.findByService_NameIgnoreCase(service);
	}

	public Video createVideo(VideoDTO dto) {
		Video video = new Video();
		video.setTitle(dto.getTitle());
		video.setUrl(dto.getUrl());
		video.setDescription(dto.getDescription());
		video.setDuration(dto.getDuration());
		video.setAuthor(dto.getAuthor());
		video.setPublisher(dto.getPublisher());
		video.setService(serviceRepo.findById(dto.getServiceId()).orElseThrow());
		return videoRepo.save(video);
	}
}
