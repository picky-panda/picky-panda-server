package io.picky.panda.auth.ui;

import io.picky.panda.auth.application.AuthService;
import io.picky.panda.auth.ui.dto.GoogleLoginRequest;
import io.picky.panda.auth.ui.dto.LoginResponse;
import io.picky.panda.common.dto.ApiResponse;
import io.picky.panda.exception.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/google/login")
    public ResponseEntity<ApiResponse<LoginResponse>> googleLogin(@RequestBody @Valid final GoogleLoginRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        SuccessCode.GOOGLE_LOGIN_SUCCESS,
                        authService.googleLogin(request.idToken())
                ));
    }
}
