package io.picky.panda.review.ui;

import io.picky.panda.auth.domain.User;
import io.picky.panda.common.dto.ApiResponse;
import io.picky.panda.exception.SuccessCode;
import io.picky.panda.review.application.ReviewService;
import io.picky.panda.review.ui.dto.ReviewRequest;
import io.picky.panda.review.ui.dto.ReviewResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{restaurantId}")
    public ResponseEntity<ApiResponse<Void>> registerReview(
            @AuthenticationPrincipal User user,
            @PathVariable Long restaurantId,
            @RequestBody @Valid final ReviewRequest request
    ) {

        reviewService.registerReview(user.getId(), restaurantId, request.content());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        SuccessCode.SAVE_REVIEW_SUCCESS
                ));
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> getAllReview(
            @AuthenticationPrincipal User user,
            @PathVariable Long restaurantId
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        SuccessCode.GET_ALL_REVIEW_SUCCESS,
                        reviewService.getAllReview(user.getId(), restaurantId)
                ));
    }

    @DeleteMapping("/{restaurantId}/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @AuthenticationPrincipal User user,
            @PathVariable Long restaurantId,
            @PathVariable Long reviewId
    ) {

        reviewService.deleteReview(user.getId(), restaurantId, reviewId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        SuccessCode.DELETE_REVIEW_SUCCESS
                ));
    }
}
