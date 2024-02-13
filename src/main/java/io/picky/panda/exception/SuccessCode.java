package io.picky.panda.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    // Auth
    GOOGLE_LOGIN_SUCCESS(HttpStatus.CREATED, "구글 로그인에 성공했습니다."),

    // Restaurant
    SAVE_RESTAURANT_SUCCESS(HttpStatus.CREATED, "가게 저장에 성공했습니다."),
    ;

    private final HttpStatus code;
    private final String message;
}
