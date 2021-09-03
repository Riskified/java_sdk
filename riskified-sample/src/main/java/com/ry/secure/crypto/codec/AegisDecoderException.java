package com.ry.secure.crypto.codec;

public class AegisDecoderException extends Exception {
    private static final long serialVersionUID = 1L;

    public AegisDecoderException() {
    }

    public AegisDecoderException(String message) {
        super(message);
    }

    public AegisDecoderException(String message, Throwable cause) {
        super(message, cause);
    }

    public AegisDecoderException(Throwable cause) {
        super(cause);
    }
}
