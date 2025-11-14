package com.example.productservice.util;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class TenantFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        log.debug("Secured TenantFilter");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String tenant = null;

        if (auth != null && auth.getPrincipal() instanceof com.sap.cloud.security.xsuaa.token.XsuaaToken token) {

            // most reliable tenant identifier:
            tenant = token.getSubaccountId();        // the BTP subaccount
            if (tenant == null) {
                tenant = token.getZoneId();          // the identity zone (usually same as tenant)
            }

            // OR read any custom claim
            // Object value = token.getClaim("tenant");
        }

        log.debug("TenantFilter resolved tenant = {}", tenant);

        TenantContext.setTenantId(tenant);

        try {
            chain.doFilter(req, res);
        } finally {
            TenantContext.clear();
        }
    }
}
