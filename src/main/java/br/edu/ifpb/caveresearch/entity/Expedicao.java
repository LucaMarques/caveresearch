package br.edu.ifpb.caveresearch.entity;

import br.edu.ifpb.caveresearch.enums.SituacaoExpedicao;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_expedicao")
public class Expedicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_expedicao", nullable = false)
    private Long idExpedicao;

    @Column(name = "codigo_expedicao", nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "objetivo", nullable = false, length = 500)
    private String objetivo;

    @Column(name = "inicio_previsto", nullable = false)
    private LocalDateTime inicioPrevisto;

    @Column(name = "termino_previsto", nullable = false)
    private LocalDateTime terminoPrevisto;

    @Column(name = "orcamento_aprovado", nullable = false, precision = 10, scale = 2)
    private BigDecimal orcamentoAprovado;

    @Column(name = "custo_realizado", nullable = false, precision = 10, scale = 2)
    private BigDecimal custoRealizado;

    @Column(name = "quantidade_maxima_participantes", nullable = false)
    private Integer quantidadeMaximaParticipantes;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false)
    private SituacaoExpedicao situacao;

    @Column(name = "cancelamento_emergencial", nullable = false)
    private boolean cancelamentoEmergencial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caverna", nullable = false)
    private Caverna caverna;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tb_expedicao_setor_pesquisa",
            joinColumns = @JoinColumn(name = "id_expedicao"),
            inverseJoinColumns = @JoinColumn(name = "id_setor")
    )
    private List<SetorPesquisa> setores = new ArrayList<>();

    @OneToOne(mappedBy = "expedicao", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private PlanoSeguranca planoSeguranca;

    @OneToOne(mappedBy = "expedicao", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private AutorizacaoAmbiental autorizacaoAmbiental;

    @OneToOne(mappedBy = "expedicao", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private RelatorioFinal relatorioFinal;

    @OneToMany(mappedBy = "expedicao", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParticipacaoExpedicao> participacoes = new ArrayList<>();

    @OneToMany(mappedBy = "expedicao", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ColetaCientifica> coletas = new ArrayList<>();
}
