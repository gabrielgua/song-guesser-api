package com.gabriel.hksongguesser.domain.service;

import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public interface MusicaArquivoStorageService {

    void armazenar(MusicaArquivoStorageRequest request);
    void remover(String nomeArquivoMusica);

    String gerarUrlArquivo(String nomeArquivo);
    MusicaArquivoStorageModel recuperarArquivo(String nomeArquivoMusica);

    default void substituir(String nomeArquivoExistente, MusicaArquivoStorageRequest novoArquivo) {
        if (!nomeArquivoExistente.isBlank()) remover(nomeArquivoExistente);
        armazenar(novoArquivo);
    }

    @Getter
    @Builder
    class MusicaArquivoStorageRequest {
        private String nomeArquivo;
        private String contentType;
        private InputStream inputStream;
    }

    @Getter
    @Builder
    class MusicaArquivoStorageModel {
        private InputStream inputStream;
        private String url;

        public boolean temUrl() { return url != null; }
    }
}
