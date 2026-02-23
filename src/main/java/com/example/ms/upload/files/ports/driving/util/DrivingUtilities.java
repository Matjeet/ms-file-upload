package com.example.ms.upload.files.ports.driving.util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

import static com.example.ms.upload.files.domain.util.FileConstants.HEADER_CORRELATION_ID;

public class DrivingUtilities {

    private DrivingUtilities() {}

    public static String generateCorrelationId(HttpServletRequest request) {

        String correlationId = request.getHeader(HEADER_CORRELATION_ID);

        if(correlationId == null || correlationId.trim().isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }
        return correlationId;
    }
}
