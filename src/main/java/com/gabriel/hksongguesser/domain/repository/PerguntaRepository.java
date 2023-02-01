package com.gabriel.hksongguesser.domain.repository;

import com.gabriel.hksongguesser.domain.model.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
}
