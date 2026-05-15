package com.invovationloop.generated;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CycleBudgetEstimator2InnovationTest {
    private final CycleBudgetEstimator2Innovation innovation = new CycleBudgetEstimator2Innovation();

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
    void middleSignalsNeedMoreTests() {
        assertThat(innovation.recommendation(52, 52, 52)).isEqualTo("needs-more-tests");
    }

    @Test
    void metadataIsPresent() {
        assertThat(innovation.id()).isNotBlank();
        assertThat(innovation.title()).isNotBlank();
        assertThat(innovation.summary()).isNotBlank();
        assertThat(innovation.purpose()).isNotBlank();
        assertThat(innovation.usage()).isNotBlank();
        assertThat(innovation.category()).isNotBlank();
    }
}
