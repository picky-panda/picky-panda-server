package io.picky.panda.auth.application;

import io.picky.panda.auth.domain.Role;
import io.picky.panda.auth.domain.User;
import io.picky.panda.auth.infrastructure.UserRepository;
import io.picky.panda.auth.ui.dto.LoginResponse;
import io.picky.panda.external.google.GoogleClientService;
import io.picky.panda.external.google.GoogleUserClient;
import io.picky.panda.external.google.dto.GoogleProfileResponse;
import io.picky.panda.security.jwt.Jwt;
import io.picky.panda.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GoogleClientService googleClientService;

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    @Transactional
    public LoginResponse googleLogin(String accessToken) {
        GoogleProfileResponse profile = googleClientService.getUserInfo(accessToken);

        User user = userRepository.findByEmail(profile.email())
                .orElseGet(() -> userRepository.save(User.builder()
                        .socialId(profile.id())
                        .email(profile.email())
                        .profileUrl(profile.picture())
                        .roles(Role.USER.name())
                        .build()
                ));

        Jwt jwt = jwtProvider.getUserJwt(user.getSocialId());

        return LoginResponse.builder()
                .socialId(user.getSocialId())
                .accessToken(jwt.accessToken())
                .refreshToken(jwt.refreshToken())
                .build();
    }
}
