package io.picky.panda.exception.model;

import io.picky.panda.exception.ErrorCode;
import lombok.Getter;

@Getter
public class PickyPandaException extends RuntimeException {

    private final ErrorCode errorCode;

    public PickyPandaException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
