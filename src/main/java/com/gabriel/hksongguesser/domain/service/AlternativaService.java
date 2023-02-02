package com.gabriel.hksongguesser.domain.service;

import com.gabriel.hksongguesser.domain.exception.AlternativaNaoEncontradaException;
import com.gabriel.hksongguesser.domain.model.Alternativa;
import com.gabriel.hksongguesser.domain.model.Musica;
import com.gabriel.hksongguesser.domain.repository.AlternativaRespository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class AlternativaService {

    private static final Integer ALTERNATIVAS_POR_PERGUNTA = 5;

    private AlternativaRespository respository;

    public List<Alternativa> buscarTodos() {
        return respository.findAll();
    }

    public Alternativa buscarPorId(Long id) {
        return respository.findById(id).orElseThrow(() -> new AlternativaNaoEncontradaException(id));
    }

    public Alternativa adicionar(Alternativa alternativa) {
        return respository.save(alternativa);
    }

    public void remover(Alternativa alternativa) {
        respository.delete(alternativa);
    }

    public Set<Alternativa> gerarAlternativasParaMusica(Musica musica) {
        var resposta = buscarPorMusica(musica);
        Set<Alternativa> randomAlternativas = new HashSet<>();
            randomAlternativas.add(resposta.get());

            while (randomAlternativas.size() < ALTERNATIVAS_POR_PERGUNTA) {
                var alternativaGerada = getRandomAlternativa();
                randomAlternativas.add(alternativaGerada);
            }

        Collections.shuffle(Arrays.asList(randomAlternativas.toArray()));
        return randomAlternativas;
    }

    private Alternativa getRandomAlternativa() {
        var alternativasTotal = respository.count();
        var randomId = (long) (Math.random() * alternativasTotal + 1);
        return buscarPorId(randomId);
    }

    public Optional<Alternativa> buscarPorMusica(Musica musica) {
        return respository.findByMusica(musica);
    }
 }
