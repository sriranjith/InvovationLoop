package com.invovationloop.api;

import com.invovationloop.product.ActiveProductFeature;
import com.invovationloop.product.OpportunityBrief;
import com.invovationloop.product.OpportunityBriefRequest;
import com.invovationloop.product.ProductFeatureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductFeatureService productFeatureService;

    public ProductController(ProductFeatureService productFeatureService) {
        this.productFeatureService = productFeatureService;
    }

    @GetMapping("/active")
    public ActiveProductFeature activeFeature() {
        return productFeatureService.activeFeature();
    }

    @PostMapping("/brief")
    public ResponseEntity<OpportunityBrief> brief(@RequestBody OpportunityBriefRequest request) {
        return ResponseEntity.ok(productFeatureService.createBrief(request));
    }
}
