package com.gabriel.hksongguesser.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ArquivoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public ArquivoNaoEncontradoException(String message) {
        super(message);
    }

    public ArquivoNaoEncontradoException(Long musicaId) {
        this(String.format("Arquivo não encontrado para música de id: #%s", musicaId));
    }
}
