package br.insper.projeto.historico.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Entity
@Document(collection = "prova")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String nome;

    private String detalhes;






}
