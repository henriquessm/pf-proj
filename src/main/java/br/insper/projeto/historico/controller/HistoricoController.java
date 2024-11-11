package br.insper.projeto.historico.controller;

import br.insper.projeto.historico.model.Tarefa;
import br.insper.projeto.historico.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tarefa")
public class HistoricoController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<?> adicionarTarefa(@RequestHeader("Authorization") String jwtToken, @RequestBody Tarefa tarefa) {
        var h = tarefaService.adicionarTarefa(jwtToken, tarefa);
        return ResponseEntity.status(HttpStatus.CREATED).body(h);
    }

    @GetMapping
    public ResponseEntity<?> listarTarefas(
            @RequestHeader("Authorization") String jwtToken,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo) {

        var historico = tarefaService.listarTarefa(jwtToken);
        return ResponseEntity.ok(historico);
    }

    @DeleteMapping
    public ResponseEntity<?> apagarTarefa(@RequestHeader("Authorization") String jwtToken, @RequestParam String id) {
        var h = tarefaService.deletarTarefa(jwtToken, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(h);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarTarefaId(
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable String id) {

        var historico = tarefaService.listarTarefaId(jwtToken, id);
        return ResponseEntity.ok(historico);
    }





}
