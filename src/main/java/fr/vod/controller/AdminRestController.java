package fr.vod.controller;

import fr.vod.dto.ServiceDTO;
import fr.vod.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.vod.dto.RestAPIResponse;

@RestController
public class AdminRestController {

	@Autowired
	private ServiceService serviceService;

	@PostMapping("/api/service/add")
	public Object addService(@RequestBody ServiceDTO serviceDTO) throws Exception {
		System.out.println(serviceDTO.getName());

		if (!serviceService.exists(serviceDTO.getName())) {
			serviceService.createService(serviceDTO.getName());
			return ResponseEntity.ok(serviceDTO);
		} else {
			throw new Exception("Service already exists");
		}
	}

	@DeleteMapping("/api/service/{id}")
	public Object deleteService(@PathVariable Long id) throws Exception {
		boolean ok = serviceService.deleteService(id);
		if (ok) {
			return ResponseEntity.ok(new RestAPIResponse(200, "Service deleted"));
		} else {
			throw new Exception("Problem deleting service");
		}
	}
}
