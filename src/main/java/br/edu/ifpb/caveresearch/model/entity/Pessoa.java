package br.edu.ifpb.caveresearch.model.entity;

import br.edu.ifpb.caveresearch.model.embeddable.Endereco;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_pessoa")
public abstract class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pessoa", nullable = false)
    private Long idPessoa;

    @Column(name = "nome_pessoa", nullable = false, length = 100)
    private String nome;

    @Column(name = "cpf_pessoa", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "dt_nascimento_pessoa", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "email_pessoa", nullable = false, length = 254)
    private String email;

    @Column(name = "telefone_pessoa", nullable = false, length = 20)
    private String telefone;

    @Column(name = "sit_ativa", nullable = false)
    private boolean situacaoAtiva;

    @Embedded
    private Endereco endereco;

    @Builder.Default
    @OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY)
    private List<ParticipacaoExpedicao> participacoes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "pessoaResponsavel", fetch = FetchType.LAZY)
    private List<MovimentacaoEquipamento> movimentacoesEquipamento = new ArrayList<>();

    public void addParticipacao(ParticipacaoExpedicao participacao) {
        if (participacao != null && this.participacoes != null) {
            this.participacoes.add(participacao);
            participacao.setPessoa(this);
        }
    }

    public void addMovimentacaoEquipamento(MovimentacaoEquipamento movimentacao) {
        if (movimentacao != null && this.movimentacoesEquipamento != null) {
            this.movimentacoesEquipamento.add(movimentacao);
            movimentacao.setPessoaResponsavel(this);
        }
    }
}
