package br.edu.ifpb.caveresearch.entity;

import br.edu.ifpb.caveresearch.enums.PapelParticipante;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_participacao_expedicao", uniqueConstraints = @UniqueConstraint(name = "uk_participacao_expedicao_pessoa_expedicao", columnNames = {"id_pessoa", "id_expedicao"}))
public class ParticipacaoExpedicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_participacao_expedicao", nullable = false)
    private Long idParticipacaoExpedicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_expedicao", nullable = false)
    private Expedicao expedicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pessoa", nullable = false)
    private Pessoa pessoa;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel", nullable = false)
    private PapelParticipante papel;

    @Column(name = "data_confirmacao", nullable = false)
    private LocalDate dataConfirmacao;

    @Column(name = "valor_diaria", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorDiaria;

    @Column(name = "quantidade_prevista_dias", nullable = false)
    private Integer quantidadePrevistaDias;

    @Column(name = "presenca_confirmada", nullable = false)
    private boolean presencaConfirmada;

    @Column(name = "observacoes", length = 500)
    private String observacoes;
}
