package br.edu.ifpb.caveresearch.entity;

import br.edu.ifpb.caveresearch.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_setor_pesquisa")
public class SetorPesquisa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_setor", nullable = false)
    private Long idSetor;

    @Column(name = "denominacao", nullable = false)
    private String denominacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "dificuldade", nullable = false)
    private NivelDificuldadeSetor dificuldade;

    @Column(name = "profundidade_maxima", precision = 10, scale = 2)
    private BigDecimal profundidadeMaxima;

    @Column(name = "extensao_aproximada", nullable = false , precision = 10, scale = 2)
    private BigDecimal extensaoAproximada;

    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;

    @Column(name = "risco_inundacao", nullable = false)
    private Boolean riscoInundacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_setor",  nullable = false)
    private SituacaoSetor situacaoSetor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caverna", nullable = false)
    private Caverna caverna;

    @ManyToMany(mappedBy = "setores", fetch = FetchType.LAZY)
    private List<Expedicao> expedicoes = new ArrayList<>();

    @OneToMany(mappedBy = "setor", fetch = FetchType.LAZY)
    private List<ColetaCientifica> coletas = new ArrayList<>();
}
