package io.picky.panda.restaurant.ui.dto;


import io.picky.panda.restaurant.domain.RestaurantCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record RestaurantRequest(

        MultipartFile image,

        @NotBlank
        String placeName,

        @NotBlank
        String address,

        @NotNull
        Double latitude,

        @NotNull
        Double longitude,

        @NotNull
        RestaurantCategory category,

        @NotBlank
        String options,

        String description
) {
}
