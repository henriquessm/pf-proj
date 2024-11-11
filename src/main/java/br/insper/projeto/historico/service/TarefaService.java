package br.insper.projeto.historico.service;

import br.insper.projeto.historico.dto.UsuarioDTO;

import br.insper.projeto.historico.model.Tarefa;
import br.insper.projeto.historico.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    private UsuarioDTO achaUsuario(String jwtToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "http://184.72.80.215/usuario/validate";

        try {
            ResponseEntity<UsuarioDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    UsuarioDTO.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                UsuarioDTO planoUsuario = response.getBody();
                    return planoUsuario;
            }
            else {
                throw new RuntimeException("Usuário não encontrado. Status code: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erro de cliente ao verificar o papel do usuário: " + e.getStatusCode(), e);
        } catch (HttpServerErrorException e) {
            throw new RuntimeException("Erro de servidor ao verificar o papel do usuário: " + e.getStatusCode(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar papel do usuário", e);
        }
    }



    public List<Tarefa> listarTarefa(String jwtToken) {

        // Obtendo o email a partir do token JWT
        String email = achaUsuario(jwtToken) != null ? achaUsuario(jwtToken).getEmail() : null;

        if(!achaUsuario(jwtToken).getPapel().equals("ADMIN") && !achaUsuario(jwtToken).getPapel().equals("DEVELOPER")){
            throw new RuntimeException("Você não tem permissão para criar listar as tarefas" );
        }

        if (email == null) {
            throw new IllegalArgumentException("Token JWT inválido ou usuário não encontrado.");
        }

        // Buscando histórico pelo email
        List<Tarefa> tarefa = tarefaRepository.findAll();

        return tarefa;
    }
    public Tarefa listarTarefaId(String jwtToken, String id) {

        // Obtendo o email a partir do token JWT
        String email = achaUsuario(jwtToken) != null ? achaUsuario(jwtToken).getEmail() : null;

        if(!achaUsuario(jwtToken).getPapel().equals("ADMIN") && !achaUsuario(jwtToken).getPapel().equals("DEVELOPER")){
            throw new RuntimeException("Você não tem permissão para listar as tarefas" );
        }

        if (email == null) {
            throw new IllegalArgumentException("Token JWT inválido ou usuário não encontrado.");
        }

        // Buscando histórico pelo email
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);

        if (tarefa.isEmpty()) {
            throw new RuntimeException("Tarefa não encontrada");
        }

        return tarefa.get();
    }


    public Tarefa adicionarTarefa(String jwtToken, Tarefa tarefa) {

        System.out.println(achaUsuario(jwtToken).getPapel());
        if(!achaUsuario(jwtToken).getPapel().equals("ADMIN")){
            throw new RuntimeException("Você não tem permissão para criar uma nova tarefa" );
        }



        tarefaRepository.save(tarefa);
        return tarefa;
    }

    public Tarefa deletarTarefa(String jwtToken, String id) {

        if (!achaUsuario(jwtToken).getPapel().equals("ADMIN")) {
            throw new RuntimeException("Você não tem permissão para deletar uma tarefa");
        }

        Optional<Tarefa> excluir = tarefaRepository.findById(id);

        if (excluir.isEmpty()) {
            throw new RuntimeException("Tarefa não encontrada");
        }

        tarefaRepository.delete(excluir.get());
        return excluir.get();
    }






}
