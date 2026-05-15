package com.invovationloop.features;

import static org.assertj.core.api.Assertions.assertThat;

import com.invovationloop.generated.CycleBudgetEstimatorInnovation;
import java.util.List;
import org.junit.jupiter.api.Test;

class FeatureCatalogServiceTest {
    @Test
    void listsAndPlaysFeatures() {
        FeatureCatalogService service = new FeatureCatalogService(List.of(new CycleBudgetEstimatorInnovation()));

        assertThat(service.listFeatures()).extracting(FeatureView::id).containsExactly("cycle-budget-estimator");
        assertThat(service.play("cycle-budget-estimator", 90, 90, 90).recommendation()).isEqualTo("commit-ready");
    }
}
