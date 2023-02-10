package com.gabriel.hksongguesser.api.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationConfig {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUsuarioId() {
        var jwt = (Jwt) getAuthentication().getPrincipal();
        return Long.valueOf(jwt.getClaim("usuario_id"));
    }

    public boolean isAutenticado() {
        return getAuthentication().isAuthenticated();
    }

    public boolean temAutorizacao(String authority) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(authority));
    }

    public boolean temEscopoLeitura() {
        return temAutorizacao("SCOPE_READ");
    }

    public boolean temEscopoEscrita() {
        return temAutorizacao("SCOPE_WRITE");
    }

    public boolean isAnonymous() {
        return temAutorizacao("ROLE_ANONYMOUS");
    }

    public boolean isAdmin() {
        return temAutorizacao("ADMIN");
    }

    public boolean podeConsultar() {
        return temEscopoLeitura() && isAdmin() && isAutenticado();
    }

    public boolean podeGerenciar() {
        return temEscopoEscrita() && isAdmin() && isAutenticado();
    }

    public boolean podeGerarPerguntas() {
        return isAnonymous() || isAdmin();
    }

    public boolean podeResponderPerguntas() {
        return isAnonymous() || isAdmin();
    }


}
