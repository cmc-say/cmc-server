package cmc.config;

import cmc.jwt.filter.JwtAuthenticationFilter;
import cmc.jwt.token.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] GET_PERMITTED_URLS = {
    };

    private static final String[] POST_PERMITTED_URLS = {
            "/**/auth/login",
            "/**/auth/reissue"
    };

    private static final String[] DOCS_PERMITTED_URLS = {
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    private final JwtProvider jwtProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // basic authentication
        http.httpBasic().disable(); // basic authentication filter 비활성화
        // csrf
        http.csrf().disable();
        // remember-me
        http.rememberMe().disable();
        // stateless
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // authorization
        http.authorizeRequests()
                .antMatchers(GET_PERMITTED_URLS).permitAll()
                .antMatchers(POST_PERMITTED_URLS).permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .anyRequest().authenticated();
        // jwt filter
        http.addFilterBefore(
                new JwtAuthenticationFilter(jwtProvider),
                UsernamePasswordAuthenticationFilter.class
        );
        ;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(DOCS_PERMITTED_URLS);
    }

}
