package com.gabriel.hksongguesser.api.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicaCompletaModel {

    private Long id;
    private String nome;
    private String diretorio;
    private ArquivoMusicaModel arquivo;
}
