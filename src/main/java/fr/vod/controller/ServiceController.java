package fr.vod.controller;

import fr.vod.dto.RestAPIResponse;
import fr.vod.dto.ServiceDTO;
import fr.vod.dto.ServiceWithVideosDTO;
import fr.vod.model.Service;
import fr.vod.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("public/v1/Service/")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
    @PostMapping("add")
    public ResponseEntity<?> addService(@RequestBody ServiceDTO serviceDTO) {
        if (!serviceService.exists(serviceDTO.getName())) {
            Service created = serviceService.createService(serviceDTO.getName());
            return ResponseEntity.ok(new RestAPIResponse(200, "Service created: " + created.getName()));
        } else {
            return ResponseEntity.badRequest().body(new RestAPIResponse(400, "Service already exists"));
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<ServiceWithVideosDTO>> getAllServicesWithVideos() {
        List<ServiceWithVideosDTO> services = serviceService.getAllServicesWithVideos();
        return ResponseEntity.ok(services);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MENTOR')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteService(@PathVariable Long id) {
        boolean deleted = serviceService.deleteService(id);
        if (deleted) {
            return ResponseEntity.ok(new RestAPIResponse(200, "Service deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(new RestAPIResponse(404, "Service not found"));
        }
    }
}
