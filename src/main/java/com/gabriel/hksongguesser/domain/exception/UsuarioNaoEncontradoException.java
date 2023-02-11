package com.gabriel.hksongguesser.domain.exception;


public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNaoEncontradoException(Long usuarioId) {
        this(String.format("Usuário de id: #%d não encontrado.", usuarioId));
    }
}
