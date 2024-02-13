package io.picky.panda.external.google;

import io.picky.panda.external.google.dto.GoogleProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "googleAuthClient", url = "https://www.googleapis.com")
public interface GoogleAuthClient {

    @GetMapping("/oauth2/v3/userinfo")
    GoogleProfileResponse getProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);
}
