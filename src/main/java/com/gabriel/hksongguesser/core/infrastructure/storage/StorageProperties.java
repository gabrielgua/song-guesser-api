package com.gabriel.hksongguesser.core.infrastructure.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Getter
@Setter
@Component
@ConfigurationProperties("song-guesser.storage")
public class StorageProperties {

    private Local local = new Local();

    @Getter
    @Setter
    public static class Local {
        private Path diretorioArquivos;
    }
}
