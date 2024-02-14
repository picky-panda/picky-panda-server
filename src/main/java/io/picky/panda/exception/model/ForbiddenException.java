package io.picky.panda.exception.model;

import io.picky.panda.exception.ErrorCode;

public class ForbiddenException extends PickyPandaException {

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
