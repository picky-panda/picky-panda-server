package io.picky.panda.auth.ui.dto;

import lombok.Builder;

@Builder
public record LoginResponse(

        String socialId,
        String accessToken,
        String refreshToken
) {
}
