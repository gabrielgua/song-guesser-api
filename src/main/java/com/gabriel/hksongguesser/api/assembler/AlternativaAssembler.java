package com.gabriel.hksongguesser.api.assembler;

import com.gabriel.hksongguesser.api.domain.model.AlternativaModel;
import com.gabriel.hksongguesser.api.domain.request.AlternativaRequest;
import com.gabriel.hksongguesser.api.domain.request.AlternativaResumoRequest;
import com.gabriel.hksongguesser.domain.model.Alternativa;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlternativaAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public AlternativaModel toModel(Alternativa alternativa) {
        return modelMapper.map(alternativa, AlternativaModel.class);
    }

    public Alternativa toEntity(AlternativaRequest request) {
        return modelMapper.map(request, Alternativa.class);
    }

    public void copyToEntity(AlternativaResumoRequest request, Alternativa alternativa) {
        modelMapper.map(request, alternativa);
    }

    public List<AlternativaModel> toCollectionModel(List<Alternativa> alternativas) {
        return alternativas.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
