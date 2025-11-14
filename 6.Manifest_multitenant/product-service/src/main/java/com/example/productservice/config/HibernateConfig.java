package com.example.productservice.config;

import com.example.productservice.util.SchemaMultiTenantConnectionProvider;
import com.example.productservice.util.SchemaTenantResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class HibernateConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource,
            SchemaMultiTenantConnectionProvider multiTenantConnectionProvider,
            SchemaTenantResolver tenantIdentifierResolver) {

        Map<String, Object> jpaProperties = new HashMap<>();

        // Use literal strings for the multi-tenancy strategy to avoid enum/version issues.
        jpaProperties.put("hibernate.multiTenancy", "SCHEMA"); // key is the stable property name
        jpaProperties.put("hibernate.multi_tenant_connection_provider", multiTenantConnectionProvider);
        jpaProperties.put("hibernate.tenant_identifier_resolver", tenantIdentifierResolver);

        // dialect — set appropriate HANA dialect or other DB dialect
//        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.HANARowStoreDialect");
        jpaProperties.put("hibernate.show_sql", true);

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.example"); // adjust
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaPropertyMap(jpaProperties);
        return emf;
    }
}
