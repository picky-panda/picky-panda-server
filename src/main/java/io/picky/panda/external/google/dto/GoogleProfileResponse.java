package io.picky.panda.external.google.dto;

public record GoogleProfileResponse(

        String sub,
        String email,
        String picture
) {
}
