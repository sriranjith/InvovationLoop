package com.invovationloop.generated;

import com.invovationloop.features.LoopFeature;
import org.springframework.stereotype.Component;

@Component
public final class ToolFitEvaluatorInnovation implements LoopFeature {
    private static final int NOVELTY_WEIGHT = 8;
    private static final int RELIABILITY_WEIGHT = 7;
    private static final int SIMPLICITY_WEIGHT = 5;

    @Override
    public String id() {
        return "tool-fit-evaluator";
    }

    @Override
    public String title() {
        return "Tool Fit Evaluator";
    }

    @Override
    public String summary() {
        return "Measures how well a proposed change matches available local and remote tools.";
    }

    @Override
    public String purpose() {
        return "Estimate whether the agent is choosing tools that fit the requested feature work.";
    }

    @Override
    public String usage() {
        return "Set the sliders for tool fit and confidence, then press Play to see if the proposal is commit-ready.";
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
