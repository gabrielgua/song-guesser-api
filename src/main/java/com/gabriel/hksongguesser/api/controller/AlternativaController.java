package com.gabriel.hksongguesser.api.controller;

import com.gabriel.hksongguesser.api.assembler.AlternativaAssembler;
import com.gabriel.hksongguesser.api.domain.model.AlternativaModel;
import com.gabriel.hksongguesser.api.domain.request.AlternativaRequest;
import com.gabriel.hksongguesser.domain.service.AlternativaService;
import com.gabriel.hksongguesser.domain.service.MusicaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("alternativas")
@AllArgsConstructor
public class AlternativaController {

    private AlternativaService alternativaService;
    private MusicaService musicaService;
    private AlternativaAssembler assembler;

    @GetMapping
    public List<AlternativaModel> listar() {
        return assembler.toCollectionModel(alternativaService.buscarTodos());
    }

    @GetMapping("/{alternativaId}")
    public AlternativaModel buscarPorId(@PathVariable Long alternativaId) {
        return assembler.toModel(alternativaService.buscarPorId(alternativaId));
    }

    @PostMapping
    public AlternativaModel salvar(@Valid @RequestBody AlternativaRequest request) {
        var musica = musicaService.buscarPorId(request.getMusicaId());
        var alternativa = assembler.toEntity(request);
        alternativa.setMusica(musica);
        return assembler.toModel(alternativaService.adicionar(alternativa));
    }

    @DeleteMapping("/{alternativaId}")
    public void remover(@PathVariable Long alternativaId) {
        var alternativa = alternativaService.buscarPorId(alternativaId);
        alternativaService.remover(alternativa);
    }
}
