package io.picky.panda.restaurant.ui;

import io.picky.panda.auth.domain.User;
import io.picky.panda.common.dto.ApiResponse;
import io.picky.panda.exception.SuccessCode;
import io.picky.panda.restaurant.application.RestaurantService;
import io.picky.panda.restaurant.ui.dto.RestaurantRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Void>> saveRestaurant(
            @AuthenticationPrincipal User user,
            @ModelAttribute @Valid final RestaurantRequest request
    ) {

        // TODO: 이미지 저장 및 링크 생성 로직 추가
        restaurantService.saveRestaurant(user.getId(), request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        SuccessCode.SAVE_RESTAURANT_SUCCESS
                ));
    }
}
