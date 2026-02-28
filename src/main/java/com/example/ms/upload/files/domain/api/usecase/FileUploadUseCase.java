package com.example.ms.upload.files.domain.api.usecase;

import com.example.ms.upload.files.domain.api.IAsyncProcessServicePort;
import com.example.ms.upload.files.domain.api.IFileJobTrackerServicePort;
import com.example.ms.upload.files.domain.api.IFileUploadServicePort;
import com.example.ms.upload.files.domain.util.FileJobStatus;
import com.example.ms.upload.files.ports.driving.dto.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.logging.Logger;

import static com.example.ms.upload.files.domain.util.FileConstants.*;
import static com.example.ms.upload.files.domain.util.FileMessageConstants.*;

public class FileUploadUseCase implements IFileUploadServicePort {

    Logger log = Logger.getLogger(FileUploadUseCase.class.getName());

    private final IFileJobTrackerServicePort fileJobTrackerService;
    private final IAsyncProcessServicePort asyncProcessService;

    protected static final String[] ALLOWED_EXTENSIONS = {".csv", ".CSV"};

    public FileUploadUseCase(
            IFileJobTrackerServicePort fileJobTrackerService,
            IAsyncProcessServicePort asyncProcessService) {
        this.fileJobTrackerService = fileJobTrackerService;
        this.asyncProcessService = asyncProcessService;
    }

    @Override
    public FileUploadResponse initiateUpload(MultipartFile file) {

        log.info("Initiating FileUpload using FileUploadUseCase.");

        UUID jobId = UUID.randomUUID();

        validateFile(file);

        fileJobTrackerService.createJob(jobId, file.getOriginalFilename());

        asyncProcessService.processFileAsync(jobId, file);

        log.info("FileUpload using FileUploadUseCase.");

        return new FileUploadResponse(
                jobId,
                FileJobStatus.Status.PROCESSING.toString(),
                ACCEPTED_FILE_MSG,
                file.getOriginalFilename()
        );
    }

    private void validateFile(MultipartFile file) {

        log.info("Validating FileUpload using FileUploadUseCase.");

        if(file.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_FILE_ERR_MSG);
        }

        if(file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(MAX_FILE_SIZE_ERR_MSG);
        }

        String originalFilename = file.getOriginalFilename();
        if(originalFilename == null || !hasCsvExtension(originalFilename)) {
            throw new IllegalArgumentException(EXT_FILE_ERR_MSG);
        }

        if(originalFilename.contains(FILENAME_SUSPECT_CHARS)) {
            throw new IllegalArgumentException(INVALID_FILENAME_ERR_MSG);
        }

        log.info("Validating FileUpload using FileUploadUseCase.");
    }

    private boolean hasCsvExtension(String filename) {
        String lowerCaseFilename = filename.toLowerCase();
        for(String ext : ALLOWED_EXTENSIONS) {
            if(lowerCaseFilename.endsWith(ext.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}
