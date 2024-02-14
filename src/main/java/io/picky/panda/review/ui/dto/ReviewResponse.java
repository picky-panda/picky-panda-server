package io.picky.panda.review.ui.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ReviewResponse(

        Long userId,
        List<ReviewResponseDto> reviewList
) {
}
