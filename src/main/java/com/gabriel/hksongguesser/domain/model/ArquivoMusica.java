package com.gabriel.hksongguesser.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ArquivoMusica {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "musica_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private Musica musica;
    private String nomeArquivo;
    private String contentType;
    private Long tamanho;
    private String diretorio;
}
