package com.invovationloop.generated;

import com.invovationloop.features.LoopFeature;
import org.springframework.stereotype.Component;

@Component
public final class CommitNarrativeBuilder4Innovation implements LoopFeature {
    private static final int NOVELTY_WEIGHT = 6;
    private static final int RELIABILITY_WEIGHT = 7;
    private static final int SIMPLICITY_WEIGHT = 8;

    @Override
    public String id() {
        return "commit-narrative-builder-4";
    }

    @Override
    public String title() {
        return "Commit Narrative Builder";
    }

    @Override
    public String summary() {
        return "Scores whether an innovation is ready for a clear, reviewable commit story.";
    }

    @Override
    public String purpose() {
        return "Judge whether a generated change can be explained as a coherent, reviewable commit.";
    }

    @Override
    public String usage() {
        return "Use the sliders to model clarity and confidence, then press Play before accepting a commit story.";
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
