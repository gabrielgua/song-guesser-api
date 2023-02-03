package com.gabriel.hksongguesser.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemaType {

    ERRO_GENERICO("/erro-generico", "Erro genérico"),

    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada");

    private final String uri;
    private final String title;

    ProblemaType(String path, String title) {
        this.title = title;
        this.uri = "https://song.api/errors" + path;
    }
}
