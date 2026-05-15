package com.ayberk.securevault.filter;

import com.ayberk.securevault.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    public static class Config {}

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                log.warn("Authorization header eksik");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT Token eksik!");
            }

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("Geçersiz JWT formatı");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Geçersiz JWT formatı!");
            }

            String token = authHeader.substring(7);

            try {
                jwtUtil.validateToken(token);
                log.info("JWT Token başarıyla doğrulandı");
            } catch (JwtException ex) {
                log.error("JWT validasyonu başarısız: {}", ex.getMessage());
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Geçersiz JWT Token!");
            } catch (Exception ex) {
                log.error("Beklenmeyen hata JWT validasyonunda: {}", ex.getMessage());
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT validasyonu başarısız!");
            }

            return chain.filter(exchange);
        };
    }
}