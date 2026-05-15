package com.invovationloop.generated;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CycleBudgetEstimatorInnovationTest {
    private final CycleBudgetEstimatorInnovation innovation = new CycleBudgetEstimatorInnovation();

    @Test
    void scoreIsAlwaysBounded() {
        assertThat(innovation.readinessScore(-100, 50, 500)).isBetween(0, 100);
    }

    @Test
    void highSignalsAreCommitReady() {
        assertThat(innovation.recommendation(90, 90, 90)).isEqualTo("commit-ready");
    }

    @Test
    void lowSignalsNeedRedesign() {
        assertThat(innovation.recommendation(5, 10, 15)).isEqualTo("needs-redesign");
    }

    @Test
    void metadataIsPresent() {
        assertThat(innovation.id()).isEqualTo("cycle-budget-estimator");
        assertThat(innovation.title()).isNotBlank();
        assertThat(innovation.summary()).isNotBlank();
        assertThat(innovation.category()).isNotBlank();
    }
}
