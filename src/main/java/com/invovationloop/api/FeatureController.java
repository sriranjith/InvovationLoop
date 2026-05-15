package com.invovationloop.api;

import com.invovationloop.features.FeatureCatalogService;
import com.invovationloop.features.FeaturePlayResult;
import com.invovationloop.features.FeatureView;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/features")
public class FeatureController {
    private final FeatureCatalogService catalogService;

    public FeatureController(FeatureCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public List<FeatureView> listFeatures() {
        return catalogService.listFeatures();
    }

    @PostMapping("/{id}/play")
    public ResponseEntity<FeaturePlayResult> play(
            @PathVariable String id,
            @RequestBody PlayRequest request
    ) {
        return ResponseEntity.ok(catalogService.play(
                id,
                request.novelty(),
                request.reliability(),
                request.simplicity()
        ));
    }

    public record PlayRequest(int novelty, int reliability, int simplicity) {
    }
}
