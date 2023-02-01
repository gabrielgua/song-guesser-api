package com.gabriel.hksongguesser.domain.repository;

import com.gabriel.hksongguesser.domain.model.Alternativa;
import com.gabriel.hksongguesser.domain.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlternativaRespository extends JpaRepository<Alternativa, Long> {

    Optional<Alternativa> findByMusica(Musica musica);

}
