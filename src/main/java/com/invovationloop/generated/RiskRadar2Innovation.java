package com.invovationloop.generated;

import com.invovationloop.features.LoopFeature;
import org.springframework.stereotype.Component;

@Component
public final class RiskRadar2Innovation implements LoopFeature {
    private static final int NOVELTY_WEIGHT = 8;
    private static final int RELIABILITY_WEIGHT = 6;
    private static final int SIMPLICITY_WEIGHT = 6;

    @Override
    public String id() {
        return "risk-radar";
    }

    @Override
    public String title() {
        return "Risk Radar";
    }

    @Override
    public String summary() {
        return "Turns novelty, reliability, and simplicity signals into a bounded delivery risk score.";
    }

    @Override
    public String purpose() {
        return "Compare delivery risk across generated ideas before the agent commits to a direction.";
    }

    @Override
    public String usage() {
        return "Tune the sliders for the current idea and press Play to classify the delivery risk band.";
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
