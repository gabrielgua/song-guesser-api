package com.gabriel.hksongguesser.api.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    public @interface Recursos {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@authorizationConfig.podeConsultar")
        public @interface podeConsultar {}

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@authorizationConfig.podeGerenciar")
        public @interface podeGerenciar {}

    }

    public @interface Perguntas {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasRole('ROLE_ANONYMOUS') or hasAuthority('ADMIN')")
        public @interface podeGerar {}


        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasRole('ROLE_ANONYMOUS') or hasAuthority('ADMIN')")
        public @interface podeResponder {}


    }
}
