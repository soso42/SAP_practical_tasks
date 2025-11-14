package com.example.productservice.util;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class SchemaTenantResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = TenantContext.getTenantId();
        return tenant != null ? TenantUtils.toSchemaName(tenant) : "DBADMIN"; // default schema
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

}
