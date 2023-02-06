package com.gabriel.hksongguesser.core.infrastructure.storage;

public class StorageException extends RuntimeException {

    public StorageException(String messsage) {
        super(messsage);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
