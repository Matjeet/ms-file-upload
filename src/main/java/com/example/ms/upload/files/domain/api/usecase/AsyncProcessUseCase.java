package com.example.ms.upload.files.domain.api.usecase;

import com.example.ms.upload.files.configuration.properties.BusinessProperties;
import com.example.ms.upload.files.domain.api.IAsyncProcessServicePort;
import com.example.ms.upload.files.domain.api.IFileJobTrackerServicePort;
import com.example.ms.upload.files.domain.api.ITempFileServicePort;
import com.example.ms.upload.files.domain.util.FileJobStatus;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.ms.upload.files.domain.util.FileConstants.MAX_FILE_LINES;
import static com.example.ms.upload.files.domain.util.FileMessageConstants.*;
import static com.example.ms.upload.files.domain.util.FileMessageConstants.PROCESS_FILE_FAILED_ERR_MSG;

public class AsyncProcessUseCase implements IAsyncProcessServicePort {

    Logger log = Logger.getLogger(AsyncProcessUseCase.class.getName());

    private final ITempFileServicePort tempFileService;
    private final IFileJobTrackerServicePort fileJobTrackerService;
    private final BusinessProperties businessProperties;

    public AsyncProcessUseCase(ITempFileServicePort tempFileService, IFileJobTrackerServicePort fileJobTrackerService, BusinessProperties businessProperties) {
        this.tempFileService = tempFileService;
        this.fileJobTrackerService = fileJobTrackerService;
        this.businessProperties = businessProperties;
    }

    @Async("ioBoundTaskExecutor")
    @Override
    public void processFileAsync(UUID jobId, MultipartFile file, String correlationId) {

        MDC.put("correlationId", correlationId);
        String tempDir = businessProperties.getFile().getTempDir();

        try {
            log.info("Iniciando el proceso asíncrono para el procesamiento del archivo");

            fileJobTrackerService.updateStatus(jobId, FileJobStatus.Status.VALIDATING, VALIDATING_FILE_MSG);

            Path tempFilePath = tempFileService.saveTempFile(file, jobId, tempDir);
            log.info("Archivo almacenado temporalmente");

            fileJobTrackerService.updateStatus(jobId, FileJobStatus.Status.STORED, FILE_STORED_SUCCESS_MSG);

            performExtendedValidation(tempFilePath);

            fileJobTrackerService.updateStatus(jobId, FileJobStatus.Status.COMPLETED, FILE_VALIDATED_SUCCESS_MSG);

            log.info("Finalizado el proceso asíncrono completado");

        } catch (Exception ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
            fileJobTrackerService.updateStatus(jobId, FileJobStatus.Status.FAILED, PROCESS_FILE_FAILED_ERR_MSG + ex.getMessage());

            tempFileService.cleanUpTempFile(jobId, file.getOriginalFilename(), tempDir);
        } finally {
            MDC.clear();
        }
    }

    private void performExtendedValidation(Path filePath) throws IOException {
        log.info("Inicio de validaciones extendidas");

        long fileSize = Files.size(filePath);

        if(fileSize == 0) {
            throw new IOException(EMPTY_OR_CORRUPTED_FILE_ERR_MSG);
        }

        try (var lines = Files.lines(filePath).limit(MAX_FILE_LINES)) {
            long lineCount = lines.count();
            if(lineCount == 0) {
                throw new IOException(EMPTY_FILE_ERR_MSG);
            }

        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
        }

        log.info("Fin de validaciones extendidas");
    }
}
