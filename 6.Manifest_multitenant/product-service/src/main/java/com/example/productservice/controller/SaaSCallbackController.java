package com.example.productservice.controller;

import com.example.productservice.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/callback")
@Slf4j
@RequiredArgsConstructor
public class SaaSCallbackController {

    private final TenantService tenantService;

    // Called when a tenant subscribes
    @PutMapping("/tenants/{tenantId}")
    public ResponseEntity<String> onSubscribe(@PathVariable String tenantId) {

        log.info("Received request to subscribe for tenant " + tenantId);

        // 1) Create per-tenant DB schema / resources
        // 2) Persist tenant mapping (tenantId -> schema, other config)
        // 3) Return the tenant app URL (where tenant will access app) or just 200

        // Example: create schema and initialize
        tenantService.createTenantResources(tenantId);

        // Return 200. Optionally return body containing URL; SaaS Registry only needs 2xx.
        return ResponseEntity.ok("Subscribed: " + tenantId);
    }

    // Called when tenant unsubscribes
    @DeleteMapping("/tenants/{tenantId}")
    public ResponseEntity<Void> onUnsubscribe(@PathVariable String tenantId) {
        tenantService.removeTenantResources(tenantId);
        return ResponseEntity.noContent().build();
    }

    // Optional: return dependency information
    @GetMapping("/dependencies")
    public ResponseEntity<List<Object>> dependencies(@RequestParam(required = false) String tenantId) {
        return ResponseEntity.ok(Collections.emptyList());
    }

}
