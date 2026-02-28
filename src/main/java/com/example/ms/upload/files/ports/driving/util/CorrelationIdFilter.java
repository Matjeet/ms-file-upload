package com.example.ms.upload.files.ports.driving.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import static com.example.ms.upload.files.domain.util.FileConstants.CORRELATION_ID_MDC_KEY;
import static com.example.ms.upload.files.domain.util.FileConstants.HEADER_CORRELATION_ID;

@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String correlationId = geCorrelationId(request);

            MDC.put(CORRELATION_ID_MDC_KEY, correlationId);

            response.addHeader(HEADER_CORRELATION_ID, correlationId);

            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(CORRELATION_ID_MDC_KEY);
        }
    }

    private String geCorrelationId(HttpServletRequest request) {
        String correlationId = request.getHeader(HEADER_CORRELATION_ID);

        if (correlationId == null || correlationId.trim().isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }

        return correlationId;
    }
}
