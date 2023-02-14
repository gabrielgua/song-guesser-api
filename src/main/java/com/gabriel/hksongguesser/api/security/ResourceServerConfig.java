package com.gabriel.hksongguesser.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class ResourceServerConfig {

    private static final String[] AUTH_WHITELIST = {
            "/login", "/logout", "/oauth2/logout", "/perguntas/*", "/fonts/**", "/css/**", "/images/**" };

    @Bean
    public SecurityFilterChain authFilterChain(HttpSecurity http, AuthProperties properties) throws Exception {

        http.formLogin(customizer -> customizer.loginPage("/login"))
                .csrf().disable()
                .cors()
                .and()
                .authorizeHttpRequests(authorize -> {
                    try {
                        authorize
                                .requestMatchers(AUTH_WHITELIST).permitAll()
                                .requestMatchers(HttpMethod.GET, "/musicas/{musicaId}/arquivo").permitAll()
                                .and().authorizeHttpRequests().anyRequest().authenticated();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    };
                })
                .oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());

        http.logout(logoutConfig -> {
            logoutConfig.logoutSuccessHandler((request, response, authentication) -> {
                String returnTo = request.getParameter("returnTo");
                if (!StringUtils.hasText(returnTo)) {
                    returnTo = properties.getProviderUrl();
                }

                response.setStatus(302);
                response.sendRedirect(returnTo);
            })
            .clearAuthentication(true)
            .invalidateHttpSession(true)
            .deleteCookies();
        });


        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<String> userAuthorities = jwt.getClaimAsStringList("authorities");

            if (userAuthorities.isEmpty()) {
                userAuthorities = Collections.emptyList();
            }

            var scopesConverter = new JwtGrantedAuthoritiesConverter();
            var scopesAuthorities = scopesConverter.convert(jwt);

            scopesAuthorities
                    .addAll(userAuthorities.stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList());

            return scopesAuthorities;
        });

        return jwtConverter;
    }
}
