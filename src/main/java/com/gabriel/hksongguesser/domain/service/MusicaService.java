package com.gabriel.hksongguesser.domain.service;

import com.gabriel.hksongguesser.domain.exception.MusicaNaoEncontradaException;
import com.gabriel.hksongguesser.domain.model.Musica;
import com.gabriel.hksongguesser.domain.repository.MusicaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor

public class MusicaService {

    public MusicaRepository repository;


    public List<Musica> buscarTodos() {
        return repository.findAll();
    }



    public Musica buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new MusicaNaoEncontradaException(id));
    }

    public Musica salvar(Musica musica) {
        return repository.save(musica);
    }

    public void remover(Musica musica) {
        repository.delete(musica);
    }

}
