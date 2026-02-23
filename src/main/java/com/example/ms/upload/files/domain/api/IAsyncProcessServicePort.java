package com.example.ms.upload.files.domain.api;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IAsyncProcessServicePort {

    void processFileAsync(UUID jobId, MultipartFile file, String correlationId);
}
