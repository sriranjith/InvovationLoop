package com.invovationloop.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProductFeatureServiceTest {
    private final ProductFeatureService service = new ProductFeatureService();

    @Test
    void exposesOneActiveProductFeature() {
        ActiveProductFeature feature = service.activeFeature();

        assertThat(feature.id()).isEqualTo("opportunity-brief-studio");
        assertThat(feature.name()).isEqualTo("Opportunity Brief Studio");
        assertThat(feature.iteration()).isGreaterThanOrEqualTo(1);
        assertThat(feature.whatItDoes()).contains("opportunity brief");
        assertThat(feature.howToUse()).contains("validation plan");
        assertThat(feature.principles()).contains("One active feature at a time");
    }

    @Test
    void createsPracticalOpportunityBrief() {
        OpportunityBrief brief = service.createBrief(new OpportunityBriefRequest(
                "healthcare operations",
                "clinic managers",
                "staff spend hours reconciling appointment no-shows",
                "small teams and no new cloud dependency"
        ));

        assertThat(brief.title()).contains("Opportunity Brief");
        assertThat(brief.summary()).contains("clinic managers");
        assertThat(brief.mvpScope()).hasSizeGreaterThanOrEqualTo(3);
        assertThat(brief.validationPlan()).anyMatch(step -> step.contains("Interview"));
        assertThat(brief.nextStep()).contains("Innovator");
    }

    @Test
    void handlesBlankInputsWithUsefulFallbacks() {
        OpportunityBrief brief = service.createBrief(new OpportunityBriefRequest("", "", "", ""));

        assertThat(brief.userSegment()).isEqualTo("teams who feel this pain");
        assertThat(brief.proposedSolution()).isNotBlank();
        assertThat(brief.risks()).isNotEmpty();
    }
}
