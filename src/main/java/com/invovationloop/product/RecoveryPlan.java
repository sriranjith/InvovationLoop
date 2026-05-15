package com.invovationloop.product;

import java.util.List;

public record RecoveryPlan(
        String title,
        String summary,
        String likelyCause,
        String operatorImpact,
        List<String> evidenceToCollect,
        List<String> adminActions,
        List<String> validationChecks,
        List<String> riskFlags,
        List<String> preventionIdeas,
        String resumePrompt,
        String nextStep
) {
}
