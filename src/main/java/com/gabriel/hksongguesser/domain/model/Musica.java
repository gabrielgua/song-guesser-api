package com.gabriel.hksongguesser.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Musica {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @OneToOne(mappedBy = "musica")
    private Alternativa alternativa;

    @OneToOne(mappedBy = "musica", cascade = CascadeType.REMOVE)
    private ArquivoMusica arquivo;
}
