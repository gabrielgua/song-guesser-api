package com.gabriel.hksongguesser.domain.service;

import com.gabriel.hksongguesser.domain.exception.AlternativaNaoEncontradaException;
import com.gabriel.hksongguesser.domain.exception.EntidadeEmUsoException;
import com.gabriel.hksongguesser.domain.exception.MusicaJaPossuiAlternativaException;
import com.gabriel.hksongguesser.domain.model.Alternativa;
import com.gabriel.hksongguesser.domain.model.Musica;
import com.gabriel.hksongguesser.domain.repository.AlternativaRespository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class AlternativaService {
    private static final Integer ALTERNATIVAS_POR_PERGUNTA = 5;

    private AlternativaRespository respository;

    private MusicaService musicaService;


    public List<Alternativa> buscarTodos() {
        return respository.findAll();
    }

    public Alternativa buscarPorId(Long id) {
        return respository.findById(id).orElseThrow(() -> new AlternativaNaoEncontradaException(id));
    }

    public Alternativa salvar(Alternativa alternativa) {
        var musica = musicaService.buscarPorId(alternativa.getMusica().getId());
        if (musica.getAlternativa() != null) {
            throw new MusicaJaPossuiAlternativaException(String.format("Música de id: #%S já possui uma alternativa.", musica.getId()));
        }

        return respository.save(alternativa);
    }

    @Transactional
    public void remover(Alternativa alternativa) {
        try {
            respository.delete(alternativa);
            respository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException("Alternativa está em uso");
        }
    }

    public Set<Alternativa> gerarAlternativasParaMusica(Musica musica) {
        var resposta = buscarPorMusica(musica);
        List<Alternativa> alternativas = respository.findAll();
        Set<Alternativa> randomAlternativas = new HashSet<>();

        randomAlternativas.add(resposta);

        while (randomAlternativas.size() != alternativas.size() && randomAlternativas.size() < ALTERNATIVAS_POR_PERGUNTA) {
            var alternativaGerada = getRandomAlternativa(alternativas);
            randomAlternativas.add(alternativaGerada);

        }

        Collections.shuffle(Arrays.asList(randomAlternativas.toArray()));
        return randomAlternativas;
    }

    private Alternativa getRandomAlternativa(List<Alternativa> alternativas) {
        var alternativasTotal = respository.count();
        var randomNumber = (int) (Math.random() * alternativasTotal);

        return buscarPorId(alternativas.get(randomNumber).getId());
    }

    public Alternativa buscarPorMusica(Musica musica) {
        return respository.findByMusica(musica);
    }
 }
