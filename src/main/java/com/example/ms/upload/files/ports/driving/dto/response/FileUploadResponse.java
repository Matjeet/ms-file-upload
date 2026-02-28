package com.example.ms.upload.files.ports.driving.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class FileUploadResponse {
    private UUID jobId;
    private String status;
    private String message;
    private LocalDateTime timestamp;
    private String fileName;

    public FileUploadResponse() {}

    public FileUploadResponse(UUID jobId, String status, String message, String fileName) {
        this.jobId = jobId;
        this.status = status;
        this.message = message;
        this.fileName = fileName;
        this.timestamp = LocalDateTime.now();
    }

    public UUID getJobId() {
        return jobId;
    }

    public void setJobId(UUID jobId) {
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
