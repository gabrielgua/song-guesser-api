package com.gabriel.hksongguesser.core.infrastructure;

import com.gabriel.hksongguesser.domain.model.ArquivoMusica;
import com.gabriel.hksongguesser.domain.repository.MusicaRepositoryQueries;
import com.gabriel.hksongguesser.domain.service.ArquivoMusicaService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class MusicaRepositoryImpl implements MusicaRepositoryQueries {

    @Autowired
    private EntityManager manager;

    @Override
    @Transactional
    public ArquivoMusica save(ArquivoMusica arquivo) {
        return manager.merge(arquivo);
    }

    @Override
    @Transactional
    public void delete(ArquivoMusica arquivo) {
        manager.remove(arquivo);
    }
}
