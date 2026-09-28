package br.edu.ifpb.caveresearch.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_movimentacao_equipamento")
public class MovimentacaoEquipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimentacao", nullable = false)
    private Long idMovimentacao;

    @Column(name = "data_hora_retirada", nullable = false)
    private LocalDateTime dataHoraRetirada;

    @Column(name = "data_hora_devolucao_prevista", nullable = false)
    private LocalDateTime dataHoraDevolucaoPrevista;

    @Column(name = "data_hora_devolucao_efetiva")
    private LocalDateTime dataHoraDevolucaoEfetiva;

    @Column(name = "estado_saida", nullable = false, length = 500)
    private String estadoSaida;

    @Column(name = "estado_retorno", length = 500)
    private String estadoRetorno;

    @Column(name = "custo_avaria", precision = 10, scale = 2)
    private BigDecimal custoAvaria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_equipamento", nullable = false)
    private Equipamento equipamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_expedicao", nullable = false)
    private Expedicao expedicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pessoa_responsavel", nullable = false)
    private Pessoa pessoaResponsavel;
}
