package com.gabriel.hksongguesser.api.controller;

import com.gabriel.hksongguesser.api.assembler.MusicaAssembler;
import com.gabriel.hksongguesser.api.domain.model.MusicaCompletaModel;
import com.gabriel.hksongguesser.api.domain.model.MusicaModel;
import com.gabriel.hksongguesser.api.domain.request.MusicaRequest;
import com.gabriel.hksongguesser.domain.service.ArquivoMusicaService;
import com.gabriel.hksongguesser.domain.service.MusicaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("musicas")
@AllArgsConstructor

public class MusicaController {

    private MusicaService service;
    private ArquivoMusicaService arquivoService;
    private MusicaAssembler assembler;

    @GetMapping
    public List<MusicaModel> listar() {
        return assembler.toCollectionModel(service.buscarTodos());
    }

    @GetMapping(params = "view=completa")
    public List<MusicaCompletaModel> listarCompleto() {
        return assembler.toCollectionCompletaModel(service.buscarTodos());
    }

    @GetMapping("/{musicaId}")
    public MusicaModel buscarPorId(@PathVariable Long musicaId) {
        return assembler.toModel(service.buscarPorId(musicaId));
    }

    @PostMapping
    public MusicaModel salvar(@RequestBody MusicaRequest request) {
        var musica = assembler.toEntity(request);
        return assembler.toModel(service.salvar(musica));
    }

    @PutMapping("/{musicaId}")
    public MusicaCompletaModel editar(@PathVariable Long musicaId, @RequestBody MusicaRequest request) {
        var musica = service.buscarPorId(musicaId);
        assembler.copyToEntity(request, musica);
        return assembler.toCompletaModel(service.salvar(musica));
    }

    @DeleteMapping("/{musicaId}")
    public void remover(@PathVariable Long musicaId) {
        var musica = service.buscarPorId(musicaId);
        service.remover(musica);

    }

}
