package com.example.productservice.controller;

import com.example.productservice.exception.NotAuthorizedException;
import com.sap.cloud.sdk.cloudplatform.connectivity.*;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.sap.cloud.security.xsuaa.token.Token;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {


    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readAll(@AuthenticationPrincipal Token token) {

        if (!token.getAuthorities().contains(new SimpleGrantedAuthority("Display"))) {
            log.info("User {} was denied entry", token.getEmail());
            throw new NotAuthorizedException("This operation requires \"Display\" scope");
        }

        log.info("User {} was logged in", token.getEmail());

        String responseString = "";
        try {
            HttpDestination destination = DestinationAccessor.getLoader().tryGetDestination("product-service")
                    .get().asHttp();
            HttpClient client = HttpClientAccessor.getHttpClient(destination);
            HttpGet httpGet = new HttpGet("/hello");
            HttpResponse httpResponse = client.execute(httpGet);
            responseString = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> result = new HashMap<>();
        result.put("responseString", responseString);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/check-destination", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> checkDestination() {
        try {
            HttpDestination dest = DestinationAccessor.getDestination("product-service").asHttp();
            return Map.of("Destination found: ", dest.getUri().toString());
        } catch (Exception e) {
            return Map.of("Destination not found: ", e.getMessage());
        }
    }

}
