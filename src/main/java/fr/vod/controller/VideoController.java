package fr.vod.controller;

import fr.vod.dto.VideoDTO;
import fr.vod.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/v1/videos/")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
    @PostMapping
    public ResponseEntity<VideoDTO> createVideo(@RequestBody VideoDTO videoDTO) {
        VideoDTO responseDTO = videoService.createVideo(videoDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<VideoDTO>> getAllVideos(@RequestParam(required = false) String service) {
        List<VideoDTO> videoDTOs;

        if (service != null) {
            videoDTOs = videoService.getAllVideosByService(service);
        } else {
            videoDTOs = videoService.getAllVideos();
        }

        return ResponseEntity.ok(videoDTOs);
    }

    @PutMapping("/{id}/active")
    public ResponseEntity<Void> updateActiveVideo(@PathVariable Long id) {
        if(videoService.updateIsActive(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
