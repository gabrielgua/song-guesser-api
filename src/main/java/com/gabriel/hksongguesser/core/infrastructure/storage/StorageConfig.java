package com.gabriel.hksongguesser.core.infrastructure.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.gabriel.hksongguesser.domain.service.MusicaArquivoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Autowired
    private StorageProperties properties;

    @Bean
    @ConditionalOnProperty(name = "song-guesser.storage.tipo", havingValue = "s3")
    public AmazonS3 amazonS3() {
        var credentials = new BasicAWSCredentials(
            properties.getS3().getIdChaveAcesso(),
            properties.getS3().getChaveAcessoSecreta());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(properties.getS3().getRegiao())
                .build();
    }

    @Bean
    public MusicaArquivoStorageService storageService() {
        if (properties.getTipo().equals(StorageProperties.TipoStorage.S3)) {
            return new S3ArquivoMusicaService();
        } else {
            return new LocalArquivoMusicaService();
        }
    }
}
