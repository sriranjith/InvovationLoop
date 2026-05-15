package com.invovationloop.product;

public record RecoveryPlanRequest(
        String agentGoal,
        String failureSignal,
        String workspaceState,
        String constraints
) {
}
