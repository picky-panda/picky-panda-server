package io.picky.panda.auth.ui.dto;


import jakarta.validation.constraints.NotBlank;

public record GoogleLoginRequest(

        @NotBlank
        String idToken
) {
}
