package io.picky.panda.exception.model;

import io.picky.panda.exception.ErrorCode;

public class ConflictException extends PickyPandaException {

    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}
