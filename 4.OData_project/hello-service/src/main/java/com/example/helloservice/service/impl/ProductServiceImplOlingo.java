package com.example.helloservice.service.impl;

import com.example.helloservice.dto.ProductDTO;
import com.example.helloservice.service.ProductService;
import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientEntitySet;
import org.apache.olingo.client.api.uri.URIBuilder;
import org.apache.olingo.client.core.ODataClientFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImplOlingo implements ProductService {

    private static final String SERVICE_ROOT = "https://services.odata.org/V4/Northwind/Northwind.svc/";

    private final ODataClient client = ODataClientFactory.getClient();


    public ProductDTO getProductById(Long id) {
        URIBuilder uriBuilder = client.newURIBuilder(SERVICE_ROOT)
                .appendEntitySetSegment("Products")
                .appendKeySegment(id);

        URI uri = uriBuilder.build();

        ODataRetrieveResponse<ClientEntity> response =
                client.getRetrieveRequestFactory().getEntityRequest(uri).execute();

        ClientEntity entity = response.getBody();

        return productMapper(entity);
    }

    public List<ProductDTO> getTopProducts(int top) {
        URIBuilder uriBuilder = client.newURIBuilder(SERVICE_ROOT)
                .appendEntitySetSegment("Products")
                .top(top);

        URI uri = uriBuilder.build();

        ODataRetrieveResponse<ClientEntitySet> response =
                client.getRetrieveRequestFactory()
                        .getEntitySetRequest(uri)
                        .execute();

        ClientEntitySet entitySet = response.getBody();

        List<ProductDTO> result = new ArrayList<>();
        for (ClientEntity entity : entitySet.getEntities()) {
            result.add(productMapper(entity));
        }

        return result;
    }

    private ProductDTO productMapper(ClientEntity entity) {
        // Safe extraction with null checks
        Integer id = null;
        if (entity.getProperty("ProductID") != null && entity.getProperty("ProductID").getValue() != null) {
            Object idValue = entity.getProperty("ProductID").getValue();
            id = (idValue instanceof Number) ? ((Number) idValue).intValue()
                    : Integer.parseInt(idValue.toString());
        }

        String name = (entity.getProperty("ProductName") != null && entity.getProperty("ProductName").getValue() != null)
                ? entity.getProperty("ProductName").getValue().toString()
                : null;

        BigDecimal price = null;
        if (entity.getProperty("UnitPrice") != null && entity.getProperty("UnitPrice").getValue() != null) {
            Object v = entity.getProperty("UnitPrice").getValue();
            // Olingo may return either BigDecimal or Double depending on type, handle both
            if (v instanceof BigDecimal) price = (BigDecimal) v;
            else if (v instanceof Number) price = BigDecimal.valueOf(((Number) v).doubleValue());
            else price = new BigDecimal(v.toString());
        }

        return new ProductDTO(id, name, price);
    }

}
