package com.invovationloop.generated;

import com.invovationloop.features.LoopFeature;
import org.springframework.stereotype.Component;

@Component
public final class TestSignalAmplifierInnovation implements LoopFeature {
    private static final int NOVELTY_WEIGHT = 4;
    private static final int RELIABILITY_WEIGHT = 9;
    private static final int SIMPLICITY_WEIGHT = 7;

    @Override
    public String id() {
        return "test-signal-amplifier";
    }

    @Override
    public String title() {
        return "Test Signal Amplifier";
    }

    @Override
    public String summary() {
        return "Highlights where a generated feature needs stronger local tests before commit.";
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
        if (score >= 55) {
            return "needs-more-tests";
        }
        return "needs-redesign";
    }

    private int normalize(int value) {
        return Math.max(0, Math.min(100, value));
    }
}
