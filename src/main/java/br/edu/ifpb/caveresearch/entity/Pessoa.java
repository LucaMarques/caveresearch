package br.edu.ifpb.caveresearch.entity;

import br.edu.ifpb.caveresearch.embeddable.Endereco;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_pessoa")
public abstract class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pessoa", nullable = false)
    private Long idPessoa;

    @Column(name = "nome_pessoa", nullable = false, length = 50)
    private String nome;

    @Column(name = "cpf_pessoa", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "dt_nascimento_pessoa", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "email_pessoa", nullable = false)
    private String email;

    @Column(name = "telefone_pessoa", nullable = false, length = 20)
    private String telefone;

    @Column(name = "sit_ativa", nullable = false)
    private boolean situacaoAtiva;

    @Embedded
    private Endereco endereco;
}
