package com.gabriel.hksongguesser.api.assembler;

import com.gabriel.hksongguesser.api.domain.model.MusicaCompletaModel;
import com.gabriel.hksongguesser.api.domain.model.MusicaModel;
import com.gabriel.hksongguesser.api.domain.request.MusicaRequest;
import com.gabriel.hksongguesser.domain.model.Musica;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MusicaAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public MusicaModel toModel(Musica musica) {
        return modelMapper.map(musica, MusicaModel.class);
    }

    public MusicaCompletaModel toCompletaModel(Musica musica) {
        return modelMapper.map(musica, MusicaCompletaModel.class);
    }

    public Musica toEntity(MusicaRequest request) {
        return modelMapper.map(request, Musica.class);
    }

    public List<MusicaModel> toCollectionModel(List<Musica> musicas) {
        return musicas.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public List<MusicaCompletaModel> toCollectionCompletaModel(List<Musica> musicas) {
        return musicas.stream()
                .map(this::toCompletaModel)
                .collect(Collectors.toList());
    }
}
