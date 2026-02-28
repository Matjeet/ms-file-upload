package com.example.ms.upload.files.domain.api;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public interface ITempFileServicePort {

    Path saveTempFile(MultipartFile file, UUID jobId, String tempDir) throws IOException;

    void cleanUpTempFile(UUID jobId, String originalFileName, String tempDir);

    Path getTempFile(UUID jobId, String originalFileName, String tempDir);
}
