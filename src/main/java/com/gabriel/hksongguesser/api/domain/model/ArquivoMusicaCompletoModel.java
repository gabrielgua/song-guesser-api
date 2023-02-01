package com.gabriel.hksongguesser.api.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArquivoMusicaCompletoModel {
    private MusicaModel musica;
    private String nomeArquivo;
    private String contentType;
    private Long tamanho;
    private String diretorio;

}
