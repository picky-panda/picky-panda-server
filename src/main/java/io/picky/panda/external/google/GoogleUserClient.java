package io.picky.panda.external.google;

import io.picky.panda.external.google.dto.GoogleProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googleUserClient", url = "https://oauth2.googleapis.com")
public interface GoogleUserClient {

    @GetMapping("/tokeninfo")
    GoogleProfileResponse getProfile(@RequestParam(name = "id_token") String idToken);
}
