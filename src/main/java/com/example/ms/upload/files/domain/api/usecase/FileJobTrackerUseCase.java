package com.example.ms.upload.files.domain.api.usecase;

import com.example.ms.upload.files.domain.api.IFileJobTrackerServicePort;
import com.example.ms.upload.files.domain.util.FileJobStatus;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FileJobTrackerUseCase implements IFileJobTrackerServicePort {

    private final Map<UUID, FileJobStatus> jobStore = new ConcurrentHashMap<>();

    @Override
    public void createJob(UUID jobId, String filename, String correlationId) {
        FileJobStatus jobStatus = new FileJobStatus(jobId);
        jobStatus.setFileName(filename);
        jobStatus.setOriginalFileName(filename);
        jobStatus.setStatus(FileJobStatus.Status.PROCESSING);

        Map<String, Object> metaData = Map.of(
                "correlationId", correlationId,
                "createdAt", jobStatus.getCreatedAt(),
                "originalFileName", filename
        );

        jobStatus.setMetaData(metaData);

        jobStore.put(jobId, jobStatus);
    }

    @Override
    public void updateStatus(UUID jobId, FileJobStatus.Status status, String message) {
        FileJobStatus jobStatus = jobStore.get(jobId);

        if(jobStatus != null) {
            jobStatus.setStatus(status);
            jobStatus.setUpdatedAt(LocalDateTime.now());

            if(status == FileJobStatus.Status.FAILED) {
                jobStatus.setErrorMessage(message);
            }
        }
    }

    @Override
    public FileJobStatus getJobStatus(UUID jobId) {
        return jobStore.get(jobId);
    }

    @Override
    public boolean jobExists(UUID jobId) {
        return jobStore.containsKey(jobId);
    }
}
