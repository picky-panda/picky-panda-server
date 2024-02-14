package io.picky.panda.review.ui.dto;

import jakarta.validation.constraints.NotBlank;

public record ReviewRequest(

        @NotBlank
        String content
) {
}
