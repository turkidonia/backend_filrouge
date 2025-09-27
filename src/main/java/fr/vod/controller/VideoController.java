package fr.vod.controller;

import fr.vod.dto.VideoDTO;
import fr.vod.model.Utilisateur;
import fr.vod.model.Video;
import fr.vod.repository.UtilisateurRepository;
import fr.vod.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/v1/videos/")
public class VideoController {
    @Autowired
    private VideoService videoService;
    
    
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
    @PostMapping
    public ResponseEntity<Video> createVideo(@RequestBody VideoDTO videoDTO) {
        
    	return ResponseEntity.ok(videoService.createVideo(videoDTO));
    }

    @GetMapping
    public ResponseEntity<List<Video>> getAllVideos(@RequestParam(required = false) String service) {
    	if (service != null){ 
    		return ResponseEntity.ok(videoService.getAllVideosByService(service));
    	} else {
    		return ResponseEntity.ok(videoService.getAllVideos());
    	}
    }
    
    @PutMapping("/{id}/active")
    public ResponseEntity<Void> updateActiveVideo(@PathVariable Long id) {
    	if(videoService.updateIsActive(id)) {
    		return ResponseEntity.noContent().build();
    	}
    	return ResponseEntity.notFound().build();
    }
}