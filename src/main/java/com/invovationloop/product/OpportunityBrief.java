package com.invovationloop.product;

import java.util.List;

public record OpportunityBrief(
        String title,
        String summary,
        String userSegment,
        String jobToBeDone,
        String proposedSolution,
        String whyNow,
        List<String> mvpScope,
        List<String> validationPlan,
        List<String> successMetrics,
        List<String> risks,
        String nextStep
) {
}
