package com.gabriel.hksongguesser.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String senha;
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;
}
