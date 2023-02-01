package com.gabriel.hksongguesser.api.assembler;

import com.gabriel.hksongguesser.api.domain.model.ArquivoMusicaCompletoModel;
import com.gabriel.hksongguesser.domain.model.ArquivoMusica;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArquivoMusicaAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ArquivoMusicaCompletoModel toModel(ArquivoMusica arquivoMusica) {
        return modelMapper.map(arquivoMusica, ArquivoMusicaCompletoModel.class);
    }
}
