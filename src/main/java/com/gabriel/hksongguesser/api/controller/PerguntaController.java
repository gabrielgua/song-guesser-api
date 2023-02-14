package com.gabriel.hksongguesser.api.controller;

import com.gabriel.hksongguesser.api.assembler.PerguntaAssembler;
import com.gabriel.hksongguesser.api.domain.model.PerguntaModel;
import com.gabriel.hksongguesser.api.domain.request.RespostaRequest;
import com.gabriel.hksongguesser.api.security.CheckSecurity;
import com.gabriel.hksongguesser.domain.model.Pergunta;
import com.gabriel.hksongguesser.domain.service.MusicaService;
import com.gabriel.hksongguesser.domain.service.PerguntaService;
import com.gabriel.hksongguesser.domain.service.RespostaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("perguntas")
public class PerguntaController {
    private PerguntaService perguntaService;
    private MusicaService musicaService;
    private RespostaService respostaService;
    private PerguntaAssembler perguntaAssembler;

    @GetMapping("/gerar")
    @CheckSecurity.Perguntas.podeGerar
    public List<PerguntaModel> gerarPergunta() {
        var musicas = musicaService.buscarTodos();
        var perguntas = new ArrayList<Pergunta>();
        musicas.forEach(musica -> {
            if (musica.getArquivo() != null) {
                if (musica.getAlternativa() != null) {
                    perguntas.add(perguntaService.gerarPergunta(musica));
                }
            }
        });

        Collections.shuffle(perguntas);
        return perguntaAssembler.toCollectionList(perguntas);
    }




    @PostMapping("/responder")
    @CheckSecurity.Perguntas.podeResponder
    public boolean reponder(@RequestBody RespostaRequest request) {
        return respostaService.isAltenativaCorreta(request.getMusicaId(), request.getAlternativaId());
    }
}
