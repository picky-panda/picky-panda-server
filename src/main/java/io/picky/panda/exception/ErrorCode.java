package io.picky.panda.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // common
    INVALID_DATA(HttpStatus.BAD_REQUEST, "잘못된 형식의 데이터입니다."),

    // jwt
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰입니다"),
    NULL_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),

    // external
    INVALID_EXTERNAL_REQUEST_DATA(HttpStatus.BAD_REQUEST, "외부 API 요청에 잘못된 데이터가 전달됐습니다."),
    ;

    private final HttpStatus code;
    private final String message;
}
