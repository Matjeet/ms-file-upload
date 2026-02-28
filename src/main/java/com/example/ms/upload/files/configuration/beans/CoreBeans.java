package com.example.ms.upload.files.configuration.beans;

import com.example.ms.upload.files.domain.api.IFileJobTrackerServicePort;
import com.example.ms.upload.files.domain.api.ITempFileServicePort;
import com.example.ms.upload.files.domain.api.usecase.FileJobTrackerUseCase;
import com.example.ms.upload.files.domain.api.usecase.TempFileUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreBeans {

    @Bean
    public IFileJobTrackerServicePort fileJobTrackerServicePort() {
        return new FileJobTrackerUseCase();
    }

    @Bean
    public ITempFileServicePort tempFileService() {
        return new TempFileUseCase();
    }
}
