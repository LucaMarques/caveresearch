package br.edu.ifpb.caveresearch.model.entity;

import br.edu.ifpb.caveresearch.model.enums.SituacaoValidacaoColeta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "tb_coleta_cientifica")
public class ColetaCientifica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coleta_cientifica")
    private Long idColeta;

    @Column(name = "data_hora_coleta", nullable = false)
    private LocalDateTime dataHoraColeta;

    @Column(name = "metodo_empregado", nullable = false, length = 100)
    private String metodoEmpregado;

    @Column(name = "descricao_ponto", nullable = false, length = 255)
    private String descricaoPonto;

    @Column(name = "temperatura", precision = 6, scale = 2)
    private BigDecimal temperatura;

    @Column(name = "umidade_relativa", precision = 5, scale = 2)
    private BigDecimal umidadeRelativa;

    @Column(name = "profundidade", precision = 10, scale = 2)
    private BigDecimal profundidade;

    @Column(name = "observacoes", length = 500)
    private String observacoes;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_validacao", nullable = false, length = 30)
    private SituacaoValidacaoColeta situacaoDeValidacao;

    @OneToMany(mappedBy = "coleta", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Amostra> amostras = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pessoa", foreignKey = @ForeignKey(name = "fk_coleta_pesquisador"), nullable = false)
    private Pesquisador pesquisadorResponsavel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_setor", foreignKey = @ForeignKey(name = "fk_coleta_setor_pesquisa"), nullable = false)
    private SetorPesquisa setor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_expedicao", foreignKey = @ForeignKey(name = "fk_coleta_expedicao"), nullable = false)
    private Expedicao expedicao;

    public void addAmostra(Amostra amostra) {
        if (amostra != null && this.amostras != null) {
            this.amostras.add(amostra);
            amostra.setColeta(this);
        }
    }
}
