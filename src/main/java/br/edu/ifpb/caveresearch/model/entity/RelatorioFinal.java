package br.edu.ifpb.caveresearch.model.entity;

import br.edu.ifpb.caveresearch.model.enums.SituacaoRelatorio;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_relatorio_final")
public class RelatorioFinal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_relatorio_final", nullable = false)
    private Long idRelatorioFinal;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "resumo", nullable = false, length = 500)
    private String resumo;

    @Column(name = "data_submissao", nullable = false)
    private LocalDate dataSubmissao;

    @Column(name = "numero_total_paginas", nullable = false)
    private Integer numeroTotalPaginas;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false)
    private SituacaoRelatorio situacao;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "arquivo_completo")
    private byte[] arquivoCompleto;

    @Column(name = "publicacao_autorizada", nullable = false)
    private boolean publicacaoAutorizada;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_expedicao", nullable = false, unique = true)
    private Expedicao expedicao;
}
