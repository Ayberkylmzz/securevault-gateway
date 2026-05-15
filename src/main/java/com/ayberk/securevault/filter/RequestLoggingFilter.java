package com.ayberk.securevault.filter;

import com.ayberk.securevault.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestLoggingFilter implements GlobalFilter {

    private final AuditLogService auditLogService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();

        String logMessage = String.format("Zaman: %s | Metod: %s | Yol: %s", LocalDateTime.now(), method, path);

        auditLogService.logEvent(logMessage);
        log.info("🚨 GATEWAY YAKALADI: {}", logMessage);

        return chain.filter(exchange);
    }
}