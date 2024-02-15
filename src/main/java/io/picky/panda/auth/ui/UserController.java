package io.picky.panda.auth.ui;

import io.picky.panda.auth.application.UserService;
import io.picky.panda.auth.domain.User;
import io.picky.panda.auth.ui.dto.ProfileResponse;
import io.picky.panda.auth.ui.dto.RestaurantDto;
import io.picky.panda.common.dto.ApiResponse;
import io.picky.panda.exception.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(
            @AuthenticationPrincipal User user
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        SuccessCode.GET_PROFILE_SUCCESS,
                        userService.getProfile(user.getId())
                ));
    }

    @GetMapping("/profile/restaurant")
    public ResponseEntity<ApiResponse<List<RestaurantDto>>> getRecentlyEvaluated(
            @AuthenticationPrincipal User user,
            @RequestParam String section
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        SuccessCode.GET_USER_RESTAURANT_SUCCESS,
                        userService.getRecentlyEvaluated(user.getId(), section)
                ));
    }
}
