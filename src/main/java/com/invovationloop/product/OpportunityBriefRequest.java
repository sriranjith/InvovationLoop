package com.invovationloop.product;

public record OpportunityBriefRequest(
        String industry,
        String audience,
        String problem,
        String constraints
) {
}
