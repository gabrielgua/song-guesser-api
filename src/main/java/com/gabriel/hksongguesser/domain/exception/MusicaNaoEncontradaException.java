package com.gabriel.hksongguesser.domain.exception;

public class MusicaNaoEncontradaException extends EntidadeNaoEncontradaException {

    public MusicaNaoEncontradaException(String message) {
        super(message);
    }

    public MusicaNaoEncontradaException(Long id) {
        this(String.format("Música não encontrada: %s", id));
    }
}
