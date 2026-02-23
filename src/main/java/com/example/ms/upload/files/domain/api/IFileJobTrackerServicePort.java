package com.example.ms.upload.files.domain.api;

import com.example.ms.upload.files.domain.util.FileJobStatus;

import java.util.UUID;

public interface IFileJobTrackerServicePort {

    void createJob(UUID jobId, String fileName, String correlationId);

    void updateStatus(UUID jobId, FileJobStatus.Status status, String message);

    FileJobStatus getJobStatus(UUID jobId);

    boolean jobExists(UUID jobId);
}
