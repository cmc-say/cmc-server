package cmc.jwt.token;

import cmc.error.exception.ErrorCode;
import cmc.error.exception.BusinessException;
import io.jsonwebtoken.io.Decoders;
import lombok.extern.slf4j.Slf4j;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class JwtProvider {
    private final long accessTokenValidity;
    private final long refreshTokenValidity;
    private final Key key;

    public JwtProvider(
            @Value("${jwt.access-token-validity}") long accessTokenValidity,
            @Value("${jwt.refresh-token-validity}") long refreshTokenValidity,
            @Value("${jwt.secret}") String secret
    ) {
        this.accessTokenValidity = accessTokenValidity * 1000;
        this.refreshTokenValidity = refreshTokenValidity * 1000;
        this.key =  Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));;
    }

    public JwtToken createAccessToken(String id) {
        return new JwtToken(id, accessTokenValidity, key);
    }

    public JwtToken createRefreshToken(String id, Key key) {
        return new JwtToken(id, refreshTokenValidity, key);
    }

    public JwtToken convertToJwtToken(String token) {
        return new JwtToken(key, token);
    }

    public Authentication getAuthentication(JwtToken jwtToken) {

        if(jwtToken.validate()) {

            Claims claims = jwtToken.getTokenClaims();

            Collection<? extends GrantedAuthority> authorities =
                    List.of( new SimpleGrantedAuthority("USER"));
//                    Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
//                            .map(SimpleGrantedAuthority::new)
//                            .collect(Collectors.toList());

            return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
        } else {
            throw new BusinessException(ErrorCode.TOKEN_INVALID_EXCEPTION);
        }
    }

}