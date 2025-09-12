package fr.vod.service;

import fr.vod.dto.ServiceWithVideosDTO;
import fr.vod.dto.VideoDTO;
import fr.vod.model.Service;
import fr.vod.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<ServiceWithVideosDTO> getAllServicesWithVideos() {
        return serviceRepository.findAll().stream()
                .map(service -> {
                    List<VideoDTO> videoDTOs = service.getVideos().stream()
                            .map(video -> {
                                VideoDTO dto = new VideoDTO();
                                dto.setTitle(video.getTitle());
                                dto.setUrl(video.getUrl());
                                dto.setDescription(video.getDescription());
                                dto.setDuration(video.getDuration());
                                dto.setAuthor(video.getAuthor());
                                dto.setPublisher(video.getPublisher());
                                dto.setServiceId(service.getId());
                                return dto;
                            })
                            .collect(Collectors.toList());

                    return new ServiceWithVideosDTO(service.getName(), videoDTOs);
                })
                .collect(Collectors.toList());
    }

    public boolean exists(String name) {
        return serviceRepository.findAll().stream()
                .anyMatch(service -> service.getName().equalsIgnoreCase(name));
    }

    public Service createService(String name) {
        Service service = new Service();
        service.setName(name);
        return serviceRepository.save(service);
    }

    public boolean deleteService(Long id) {
        if (serviceRepository.existsById(id)) {
            serviceRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
