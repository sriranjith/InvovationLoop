package com.invovationloop.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProductFeatureServiceTest {
    private final ProductFeatureService service = new ProductFeatureService();

    @Test
    void exposesOneActiveProductFeatureForAgentRecovery() {
        ActiveProductFeature feature = service.activeFeature();

        assertThat(feature.id()).isEqualTo("agent-recovery-console");
        assertThat(feature.name()).isEqualTo("Agent Recovery Console");
        assertThat(feature.iteration()).isGreaterThanOrEqualTo(1);
        assertThat(feature.whatItDoes()).contains("failed AI-agent run");
        assertThat(feature.howToUse()).contains("resume the agent");
        assertThat(feature.principles()).contains("One feature only");
    }

    @Test
    void createsPracticalRecoveryPlanForOciFailure() {
        RecoveryPlan plan = service.createRecoveryPlan(new RecoveryPlanRequest(
                "improve InvovationLoop",
                "OCI compartment id is not configured",
                "working tree has uncommitted files",
                "admin must preserve user changes"
        ));

        assertThat(plan.title()).contains("Agent Recovery Plan");
        assertThat(plan.summary()).contains("improve InvovationLoop");
        assertThat(plan.likelyCause()).contains("OCI");
        assertThat(plan.adminActions()).hasSizeGreaterThanOrEqualTo(3);
        assertThat(plan.validationChecks()).anyMatch(step -> step.contains("failing command"));
        assertThat(plan.resumePrompt()).contains("Resume only after");
    }

    @Test
    void handlesBlankInputsWithUsefulFallbacks() {
        RecoveryPlan plan = service.createRecoveryPlan(new RecoveryPlanRequest("", "", "", ""));

        assertThat(plan.summary()).contains("complete the current autonomous coding task");
        assertThat(plan.riskFlags()).isNotEmpty();
        assertThat(plan.nextStep()).contains("resume the agent");
    }
}
