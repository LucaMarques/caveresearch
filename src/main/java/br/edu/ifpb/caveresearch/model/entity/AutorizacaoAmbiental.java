package br.edu.ifpb.caveresearch.model.entity;

import br.edu.ifpb.caveresearch.model.enums.SituacaoAutorizacao;
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
@Table(name = "tb_autorizacao_ambiental")
public class AutorizacaoAmbiental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autorizacao_ambiental", nullable = false)
    private Long idAutorizacaoAmbiental;

    @Column(name = "numero", nullable = false, unique = true, length = 50)
    private String numero;

    @Column(name = "orgao_emissor", nullable = false, length = 100)
    private String orgaoEmissor;

    @Column(name = "data_emissao", nullable = false)
    private LocalDate dataEmissao;

    @Column(name = "data_validade", nullable = false)
    private LocalDate dataValidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false)
    private SituacaoAutorizacao situacao;

    @Column(name = "observacoes", length = 500)
    private String observacoes;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "arquivo_pdf_assinado")
    private byte[] arquivoPdfAssinado;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_expedicao", nullable = false, unique = true)
    private Expedicao expedicao;
}
