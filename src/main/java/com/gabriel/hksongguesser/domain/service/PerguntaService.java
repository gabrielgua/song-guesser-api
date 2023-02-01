package com.gabriel.hksongguesser.domain.service;

import com.gabriel.hksongguesser.domain.model.Pergunta;
import com.gabriel.hksongguesser.domain.repository.PerguntaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class PerguntaService {

    private PerguntaRepository repository;

    public List<Pergunta> buscarTodos() {
        return repository.findAll();
    }

    public Pergunta buscarPorId(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Pergunta adicionar(Pergunta pergunta) {
        return repository.save(pergunta);
    }

    public void remover(Pergunta pergunta) {
        repository.delete(pergunta);
    }
}
