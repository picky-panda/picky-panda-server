package io.picky.panda.security.service;

import io.picky.panda.auth.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String socialId) throws UsernameNotFoundException {
        return userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 socialId 입니다.: " + socialId));
    }
}
