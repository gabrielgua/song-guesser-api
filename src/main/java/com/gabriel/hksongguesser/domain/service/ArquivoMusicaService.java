package com.gabriel.hksongguesser.domain.service;

import com.gabriel.hksongguesser.domain.exception.MusicaNaoEncontradaException;
import com.gabriel.hksongguesser.domain.model.ArquivoMusica;
import com.gabriel.hksongguesser.domain.model.Musica;
import com.gabriel.hksongguesser.domain.repository.MusicaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

@Service
@AllArgsConstructor
public class ArquivoMusicaService {

    private MusicaRepository musicaRepository;

    private MusicaArquivoStorageService storageService;

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
        return musicaRepository.save(arquivo);
    }

    public ArquivoMusica buscarPorId(Long musicaId) {
        return musicaRepository.findArquivoById(musicaId).orElseThrow(() -> new MusicaNaoEncontradaException(musicaId));
    }
}
