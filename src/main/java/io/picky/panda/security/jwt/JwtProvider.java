package io.picky.panda.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.picky.panda.auth.domain.Role;
import io.picky.panda.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class JwtProvider implements InitializingBean {

    private final CustomUserDetailsService userDetailsService;

    private static final String AUTHORITIES_KEY = "auth";
    private static final String TOKEN_TYPE_KEY = "type";
    private static final String ACCESS_TOKEN_TYPE = "access";
    private static final String REFRESH_TOKEN_TYPE = "refresh";

    private final String secretCode;
    private final Long accessTokenExpiration;
    private final Long refreshTokenExpiration;

    private final RedisTemplate<String, String> redisTemplate;

    private Key key;

    public JwtProvider(
            CustomUserDetailsService userDetailsService,
            @Value("${jwt.secret}") String secretCode,
            @Value("${jwt.access-expiration}") Long accessTokenExpiration,
            @Value("${jwt.refresh-expiration}") Long refreshTokenExpiration,
            RedisTemplate<String, String> redisTemplate
    ) {
        this.userDetailsService = userDetailsService;
        this.secretCode = secretCode;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void afterPropertiesSet() {

        byte[] keyBytes = Decoders.BASE64.decode(secretCode);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public Jwt getUserJwt(String principle) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principle, null, List.of(new SimpleGrantedAuthority(Role.USER.name())));
        return createToken(authenticationToken);
    }

    public Jwt createToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String accessToken = generateToken(authentication.getName(), authorities, ACCESS_TOKEN_TYPE, accessTokenExpiration);
        String refreshToken = generateToken(authentication.getName(), authorities, REFRESH_TOKEN_TYPE, refreshTokenExpiration);

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(authentication.getName(), refreshToken, refreshTokenExpiration, TimeUnit.SECONDS);

        return Jwt.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String token) {

        Claims claims = parseClaims(token);

        if (claims.get("auth") == null || claims.get("type") == null) {
            throw new RuntimeException("정보가 누락된 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        UserDetails principal = userDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private String generateToken(String subject, String authorities, String tokenType, Long expiration) {

        long now = (new Date()).getTime();
        Date expirationDate = new Date(now + expiration * 1000);

        return Jwts.builder()
                .setSubject(subject)
                .claim(AUTHORITIES_KEY, authorities)
                .claim(TOKEN_TYPE_KEY, tokenType)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expirationDate)
                .compact();
    }

    public boolean validateToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return true;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
