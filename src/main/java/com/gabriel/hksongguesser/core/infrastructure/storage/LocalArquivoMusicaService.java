package com.gabriel.hksongguesser.core.infrastructure.storage;

import com.gabriel.hksongguesser.domain.exception.NegocioException;
import com.gabriel.hksongguesser.domain.service.MusicaArquivoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalArquivoMusicaService implements MusicaArquivoStorageService {


    @Autowired
    private StorageProperties properties;

    @Override
    public void armazenar(MusicaArquivoStorageRequest request) {
        Path path = getArquivoPath(request.getNomeArquivo());

        try {
            FileCopyUtils.copy(request.getInputStream(), Files.newOutputStream(path));
        } catch (Exception ex) {
            throw new NegocioException("Não foi possível armazenar o arquivo da música", ex);
        }

    }
    @Override
    public MusicaArquivoStorageModel recuperarArquivo(String nomeArquivoMusica) {
        try {
            Path path = getArquivoPath(nomeArquivoMusica);
            return MusicaArquivoStorageModel.builder()
                    .inputStream(Files.newInputStream(path))
                    .build();
        } catch (Exception ex) {
            throw new NegocioException("Não foi possível recuperar o arquivo da música", ex);
        }
    }

    @Override
    public void remover(String nomeArquivoMusica) {
        Path path = getArquivoPath(nomeArquivoMusica);
        try {
            Files.deleteIfExists(path);
        } catch (Exception ex) {
            throw new NegocioException("Não foi possível remover o arquivo do armazenamento", ex);
        }
    }

    @Override
    public String gerarUrlArquivo(String nomeArquivo) {
        return getArquivoPath(nomeArquivo).toString();
    }

    public Path getArquivoPath(String nomeArquivoMusica) {
        return properties.getLocal().getDiretorioArquivos().resolve(Path.of(nomeArquivoMusica));
    }
}
