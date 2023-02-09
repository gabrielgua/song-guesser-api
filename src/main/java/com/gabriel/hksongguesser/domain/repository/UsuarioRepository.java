package com.gabriel.hksongguesser.domain.repository;

import com.gabriel.hksongguesser.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Long, Usuario> {

    Optional<Usuario> findByEmail(String email);
}
