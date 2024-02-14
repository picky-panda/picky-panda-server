package io.picky.panda.review.ui.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReviewResponseDto(

        Long id,
        Long writerId,
        String content,
        LocalDateTime createdAt
) {
}
