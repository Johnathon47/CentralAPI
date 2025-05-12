package exam.hei.school.central_api.components.controller;

import exam.hei.school.central_api.components.service.SynchronizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SynchronisationController {
    private final SynchronizationService synchronizationService;

    public SynchronisationController(SynchronizationService synchronizationService) {
        this.synchronizationService = synchronizationService;
    }

    @PostMapping("/synchronization")
    public ResponseEntity<String> synchronize() {
        synchronizationService.synchronize();
        return ResponseEntity.ok("Synchronisation terminée.");
    }
}
