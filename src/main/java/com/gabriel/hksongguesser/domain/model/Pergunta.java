package com.gabriel.hksongguesser.domain.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pergunta {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Musica musica;
    @ManyToMany
    @JoinTable(name = "pergunta_alternativa",
        joinColumns = @JoinColumn(name = "pergunta_id"),
            inverseJoinColumns = @JoinColumn(name = "alternativa_id")
    )
    private Set<Alternativa> alternativas = new HashSet<>();

}
