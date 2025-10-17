package fr.vod.service;

import fr.vod.dto.VideoDTO;
import fr.vod.model.Utilisateur;
import fr.vod.model.Video;
import fr.vod.repository.ServiceRepository;
import fr.vod.repository.UtilisateurRepository;
import fr.vod.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepo;
    @Autowired
    private ServiceRepository serviceRepo;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // Retourne toutes les vidéos sous forme de DTO
    public List<VideoDTO> getAllVideos() {
        return videoRepo.findAll()
                        .stream()
                        .map(VideoDTO::fromEntity)
                        .collect(Collectors.toList());
    }

    public List<VideoDTO> getAllVideosByService(String service) {
        return videoRepo.findByService_NameIgnoreCaseAndIsActiveTrue(service)
                        .stream()
                        .map(VideoDTO::fromEntity)
                        .collect(Collectors.toList());
    }

    public VideoDTO createVideo(VideoDTO dto) {
        Utilisateur mentor = utilisateurRepository.findByEmail(dto.getMentorEmail()).orElse(null);

        Video video = new Video();
        video.setTitle(dto.getTitle());
        video.setUrl(dto.getUrl());
        video.setDescription(dto.getDescription());
        video.setDuration(dto.getDuration());
        video.setService(serviceRepo.findById(dto.getServiceId()).orElseThrow());
        video.setUtilisateur(mentor);

        Video savedVideo = videoRepo.save(video);

        return VideoDTO.fromEntity(savedVideo);
    }

    public boolean updateIsActive(Long id) {
        return videoRepo.findById(id)
                        .map(video -> {
                            video.setIsActive(!video.getIsActive());
                            videoRepo.save(video);
                            return true;
                        }).orElse(false);
    }
}
