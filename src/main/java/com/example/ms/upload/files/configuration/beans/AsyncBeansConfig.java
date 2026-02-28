package com.example.ms.upload.files.configuration.beans;

import com.example.ms.upload.files.configuration.properties.BusinessProperties;
import com.example.ms.upload.files.domain.api.IAsyncProcessServicePort;
import com.example.ms.upload.files.domain.api.IFileJobTrackerServicePort;
import com.example.ms.upload.files.domain.api.ITempFileServicePort;
import com.example.ms.upload.files.domain.api.usecase.AsyncProcessUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AsyncBeansConfig {

    private final ITempFileServicePort tempFileService;
    private final IFileJobTrackerServicePort fileJobTrackerService;
    private final BusinessProperties businessProperties;

    @Bean
    public IAsyncProcessServicePort asyncProcessServicePort() {
        return new AsyncProcessUseCase(
                tempFileService,
                fileJobTrackerService,
                businessProperties
        );
    }

}
