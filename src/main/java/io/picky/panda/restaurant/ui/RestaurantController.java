package io.picky.panda.restaurant.ui;

import io.picky.panda.auth.domain.User;
import io.picky.panda.common.dto.ApiResponse;
import io.picky.panda.exception.SuccessCode;
import io.picky.panda.restaurant.application.RestaurantService;
import io.picky.panda.restaurant.ui.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{restaurantId}")
    public ResponseEntity<ApiResponse<RestaurantResponse>> getRestaurant(
            @AuthenticationPrincipal User user,
            @PathVariable Long restaurantId
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        SuccessCode.GET_RESTAURANT_SUCCESS,
                        restaurantService.getRestaurant(user.getId(), restaurantId)
                ));
    }

    @PostMapping("/save/{restaurantId}")
    public ResponseEntity<ApiResponse<Void>> saveRestaurant(
            @AuthenticationPrincipal User user,
            @PathVariable Long restaurantId,
            @RequestBody @Valid final SaveRestaurantRequest request
    ) {

        restaurantService.bookmarkRestaurant(user.getId(), restaurantId, request.status());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        SuccessCode.BOOKMARK_RESTAURANT_SUCCESS
                ));
    }

    @PostMapping("/{descriptionId}")
    public ResponseEntity<ApiResponse<Void>> agreeDescription(
            @AuthenticationPrincipal User user,
            @PathVariable Long descriptionId,
            @RequestBody @Valid final AgreeDescriptionRequest request
    ) {

        restaurantService.agreeDescription(user.getId(), descriptionId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        SuccessCode.AGREE_DESCRIPTION_SUCCESS
                ));
    }

    @PostMapping("/description/{restaurantId}")
    public ResponseEntity<ApiResponse<Void>> registerDescription(
            @AuthenticationPrincipal User user,
            @PathVariable Long restaurantId,
            @RequestBody @Valid final DescriptionRequest request
            ) {

        restaurantService.saveDescription(user.getId(), restaurantId, request.description());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        SuccessCode.SAVE_DESCRIPTION_SUCCESS
                ));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<RestaurantMapResponse>>> getRestaurantList(
            @AuthenticationPrincipal User user,
            @RequestParam Long northEastX,
            @RequestParam Long northEastY,
            @RequestParam Long southWestX,
            @RequestParam Long southWestY
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        SuccessCode.GET_RESTAURANT_LIST_SUCCESS,
                        restaurantService.getRestaurantList(user.getId(), northEastX, northEastY, southWestX, southWestY)
                ));
    }
}
