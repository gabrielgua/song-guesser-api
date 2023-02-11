package com.gabriel.hksongguesser.api.assembler;

import com.gabriel.hksongguesser.api.domain.model.UsuarioModel;
import com.gabriel.hksongguesser.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioModel toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioModel.class);
    }
}
