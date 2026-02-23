package com.example.ms.upload.files.domain.api.usecase;

import com.example.ms.upload.files.domain.api.ITempFileServicePort;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.ms.upload.files.domain.util.FileConstants.UNDERSCORE;
import static com.example.ms.upload.files.domain.util.FileMessageConstants.IO_SAVE_FILE_ERR_MSG;

public class TempFileUseCase implements ITempFileServicePort {

    Logger log = Logger.getLogger(TempFileUseCase.class.getName());

    @Override
    public Path saveTempFile(MultipartFile file, UUID jobId, String tempDir) throws IOException {

        Path tempPath = Paths.get(tempDir);
        Files.createDirectories(tempPath);

        String tempFileName = jobId.toString() + UNDERSCORE + file.getOriginalFilename();
        Path targetLocation = tempPath.resolve(tempFileName);

        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return targetLocation;
    }

    @Override
    public void cleanUpTempFile(UUID jobId, String originalFileName, String tempDir) {
        try{
            String tempFileName = jobId.toString() + UNDERSCORE + originalFileName;
            Path filePath = Paths.get(tempDir).resolve(tempFileName);

            if(Files.exists(filePath)){
                Files.delete(filePath);
            }
        } catch(IOException ex){
            log.log(Level.WARNING, IO_SAVE_FILE_ERR_MSG, ex);
        }
    }

    @Override
    public Path getTempFile(UUID jobId, String originalFileName, String tempDir) {
        String tempFileName = jobId.toString() + UNDERSCORE + originalFileName;
        return Paths.get(tempDir).resolve(tempFileName);
    }
}
