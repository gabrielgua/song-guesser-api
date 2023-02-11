package com.gabriel.hksongguesser.api.controller;

import com.gabriel.hksongguesser.api.assembler.AlternativaAssembler;
import com.gabriel.hksongguesser.api.assembler.MusicaAssembler;
import com.gabriel.hksongguesser.api.assembler.PerguntaAssembler;
import com.gabriel.hksongguesser.api.domain.model.PerguntaHardModel;
import com.gabriel.hksongguesser.api.domain.model.PerguntaModel;
import com.gabriel.hksongguesser.api.domain.request.RespostaRequest;
import com.gabriel.hksongguesser.api.security.CheckSecurity;
import com.gabriel.hksongguesser.domain.model.Pergunta;
import com.gabriel.hksongguesser.domain.service.AlternativaService;
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
    private AlternativaService alternativaService;
    private MusicaService musicaService;
    private RespostaService respostaService;

    private PerguntaAssembler perguntaAssembler;
    private MusicaAssembler musicaAssembler;
    private AlternativaAssembler alternativaAssembler;



    @GetMapping(value = "/gerar", params = "mode=easy")
    @CheckSecurity.Perguntas.podeGerar
    public List<PerguntaModel> gerarPergunta() {
        var musicas = musicaService.buscarTodos();
        var perguntas = new ArrayList<Pergunta>();
        musicas.forEach(musica -> {
            if (musica.getArquivo() != null) {
                if (musica.getAlternativa() != null) {
                    perguntas.add(perguntaService.gerarPerguntaComAlternativasRandom(musica));
                }
            }
        });

        Collections.shuffle(perguntas);
        return perguntaAssembler.toCollectionList(perguntas);
    }

    @GetMapping(value = "/gerar", params = "mode=hard")
    @CheckSecurity.Perguntas.podeGerar
    public PerguntaHardModel gerarPerguntas() {
        var musicas = musicaService.buscarTodos();
        var alternativas = alternativaService.buscarTodos();

        Collections.shuffle(musicas);

        var perguntaModel = new PerguntaHardModel();
        perguntaModel.setMusicas(musicaAssembler.toCollectionModel(musicas));
        perguntaModel.setAlternativas(alternativaAssembler.toCollectionModel(alternativas));
        return perguntaModel;
    }


    @PostMapping("/responder")
    @CheckSecurity.Perguntas.podeResponder
    public boolean reponder(@RequestBody RespostaRequest request) {
        return respostaService.isAltenativaCorreta(request.getMusicaId(), request.getAlternativaId());
    }
}
