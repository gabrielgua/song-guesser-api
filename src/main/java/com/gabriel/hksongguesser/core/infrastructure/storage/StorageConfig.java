package com.gabriel.hksongguesser.core.infrastructure.storage;

import com.gabriel.hksongguesser.domain.service.MusicaArquivoStorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Bean
    public MusicaArquivoStorageService storageService() {
        return new LocalArquivoMusicaService();
    }
}
