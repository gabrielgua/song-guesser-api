package com.gabriel.hksongguesser.api.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicaRequest {

    @NotBlank
    private String nome;
}
