package com.example.helloservice.service.impl;

import com.example.helloservice.vdm.namespaces.productsrv.Product;
import com.example.helloservice.vdm.services.DefaultAPIPRODUCTSRVService;
import com.sap.cloud.sdk.cloudplatform.connectivity.AuthenticationType;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImplSAP {

    private DefaultAPIPRODUCTSRVService service =
            new DefaultAPIPRODUCTSRVService().withServicePath("/"); // withServicePath is important

    private HttpDestination destination =
            DestinationAccessor.getDestination("northwind").asHttp();


    public Product getProductById(Long id) {

//        HttpDestination destination = DefaultHttpDestination
//                .builder("https://services.odata.org/V4/Northwind/Northwind.svc/")
//                .authenticationType(AuthenticationType.NO_AUTHENTICATION)
//                .build();

        log.info("ProductServiceImplSAP: Get product by id {}", id);
        Product product = service
                .getProductsByKey(id.intValue())
                .execute(destination);

        return product;
    }

    public List<Product> getTopProducts(int top) {

//        HttpDestination destination = DefaultHttpDestination
//                .builder("https://services.odata.org/V4/Northwind/Northwind.svc/")
//                .authenticationType(AuthenticationType.NO_AUTHENTICATION)
//                .build();

        log.info("ProductServiceImplSAP: Get product list");
        List<Product> products = service
                .getAllProducts()
                .top(top)
                .execute(destination);

        return products;
    }

}
