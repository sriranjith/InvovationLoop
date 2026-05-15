package com.invovationloop.features;

import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FeatureCatalogService {
    private final List<LoopFeature> features;

    public FeatureCatalogService(List<LoopFeature> features) {
        this.features = features.stream()
                .sorted(Comparator.comparing(LoopFeature::title))
                .toList();
    }

    public List<FeatureView> listFeatures() {
        return features.stream().map(FeatureView::from).toList();
    }

    public FeaturePlayResult play(String id, int novelty, int reliability, int simplicity) {
        LoopFeature feature = features.stream()
                .filter(candidate -> candidate.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown feature: " + id));
        int score = feature.readinessScore(novelty, reliability, simplicity);
        return new FeaturePlayResult(id, feature.title(), score, feature.recommendation(novelty, reliability, simplicity));
    }
}
