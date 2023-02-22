package cmc.jwt.filter;

import cmc.jwt.token.JwtToken;
import cmc.jwt.token.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)  throws ServletException, IOException {

        JwtToken jwtToken = tokenProvider.createAccessToken("1");
        Authentication authentication = tokenProvider.getAuthentication(jwtToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

//        String tokenStr = HeaderUtil.getAccessToken(request);
//        JwtToken token = tokenProvider.convertToJwtToken(tokenStr);
//
//        if (token.validate()) {
//            Authentication authentication = tokenProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }

        filterChain.doFilter(request, response);
    }

}
