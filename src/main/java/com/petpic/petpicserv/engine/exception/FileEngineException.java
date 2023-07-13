package com.petpic.petpicserv.engine.exception;

public class FileEngineException extends RuntimeException {
    public FileEngineException(String message) {
        super(message);
    }

    public FileEngineException(String message, Throwable cause) {
        super(message, cause);
    }
}
