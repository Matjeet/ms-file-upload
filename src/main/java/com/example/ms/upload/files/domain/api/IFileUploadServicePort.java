package com.example.ms.upload.files.domain.api;

import com.example.ms.upload.files.ports.driving.dto.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadServicePort {

    FileUploadResponse initiateUpload(MultipartFile file, String correlationId);
}
