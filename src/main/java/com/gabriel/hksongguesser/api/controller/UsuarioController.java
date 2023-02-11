package com.gabriel.hksongguesser.api.controller;

import com.gabriel.hksongguesser.api.assembler.UsuarioAssembler;
import com.gabriel.hksongguesser.api.domain.model.UsuarioModel;
import com.gabriel.hksongguesser.api.security.CheckSecurity;
import com.gabriel.hksongguesser.domain.exception.UsuarioNaoEncontradoException;
import com.gabriel.hksongguesser.domain.model.Usuario;
import com.gabriel.hksongguesser.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("usuarios")
public class UsuarioController {

    private UsuarioRepository repository;
    private UsuarioAssembler assembler;

    @GetMapping("/{usuarioId}")
    @CheckSecurity.Recursos.podeConsultar
    public UsuarioModel buscarPorId(@PathVariable Long usuarioId) {
        var usuario = repository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
        return assembler.toModel(usuario);
    }
}
