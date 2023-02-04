package com.gabriel.hksongguesser.api.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlternativaResumoRequest {

    @NotBlank
    private String nome;
}
