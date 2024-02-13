package io.picky.panda.security.jwt;

import lombok.Builder;

@Builder
public record Jwt(

        String accessToken,
        String refreshToken
) {

}
