package io.picky.panda.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    // Auth
    GOOGLE_LOGIN_SUCCESS(HttpStatus.CREATED, "구글 로그인에 성공했습니다."),

    // User
    GET_PROFILE_SUCCESS(HttpStatus.OK, "프로필 조회에 성공했습니다."),
    GET_USER_RESTAURANT_SUCCESS(HttpStatus.OK, "유저 관련 가게 조회에 성공했습니다."),

    // Restaurant
    SAVE_RESTAURANT_SUCCESS(HttpStatus.CREATED, "가게 저장에 성공했습니다."),
    SAVE_DESCRIPTION_SUCCESS(HttpStatus.CREATED, "가게 설명 저장에 성공했습니다."),
    GET_RESTAURANT_SUCCESS(HttpStatus.OK, "가게 조회에 성공했습니다."),
    BOOKMARK_RESTAURANT_SUCCESS(HttpStatus.OK, "가게 저장에 성공했습니다."),
    AGREE_DESCRIPTION_SUCCESS(HttpStatus.OK, "가게 설명 동의에 성공했습니다."),
    GET_RESTAURANT_LIST_SUCCESS(HttpStatus.OK, "가게 목록 조회에 성공했습니다."),

    // review
    SAVE_REVIEW_SUCCESS(HttpStatus.CREATED, "리뷰 등록에 성공했습니다."),
    GET_ALL_REVIEW_SUCCESS(HttpStatus.OK, "리뷰 조회에 성공했습니다."),
    DELETE_REVIEW_SUCCESS(HttpStatus.OK, "리뷰 삭제에 성공했습니다."),
    ;

    private final HttpStatus code;
    private final String message;
}
