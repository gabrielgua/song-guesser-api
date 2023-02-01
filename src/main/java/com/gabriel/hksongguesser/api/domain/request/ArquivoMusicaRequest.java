package com.gabriel.hksongguesser.api.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ArquivoMusicaRequest {

    @NotNull
    private MultipartFile arquivo;

}
