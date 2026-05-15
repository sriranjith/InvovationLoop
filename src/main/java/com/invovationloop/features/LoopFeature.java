package com.invovationloop.features;

public interface LoopFeature {
    String id();

    String title();

    String summary();

    String category();

    int readinessScore(int novelty, int reliability, int simplicity);

    String recommendation(int novelty, int reliability, int simplicity);
}
