package io.picky.panda.exception;

import feign.FeignException;
import io.picky.panda.common.dto.ApiResponse;
import io.picky.panda.exception.model.PickyPandaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionAdvise {

    /**
     * 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        ErrorCode.INVALID_DATA
                ));
    }

    /**
     * external Error
     */
    @ExceptionHandler(FeignException.class)
    protected ResponseEntity<ApiResponse<Void>> handleFeignException(final FeignException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        ErrorCode.INVALID_EXTERNAL_REQUEST_DATA
                ));
    }

    /**
     * picky panda custom Error
     */
    @ExceptionHandler(PickyPandaException.class)
    protected ResponseEntity<ApiResponse<Void>> handlePickyPandaException(final PickyPandaException exception) {
        return new ResponseEntity<>(
                ApiResponse.error(exception.getErrorCode()),
                exception.getErrorCode().getCode()
        );
    }
}
