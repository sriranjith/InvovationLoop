package com.invovationloop.product;

import java.util.List;

public record ActiveProductFeature(
        String id,
        String name,
        String stage,
        int iteration,
        int stabilityScore,
        String problem,
        String audience,
        String whatItDoes,
        String howToUse,
        String lastImprovement,
        String nextIterationFocus,
        List<String> principles
) {
}
