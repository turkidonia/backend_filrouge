package fr.vod.controller;

import java.util.List;

import fr.vod.dto.ServiceWithVideosDTO;
import fr.vod.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeRestController {

	@Autowired
	private ServiceService serviceService;

	@GetMapping("/public/home")
	public ResponseEntity<List<ServiceWithVideosDTO>> getServicesWithVideos() {
		return ResponseEntity.ok(serviceService.getAllServicesWithVideos());
	}

}
