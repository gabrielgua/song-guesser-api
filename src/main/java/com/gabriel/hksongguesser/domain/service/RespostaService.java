package com.gabriel.hksongguesser.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RespostaService {

    private MusicaService musicaService;
    private AlternativaService alternativaService;

    public boolean isAltenativaCorreta(Long musicaId, Long alternativaId) {
        var musica = musicaService.buscarPorId(musicaId);
        var alternativa = alternativaService.buscarPorId(alternativaId);

        return musica.getId().equals(alternativa.getMusica().getId());
    }
}
