package com.gabriel.hksongguesser.domain.service;

import com.gabriel.hksongguesser.core.config.ConfigProperties;
import com.gabriel.hksongguesser.core.infrastructure.storage.StorageProperties;
import com.gabriel.hksongguesser.domain.exception.ArquivoNaoEncontradoException;
import com.gabriel.hksongguesser.domain.model.ArquivoMusica;
import com.gabriel.hksongguesser.domain.repository.MusicaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

@Service
@AllArgsConstructor
public class ArquivoMusicaService {

    private MusicaRepository musicaRepository;
    private MusicaArquivoStorageService storageService;
    private StorageProperties storageProperties;
    private ConfigProperties configProperties;

    @Transactional
    public ArquivoMusica salvar(ArquivoMusica arquivo, InputStream inputStream) {
        var musicaId = arquivo.getMusica().getId();
        var arquivoExistente = musicaRepository.findArquivoById(musicaId);
        var nomeArquivoExistente = "";

        if (arquivoExistente.isPresent()) {
            musicaRepository.delete(arquivoExistente.get());
            nomeArquivoExistente = arquivoExistente.get().getNomeArquivo();
        }

        var novoArquivoMusica = MusicaArquivoStorageService.MusicaArquivoStorageRequest.builder()
                .nomeArquivo(arquivo.getNomeArquivo())
                .contentType(arquivo.getContentType())
                .inputStream(inputStream)
                .build();

        storageService.substituir(nomeArquivoExistente, novoArquivoMusica);

        arquivo.setDiretorio(gerarDiretorioArquivo(arquivo.getNomeArquivo(), musicaId));
        return musicaRepository.save(arquivo);
    }

    public ArquivoMusica buscarPorId(Long musicaId) {
        return musicaRepository.findArquivoById(musicaId).orElseThrow(() -> new ArquivoNaoEncontradoException(musicaId));
    }

    private String gerarDiretorioArquivo(String nomeArquivo, Long musicaId) {
        if (storageProperties.getTipo().equals(StorageProperties.TipoStorage.S3)) {
            return storageService.gerarUrlArquivo(nomeArquivo);
        } else {
            return String.format("%s/musicas/%d/arquivo", configProperties.getUrl(), musicaId);
        }
    }

    @Transactional
    public void remover(ArquivoMusica arquivoMusica) {
        storageService.remover(arquivoMusica.getNomeArquivo());
    }
}
