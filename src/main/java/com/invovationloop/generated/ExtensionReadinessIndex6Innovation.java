package com.invovationloop.generated;

import com.invovationloop.features.LoopFeature;
import org.springframework.stereotype.Component;

@Component
public final class ExtensionReadinessIndex6Innovation implements LoopFeature {
    private static final int NOVELTY_WEIGHT = 7;
    private static final int RELIABILITY_WEIGHT = 6;
    private static final int SIMPLICITY_WEIGHT = 7;

    @Override
    public String id() {
        return "extension-readiness-index-6";
    }

    @Override
    public String title() {
        return "Extension Readiness Index";
    }

    @Override
    public String summary() {
        return "Estimates whether a local-only feature can later be wired into cloud services.";
    }

    @Override
    public String purpose() {
        return "Check whether a local feature has enough shape to grow into a future cloud-backed capability.";
    }

    @Override
    public String usage() {
        return "Score the local feature with the sliders, then press Play to see whether it is extension-ready.";
    }

    @Override
    public String category() {
        return "agent-generated";
    }

    @Override
    public int readinessScore(int novelty, int reliability, int simplicity) {
        int weighted = (normalize(novelty) * NOVELTY_WEIGHT)
                + (normalize(reliability) * RELIABILITY_WEIGHT)
                + (normalize(simplicity) * SIMPLICITY_WEIGHT);
        int totalWeight = NOVELTY_WEIGHT + RELIABILITY_WEIGHT + SIMPLICITY_WEIGHT;
        return weighted / totalWeight;
    }

    @Override
    public String recommendation(int novelty, int reliability, int simplicity) {
        int score = readinessScore(novelty, reliability, simplicity);
        if (score >= 80) {
            return "commit-ready";
        }
        if (score >= 50) {
            return "needs-more-tests";
        }
        return "needs-redesign";
    }

    private int normalize(int value) {
        return Math.max(0, Math.min(100, value));
    }
}
