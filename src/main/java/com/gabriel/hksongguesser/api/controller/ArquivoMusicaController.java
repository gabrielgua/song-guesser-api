package com.gabriel.hksongguesser.api.controller;

import com.gabriel.hksongguesser.api.assembler.ArquivoMusicaAssembler;
import com.gabriel.hksongguesser.api.domain.model.ArquivoMusicaCompletoModel;
import com.gabriel.hksongguesser.api.domain.request.ArquivoMusicaRequest;
import com.gabriel.hksongguesser.api.security.CheckSecurity;
import com.gabriel.hksongguesser.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.hksongguesser.domain.model.ArquivoMusica;
import com.gabriel.hksongguesser.domain.service.ArquivoMusicaService;
import com.gabriel.hksongguesser.domain.service.MusicaArquivoStorageService;
import com.gabriel.hksongguesser.domain.service.MusicaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.http.MediaType.parseMediaType;

@RestController
@AllArgsConstructor
@RequestMapping("musicas/{musicaId}/arquivo")
public class ArquivoMusicaController {

    private MusicaService musicaService;
    private ArquivoMusicaService arquivoMusicaService;
    private MusicaArquivoStorageService storageService;
    private ArquivoMusicaAssembler assembler;


    @GetMapping(produces = MediaType.ALL_VALUE)
    @CheckSecurity.Recursos.podeConsultar
    public ResponseEntity<?> servirArquivo(@PathVariable Long musicaId) {
        try {
            var arquivoMusica = arquivoMusicaService.buscarPorId(musicaId);

            var arquivoRecuperado = storageService.recuperarArquivo(arquivoMusica.getNomeArquivo());
            var contentType = parseMediaType(arquivoMusica.getContentType());

            return ResponseEntity.ok()
                    .contentType(contentType)
                    .body(new InputStreamResource(arquivoRecuperado.getInputStream()));

        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @CheckSecurity.Recursos.podeGerenciar
    public ArquivoMusicaCompletoModel novoArquivo(@PathVariable Long musicaId, @Valid ArquivoMusicaRequest request) throws IOException {

        var musica = musicaService.buscarPorId(musicaId);
        var arquivo = request.getArquivo();

        var novoArquivoMusica = new ArquivoMusica();
        novoArquivoMusica.setMusica(musica);
        novoArquivoMusica.setNomeArquivo(arquivo.getOriginalFilename());
        novoArquivoMusica.setTamanho(arquivo.getSize());
        novoArquivoMusica.setContentType(arquivo.getContentType());

        return assembler.toModel(arquivoMusicaService.salvar(novoArquivoMusica, arquivo.getInputStream()));
    }



}
