package com.gabriel.hksongguesser.api.security.authorizationserver;

import com.gabriel.hksongguesser.api.security.AuthProperties;
import com.gabriel.hksongguesser.domain.repository.UsuarioRepository;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyStore;
import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Configuration
public class AuthorizationServerConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        return http.formLogin(customizer -> customizer.loginPage("/login")).build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder, AuthProperties properties) {
        var angularClient = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId(properties.getAngular().getClientId())
                .clientSecret(passwordEncoder.encode(properties.getAngular().getClientSecret()))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUris(uris -> uris.addAll(properties.getRedirectsPermitidos()))
                .scope("READ")
                .scope("WRITE")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(30))
                        .refreshTokenTimeToLive(Duration.ofDays(10))
                        .reuseRefreshTokens(false)
                        .build())
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(false)
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(Collections.singletonList(angularClient));
    };

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtEncodingContextOAuth2TokenCustomizer(UsuarioRepository repository) {
        return (context -> {
            var authentication = context.getPrincipal();
            if (authentication.getPrincipal() instanceof User user) {
                var usuario = repository.findByUsername(user.getUsername()).orElseThrow();

                Set<String> authorities = new HashSet<>();
                for (GrantedAuthority authority : user.getAuthorities()) {
                    authorities.add(authority.toString());
                }

                context.getClaims().claim("usuario_id", usuario.getId().toString());
                context.getClaims().claim("authorities", authorities);
            }
        });
    }

    @Bean
    public AuthorizationServerSettings providerSettings(AuthProperties properties) {
        return AuthorizationServerSettings.builder()
                .issuer(properties.getProviderUrl())
                .build();
    }

    @Bean
    public JWKSet jwkSet(AuthProperties properties) throws Exception {
        final var jksPath = properties.getJks().getPath();
        final var inputStream = new ClassPathResource(jksPath).getInputStream();

        final var keyStore = KeyStore.getInstance("JKS");
        keyStore.load(inputStream, properties.getJks().getStorepass().toCharArray());

        RSAKey rsaKey = RSAKey.load(keyStore,
                properties.getJks().getAlias(),
                properties.getJks().getKeypass().toCharArray());

        return new JWKSet(rsaKey);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(JWKSet jwkSet) {
        return (((jwkSelector, securityContext) -> jwkSelector.select(jwkSet)));
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

}
