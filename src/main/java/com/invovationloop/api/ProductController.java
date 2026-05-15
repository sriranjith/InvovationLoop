package com.invovationloop.api;

import com.invovationloop.product.ActiveProductFeature;
import com.invovationloop.product.ProductFeatureService;
import com.invovationloop.product.RecoveryPlan;
import com.invovationloop.product.RecoveryPlanRequest;
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

    @PostMapping("/recovery-plan")
    public ResponseEntity<RecoveryPlan> recoveryPlan(@RequestBody RecoveryPlanRequest request) {
        return ResponseEntity.ok(productFeatureService.createRecoveryPlan(request));
    }
}
