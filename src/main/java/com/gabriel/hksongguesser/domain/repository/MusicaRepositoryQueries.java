package com.gabriel.hksongguesser.domain.repository;

import com.gabriel.hksongguesser.domain.model.ArquivoMusica;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MusicaRepositoryQueries {


    ArquivoMusica save(ArquivoMusica arquivoMusica);

    void delete(ArquivoMusica arquivoMusica);

}
