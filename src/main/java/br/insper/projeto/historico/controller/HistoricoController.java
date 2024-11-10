package br.insper.projeto.historico.controller;

import br.insper.projeto.historico.service.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/historico")
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

    @PostMapping
    public ResponseEntity<?> adicionarAoHistorico(@RequestHeader("Authorization") String jwtToken) {
        var h = historicoService.adicionarAoHistorico(jwtToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(h);
    }

    @GetMapping
    public ResponseEntity<?> listarHistorico(
            @RequestHeader("Authorization") String jwtToken) {

        var historico = historicoService.listarHistorico(jwtToken);
        return ResponseEntity.ok(historico);
    }


//    @GetMapping("/resumo")
//    public ResponseEntity<?> gerarResumoUsuario(@RequestHeader("Authorization") String jwtToken) {
//        var resumo = historicoService.gerarResumoUsuario(jwtToken);
//        return ResponseEntity.ok(resumo);
//    }

}
