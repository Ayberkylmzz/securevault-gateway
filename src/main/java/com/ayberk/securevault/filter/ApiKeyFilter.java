package com.ayberk.securevault.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
public class ApiKeyFilter extends AbstractGatewayFilterFactory<ApiKeyFilter.Config> {

    @Value("${securevault.api-key}")
    private String secureApiKey;

    public ApiKeyFilter() {
        super(Config.class);
    }

    public static class Config { }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            String apiKey = exchange.getRequest().getHeaders().getFirst("X-API-KEY");

            if (apiKey == null || !constantTimeEquals(apiKey, secureApiKey)) {
                log.warn("Geçersiz veya eksik API anahtarı");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Geçersiz veya eksik API anahtarı!");
            }

            log.info("API Key başarıyla doğrulandı");
            return chain.filter(exchange);
        };
    }

    private boolean constantTimeEquals(String provided, String expected) {
        if (provided == null || expected == null) {
            return false;
        }

        byte[] providedBytes = provided.getBytes();
        byte[] expectedBytes = expected.getBytes();

        if (providedBytes.length != expectedBytes.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < providedBytes.length; i++) {
            result |= providedBytes[i] ^ expectedBytes[i];
        }

        return result == 0;
    }
}