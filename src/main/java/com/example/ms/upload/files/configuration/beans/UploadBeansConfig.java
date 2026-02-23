package com.example.ms.upload.files.configuration.beans;

import com.example.ms.upload.files.domain.api.IAsyncProcessServicePort;
import com.example.ms.upload.files.domain.api.IFileJobTrackerServicePort;
import com.example.ms.upload.files.domain.api.IFileUploadServicePort;
import com.example.ms.upload.files.domain.api.usecase.FileUploadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UploadBeansConfig {

    private final IFileJobTrackerServicePort fileJobTrackerService;
    private final IAsyncProcessServicePort asyncProcessService;

    @Bean
    public IFileUploadServicePort fileUploadServicePort() {
        return new FileUploadUseCase(
                fileJobTrackerService,
                asyncProcessService
        );
    }

}
