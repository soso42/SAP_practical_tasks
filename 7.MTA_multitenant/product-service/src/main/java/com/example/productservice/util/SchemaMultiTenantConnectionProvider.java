package com.example.productservice.util;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@Slf4j
public class SchemaMultiTenantConnectionProvider implements MultiTenantConnectionProvider {

    private final DataSource dataSource;

    public SchemaMultiTenantConnectionProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Called by Hibernate when it needs a "generic" connection
    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Return a generic connection
    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // Called by Hibernate when it needs a connection for a specific tenant
    @Override
    public Connection getConnection(Object tenantIdentifier) throws SQLException {
        final Connection connection = getAnyConnection();
        try (Statement stmt = connection.createStatement()) {
            // use quoting if tenantIdentifier may contain lowercase or special chars
            // For plain uppercase schema names you can use: stmt.execute("SET SCHEMA " + tenantIdentifier);
            String schema = tenantIdentifier.toString();
            stmt.execute("SET SCHEMA \"" + schema + "\"");
            log.info("Schema set to " + schema);
        } catch (SQLException e) {
            // ensure connection closed on failure to avoid leaks
            try { connection.close(); } catch (SQLException ignored) {}
            throw e;
        }
        return connection;
    }

    // Called by Hibernate to release a tenant-specific connection
    @Override
    public void releaseConnection(Object tenantIdentifier, Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            // Optionally reset to default schema before closing (not strictly necessary)
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("SET SCHEMA \"DBADMIN\"");
                log.info("schema is set to: public");
            } catch (SQLException ignore) {
                // ignore; we still must close the connection
            } finally {
                connection.close();
            }
        }
    }

    // --- Methods required by the interface ---

    @Override
    public boolean supportsAggressiveRelease() {
        // return false for typical pooled DataSource usage
        return false;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public boolean isUnwrappableAs(Class unwrapType) {
        return MultiTenantConnectionProvider.class.equals(unwrapType) ||
                SchemaMultiTenantConnectionProvider.class.equals(unwrapType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> unwrapType) {
        if (isUnwrappableAs(unwrapType)) {
            return (T) this;
        }
        throw new IllegalArgumentException("Unknown unwrap type: " + unwrapType);
    }
}