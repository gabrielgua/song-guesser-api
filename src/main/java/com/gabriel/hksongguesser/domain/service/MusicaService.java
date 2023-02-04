package com.gabriel.hksongguesser.domain.service;

import com.gabriel.hksongguesser.domain.exception.EntidadeEmUsoException;
import com.gabriel.hksongguesser.domain.exception.MusicaNaoEncontradaException;
import com.gabriel.hksongguesser.domain.model.Musica;
import com.gabriel.hksongguesser.domain.repository.MusicaRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor

public class MusicaService {

    public MusicaRepository repository;
    private ArquivoMusicaService arquivoService;


    public List<Musica> buscarTodos() {
        return repository.findAll();
    }



    public Musica buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new MusicaNaoEncontradaException(id));
    }

    public Musica salvar(Musica musica) {
        return repository.save(musica);
    }

    @Transactional
    public void remover(Musica musica) {
        try {
            repository.delete(musica);
            repository.flush();
            if (musica.getArquivo() != null) {
                arquivoService.remover(musica.getArquivo());
            }
        } catch (EmptyResultDataAccessException ex) {
            throw new MusicaNaoEncontradaException(ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(String.format("Música de id: #%s possui uma alternativa atrelada e não pode ser removida.", musica.getId()));
        }
    }

}
