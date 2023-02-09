package com.gabriel.hksongguesser.api.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Component
@Getter
@Setter
@Validated
@ConfigurationProperties("song-guesser.security")
public class AuthProperties {


    @NotBlank
    private String providerUrl;
    @NotNull
    private List<String> redirectsPermitidos;

    @NotNull
    private Angular angular;

    @NotNull
    private JksProperties jks;

    @Getter
    @Setter
    @Validated
    public static class Angular {
        @NotBlank
        private String clientId;
        @NotBlank
        private String clientSecret;
    }

    @Getter
    @Setter
    @Validated
    public static class JksProperties {
        @NotBlank
        private String keypass;

        @NotBlank
        private String storepass;

        @NotBlank
        private String alias;

        @NotBlank
        private String path;
    }

}


