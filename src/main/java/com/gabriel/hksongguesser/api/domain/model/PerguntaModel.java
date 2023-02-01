package com.gabriel.hksongguesser.api.domain.model;

import com.gabriel.hksongguesser.domain.model.Musica;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PerguntaModel {

    private Long id;
    private MusicaModel musica;
    private Set<AlternativaModel> alternativas;
}
