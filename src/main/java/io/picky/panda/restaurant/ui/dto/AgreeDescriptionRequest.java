package io.picky.panda.restaurant.ui.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AgreeDescriptionRequest(

        @NotBlank
        String status,

        @NotNull
        Boolean isApply
) {
}
