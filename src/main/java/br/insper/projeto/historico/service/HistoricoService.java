package br.insper.projeto.historico.service;

import br.insper.projeto.historico.dto.PlanoUsuarioDTO;

import br.insper.projeto.historico.model.Historico;
import br.insper.projeto.historico.repository.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HistoricoService {

    @Autowired
    private HistoricoRepository historicoRepository;

    private String usuarioPapel(String jwtToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // URL simplificada
        String url = "http://184.72.80.215/usuario/validate";

        try {
            ResponseEntity<PlanoUsuarioDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    PlanoUsuarioDTO.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                PlanoUsuarioDTO planoUsuario = response.getBody();


                return planoUsuario.getPapel();
            }
            else {
                throw new RuntimeException("Usuário não encontrado");
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Erro ao verificar papel do usuário", e);
        }
    }


    // função que lista todos os filmes do histórico, podendo filtrar por data, título e gênero
    public List<Historico> listarHistorico(String jwtToken) {
//        if (!usuarioTemPlanoAtivo(jwtToken)) {
//            throw new RuntimeException("Usuário não tem plano ativo");
//        }

        String email = TokenUtils.getEmailFromToken(jwtToken);

        List<Historico> historico = historicoRepository.findByEmail(email);


        return historico;
    }

    public Historico adicionarAoHistorico(String jwtToken) {
//        if (!usuarioTemPlanoAtivo(jwtToken)) {
//            throw new RuntimeException("Usuário não tem plano ativo");
//        }


        Historico historico = new Historico();
        historico.setEmail(TokenUtils.getEmailFromToken(jwtToken));
        historicoRepository.save(historico);

        return historico;
    }

//    public String gerarResumoUsuario(String jwtToken) {
////        if (!usuarioTemPlanoAtivo(jwtToken)) {
////            throw new RuntimeException("Usuário não tem plano ativo");
////        }
//
//        String email = TokenUtils.getEmailFromToken(jwtToken);
//
//        List<Historico> historico = historicoRepository.findByEmail(email);
//        int tempoTotal = 0;
//
//        Map<String, Integer> generoContagemMap = new HashMap<>();
//
//        for (Historico h : historico) {
//            tempoTotal += h.getTempoAssistido();
//
//            String genero = h.getGenero();
//            generoContagemMap.put(genero, generoContagemMap.getOrDefault(genero, 0) + 1);
//        }
//
//        // Ordenar os gêneros pelo número de filmes assistidos em ordem decrescente
//        List<Map.Entry<String, Integer>> generosOrdenados = new ArrayList<>(generoContagemMap.entrySet());
//        generosOrdenados.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
//
//        StringBuilder resumo = new StringBuilder();
//        resumo.append("Tempo total de filmes assistidos: ").append(tempoTotal).append(" minutos\n");
//        resumo.append("Gêneros mais assistidos: ");
//
//        for (Map.Entry<String, Integer> entry : generosOrdenados) {
//            resumo.append(entry.getKey()).append(" (").append(entry.getValue()).append(" filmes), ");
//        }
//
//        // Remover a última vírgula e espaço, se necessário
//        if (!generoContagemMap.isEmpty()) {
//            resumo.setLength(resumo.length() - 2);
//        }
//
//        return resumo.toString();
//    }



}
