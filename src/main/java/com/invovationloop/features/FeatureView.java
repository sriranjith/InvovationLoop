package com.invovationloop.features;

public record FeatureView(
        String id,
        String title,
        String summary,
        String purpose,
        String usage,
        String category
) {
    public static FeatureView from(LoopFeature feature) {
        return new FeatureView(
                feature.id(),
                feature.title(),
                feature.summary(),
                feature.purpose(),
                feature.usage(),
                feature.category()
        );
    }
}
