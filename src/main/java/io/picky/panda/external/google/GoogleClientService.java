package io.picky.panda.external.google;

import io.picky.panda.external.google.dto.GoogleProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class GoogleClientService {

    private final GoogleUserClient googleUserClient;

    public GoogleProfileResponse getUserInfo(String idToken) {
        return googleUserClient.getProfile(idToken);
    }
}
