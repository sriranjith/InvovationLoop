package com.invovationloop.features;

public interface LoopFeature {
    String id();

    String title();

    String summary();

    default String purpose() {
        return summary();
    }

    default String usage() {
        return "Adjust the novelty, reliability, and simplicity sliders, then press Play to evaluate this feature.";
    }

    String category();

    int readinessScore(int novelty, int reliability, int simplicity);

    String recommendation(int novelty, int reliability, int simplicity);
}
