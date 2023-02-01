package com.gabriel.hksongguesser.domain.repository;

import com.gabriel.hksongguesser.domain.model.ArquivoMusica;
import com.gabriel.hksongguesser.domain.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MusicaRepository extends JpaRepository<Musica, Long>, MusicaRepositoryQueries {

    @Query("SELECT a FROM ArquivoMusica a WHERE a.musica.id = :musicaId")
    Optional<ArquivoMusica> findArquivoById(Long musicaId);


}
