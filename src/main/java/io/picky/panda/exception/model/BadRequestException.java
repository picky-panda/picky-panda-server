package io.picky.panda.exception.model;

import io.picky.panda.exception.ErrorCode;

public class BadRequestException extends PickyPandaException {

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
