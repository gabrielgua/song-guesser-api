package com.gabriel.hksongguesser.domain.exception;

public class MusicaJaPossuiAlternativaException extends NegocioException {

    public MusicaJaPossuiAlternativaException(String message) {
        super(message);
    }

    public MusicaJaPossuiAlternativaException(String message, Throwable cause) {
        super(message, cause);
    }
}
