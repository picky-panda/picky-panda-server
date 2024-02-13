package io.picky.panda.exception.model;

import io.picky.panda.exception.ErrorCode;

public class NotFoundException extends PickyPandaException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
