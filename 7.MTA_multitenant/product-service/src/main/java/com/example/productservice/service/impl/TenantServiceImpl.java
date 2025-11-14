package com.example.productservice.service.impl;

import com.example.productservice.service.TenantService;
import com.example.productservice.util.TenantUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final JdbcTemplate jdbcTemplate;

    public void createTenantResources(String tenantId) {
        String schemaName = TenantUtils.toSchemaName(tenantId);

        log.info("Creating schema for tenant: {}", tenantId);

        try {
            // Create schema (if not exists)
            jdbcTemplate.execute("CREATE SCHEMA \"" + schemaName + "\"");

            // Initialize tables
            jdbcTemplate.execute("CREATE TABLE \"" + schemaName + "\".\"PRODUCTS\" ("
                    + "ID INT PRIMARY KEY, "
                    + "NAME VARCHAR(255), "
                    + "PRICE INT)");

            log.info("Schema {} created successfully for tenant {}", schemaName, tenantId);

        } catch (Exception e) {
            if (e.getMessage().contains("already exists")) {
                log.info("Schema {} already exists, skipping creation.", schemaName);
            } else {
                log.error("Error creating schema for tenant {}", tenantId, e);
                throw new RuntimeException(e);
            }
        }
    }

    public void removeTenantResources(String tenantId) {
        String schemaName = TenantUtils.toSchemaName(tenantId);
        try {
            jdbcTemplate.execute("DROP SCHEMA \"" + schemaName + "\" CASCADE");
            log.info("Schema {} dropped successfully for tenant {}", schemaName, tenantId);
        } catch (Exception e) {
            log.warn("Error removing schema for tenant {}: {}", tenantId, e.getMessage());
        }
    }

}
