package com.gabriel.hksongguesser.domain.exception;

public class AlternativaNaoEncontradaException extends EntidadeNaoEncontradaException {

    public AlternativaNaoEncontradaException(String message) {
        super(message);
    }

    public AlternativaNaoEncontradaException(Long id) {
        this(String.format("Alternativa não encontrada: %s", id));
    }
}


