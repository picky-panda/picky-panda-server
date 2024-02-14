package io.picky.panda.restaurant.ui.dto;

import jakarta.validation.constraints.NotBlank;

public record SaveRestaurantRequest(

        @NotBlank
        String status
) {
}
