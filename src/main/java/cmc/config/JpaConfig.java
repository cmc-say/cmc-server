package cmc.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean(name = "AuditorAware")
    public AuditorAware<Long> auditorAware() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Principal principal = (Principal) authentication.getPrincipal();
        return () -> Optional.of(Long.parseLong(principal.getName()));
    }
}