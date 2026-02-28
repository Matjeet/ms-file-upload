package com.example.ms.upload.files.ports.driving.controller;

import com.example.ms.upload.files.configuration.properties.BusinessProperties;
import com.example.ms.upload.files.domain.api.IFileJobTrackerServicePort;
import com.example.ms.upload.files.domain.api.IFileUploadServicePort;
import com.example.ms.upload.files.domain.api.ITempFileServicePort;
import com.example.ms.upload.files.domain.util.FileJobStatus;
import com.example.ms.upload.files.ports.driving.dto.response.FileUploadResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static com.example.ms.upload.files.domain.util.FileConstants.ERROR_STATUS;
import static com.example.ms.upload.files.domain.util.FileConstants.REJECTED_STATUS;
import static com.example.ms.upload.files.domain.util.FileMessageConstants.INTERNAL_ERROR_MSG;
import static com.example.ms.upload.files.ports.driving.util.DrivingUtilities.generateCorrelationId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final ITempFileServicePort tempFileServicePort;
    private final IFileJobTrackerServicePort fileJobTrackerServicePort;
    private final IFileUploadServicePort fileUploadServicePort;
    private final BusinessProperties businessProperties;

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file")MultipartFile file,
            HttpServletRequest request) {

        logger.info("Received file upload request: {} ({} bytes)",
                file.getOriginalFilename(), file.getSize());

        try {
            FileUploadResponse response = fileUploadServicePort.initiateUpload(file);

            logger.info("File accepted for async processing. JobId: {}", response.getJobId());

            return ResponseEntity.accepted().body(response);
        } catch (IllegalArgumentException e) {
            logger.warn("File validation failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(
                    new FileUploadResponse(null, REJECTED_STATUS, e.getMessage(), file.getOriginalFilename())
            );
        } catch (Exception e) {
            logger.error("Unexpected error while uploading file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new  FileUploadResponse(null, ERROR_STATUS, INTERNAL_ERROR_MSG, file.getOriginalFilename())
            );
        } finally {
            MDC.clear();
        }
    }

    @GetMapping("/status/{jobId}")
    public ResponseEntity<FileJobStatus> getStatus(@PathVariable("jobId") UUID jobId) {
        FileJobStatus jobStatus = fileJobTrackerServicePort.getJobStatus(jobId);

        if(jobStatus == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(jobStatus);
    }
}
