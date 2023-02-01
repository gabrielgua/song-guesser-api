package com.gabriel.hksongguesser.api.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlternativaRequest {

    @NotBlank
    private String nome;

    @NotNull
    private Long musicaId;
}
