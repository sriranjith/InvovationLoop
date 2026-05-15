package com.invovationloop.generated;

import com.invovationloop.features.LoopFeature;
import org.springframework.stereotype.Component;

@Component
public final class CycleBudgetEstimator2Innovation implements LoopFeature {
    private static final int NOVELTY_WEIGHT = 5;
    private static final int RELIABILITY_WEIGHT = 7;
    private static final int SIMPLICITY_WEIGHT = 8;

    @Override
    public String id() {
        return "cycle-budget-estimator-2";
    }

    @Override
    public String title() {
        return "Cycle Budget Estimator";
    }

    @Override
    public String summary() {
        return "Scores how much implementation budget an idea deserves before code is generated.";
    }

    @Override
    public String purpose() {
        return "Estimate whether an idea has enough balanced signal to deserve implementation time.";
    }

    @Override
    public String usage() {
        return "Set the sliders to describe the idea, then press Play to see if it is ready for a commit-sized build.";
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
