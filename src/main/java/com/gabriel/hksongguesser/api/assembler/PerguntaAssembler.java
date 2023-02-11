package com.gabriel.hksongguesser.api.assembler;

import com.gabriel.hksongguesser.api.domain.model.PerguntaHardModel;
import com.gabriel.hksongguesser.api.domain.model.PerguntaModel;
import com.gabriel.hksongguesser.domain.model.Pergunta;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PerguntaAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PerguntaModel toModel(Pergunta pergunta) {
        return modelMapper.map(pergunta, PerguntaModel.class);
    }


    public List<PerguntaModel> toCollectionList(List<Pergunta> perguntas) {
        return perguntas.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

}
