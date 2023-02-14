package com.gabriel.hksongguesser.domain.service;

import com.gabriel.hksongguesser.domain.model.Musica;
import com.gabriel.hksongguesser.domain.model.Pergunta;
import com.gabriel.hksongguesser.domain.repository.PerguntaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class PerguntaService {

    private PerguntaRepository repository;

    private AlternativaService alternativaService;

    public List<Pergunta> buscarTodos() {
        return repository.findAll();
    }

    public Pergunta gerarPergunta(Musica musica) {
        var pergunta = new Pergunta();
        pergunta.setId(musica.getId());
        pergunta.setMusica(musica);

        pergunta.setAlternativas(alternativaService.gerarAlternativasParaMusica(musica));
        return pergunta;
    }
}
