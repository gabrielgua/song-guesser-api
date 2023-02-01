package com.gabriel.hksongguesser.api.domain.request;

import com.gabriel.hksongguesser.domain.model.Alternativa;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespostaRequest {

    @NotNull
    private Long musicaId;
    @NotNull
    private Long alternativaId;

}
