package com.example.productservice.util;

public class TenantUtils {

    public static String toSchemaName(String tenantId) {
        // Sanitize tenantId for use as schema name (HANA allows limited chars)
        return "TENANT_" + tenantId.replaceAll("[^A-Za-z0-9_]", "_").toUpperCase();
    }

}
