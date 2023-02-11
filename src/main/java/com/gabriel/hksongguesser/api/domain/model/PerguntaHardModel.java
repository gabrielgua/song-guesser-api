package com.gabriel.hksongguesser.api.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PerguntaHardModel {

    private List<MusicaModel> musicas;
    private List<AlternativaModel> alternativas;
}
