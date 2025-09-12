package fr.vod.controller;

import fr.vod.dto.VideoDTO;
import fr.vod.model.Video;
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
    @PostMapping("Create")
    public ResponseEntity<Video> createVideo(@RequestBody VideoDTO videoDTO) {
        return ResponseEntity.ok(videoService.createVideo(videoDTO));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR', 'MENTOREE')")
    @GetMapping("")
    public ResponseEntity<List<Video>> getAllVideos() {
        return ResponseEntity.ok(videoService.getAllVideos());
    }
}